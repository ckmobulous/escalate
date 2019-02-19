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
import android.net.Uri;
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
import android.view.Gravity;
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
import com.escalate.adapter.CommentAdapter;
import com.escalate.databinding.ActivityCommentBinding;
import com.escalate.model.ReplyCommentResponse;
import com.escalate.model.ReplyPostList;
import com.escalate.model.ViewProfileResponce;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.escalate.utils.Util;
import com.squareup.picasso.Picasso;

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

import static com.escalate.utils.MyApplication.getContext;

public class CommentAct extends AppCompatActivity implements View.OnClickListener {
    private ActivityCommentBinding binding;
    private String userId;
    private String postId;
    private String postUrl = "", postName = "", fullName = "", duration = "";
    //    private String postIdDialog="";
    private String position = "";
    //    @BindView(R.id.rippleEffect) com.skyfishjy.library.RippleBackground rippleBackground;
    private MediaPlayer mediaPlayer = null;
    private MediaRecorder comment_mediaRecorder = null;
    private MediaPlayer comment_mediaPlayer = null;
    private Handler comment_handler = null;
    private List<ReplyPostList.DataBean> postList;
    private String bio_audioFilePath = null;
    private String bio_time_duration = "";
    private ImageView imgAudioMicGlobal;
    private boolean isBioPlayCheck = false;
    private CountDownTimer bio_timer = null;
    private long bio_elapsedTime = 0L;
    private String comment_audioFilePath = null;
    private File comment_audioFile = null;
    private boolean isCommentPlayCheck = false;
    //SIRI VIEW
    private SiriUpdateThread updaterThread;
    private Thread theWThread;
    private DrawView siriDView;
    private MediaPlayer bio_mediaPlayer = null;
    private CommentBioListener commentBioListener;
    private CommentAdapter commentAdapter;
    //COMMENTS
    TextView tv_comment_timer;
    private boolean isCommentRecording = false;
    private static final int REQUEST_CODE_ASK_ALL_PERMISSIONS = 1010;
    private long comment_timeInMilliseconds = 0L;
    private long comment_timeSwapBuff = 0L;
    private long comment_updatedTime = 0L;
    private int comment_min, comment_sec, comment_milliseconds;
    private long comment_startTime = 0L;
    private String comment_convertedTime = "";
    private CountDownTimer comment_timer = null;
    private long comment_elapsedTime = 0L;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment);

        binding.backImgView.setOnClickListener(this);
        binding.playPauseIvCmt.setOnClickListener(this);
        binding.commentLay.setOnClickListener(this);
        // initialization
        if (comment_mediaRecorder != null) {
            comment_mediaRecorder.release();
            comment_mediaRecorder = null;
        }
        if (comment_mediaPlayer != null) {
            comment_mediaPlayer.release();
            comment_mediaPlayer = null;
        }
        comment_handler = new Handler();
        postList = new ArrayList<>();

        Intent intent = getIntent();
        if (intent != null) {
            userId = getIntent().getStringExtra("user_id");
            postId = getIntent().getStringExtra("post_id");
            position = getIntent().getStringExtra("position");
            postUrl = getIntent().getStringExtra("post_url");
            postName = getIntent().getStringExtra("post_name");
            fullName = getIntent().getStringExtra("full_name");
            duration = getIntent().getStringExtra("duration");
            binding.tvNameCmt.setText(fullName);
            binding.txtDescriptionCmt.setText(postName);
           /* String audioUrl = postUrl;
            mediaPlayer = MediaPlayer.create(CommentAct.this, Uri.parse(audioUrl));*/
        }

        if (Util.showInternetAlert(this)) {
            viewProfileService(userId);
        }
        if (Util.showInternetAlert(this)) {
            commentListService();  //in onCreateView
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backImgView:
                onBackPressed();
                break;
            case R.id.playPauseIvCmt:
                if (bio_audioFilePath != null && !bio_audioFilePath.isEmpty()) {
                    //BIO
                    doJustPlayStopPlayBio();//PLAYING BIO
                } else {
                    Toast.makeText(CommentAct.this, "Bio is empty.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.commentLay:
                if (!isCommentRecording) {
                    doAllPermissionChecking();
                    isCommentRecording = true;
                    scaleAnimationComments(binding.micIviewCmnt);
                    binding.micIviewCmnt.setImageDrawable(ContextCompat.getDrawable(CommentAct.this,
                            R.drawable.comment_mic));
                } else {
                    stopRecordingComments();

                    comment_handler.removeCallbacks(comment_up_timerRunnable);
                    isCommentRecording = false;

                    binding.micIviewCmnt.setImageDrawable(ContextCompat.getDrawable(CommentAct.this,
                            R.drawable.comment_mic));
                    binding.cmtTxt.setText("Reply");

                    // dialog.dismiss();
                    //POST AUDIO
                    showDialogCommentReplyPostAudio(false, CommentAct.this, postId);
                }
                break;
        }
    }

    // **************RECORD DIALOG
    private void showDialogCommentReplyRecordAudio(final boolean isCommenting, final Context context,
                                                   final String post_id) {

        dialog = new Dialog(context);
        String genre = SPreferenceWriter.getInstance(getContext())
                .getString(SPreferenceKey.GENRE);

        String user = SPreferenceWriter.getInstance(getContext())
                .getString(SPreferenceKey.LOGINUSERNAME);

        String image = SPreferenceWriter.getInstance(getContext())
                .getString(SPreferenceKey.IMAGE);

        dialog.setContentView(R.layout.dialog_post_com_record);
        Objects.requireNonNull(dialog.getWindow())
                .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        tv_comment_timer = dialog.findViewById(R.id.tv_timerRecord);

        ((TextView) dialog.findViewById(R.id.tv_name)).setText(user);
        ((TextView) dialog.findViewById(R.id.tv_category_hash)).setText(genre);

        final ImageView img_audio_mic = dialog.findViewById(R.id.img_audio_mic);
        setProfilePicSmall(
                ((ProgressBar) dialog.findViewById(R.id.progressBar)),
                image,
                (ImageView) dialog.findViewById(R.id.img_user));

        //CANCEL
        dialog.findViewById(R.id.img_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                comment_handler.removeCallbacks(comment_up_timerRunnable);
                isCommentRecording = false;
                comment_timeInMilliseconds = 0L;
                comment_timeSwapBuff = 0L;
                comment_timeInMilliseconds = 0L;
                tv_comment_timer.setText("00:00");
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
                    img_audio_mic.setImageDrawable(ContextCompat.getDrawable(CommentAct.this,
                            R.drawable.create_memo_hour));
                    imgAudioMicGlobal = img_audio_mic;
                } else {

                    stopRecordingComments();

                    comment_handler.removeCallbacks(comment_up_timerRunnable);
                    isCommentRecording = false;

                    img_audio_mic.setImageDrawable(ContextCompat.getDrawable(CommentAct.this,
                            R.drawable.create_memo_mic));

                    dialog.dismiss();

                    //POST AUDIO
                    showDialogCommentReplyPostAudio(isCommenting, context, postId);
                }
            }
        });
        dialog.show();
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
                img_play_pause.setImageDrawable(ContextCompat.getDrawable(CommentAct.this,
                        R.drawable.create_memo_play));
                isCommentPlayCheck = false;

            }
        });
    }

    // **************PLAY AND POST DIALOG
    private void showDialogCommentReplyPostAudio(final boolean isCommenting, final Context context,
                                                 final String post_id) {

        final Dialog dialog = new Dialog(context);

        String genre = SPreferenceWriter.getInstance(getContext())
                .getString(SPreferenceKey.GENRE);

        String user = SPreferenceWriter.getInstance(getContext())
                .getString(SPreferenceKey.LOGINUSERNAME);

        String image = SPreferenceWriter.getInstance(getContext())
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
                resetUserAllThreadsPlayers();
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
                    if (comment_mediaPlayer != null)
                        comment_mediaPlayer.pause();

                    img_play_pause.setImageDrawable(ContextCompat.getDrawable(CommentAct.this,
                            R.drawable.create_memo_play));
                    isCommentPlayCheck = false;


                    tv_duration.setText(comment_convertedTime);

                    if (comment_timer != null) {
                        comment_timer.cancel();
                        comment_timer = null;
                    }

                    resetSiriView();
                    tv_duration.setText("00:00");
                    String desc = et_description.getText().toString();
                    String time = comment_convertedTime;

                    resetAllOtherPrevious();
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

    //ALL METHODS
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
                                ActivityCompat.requestPermissions(CommentAct.this,
                                        permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_ALL_PERMISSIONS);
                            }
                        });
                return;
            }
            ActivityCompat.requestPermissions(CommentAct.this,
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

            if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                if (!ActivityCompat.shouldShowRequestPermissionRationale(CommentAct.this, permission))
                    return false;
            }
        }
        // Pre-Marshmallow
        return true;
    }

    //    Handle "Don't / Never Ask Again" when asking permission
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
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
            Toast.makeText(CommentAct.this, "SD CARD not available.", Toast.LENGTH_SHORT).show();
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

    private void scaleAnimationComments(final ImageView recordBtn) {

        final Interpolator interpolador;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            interpolador = AnimationUtils.loadInterpolator(getContext(),
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

    /*
     * **************************
     * //TODO: PLAY BIO RECORDING
     * **************************
     * */
    private void doJustPlayStopPlayBio() {
        ImageView img_play_pause = binding.playPauseIvCmt;
        final TextView tv_duration = binding.txtTimerCmt;

        if (!isBioPlayCheck) {

            playLastRecordingBio(); //BIO

            try {
                final int duration = bio_mediaPlayer.getDuration();

                if (bio_timer != null) {
                    bio_timer.cancel();
                    bio_timer = null;
                }

                bio_timer = new CountDownTimer(duration, 1000) {

                    public void onTick(long millisUntilFinished) {
                        bio_elapsedTime = millisUntilFinished;
                        tv_duration.setText(convertSecondsToHMmSs(millisUntilFinished));

                        //SIRI LIKE VIEW
                        startSetUpSiriView(true);   //BIO
                    }

                    public void onFinish() {

                        tv_duration.setText("00:00");

                        resetSiriView();    //reset SIRI

                        if (commentBioListener != null)
                            commentBioListener.onBioPlaying(false);
                    }
                }.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
            img_play_pause.setImageDrawable(ContextCompat.getDrawable(CommentAct.this,
                    R.drawable.home_pause_a));
            isBioPlayCheck = true;

        } else {

            if (bio_mediaPlayer != null)
                bio_mediaPlayer.pause();

            img_play_pause.setImageDrawable(ContextCompat.getDrawable(CommentAct.this,
                    R.drawable.home_play_a));
            isBioPlayCheck = false;

            tv_duration.setText("00:00");

            if (bio_timer != null) {
                bio_timer.cancel();
                bio_timer = null;
            }

            resetSiriView();    //reset SIRI
            if (commentBioListener != null)
                commentBioListener.onBioPlaying(false);
        }
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
            if (isBio)
                updaterThread = new CommentAct.SiriUpdateThread(30, binding.siriDViewCmnt, CommentAct.this);
            else
                updaterThread = new CommentAct.SiriUpdateThread(30, siriDView, CommentAct.this);

            updaterThread.setPRog(10000);
        }

        if (theWThread == null) {
            theWThread = new Thread(updaterThread);
            theWThread.start();
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

    //        PLAY LAST RECORDING
    private void playLastRecordingBio() {


        if (bio_mediaPlayer == null) {
            snippetOfPlayLastRecBio();
        } else {
            bio_mediaPlayer.release();
            bio_mediaPlayer = null;

            snippetOfPlayLastRecBio();
        }

        if (commentBioListener != null)
            commentBioListener.onBioPlaying(true);

        //SIRI LIKE VIEW
        startSetUpSiriView(true);  // BIO

    }

    //        PLAY LAST RECORDING snippet
    private void snippetOfPlayLastRecBio() {

        if (bio_audioFilePath != null && !bio_audioFilePath.isEmpty()) {

            final ImageView img_play_pause = binding.playPauseIvCmt;
            bio_mediaPlayer = new MediaPlayer();

            try {
                bio_mediaPlayer.setDataSource(bio_audioFilePath);
                bio_mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bio_mediaPlayer.start();

            bio_mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    try {
                        bio_mediaPlayer.stop();
                        img_play_pause.setImageDrawable(ContextCompat.getDrawable(CommentAct.this,
                                R.drawable.home_play_a));
                        isBioPlayCheck = false;


                        if (commentBioListener != null)
                            commentBioListener.onBioPlaying(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    private void setProfilePicSmall(final ProgressBar progressBar,
                                    final String imageUri, final ImageView imageView) {

        progressBar.setVisibility(View.VISIBLE);

        Glide.with(CommentAct.this.getApplicationContext())
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

    public void resetUserAllThreadsPlayers() {
        try {
            //POST LIST
            if (commentAdapter != null) {
                commentAdapter.resetAllOtherPrevious();
            }
            //BIO
            if (bio_mediaPlayer != null) {
                bio_mediaPlayer.stop();
                bio_mediaPlayer.reset();
                bio_mediaPlayer.release();
                bio_mediaPlayer = null;
            }
            //COMMENTS IN POST LIST
            if (comment_mediaPlayer != null) {
                comment_mediaPlayer.stop();
                comment_mediaPlayer.reset();
                comment_mediaPlayer.release();
                comment_mediaPlayer = null;
            }

            if (comment_mediaRecorder != null) {
                comment_mediaRecorder.stop();
                comment_mediaRecorder.reset();
                comment_mediaRecorder.release();
                comment_mediaRecorder = null;
            }
            // SIRI RESET
            if (updaterThread != null) {
                updaterThread.setPRog(0);
                updaterThread.updatePlayingStatus(0);   //ALL RESET
                updaterThread = null;
                theWThread = null;
            }
            if (theWThread != null) {
                theWThread = null;
            }

            //RESET BIO
            if (bio_mediaPlayer != null)
                bio_mediaPlayer.pause();

            if (binding.playPauseIvCmt != null && binding.playPauseIvCmt != null) {

                ImageView img_play_pause = binding.playPauseIvCmt;
                final TextView tv_duration = binding.txtTimerCmt;

                img_play_pause.setImageDrawable(ContextCompat.getDrawable(CommentAct.this,
                        R.drawable.home_play_a));
                isBioPlayCheck = false;
                tv_duration.setText("00:00");
                tv_duration.setText(bio_time_duration);
            }

            if (bio_timer != null) {
                bio_timer.cancel();
                bio_timer = null;
            }
            if (commentBioListener != null)
                commentBioListener.onBioPlaying(false);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: POSTS LIST
    private void setUpRecyclerPost(List<ReplyPostList.DataBean> postList) {

        if (postList != null && postList.size() > 0) {
            binding.commentRecView.setVisibility(View.VISIBLE);
            binding.tvNoDataCmt.setVisibility(View.GONE);

            for (int i = 0; i < postList.size(); i++) {
                ReplyPostList.DataBean dataBean = postList.get(i);
                dataBean.setPlayPauseEnable(true);
            }

            commentAdapter = new CommentAdapter(this, postList);

            binding.commentRecView.setLayoutManager(new LinearLayoutManager(this));
            binding.commentRecView.setAdapter(commentAdapter);

            commentBioListener = new CommentBioListener() {
                @Override
                public void onBioPlaying(boolean isBioPlaying) {
                    Log.w(CommentAct.class.getSimpleName(),
                            "UserProfileBioListener : isBioPlaying :->" + isBioPlaying);

                    if (isBioPlaying)
                        commentAdapter.setDisableAll();
                    else
                        commentAdapter.setEnableAll();
                }
            };
//            adapter.setProfileBioListener(commentBioListener);

        } else {
            binding.commentRecView.setVisibility(View.GONE);
            binding.tvNoDataCmt.setVisibility(View.VISIBLE);
        }
    }


    public void startTimerComments() {
        comment_startTime = SystemClock.uptimeMillis();
        comment_handler.postDelayed(comment_up_timerRunnable, 0);

    }


    // Set Up MEDIA RECORDER to Record Audio
    public void setUpMediaRecorderComments() {

        /*comment_mediaRecorder = new MediaRecorder();

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

                        tv_duration.setText(comment_convertedTime);

                        resetSiriView();    //reset SIRI
                    }
                }.start();


            } catch (Exception e) {
                e.printStackTrace();
            }

            img_play_pause.setImageDrawable(ContextCompat.getDrawable(CommentAct.this,
                    R.drawable.create_memo_pause));
            isCommentPlayCheck = true;


        } else {

            if (comment_mediaPlayer != null)
                comment_mediaPlayer.pause();

            img_play_pause.setImageDrawable(ContextCompat.getDrawable(CommentAct.this,
                    R.drawable.create_memo_play));
            isCommentPlayCheck = false;


            tv_duration.setText(comment_convertedTime);

            if (comment_timer != null) {
                comment_timer.cancel();
                comment_timer = null;
            }

            resetSiriView();    //reset SIRI

        }
    }

    private boolean validationPostCommentAudio(Dialog dialog) {
        EditText et_description = dialog.findViewById(R.id.et_description);
        if (et_description.getText().toString().isEmpty()) {
            Toast.makeText(CommentAct.this, "Enter description", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }

    //API
    private void commentListService() {
        try {

            MyDialog.getInstance(CommentAct.this).showDialog(CommentAct.this);
            String userid = SPreferenceWriter.getInstance(this).getString(SPreferenceKey.USERID);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<ReplyPostList> call = apiInterface.reply_list_by_audioid(postId);

            call.enqueue(new retrofit2.Callback<ReplyPostList>() {
                @Override
                public void onResponse(@NonNull Call<ReplyPostList> call, @NonNull Response<ReplyPostList> response) {
                    if (response.isSuccessful()) {
                        ReplyPostList replyPostList = response.body();
                        if (replyPostList != null) {
                            if (replyPostList.getStatus().equalsIgnoreCase("SUCCESS")) {
                                postList = replyPostList.getData();
                                //                            recyclerView(postList);

                                setUpRecyclerPost(postList);

                            } else {
                                // Toast.makeText(this, "" + replyPostList.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(CommentAct.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                    MyDialog.getInstance(CommentAct.this).hideDialog();
                }

                @Override
                public void onFailure(@NonNull Call<ReplyPostList> call, Throwable t) {
                    MyDialog.getInstance(CommentAct.this).hideDialog();
                    t.printStackTrace();
                }
            });

        } catch (Exception e) {
            MyDialog.getInstance(CommentAct.this).hideDialog();
            e.printStackTrace();
        }
    }



    /* *//*API SERVICES*//*
    private void commentListService() {
        try {

            String useridLogin = SPreferenceWriter.getInstance(this).getString(SPreferenceKey.USERID);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<PostListResponce> call = apiInterface.post_list_by_id(userId, useridLogin);

            call.enqueue(new retrofit2.Callback<PostListResponce>() {
                @Override
                public void onResponse(Call<PostListResponce> call,
                                       Response<PostListResponce> response) {

                    if (response.isSuccessful()) {
                        PostListResponce postListResponce = response.body();
                        Log.e("response", "" + postListResponce.toString());

                        if (postListResponce.getStatus().equalsIgnoreCase("SUCCESS")) {
                            postList = postListResponce.getData();

                            setUpRecyclerPost(postList);

                        } else {
                        }
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<PostListResponce> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private void viewProfileService(String postId) {

        try {

            MyDialog.getInstance(this).showDialog(this);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<ViewProfileResponce> call = apiInterface.view_profile(postId, SPreferenceWriter.getInstance(this).getString(SPreferenceKey.USERID));
            call.enqueue(new retrofit2.Callback<ViewProfileResponce>() {
                @Override
                public void onResponse(Call<ViewProfileResponce> call,
                                       Response<ViewProfileResponce> response) {
                    if (response.isSuccessful()) {
                        ViewProfileResponce loginResponse = response.body();
                        Log.e("responce", "" + loginResponse.toString());

                        if (loginResponse.getStatus().equalsIgnoreCase("SUCCESS")) {

//                            tvUserName.setText(loginResponse.getData().getFullname());
//                            txtDescriptionCmt.setText(SPreferenceWriter.getInstance(CommentAct.this).getString(SPreferenceKey.GENRE));
                            String audioUrl = postUrl;

                            if (audioUrl != null && !audioUrl.isEmpty()) {

                                mediaPlayer = MediaPlayer.create(CommentAct.this, Uri.parse(audioUrl));

                                String bio = postUrl;
                                String bio_duration = loginResponse.getData().getBio_duration();
                                if (bio != null) {
                                    if (!bio.isEmpty()) {
                                        bio_audioFilePath = bio;
                                    } else {
                                        bio_audioFilePath = "";
                                    }
                                } else {
                                    bio_audioFilePath = "";
                                }

                                if (bio_duration != null) {
                                    if (!bio_duration.isEmpty()) {
                                        bio_time_duration = bio_duration;

                                    } else {
                                        bio_time_duration = "00:00";

                                    }
                                } else {
                                    bio_time_duration = "00:00";
                                }
                                binding.txtTimerCmt.setText(duration);
                            }

                            if (loginResponse.getData().getImage() != null) {
                                if (!(loginResponse.getData().getImage()).isEmpty()) {

                                    Picasso.get().load(loginResponse.getData().getImage())
                                            .resize(100, 100).centerCrop(Gravity.CENTER).into(binding.imgUserCmt);
                                } else {
                                    binding.imgUserCmt.setImageResource(R.drawable.default_image);
                                }
                            }

                        } else {

                            Toast.makeText(CommentAct.this, "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CommentAct.this, "Server Error!", Toast.LENGTH_SHORT).show();
                    }
                    MyDialog.getInstance(CommentAct.this).hideDialog();

                }

                @Override
                public void onFailure(Call<ViewProfileResponce> call, Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(CommentAct.this).hideDialog();
                }
            });

        } catch (Exception e) {
            MyDialog.getInstance(CommentAct.this).hideDialog();
            e.printStackTrace();
        }
    }


    private void postAudioServiceComments(final boolean isCommenting, final Dialog the_post_dialog, String the_post_or_audio_id,
                                          String the_audio_time,
                                          String the_desc) {
        try {

            String token = SPreferenceWriter.getInstance(CommentAct.this).getString(SPreferenceKey.TOKEN);
            String userid = SPreferenceWriter.getInstance(CommentAct.this).getString(SPreferenceKey.USERID);
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

            MyDialog.getInstance(CommentAct.this).showDialog(CommentAct.this);

            Call<ReplyCommentResponse> call = apiInterface.commentOnAudioReq(map, profilePartAudio);
            call.enqueue(new retrofit2.Callback<ReplyCommentResponse>() {
                @Override
                public void onResponse(@NonNull Call<ReplyCommentResponse> call,
                                       Response<ReplyCommentResponse> response) {
                    MyDialog.getInstance(CommentAct.this).hideDialog();
                    if (response.isSuccessful()) {
                        ReplyCommentResponse responseBody = response.body();

                        if (responseBody.getStatus().equalsIgnoreCase("SUCCESS")) {


                            if (isCommenting)
                                Toast.makeText(CommentAct.this, "Commented on post successfully", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(CommentAct.this, "Replied on post successfully", Toast.LENGTH_SHORT).show();

                            the_post_dialog.dismiss();


                            if (Util.showInternetAlert(getContext())) {
                                commentListService();  //in commenting api
                            }

                        } else {
                            Toast.makeText(CommentAct.this, "" + responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(CommentAct.this, "Server Error!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ReplyCommentResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(CommentAct.this).hideDialog();
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


    @Override
    protected void onPause() {
        super.onPause();

        resetUserAllThreadsPlayers();
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null)
            mediaPlayer.release();
        super.onDestroy();
    }


    //INTERFACE AS LISTENER
    public interface CommentBioListener {

        void onBioPlaying(boolean isBioPlaying);

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
                Toast.makeText(CommentAct.this, "Recording limit(40 sec) reached!", Toast.LENGTH_SHORT).show();
//                tv_comment_timer.setVisibility(View.GONE);

                stopRecordingComments();
                comment_handler.removeCallbacks(comment_up_timerRunnable);
                isCommentRecording = false;

//                imgAudioMicGlobal.setImageDrawable(ContextCompat.getDrawable(CommentAct.this, R.drawable.create_memo_mic));

                comment_timeInMilliseconds = 0L;
                comment_timeSwapBuff = 0L;
                comment_timeInMilliseconds = 0L;
                binding.cmtTxt.setText("Reply");

//                dialog.dismiss();

                //POST AUDIO
                showDialogCommentReplyPostAudio(false, CommentAct.this, postId);
            } else {
                //update the time
                comment_convertedTime = converted_time;
//                tv_comment_timer.setText(converted_time);
                binding.cmtTxt.setText(converted_time);
                comment_handler.postDelayed(this, 0);
            }
        }

    };

    // Runnable  CLASS SiriUpdateThread
    class SiriUpdateThread implements Runnable {


        int REFRESH_INTERVAL_MS;
        Activity c;
        float tr = 0.0F;
        DrawView v;

        private volatile int isPlayingStatus = -1;
        private final String TAG
                = CommentAct.SiriUpdateThread.class.getSimpleName();


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
                        if (CommentAct.SiriUpdateThread.this.v != null) {
                            CommentAct.SiriUpdateThread.this.v.setMaxAmplitude(CommentAct.SiriUpdateThread.this.tr);
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


    public void resetAllOtherPrevious() {
        try {

            //COMMENTS IN POST LIST
            if (mediaPlayer != null) {
                mediaPlayer.pause();
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }

            if (comment_mediaRecorder != null) {
                comment_mediaRecorder.stop();
                comment_mediaRecorder.reset();
                comment_mediaRecorder.release();
                comment_mediaRecorder = null;
            }

            if (comment_timer != null) {
                comment_timer.cancel();
                comment_timer = null;
            }


            // SIRI RESET
            if (updaterThread != null) {
                updaterThread.setPRog(0);
                updaterThread.updatePlayingStatus(0);   //ALL RESET
                updaterThread = null;

                theWThread = null;
            }
            if (theWThread != null) {
                theWThread = null;
            }

            if (comment_timer != null) {
                comment_timer.cancel();
                comment_timer = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
