package com.escalate.activity;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.am.siriview.DrawView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.escalate.R;
import com.escalate.adapter.TopicDetailAdapter;
import com.escalate.databinding.ActivityTopicDetailBinding;
import com.escalate.model.LikeResponce;
import com.escalate.model.ReplyCommentResponse;
import com.escalate.model.TopicDetailModel;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.escalate.utils.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class TopicDetailAct extends AppCompatActivity implements View.OnClickListener {
    private ActivityTopicDetailBinding binding;

    String topicId = "";
    private final String TAG = TopicDetailAct.class.getSimpleName();
    private String statusToLike = "0";
    private String postIdGlobal = "";
    private List<TopicDetailModel.DataBean> topicDetailList;
    UserTopicBioListener profileBioListener;
    TopicDetailAdapter adapter;
    Dialog dialog;
    TextView tv_comment_timer;
    ImageView imgAudioMicGlobal;
    private String comment_audioFilePath = null;
    private File comment_audioFile = null;
    private MediaRecorder comment_mediaRecorder = null;
    private MediaPlayer comment_mediaPlayer = null;
    private long comment_startTime = 0L;

    private boolean isCommentRecording = false;
    private boolean isCommentPlayCheck = false;
    private static final int REQUEST_CODE_ASK_ALL_PERMISSIONS = 1010;
    private Handler comment_handler = null;

    private long comment_timeInMilliseconds = 0L;
    private long comment_timeSwapBuff = 0L;
    private long comment_updatedTime = 0L;
    private int comment_min, comment_sec;

    private CountDownTimer comment_timer = null;
    private long comment_elapsedTime = 0L;
    private String comment_convertedTime = "";

    //SIRI VIEW
    private SiriUpdateThread updaterThread;
    private Thread theWThread;
    private DrawView siriDView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_topic_detail);

        binding.backImgTopic.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent != null) {
            topicId = getIntent().getStringExtra("topic_id");
            RequestGenrePostbyId();
        }
        comment_handler = new Handler();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backImgTopic:
                if (adapter != null) {
                    adapter.resetAllOtherPrevious();
                }
                onBackPressed();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (adapter != null) {
            adapter.resetAllOtherPrevious();
        }
        super.onBackPressed();
    }

    // SERVICES
    private void RequestGenrePostbyId() {
        try {
            MyDialog.getInstance(TopicDetailAct.this).showDialog(TopicDetailAct.this);

            String userid = SPreferenceWriter.getInstance(TopicDetailAct.this).getString(SPreferenceKey.USERID);
            String token = SPreferenceWriter.getInstance(TopicDetailAct.this).getString(SPreferenceKey.TOKEN);

//            String url = "topicDetailList" + "/" + userid;

            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<TopicDetailModel> call = apiInterface.requestGenrePostbyId(token, userid, topicId);

            call.enqueue(new retrofit2.Callback<TopicDetailModel>() {
                @Override
                public void onResponse(@NonNull Call<TopicDetailModel> call, @NonNull Response<TopicDetailModel> response) {

                    if (response.isSuccessful()) {
                        TopicDetailModel TopicDetailModel = response.body();

                        if (TopicDetailModel != null) {
                            if (TopicDetailModel.getStatus().equalsIgnoreCase("SUCCESS")) {
                                topicDetailList = TopicDetailModel.getData();

                                if (TopicDetailModel.getData() != null &&
                                        TopicDetailModel.getData().size() > 0) {

                                    binding.tvNoDataTopic.setVisibility(View.GONE);
                                    binding.topicRecView.setVisibility(View.VISIBLE);

                                    setUpRecyclerTopic(topicDetailList);

                                } else {

                                    binding.tvNoDataTopic.setVisibility(View.VISIBLE);
                                    binding.topicRecView.setVisibility(View.GONE);
                                }

                            } else {
                                //"Login Token Expire"
                                if (TopicDetailModel.getMessage().equalsIgnoreCase("Login Token Expire")) {
                                    goToSplash();
                                }
                            }
                        }
                    } else {

                    }
                    MyDialog.getInstance(TopicDetailAct.this).hideDialog();
                }

                @Override
                public void onFailure(@NonNull Call<TopicDetailModel> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(TopicDetailAct.this).hideDialog();
                }
            });

        } catch (Exception e) {
            MyDialog.getInstance(TopicDetailAct.this).hideDialog();
            e.printStackTrace();
        }
    }


    // TODO: POSTS LIST
    private void setUpRecyclerTopic(List<TopicDetailModel.DataBean> topicDetailList) {

        final String userLoginId =
                SPreferenceWriter.getInstance(TopicDetailAct.this).getString(SPreferenceKey.USERID);

        if (topicDetailList != null && topicDetailList.size() > 0) {
            binding.topicRecView.setVisibility(View.VISIBLE);
            binding.tvNoDataTopic.setVisibility(View.GONE);

            for (int i = 0; i < topicDetailList.size(); i++) {
                TopicDetailModel.DataBean dataBean = topicDetailList.get(i);
                dataBean.setPlayPauseEnable(true);
            }

            adapter = new TopicDetailAdapter(TopicDetailAct.this, topicDetailList);

            binding.topicRecView.setLayoutManager(new LinearLayoutManager(TopicDetailAct.this));
            binding.topicRecView.setAdapter(adapter);

            //CLICKS CALLBACKS
            adapter.setListener(new TopicDetailAdapter.TopicInterface() {
                @Override
                public void onUserClick(View v, int pos) {
                    if (adapter.getArrayList() != null && adapter.getArrayList().size() > 0) {

                        TopicDetailModel.DataBean
                                bean = adapter.getArrayList().get(pos);
                        if (bean != null) {
                            String id = bean.getUser_id();
                            String post_id = bean.getPost_id();
                            String position = String.valueOf(pos);
                            //LOGIN USER CHECK
                            if (id != null && !id.isEmpty()) {
                                if (userLoginId.equalsIgnoreCase(id)) {
                                    // don't go

                                } else {
                                    //  go
                                    dispatchOtherUserAct(id, post_id, position);
                                }
                            } else {
                                //  go
                                dispatchOtherUserAct(id, post_id, position);
                            }
                        }
                    }
                }

                @Override
                public void onLikeClick(View v, int pos) {
                    if (adapter.getArrayList() != null && adapter.getArrayList().size() > 0) {

                        TopicDetailModel.DataBean
                                bean = adapter.getArrayList().get(pos);
                        if (bean != null) {
                            String post_id = bean.getPost_id();
                            String b = bean.getLike_flag();

                            if (b != null && !b.isEmpty()) {
                                if (b.equalsIgnoreCase("1")) {
                                    statusToLike = "0";
                                } else {
                                    statusToLike = "1";
                                }
                            } else {
                                statusToLike = "1";
                            }

                            postLikeServiceApi(pos, post_id, statusToLike); //service hit

                        }
                    }
                }

                @Override
                public void onCommentsClick(View v, int pos) {
                    if (adapter.getArrayList() != null && adapter.getArrayList().size() > 0) {

                        adapter.resetAllOtherPrevious();
                        TopicDetailModel.DataBean
                                bean = adapter.getArrayList().get(pos);
                        if (bean != null) {
                            String id = bean.getUser_id();
                            String post_id = bean.getPost_id();
                            String position = String.valueOf(pos);

                            String fullName = bean.getFullname();
                            String postUrl = bean.getAudio_url();
                            String postName = bean.getDescription();

                            //LOGIN USER CHECK
                            if (id != null && !id.isEmpty()) {
                                if (userLoginId.equalsIgnoreCase(id)) {
                                    //  go
                                    dispatchCommentAct(id, post_id, position, postUrl, postName, fullName);

                                } else {
                                    //  go
                                    dispatchCommentAct(id, post_id, position, postUrl, postName, fullName);
                                }
                            } else {
                                //  go
                                dispatchCommentAct(id, post_id, position, postUrl, postName, fullName);
                            }
                        }


                    }

                }


                @Override
                public void onShareClick(View v, int pos) {
                    if (adapter.getArrayList() != null && adapter.getArrayList().size() > 0) {

                        TopicDetailModel.DataBean
                                bean = adapter.getArrayList().get(pos);
                        if (bean != null) {
                            String url = bean.getAudio_url();
                            if (url != null && !url.isEmpty()) {
                                dispatchShare(url);

                            } else {
                                Toast.makeText(TopicDetailAct.this, "Url empty", Toast.LENGTH_SHORT).show();
                            }
                        }


                    }

                }

                @Override
                public void onReplyClick(View v, int pos) {
                    if (adapter.getArrayList() != null && adapter.getArrayList().size() > 0) {
                        adapter.resetAllOtherPrevious();
                        TopicDetailModel.DataBean
                                bean = adapter.getArrayList().get(pos);
                        if (bean != null) {
                            String username = bean.getUsername();
                            String user_image = bean.getUser_image();
                            String topic_name = bean.getTopic_name();
                            postIdGlobal = bean.getPost_id();

                            showDialogCommentReplyRecordAudio(false,
                                    TopicDetailAct.this, postIdGlobal); //REPLY record audio dialog

                        }
                    }
                }

                @Override
                public void onRepliesTextClick(View v, int pos) {
                    if (adapter.getArrayList() != null && adapter.getArrayList().size() > 0) {

                       /* TopicDetailModel.DataBean
                                bean = adapter.getArrayList().get(pos);
                        if (bean != null) {

                            String id = bean.getPost_id();
                            dispatchReplyAct(id);
                        }*/
                    }
                }
            });

            //POST PLAYINGS CALLBACKS
            adapter.setHomePostPlayingListener(
                    new TopicDetailAdapter.TopicDetailListener() {
                        @Override
                        public void onPostPlaying(boolean isPostPlaying) {
//                            if (isPostPlaying) {
//
//                            } else {
//
//                            }
                            Log.w(TAG,
                                    "HomePostPlayingListener : isBioPlaying :->" + isPostPlaying);

                        }
                    });

            //USER BIO CALLBACKS
            profileBioListener = new TopicDetailAct.UserTopicBioListener() {
                @Override
                public void onBioPlaying(boolean isBioPlaying) {

                    Log.w(TAG,
                            "UserProfileBioListener : isBioPlaying :->" + isBioPlaying);

                    if (isBioPlaying)
                        adapter.setDisableAll();
                    else
                        adapter.setEnableAll();
                }
            };
            adapter.setHomeBioListener(profileBioListener);

        } else {
            binding.topicRecView.setVisibility(View.GONE);
            binding.tvNoDataTopic.setVisibility(View.VISIBLE);
        }
    }

    ////////////////*********************
    /**/

    private void goToSplash() {

        SPreferenceWriter.getInstance(TopicDetailAct.this).clearPreferenceValues("");

        Toast.makeText(TopicDetailAct.this, "User already logged in some Other device.", Toast.LENGTH_SHORT).show();

        Objects.requireNonNull(TopicDetailAct.this).finishAffinity();

        Intent intent = new Intent(TopicDetailAct.this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void dispatchOtherUserAct(String userId, String postID, String pos) {
        Intent intent = new Intent(TopicDetailAct.this, OtherProfileActivity.class);
        intent.putExtra("user_id", userId);
        intent.putExtra("post_id", postID);
        intent.putExtra("position", pos);
        startActivity(intent);
    }

    private void dispatchCommentAct(String userId, String postID, String position, String postUrl, String postName, String fullName) {
        Intent intentCmt = new Intent(TopicDetailAct.this, CommentAct.class);
        intentCmt.putExtra("user_id", userId);
        intentCmt.putExtra("post_id", postID);
        intentCmt.putExtra("position", position);

        intentCmt.putExtra("post_url", postUrl);
        intentCmt.putExtra("post_name", postName);
        intentCmt.putExtra("full_name", fullName);
        startActivity(intentCmt);
    }

    private void postLikeServiceApi(final int position, String audioid, final String statusToLike) {

        try {
            MyDialog.getInstance(TopicDetailAct.this).showDialog(TopicDetailAct.this);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<LikeResponce> call = apiInterface.like_post(SPreferenceWriter.getInstance(TopicDetailAct.this).getString(SPreferenceKey.TOKEN),
                    SPreferenceWriter.getInstance(TopicDetailAct.this).getString(SPreferenceKey.USERID), audioid);
            Log.e(".....", "postLikeServiceApi: " + call.request().toString());

            call.enqueue(new retrofit2.Callback<LikeResponce>() {
                @Override
                public void onResponse(Call<LikeResponce> call, Response<LikeResponce> response) {

                    MyDialog.getInstance(TopicDetailAct.this).hideDialog();
                    if (response.isSuccessful()) {
                        LikeResponce loginResponse = response.body();

                        Log.e("onResponse", "" + loginResponse.toString());

                        String status = adapter.getArrayList().get(position).getLike_flag();
                        if (loginResponse.getStatus().equalsIgnoreCase("SUCCESS")) {

                            if (status.equalsIgnoreCase("1")) {
                                status = "0";
                            } else {
                                status = "1";
                            }
                            if (adapter != null) {

                                TopicDetailModel.DataBean model = adapter.getArrayList().get(position);

                                String b = model.getLike_flag();

                                if (b != null && !b.isEmpty()) {
                                    if (b.equalsIgnoreCase("1")) {

                                        String like_count = adapter.getArrayList().get(position).getLike_count();
                                        if (like_count != null && !like_count.isEmpty()) {

                                            int lk = Integer.parseInt(like_count);
                                            lk = lk - 1;
                                            adapter.getArrayList().get(position).setLike_count(String.valueOf(lk));
                                        }

                                    } else {

                                        String like_count = adapter.getArrayList().get(position).getLike_count();
                                        if (like_count != null && !like_count.isEmpty()) {

                                            int lk = Integer.parseInt(like_count);
                                            lk = lk + 1;
                                            adapter.getArrayList().get(position).setLike_count(String.valueOf(lk));
                                        } else {
                                            int lk = 0;
                                            lk = lk + 1;
                                            adapter.getArrayList().get(position).setLike_count(String.valueOf(lk));
                                        }

                                    }
                                }

                                model.setLike_flag(status);
                                adapter.notifyDataSetChanged();

                            } else {
                                // postListService();
                            }
                            //adapter.notifyDataSetChanged();
                        } else {

                            //Toast.makeText(TopicDetailAct.this, "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(TopicDetailAct.this, "Error!", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<LikeResponce> call, Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(TopicDetailAct.this).hideDialog();
                    String s = "";
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void dispatchShare(String url) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "Escalate");

        i.putExtra(Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(i, "Choose one"));
    }

    private void dispatchReplyAct(String audio_post_id) {
        Intent intent = new Intent(TopicDetailAct.this, ReplyActivity.class);
        intent.putExtra("audioid", audio_post_id);
        startActivityForResult(intent, 125);
    }


    // **************RECORD DIALOG
    private void showDialogCommentReplyRecordAudio(final boolean isCommenting, final Context context,
                                                   final String post_id) {

        dialog = new Dialog(context);

        String genre = SPreferenceWriter.getInstance(TopicDetailAct.this)
                .getString(SPreferenceKey.GENRE);

        String user = SPreferenceWriter.getInstance(TopicDetailAct.this)
                .getString(SPreferenceKey.LOGINUSERNAME);

        String image = SPreferenceWriter.getInstance(TopicDetailAct.this)
                .getString(SPreferenceKey.IMAGE);


        dialog.setContentView(R.layout.dialog_post_com_record);
        Objects.requireNonNull(dialog.getWindow())
                .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);


        tv_comment_timer = dialog.findViewById(R.id.tv_timerRecord);


        ((TextView) dialog.findViewById(R.id.tv_name)).setText(user);
        ((TextView) dialog.findViewById(R.id.tv_category_hash)).setText(genre);

        ImageView img_audio_mic = dialog.findViewById(R.id.img_audio_mic);


        setProfilePicSmall(
                ((ProgressBar) dialog.findViewById(R.id.progressBar)),
                image,
                (ImageView) dialog.findViewById(R.id.img_user));

        imgAudioMicGlobal = img_audio_mic;

        //CANCEL
        dialog.findViewById(R.id.img_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecordingComments();

                comment_handler.removeCallbacks(comment_up_timerRunnable);
                isCommentRecording = false;

                img_audio_mic.setImageDrawable(ContextCompat.getDrawable(TopicDetailAct.this,
                        R.drawable.create_memo_mic));

                dialog.dismiss();
            }
        });

        //AUDIO MIC
        img_audio_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isCommentRecording) {

                    doAllPermissionChecking();
                    isCommentRecording = true;

                    scaleAnimationComments(img_audio_mic);
                    img_audio_mic.setImageDrawable(ContextCompat.getDrawable(TopicDetailAct.this,
                            R.drawable.create_memo_hour));
                } else {

                    stopRecordingComments();

                    comment_handler.removeCallbacks(comment_up_timerRunnable);
                    isCommentRecording = false;

                    img_audio_mic.setImageDrawable(ContextCompat.getDrawable(TopicDetailAct.this,
                            R.drawable.create_memo_mic));

                    dialog.dismiss();

                    //POST AUDIO
                    showDialogCommentReplyPostAudio(isCommenting, context, post_id);
                }


            }
        });

        dialog.show();

    }

    // **************PLAY AND POST DIALOG
    private void showDialogCommentReplyPostAudio(final boolean isCommenting, final Context context,
                                                 final String post_id) {

        final Dialog dialog = new Dialog(context);

        String genre = SPreferenceWriter.getInstance(TopicDetailAct.this)
                .getString(SPreferenceKey.GENRE);

        String user = SPreferenceWriter.getInstance(TopicDetailAct.this)
                .getString(SPreferenceKey.LOGINUSERNAME);

        String image = SPreferenceWriter.getInstance(TopicDetailAct.this)
                .getString(SPreferenceKey.IMAGE);


        dialog.setContentView(R.layout.dialog_post_com_audio);
        Objects.requireNonNull(dialog.getWindow())
                .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        //SIRI VIEW
        siriDView = dialog.findViewById(R.id.siriDView);


        ((TextView) dialog.findViewById(R.id.tv_name)).setText(user);
        ((TextView) dialog.findViewById(R.id.tv_category_hash)).setText(genre);

        final EditText et_description = dialog.findViewById(R.id.et_description);
        final TextView tv_duration = dialog.findViewById(R.id.tv_duration);

        tv_duration.setText(comment_convertedTime);

        setProfilePicSmall(
                ((ProgressBar) dialog.findViewById(R.id.progressBar)),
                image,
                (ImageView) dialog.findViewById(R.id.img_user));


        dialog.findViewById(R.id.img_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        ImageView img_play_pause = dialog.findViewById(R.id.img_play_pause);
        img_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (comment_audioFilePath != null && !comment_audioFilePath.isEmpty()) {

                    doJustCommentsPlayStopPlay(dialog);//PLAYING
                } else {
                    Toast.makeText(context, "Empty audio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.findViewById(R.id.ll_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationPostCommentAudio(dialog)) {
                    String desc = et_description.getText().toString();
                    String time = comment_convertedTime;

                    postAudioServiceComments(isCommenting,
                            dialog,
                            post_id,
                            time,
                            desc); //SERVICE HIT..

                }
            }
        });

        dialog.show();
    }

    private boolean validationPostCommentAudio(Dialog dialog) {
        EditText et_description = dialog.findViewById(R.id.et_description);
        if (et_description.getText().toString().isEmpty()) {
            Toast.makeText(TopicDetailAct.this, "Enter description", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }

    // **************checking MULTIPLE RUNTIME PERMISSION*********************

    private void doAllPermissionChecking() {
        List<String> permissionsNeeded = new ArrayList<>();

        final List<String> permissionsList = new ArrayList<>();

        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsNeeded.add("Read Storage");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Write Storage");
        if (!addPermission(permissionsList, Manifest.permission.RECORD_AUDIO))
            permissionsNeeded.add("Record Audio");

//        for Pre-Marshmallow the permissionsNeeded.size() will always be 0; , if clause don't run  Pre-Marshmallow
        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(TopicDetailAct.this,
                                        permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_ALL_PERMISSIONS);
                            }
                        });
                return;
            }
            ActivityCompat.requestPermissions(TopicDetailAct.this,
                    permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_ALL_PERMISSIONS);
            return;
        }

//        start doing things if all PERMISSIONS are Granted whensoever
//        for Marshmallow+ and Pre-Marshmallow both
        startRecordingComments();
    }

    ////    adding RUNTIME PERMISSION to permissionsList and checking If user has GRANTED Permissions or Not  /////
    private boolean addPermission(List<String> permissionsList, String permission) {
        // Marshmallow+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(TopicDetailAct.this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                if (!ActivityCompat.shouldShowRequestPermissionRationale(TopicDetailAct.this, permission))
                    return false;
            }
        }
        // Pre-Marshmallow
        return true;
    }

    //    Handle "Don't / Never Ask Again" when asking permission
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(TopicDetailAct.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    //    check If User has Mounted the SD CARD or not
    private boolean didUserHasSDCard() {
        boolean isSDPresent = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (isSDPresent) {
            Log.w("SD_CARD", "YES");
            return true;
        } else {
            Log.w("SD_CARD", "NO");
            Toast.makeText(TopicDetailAct.this, "SD CARD not available.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // Checks if external storage is available for read and write
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private void setProfilePicSmall(final ProgressBar progressBar,
                                    final String imageUri, final ImageView imageView) {

        progressBar.setVisibility(View.VISIBLE);


        Glide.with(TopicDetailAct.this.getApplicationContext())
                .load(imageUri)
                .centerCrop()
                .into(new SimpleTarget<GlideDrawable>(200, 200) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {

                        imageView.setVisibility(View.VISIBLE);
                        imageView.setImageDrawable(resource);

                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        progressBar.setVisibility(View.GONE);

                        setProfilePicSmall(progressBar, imageUri, imageView);   //again
                    }
                });
    }

    public void startTimerComments() {
        comment_startTime = SystemClock.uptimeMillis();
        comment_handler.postDelayed(comment_up_timerRunnable, 0);

    }


    // CLASS FOR COMMENTS RECORD COUNTDOWN UP
    private Runnable comment_up_timerRunnable = new Runnable() {
        public void run() {

            comment_timeInMilliseconds = SystemClock.uptimeMillis() - comment_startTime;

            comment_updatedTime = comment_timeSwapBuff + comment_timeInMilliseconds;
            comment_sec = (int) (comment_updatedTime / 1000);
            comment_min = comment_sec / 60;
            comment_sec = comment_sec % 60;
//            comment_milliseconds = (int) (comment_updatedTime % 1000);


            String converted_time = convertSecondsToHMmSs(comment_updatedTime);


            if (comment_updatedTime >= 41000) {
                Toast.makeText(TopicDetailAct.this, "Recording limit(40 sec) reached!", Toast.LENGTH_SHORT).show();
                tv_comment_timer.setVisibility(View.GONE);

                stopRecordingComments();
                comment_handler.removeCallbacks(comment_up_timerRunnable);

                imgAudioMicGlobal.setImageDrawable(ContextCompat.getDrawable(TopicDetailAct.this, R.drawable.create_memo_mic));

                comment_timeInMilliseconds = 0L;
                comment_timeSwapBuff = 0L;
                comment_timeInMilliseconds = 0L;

                stopRecordingComments();

                comment_handler.removeCallbacks(comment_up_timerRunnable);
                isCommentRecording = false;

                dialog.dismiss();

                //POST AUDIO
                showDialogCommentReplyPostAudio(false, TopicDetailAct.this, postIdGlobal);
            } else {
                //update the time
                comment_convertedTime = converted_time;
                tv_comment_timer.setText(converted_time);
                comment_handler.postDelayed(this, 0);
            }

//            int seconds = (int) (comment_updatedTime / 1000) % 60;
//            int minutes = (int) ((comment_updatedTime / (1000 * 60)) % 60);
//            int hours = (int) ((comment_updatedTime / (1000 * 60 * 60)) % 24);
//
//            Log.w("comment_timer","comment_up_timerRunnable:" + "**** \n");
//            Log.w("comment_timer","conv:" + " "+converted_time);
//            Log.w("comment_timer","seconds:" + " "+seconds);
//            Log.w("comment_timer","minutes:" + " "+minutes);
//            Log.w("comment_timer","hours:" + " "+hours);
//
//            Log.w("comment_timer","comment_up_timerRunnable:" + "**** \n");

//
//            String s ;
//
//            s = String.format(Locale.getDefault(), "%02d", comment_min) + ":"
//                    + String.format(Locale.getDefault(), "%02d", comment_sec) + ":"
//                    + String.format(Locale.getDefault(), "%03d", comment_milliseconds);

            /*comment_convertedTime = converted_time;
            tv_comment_timer.setText(converted_time);
            comment_handler.postDelayed(this, 0);*/
        }

    };

    private void scaleAnimationComments(final ImageView recordBtn) {

        final Interpolator interpolador;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            interpolador = AnimationUtils.loadInterpolator(TopicDetailAct.this,
                    android.R.interpolator.fast_out_slow_in);

            recordBtn.animate()
                    .scaleX(1.1f)
                    .scaleY(1.1f)
                    .setInterpolator(interpolador)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            recordBtn.animate().scaleX(1f).scaleY(1f).start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                            recordBtn.animate().scaleX(1f).scaleY(1f).start();
                        }
                    });
        }

    }

    //          STOP AND SAVE RECORDING
    private void stopRecordingComments() {
        try {
            if (comment_mediaRecorder != null)
                comment_mediaRecorder.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /*
     * **************************
     * //TODO: PLAY RECORDING
     * **************************
     * */
    private void doJustCommentsPlayStopPlay(Dialog dialog) {
        ImageView img_play_pause = dialog.findViewById(R.id.img_play_pause);
        final TextView tv_duration = dialog.findViewById(R.id.tv_duration);

        if (!isCommentPlayCheck) {

            playLastRecordingComments(dialog);

            try {
                final int duration = comment_mediaPlayer.getDuration();

                if (comment_timer != null) {
                    comment_timer.cancel();
                    comment_timer = null;
                }

                comment_timer = new CountDownTimer(duration, 1000) {

                    public void onTick(long millisUntilFinished) {
                        comment_elapsedTime = millisUntilFinished;
                        tv_duration.setText(convertSecondsToHMmSs(millisUntilFinished));


                        //SIRI LIKE VIEW
                        startSetUpSiriView(false);   //COMMENT
                    }

                    public void onFinish() {

                        tv_duration.setText("00:00");

                        resetSiriView();    //reset SIRI
                    }
                }.start();


            } catch (Exception e) {
                e.printStackTrace();
            }

            img_play_pause.setImageDrawable(ContextCompat.getDrawable(TopicDetailAct.this,
                    R.drawable.create_memo_pause));
            isCommentPlayCheck = true;


        } else {

            if (comment_mediaPlayer != null)
                comment_mediaPlayer.pause();

            img_play_pause.setImageDrawable(ContextCompat.getDrawable(TopicDetailAct.this,
                    R.drawable.create_memo_play));
            isCommentPlayCheck = false;


            tv_duration.setText("00:00");

            if (comment_timer != null) {
                comment_timer.cancel();
                comment_timer = null;
            }

            resetSiriView();    //reset SIRI

        }
    }


    private void resetSiriView() {
        if (updaterThread != null) {
            updaterThread.setPRog(0);
            updaterThread.updatePlayingStatus(0);
            updaterThread = null;

            theWThread = null;
        }
    }

    public static String convertSecondsToHMmSs(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
//        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);


        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }


    /*
     * **************************
     * //TODO: DO THE RECORDING
     * **************************
     * */

    //      START RECORDING
    private void startRecordingComments() {
        if (didUserHasSDCard()) {
            if (isExternalStorageWritable()) {

//       get the path to sdcard
                File sdcard = Environment.getExternalStorageDirectory();
//      to this path add a new directory path
                File dir = new File(sdcard.getAbsolutePath() + "/" + getResources().getString(R.string.app_name) + "/" + "Audio");
//      create this directory if not already created
                if (!dir.exists()) {
                    dir.mkdirs();
                    Log.w("isDirectoryCREATED :", "YES");
                }
//     create the file in which we will write the contents

                Log.w("Directory_Path :", "" + dir.getAbsolutePath());

                long time = System.currentTimeMillis();
                String randomName = String.valueOf(time).substring(4, 12);

                String audioFileName = "RecAudio" + randomName + ".mp3";

                comment_audioFile = new File(dir, audioFileName);
                comment_audioFilePath = comment_audioFile.getAbsolutePath();

                Log.w("AudioFile_Path :", "" + comment_audioFilePath);

                setUpMediaRecorderComments();

                try {
                    comment_mediaRecorder.prepare();
                    comment_mediaRecorder.start();

                    startTimerComments();

                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                }


            }
        }


    }

    ////////////////************************//////////////
    //INTERFACE AS LISTENER
    public interface UserTopicBioListener {

        void onBioPlaying(boolean isBioPlaying);

    }


    // Runnable  CLASS SiriUpdateThread
    class SiriUpdateThread implements Runnable {


        int REFRESH_INTERVAL_MS;
        Activity c;
        float tr = 0.0F;
        DrawView v;

        private volatile int isPlayingStatus = -1;
        private final String TAG
                = SiriUpdateThread.class.getSimpleName();


        public SiriUpdateThread(int refreshTime, DrawView v, Activity c) {
            this.v = v;
            this.c = c;
            this.REFRESH_INTERVAL_MS = refreshTime;
            isPlayingStatus = 1;
        }

        public void setPRog(float prog) {
            this.tr = prog;
        }

        private long redraw() {
            long t = System.currentTimeMillis();
            this.display_game();
            return System.currentTimeMillis() - t;
        }

        private void display_game() {
            if (this.c != null) {
                this.c.runOnUiThread(new Runnable() {
                    public void run() {
                        if (SiriUpdateThread.this.v != null) {
                            SiriUpdateThread.this.v.setMaxAmplitude(SiriUpdateThread.this.tr);
                        }
                    }
                });
            }
        }


        @Override
        public void run() {
            while (isPlayingStatus == 1) {
                try {
                    Thread.sleep(Math.max(0L, (long) this.REFRESH_INTERVAL_MS - this.redraw()));
                } catch (Exception var2) {
                    var2.printStackTrace();
                }
            }
        }


        public void updatePlayingStatus(int isPlaying) {
            switch (isPlaying) {
                case 0:
                    isPlayingStatus = 0;

                    break;

                case 1:
                    isPlayingStatus = 1;
                    break;

                case -1:
                    isPlayingStatus = -1;
                    break;
                default:
                    Log.w(TAG, "updatePlayingStatus: not valid");
                    break;
            }

        }
    }

    // Set Up MEDIA RECORDER to Record Audio
    public void setUpMediaRecorderComments() {

       /* comment_mediaRecorder = new MediaRecorder();

        comment_mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        comment_mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        comment_mediaRecorder.setOutputFile(comment_audioFilePath);

        comment_mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        comment_mediaRecorder.setAudioEncodingBitRate(16);
        comment_mediaRecorder.setAudioSamplingRate(44100);*/

        comment_mediaRecorder = new MediaRecorder();

        comment_mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        comment_mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

        comment_mediaRecorder.setOutputFile(comment_audioFilePath);
        //new voice
        comment_mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        //  mediaRecorder.setAudioEncodingBitRate(16);
        comment_mediaRecorder.setAudioEncodingBitRate(128000);
        comment_mediaRecorder.setAudioSamplingRate(44100);
    }

    //        PLAY LAST RECORDING
    private void playLastRecordingComments(Dialog dialog) {


        if (comment_mediaPlayer == null) {
            snippetOfPlayLastRecComments(dialog);
        } else {
            comment_mediaPlayer.release();
            comment_mediaPlayer = null;

            snippetOfPlayLastRecComments(dialog);
        }


        //SIRI LIKE VIEW
        startSetUpSiriView(false);   //COMMENT


    }


    //        PLAY LAST RECORDING snippet
    private void snippetOfPlayLastRecComments(Dialog dialog) {

        final ImageView img_play_pause = dialog.findViewById(R.id.img_play_pause);
        comment_mediaPlayer = new MediaPlayer();

        try {
            comment_mediaPlayer.setDataSource(comment_audioFilePath);
            comment_mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        comment_mediaPlayer.start();

        comment_mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                comment_mediaPlayer.stop();
                img_play_pause.setImageDrawable(ContextCompat.getDrawable(TopicDetailAct.this,
                        R.drawable.create_memo_play));
                isCommentPlayCheck = false;

            }
        });
    }

    /*
     * ****************
     * //TODO: SYNC SIRI VIEW
     * ****************
     * */
    private void startSetUpSiriView(boolean isBio) {

        resetSiriView();

        //SIRI LIKE VIEW
        if (updaterThread == null) {

            updaterThread = new SiriUpdateThread(30, siriDView, this);
            updaterThread.setPRog(10000);
        }

        if (theWThread == null) {
            theWThread = new Thread(updaterThread);
            theWThread.start();
        }

    }


    private void postAudioServiceComments(final boolean isCommenting, final Dialog the_post_dialog, String the_post_or_audio_id,
                                          String the_audio_time,
                                          String the_desc) {
        try {

            String token = SPreferenceWriter.getInstance(TopicDetailAct.this).getString(SPreferenceKey.TOKEN);
            String userid = SPreferenceWriter.getInstance(TopicDetailAct.this).getString(SPreferenceKey.USERID);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();

            RequestBody profile_body_audio;
            MultipartBody.Part profilePartAudio;

            if (comment_audioFile != null) {
                profile_body_audio = RequestBody.create(MediaType.parse("audio/*"), comment_audioFile);
                profilePartAudio = MultipartBody.Part.createFormData("audio", comment_audioFile.getName(), profile_body_audio);

            } else {
                profilePartAudio = MultipartBody.Part.createFormData("audio", "");
            }

            Map<String, RequestBody> map = setUpMapDataComments(token,
                    userid, the_post_or_audio_id, the_audio_time, the_desc);

            MyDialog.getInstance(TopicDetailAct.this).showDialog(TopicDetailAct.this);

            Call<ReplyCommentResponse> call = apiInterface.commentOnAudioReq(map, profilePartAudio);
            call.enqueue(new retrofit2.Callback<ReplyCommentResponse>() {
                @Override
                public void onResponse(@NonNull Call<ReplyCommentResponse> call,
                                       Response<ReplyCommentResponse> response) {
                    MyDialog.getInstance(TopicDetailAct.this).hideDialog();
                    if (response.isSuccessful()) {
                        ReplyCommentResponse responseBody = response.body();

                        if (responseBody.getStatus().equalsIgnoreCase("SUCCESS")) {


                            if (isCommenting)
                                Toast.makeText(TopicDetailAct.this, "Commented on post successfully", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(TopicDetailAct.this, "Replied on post successfully", Toast.LENGTH_SHORT).show();

                            the_post_dialog.dismiss();


                            if (Util.showInternetAlert(TopicDetailAct.this)) {
                                RequestGenrePostbyId();  //in commenting api
                            }

                        } else {
                            Toast.makeText(TopicDetailAct.this, "" + responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(TopicDetailAct.this, "Server Error!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ReplyCommentResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(TopicDetailAct.this).hideDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*API SERVICES*/
    private Map<String, RequestBody> setUpMapDataComments(String the_token,
                                                          String the_user_id,
                                                          String the_post_or_audio_id,
                                                          String the_audio_time,
                                                          String the_desc) {

        Map<String, RequestBody> fields = new HashMap<>();
//
        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), the_token);
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), the_user_id);
        RequestBody audio_id = RequestBody.create(MediaType.parse("text/plain"), the_post_or_audio_id);
        RequestBody audio_time = RequestBody.create(MediaType.parse("text/plain"), the_audio_time);
        RequestBody desc = RequestBody.create(MediaType.parse("text/plain"), the_desc);

        fields.put("token", token);
        fields.put("user_id", user_id);
        fields.put("audio_id", audio_id);

        fields.put("msg_duration", audio_time);
        fields.put("description", desc);

        return fields;
    }


}
