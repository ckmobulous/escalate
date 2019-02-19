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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.escalate.adapter.FollowerAdapter;
import com.escalate.adapter.FollowingAdapter;
import com.escalate.adapter.OtherProfPostsAdapter;
import com.escalate.databinding.ActivityOtherProfileBinding;
import com.escalate.fragments.UserProfileFragment;
import com.escalate.model.FollowReponce;
import com.escalate.model.LikeResponce;
import com.escalate.model.PostListResponce;
import com.escalate.model.ReplyCommentResponse;
import com.escalate.model.ReportedUserModel;
import com.escalate.model.SampleResponce;
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

public class OtherProfileActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityOtherProfileBinding binding;
    private String statusToLike = "0";
    private FollowerAdapter followerAdapter;
    private FollowingAdapter followingAdapter;
    private String postId = "", userId = "", pos = "", flagCheck = "";
    private MediaPlayer mediaPlayer = null;
    //COMMENTS
    TextView tv_comment_timer;
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
    private int comment_min, comment_sec, comment_milliseconds;
    private CountDownTimer comment_timer = null;
    private long comment_elapsedTime = 0L;
    private String comment_convertedTime = "";
    private String bio_time_duration = "";
    private String fireBaseChatUserId = "";
    private String chatUserImg = "", userName = "";
    private String userID = "";
    //SIRI VIEW
    private SiriUpdateThread updaterThread;
    private Thread theWThread;
    private DrawView siriDView;
    private String bio_audioFilePath = null;
    private MediaPlayer bio_mediaPlayer = null;
    private boolean isBioPlayCheck = false;
    private CountDownTimer bio_timer = null;
    private long bio_elapsedTime = 0L;

    private List<PostListResponce.DataBean> postList;
    private List<FollowReponce.DataBean> followerList;
    private List<FollowReponce.DataBean> followingList;
    private OtherProfPostsAdapter adapter;

    private UserProfileBioListener profileBioListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_other_profile);

        binding.reportLay.setOnClickListener(this);
        binding.backIv.setOnClickListener(this);
        binding.layPostOprofile.setOnClickListener(this);
        binding.layFollowers.setOnClickListener(this);
        binding.layFollowing.setOnClickListener(this);
        binding.txtFollowOprofile.setOnClickListener(this);
        binding.playPauseIvOpro.setOnClickListener(this);
        binding.reportLay.setOnClickListener(this);

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
        followerList = new ArrayList<>();
        followerList = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        init();

        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getStringExtra("user_id");
            postId = intent.getStringExtra("post_id");
            pos = intent.getStringExtra("position");
        }

        if (Util.showInternetAlert(this)) {
            viewProfileService(userId);
        }

        if (Util.showInternetAlert(this)) {
            postListService();  //in onCreateView
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.backIv:
                onBackPressed();
                break;
            case R.id.layPostOprofile:
                binding.layPostOprofile.setBackground(ContextCompat.getDrawable(this, R.drawable.save_rectangle_back));
                binding.txtPostsCount.setTextColor(ContextCompat.getColor(this, R.color.white));
                binding.txtPostOprofile.setTextColor(ContextCompat.getColor(this, R.color.white));
                binding.layFollowers.setBackground(ContextCompat.getDrawable(this, R.drawable.save_rectangle_back_white));
                binding.txtCountFolowers.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.txtFollower.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.layFollowing.setBackground(ContextCompat.getDrawable(this, R.drawable.save_rectangle_back_white));
                binding.txtCountFollowing.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.txtFollowing.setTextColor(ContextCompat.getColor(this, R.color.black));

                resetUserAllThreadsPlayers();

                if (Util.showInternetAlert(this)) {
                    postListService();
                }
                break;
            case R.id.layFollowers:
                binding.layPostOprofile.setBackground(ContextCompat.getDrawable(this, R.drawable.save_rectangle_back_white));
                binding.txtPostsCount.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.txtPostOprofile.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.layFollowers.setBackground(ContextCompat.getDrawable(this, R.drawable.save_rectangle_back));
                binding.txtCountFolowers.setTextColor(ContextCompat.getColor(this, R.color.white));
                binding.txtFollower.setTextColor(ContextCompat.getColor(this, R.color.white));
                binding.layFollowing.setBackground(ContextCompat.getDrawable(this, R.drawable.save_rectangle_back_white));
                binding.txtCountFollowing.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.txtFollowing.setTextColor(ContextCompat.getColor(this, R.color.black));

                resetUserAllThreadsPlayers();

                if (Util.showInternetAlert(this)) {
                    followerListService();
                }
                break;

            case R.id.layFollowing:
                binding.layPostOprofile.setBackground(ContextCompat.getDrawable(this, R.drawable.save_rectangle_back_white));
                binding.txtPostsCount.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.txtPostOprofile.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.layFollowers.setBackground(ContextCompat.getDrawable(this, R.drawable.save_rectangle_back_white));
                binding.txtCountFolowers.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.txtFollower.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.layFollowing.setBackground(ContextCompat.getDrawable(this, R.drawable.save_rectangle_back));
                binding.txtCountFollowing.setTextColor(ContextCompat.getColor(this, R.color.white));
                binding.txtFollowing.setTextColor(ContextCompat.getColor(this, R.color.white));

                resetUserAllThreadsPlayers();

                if (Util.showInternetAlert(this)) {
                    followingListService();
                }
                break;
            case R.id.txtFollowOprofile:
                updateFollower(0, userId, "upFollow");
                break;
            case R.id.playPauseIvOpro:
                if (bio_audioFilePath != null && !bio_audioFilePath.isEmpty()) {
                    //BIO
                    doJustPlayStopPlayBio();//PLAYING BIO
                } else {
                    Toast.makeText(OtherProfileActivity.this, "Bio is empty.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.reportLay:
                serviceReportUser();
                break;
        }

    }


   /* @OnClick({R.id.backIv, R.id.txt_cancel, R.id.linear_post, R.id.linear_follower, R.id.linear_following, R.id.txt_followUP, R.id.playPauseIv,R.id.reportLay})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIv:
                finish();
                break;
            case R.id.txt_apply:
                //startActivity(new Intent(this, updateUserActivity.class));
                break;
            case R.id.txt_cancel:
                filterIncludeLayout();
                break;
            case R.id.linear_post:
                binding.layPostOprofile.setBackground(ContextCompat.getDrawable(this, R.drawable.save_rectangle_back));
                binding.txtPostsCount.setTextColor(ContextCompat.getColor(this, R.color.white));
                binding.txtPostOprofile.setTextColor(ContextCompat.getColor(this, R.color.white));
                binding.layFollowers.setBackground(ContextCompat.getDrawable(this, R.drawable.save_rectangle_back_white));
                binding.txtCountFolowers.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.txtFollower.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.layFollowing.setBackground(ContextCompat.getDrawable(this, R.drawable.save_rectangle_back_white));
                binding.txtCountFollowing.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.txtFollowing.setTextColor(ContextCompat.getColor(this, R.color.black));

                resetUserAllThreadsPlayers();

                if (Util.showInternetAlert(this)) {
                    postListService();
                }
                break;
            case R.id.linear_follower:
                binding.layPostOprofile.setBackground(ContextCompat.getDrawable(this, R.drawable.save_rectangle_back_white));
                binding.txtPostsCount.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.txtPostOprofile.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.layFollowers.setBackground(ContextCompat.getDrawable(this, R.drawable.save_rectangle_back));
                binding.txtCountFolowers.setTextColor(ContextCompat.getColor(this, R.color.white));
                binding.txtFollower.setTextColor(ContextCompat.getColor(this, R.color.white));
                binding.layFollowing.setBackground(ContextCompat.getDrawable(this, R.drawable.save_rectangle_back_white));
                binding.txtCountFollowing.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.txtFollowing.setTextColor(ContextCompat.getColor(this, R.color.black));

                resetUserAllThreadsPlayers();

                if (Util.showInternetAlert(this)) {
                    followerListService();
                }
                break;
            case R.id.linear_following:
                binding.layPostOprofile.setBackground(ContextCompat.getDrawable(this, R.drawable.save_rectangle_back_white));
                binding.txtPostsCount.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.txtPostOprofile.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.layFollowers.setBackground(ContextCompat.getDrawable(this, R.drawable.save_rectangle_back_white));
                binding.txtCountFolowers.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.txtFollower.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.layFollowing.setBackground(ContextCompat.getDrawable(this, R.drawable.save_rectangle_back));
                binding.txtCountFollowing.setTextColor(ContextCompat.getColor(this, R.color.white));
                binding.txtFollowing.setTextColor(ContextCompat.getColor(this, R.color.white));

                resetUserAllThreadsPlayers();

                if (Util.showInternetAlert(this)) {
                    followingListService();
                }
                break;
            case R.id.txt_followUP:
                updateFollower(0, userId, "upFollow");
                break;
            case R.id.playPauseIv:

                if (bio_audioFilePath != null && !bio_audioFilePath.isEmpty()) {
                    //BIO
                    doJustPlayStopPlayBio();//PLAYING BIO
                } else {
                    Toast.makeText(OtherProfileActivity.this, "Bio is empty.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.reportLay:
                serviceReportUser();
                break;
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_other_profile_option, menu);
        /*menu.findItem(R.id.action_filter).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                filterIncludeLayout();
                return false;
            }
        });*/

        menu.findItem(R.id.action_chat).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intentChat = new Intent(OtherProfileActivity.this, ChatActivity.class);
                intentChat.putExtra("user_img", chatUserImg);
                intentChat.putExtra("firbase_chat_user_id", fireBaseChatUserId);
                intentChat.putExtra("user_name", userName);
                intentChat.putExtra("user_id", userID);
                startActivity(intentChat);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        /*if (id == R.id.action_filter) {
        }*/
        return super.onOptionsItemSelected(item);
    }

    private void filterIncludeLayout() {

        if (binding.relativeIncludeOP.getVisibility() == View.VISIBLE) {
            binding.relativeIncludeOP.setVisibility(View.GONE);
        } else {
            binding.relativeIncludeOP.setVisibility(View.VISIBLE);
        }
    }

    //ALL DIALOGS
    // **************RECORD DIALOG
    private void showDialogCommentReplyRecordAudio(final boolean isCommenting, final Context context,
                                                   final String post_id) {

        final Dialog dialog = new Dialog(context);

        String genre = SPreferenceWriter.getInstance(OtherProfileActivity.this)
                .getString(SPreferenceKey.GENRE);

        String user = SPreferenceWriter.getInstance(OtherProfileActivity.this)
                .getString(SPreferenceKey.LOGINUSERNAME);

        String image = SPreferenceWriter.getInstance(OtherProfileActivity.this)
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

                stopRecordingComments();

                comment_handler.removeCallbacks(comment_up_timerRunnable);
                isCommentRecording = false;

                img_audio_mic.setImageDrawable(ContextCompat.getDrawable(OtherProfileActivity.this,
                        R.drawable.create_memo_mic));
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
                    img_audio_mic.setImageDrawable(ContextCompat.getDrawable(OtherProfileActivity.this,
                            R.drawable.create_memo_hour));
                } else {

                    stopRecordingComments();

                    comment_handler.removeCallbacks(comment_up_timerRunnable);
                    isCommentRecording = false;

                    img_audio_mic.setImageDrawable(ContextCompat.getDrawable(OtherProfileActivity.this,
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

        String genre = SPreferenceWriter.getInstance(OtherProfileActivity.this)
                .getString(SPreferenceKey.GENRE);

        String user = SPreferenceWriter.getInstance(OtherProfileActivity.this)
                .getString(SPreferenceKey.LOGINUSERNAME);

        String image = SPreferenceWriter.getInstance(OtherProfileActivity.this)
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


    //ALL METHODS
    private void init() {
        setSupportActionBar(binding.toolbarOProfile);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    private void recyclerFollower(List<FollowReponce.DataBean> followerLIst) {

        for (int i = 0; i < followerList.size(); i++) {
            FollowReponce.DataBean dataBean = followerList.get(i);
            dataBean.setPlayPauseEnable(true);
        }
        followerAdapter = new FollowerAdapter(this, followerLIst, new FollowerAdapter.FollowerAdapterListener() {
            @Override
            public void followUnFollowService(int position, String audioid) {

                if (followerAdapter.getArrayList() != null
                        && followerAdapter.getArrayList().size() > 0) {
                    String followerId = audioid;
                    fUnfServiceInFollowers(position, followerId); //SERVICE IN FOLLOWERS
                }
            }

            @Override
            public void folowProfileService(int position, String audioid) {

            }

            @Override
            public void onUserClick(int position, FollowReponce.DataBean data) {

                dispatchOtherUserAct(data.getUser_id(), "", position);

            }
        });

        binding.recyclerViewProfileList.setVisibility(View.VISIBLE);
        binding.tvNoDataOPro.setVisibility(View.GONE);
        binding.recyclerViewProfileList.setLayoutManager(new LinearLayoutManager(OtherProfileActivity.this));
        binding.recyclerViewProfileList.setAdapter(followerAdapter);

        followerAdapter.setFfUserBioPlayingListener(new FollowerAdapter.FFUserBioPlayingListener() {
            @Override
            public void onUserBioPlaying(boolean isUserBioPlaying) {

                if (isUserBioPlaying) {
                    binding.playPauseIvOpro.setEnabled(false);
                } else {
                    binding.playPauseIvOpro.setEnabled(true);
                }
            }


        });

        profileBioListener = new OtherProfileActivity.UserProfileBioListener() {
            @Override
            public void onBioPlaying(boolean isBioPlaying) {

                Log.w(UserProfileFragment.class.getSimpleName(),
                        "UserProfileBioListener : isBioPlaying :->" + isBioPlaying);

                if (isBioPlaying)
                    followerAdapter.setDisableAll();
                else
                    followerAdapter.setEnableAll();
            }
        };
        followerAdapter.setOtherBioListener(profileBioListener);
    }

    private void recyclerFollowing(List<FollowReponce.DataBean> followerLIst) {

        followingAdapter = new FollowingAdapter(this, followerLIst, new FollowingAdapter.FollowingAdapterListener() {
            @Override
            public void followingUnFollowService(int position, String audioid) {
                String followingId = audioid;
                fUnfServiceInFollowings(position, followingId); //SERVICE IN FOLLOWINGS
            }

            @Override
            public void folowingProfileService(int position, String audioid) {

            }

            @Override
            public void onUserClick(int position, FollowReponce.DataBean data) {


                dispatchOtherUserAct(data.getUser_id(), "", position);

            }
        });

        binding.recyclerViewProfileList.setVisibility(View.VISIBLE);
        binding.tvNoDataOPro.setVisibility(View.GONE);

        binding.recyclerViewProfileList.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewProfileList.setAdapter(followingAdapter);
        followingAdapter.setFfUserBioPlayingListener(new FollowingAdapter.FFUserBioPlayingListener() {
            @Override
            public void onUserBioPlaying(boolean isUserBioPlaying) {

                if (isUserBioPlaying) {
                    binding.playPauseIvOpro.setEnabled(false);
                } else {
                    binding.playPauseIvOpro.setEnabled(true);
                }
            }
        });


        profileBioListener = new OtherProfileActivity.UserProfileBioListener() {
            @Override
            public void onBioPlaying(boolean isBioPlaying) {

                Log.w(UserProfileFragment.class.getSimpleName(),
                        "UserProfileBioListener : isBioPlaying :->" + isBioPlaying);

                if (isBioPlaying)
                    followingAdapter.setDisableAll();
                else
                    followingAdapter.setEnableAll();
            }
        };
        followingAdapter.setOtherBioListener(profileBioListener);
    }

    ////////////////************************//////////////

    /*
     * **************************
     * //TODO: PLAY BIO RECORDING
     * **************************
     * */
    private void doJustPlayStopPlayBio() {
        ImageView img_play_pause = binding.playPauseIvOpro;
        final TextView tv_duration = binding.durationTxt;

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

                        if (profileBioListener != null)
                            profileBioListener.onBioPlaying(false);
                    }
                }.start();


            } catch (Exception e) {
                e.printStackTrace();
            }

            img_play_pause.setImageDrawable(ContextCompat.getDrawable(OtherProfileActivity.this,
                    R.drawable.home_pause_a));
            isBioPlayCheck = true;


        } else {

            if (bio_mediaPlayer != null)
                bio_mediaPlayer.pause();

            img_play_pause.setImageDrawable(ContextCompat.getDrawable(OtherProfileActivity.this,
                    R.drawable.home_play_a));
            isBioPlayCheck = false;


            tv_duration.setText("00:00");

            if (bio_timer != null) {
                bio_timer.cancel();
                bio_timer = null;
            }

            resetSiriView();    //reset SIRI

            if (profileBioListener != null)
                profileBioListener.onBioPlaying(false);

        }
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


        if (profileBioListener != null)
            profileBioListener.onBioPlaying(true);

        //SIRI LIKE VIEW
        startSetUpSiriView(true);  // BIO


    }


    //        PLAY LAST RECORDING snippet
    private void snippetOfPlayLastRecBio() {

        if (bio_audioFilePath != null && !bio_audioFilePath.isEmpty()) {

            final ImageView img_play_pause = binding.playPauseIvOpro;
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
                        img_play_pause.setImageDrawable(ContextCompat.getDrawable(OtherProfileActivity.this,
                                R.drawable.home_play_a));
                        isBioPlayCheck = false;


                        if (profileBioListener != null)
                            profileBioListener.onBioPlaying(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }


    }


    // TODO: POSTS LIST
    private void setUpRecyclerPost(List<PostListResponce.DataBean> postList) {

        if (postList != null && postList.size() > 0) {
            binding.recyclerViewProfileList.setVisibility(View.VISIBLE);
            binding.tvNoDataOPro.setVisibility(View.GONE);

            for (int i = 0; i < postList.size(); i++) {
                PostListResponce.DataBean dataBean = postList.get(i);
                dataBean.setPlayPauseEnable(true);
            }

            adapter = new OtherProfPostsAdapter(this, postList, OtherProfPostsAdapter.OTHER_PROFILE);

            binding.recyclerViewProfileList.setLayoutManager(new LinearLayoutManager(this));
            binding.recyclerViewProfileList.setAdapter(adapter);

            adapter.setListener(new OtherProfPostsAdapter.OtherProfileInterface() {
                @Override
                public void onUserClick(View v, int pos) {
                    if (adapter.getArrayList() != null && adapter.getArrayList().size() > 0) {

                        PostListResponce.DataBean
                                bean = adapter.getArrayList().get(pos);
                        if (bean != null) {
                            String url = bean.getAudio_url();
                        }
                    }
                }

                @Override
                public void onLikeClick(View v, int pos) {
                    if (adapter.getArrayList() != null && adapter.getArrayList().size() > 0) {

                        PostListResponce.DataBean
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
                        final String userLoginId =
                                SPreferenceWriter.getInstance(OtherProfileActivity.this).getString(SPreferenceKey.USERID);
                        PostListResponce.DataBean
                                bean = adapter.getArrayList().get(pos);
                        if (bean != null) {
                            String id = bean.getUser_id();
                            String fullName = bean.getFullname();
                            String position = String.valueOf(pos);

                            String username = bean.getUsername();
                            String user_image = bean.getUser_image();
                            String topic_name = bean.getTopic_name();
                            String post_id = bean.getPost_id();
                            String postUrl = bean.getAudio_url();
                            String postName = bean.getDescription();


                           /* showDialogCommentReplyRecordAudio(true,
                                    OtherProfileActivity.this, post_id); //COMMENTS record audio dialog
                       */
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

                        PostListResponce.DataBean
                                bean = adapter.getArrayList().get(pos);
                        if (bean != null) {
                            String url = bean.getAudio_url();
                            if (url != null && !url.isEmpty()) {
                                dispatchShare(url);

                            } else {
                                Toast.makeText(OtherProfileActivity.this, "Url empty", Toast.LENGTH_SHORT).show();
                            }
                        }


                    }

                }

                @Override
                public void onReplyClick(View v, int pos) {
                    if (adapter.getArrayList() != null && adapter.getArrayList().size() > 0) {

                        PostListResponce.DataBean
                                bean = adapter.getArrayList().get(pos);
                        if (bean != null) {
                            String username = bean.getUsername();
                            String user_image = bean.getUser_image();
                            String topic_name = bean.getTopic_name();
                            String post_id = bean.getPost_id();

                            showDialogCommentReplyRecordAudio(false,
                                    OtherProfileActivity.this, post_id); //REPLY record audio dialog
                        }
                    }
                }

                @Override
                public void onRepliesTextClick(View v, int pos) {
                    if (adapter.getArrayList() != null && adapter.getArrayList().size() > 0) {

                        /*PostListResponce.DataBean
                                bean = adapter.getArrayList().get(pos);
                        if (bean != null) {

                            String id = bean.getPost_id();
                            Intent i = new Intent(OtherProfileActivity.this, ReplyActivity.class);
                            i.putExtra("audioid", id);
                            startActivityForResult(i, 125);
                        }*/
                    }
                }
            });


            adapter.setProfilePostPlayingListener(new OtherProfPostsAdapter.OtherProfPostPlayingListener() {
                @Override
                public void onPostPlaying(boolean isPostPlaying) {
                    if (isPostPlaying) {
                        binding.playPauseIvOpro.setEnabled(false);
                    } else {
                        binding.playPauseIvOpro.setEnabled(true);
                    }
                }
            });


            profileBioListener = new UserProfileBioListener() {
                @Override
                public void onBioPlaying(boolean isBioPlaying) {
                    Log.w(UserProfileFragment.class.getSimpleName(),
                            "UserProfileBioListener : isBioPlaying :->" + isBioPlaying);

                    if (isBioPlaying)
                        adapter.setDisableAll();
                    else
                        adapter.setEnableAll();
                }
            };
            adapter.setProfileBioListener(profileBioListener);

        } else {
            binding.recyclerViewProfileList.setVisibility(View.GONE);
            binding.tvNoDataOPro.setVisibility(View.VISIBLE);
        }
    }


    private void dispatchShare(String url) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "Escalate");

        i.putExtra(Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(i, "Choose one"));
    }


    private void setProfilePicSmall(final ProgressBar progressBar,
                                    final String imageUri, final ImageView imageView) {

        progressBar.setVisibility(View.VISIBLE);

        Glide.with(OtherProfileActivity.this.getApplicationContext())
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


    private boolean validationPostCommentAudio(Dialog dialog) {
        EditText et_description = dialog.findViewById(R.id.et_description);
        if (et_description.getText().toString().isEmpty()) {
            Toast.makeText(OtherProfileActivity.this, "Enter description", Toast.LENGTH_SHORT).show();
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
                                ActivityCompat.requestPermissions(OtherProfileActivity.this,
                                        permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_ALL_PERMISSIONS);
                            }
                        });
                return;
            }
            ActivityCompat.requestPermissions(OtherProfileActivity.this,
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

            if (ContextCompat.checkSelfPermission(OtherProfileActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                if (!ActivityCompat.shouldShowRequestPermissionRationale(OtherProfileActivity.this, permission))
                    return false;
            }
        }
        // Pre-Marshmallow
        return true;
    }

    //    Handle "Don't / Never Ask Again" when asking permission
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(OtherProfileActivity.this)
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
            Toast.makeText(OtherProfileActivity.this, "SD CARD not available.", Toast.LENGTH_SHORT).show();
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

    public void startTimerComments() {
        comment_startTime = SystemClock.uptimeMillis();
        comment_handler.postDelayed(comment_up_timerRunnable, 0);

    }


    private void scaleAnimationComments(final ImageView recordBtn) {

        final Interpolator interpolador;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            interpolador = AnimationUtils.loadInterpolator(OtherProfileActivity.this,
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
                        tv_duration.setText(bio_time_duration);

                        resetSiriView();    //reset SIRI
                    }
                }.start();


            } catch (Exception e) {
                e.printStackTrace();
            }

            img_play_pause.setImageDrawable(ContextCompat.getDrawable(OtherProfileActivity.this,
                    R.drawable.create_memo_pause));
            isCommentPlayCheck = true;


        } else {

            if (comment_mediaPlayer != null)
                comment_mediaPlayer.pause();

            img_play_pause.setImageDrawable(ContextCompat.getDrawable(OtherProfileActivity.this,
                    R.drawable.create_memo_play));
            isCommentPlayCheck = false;


            tv_duration.setText("00:00");
            tv_duration.setText(bio_time_duration);

            if (comment_timer != null) {
                comment_timer.cancel();
                comment_timer = null;
            }

            resetSiriView();    //reset SIRI

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
                img_play_pause.setImageDrawable(ContextCompat.getDrawable(OtherProfileActivity.this,
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
            if (isBio)
                updaterThread = new SiriUpdateThread(30, binding.siriDViewOprofile, OtherProfileActivity.this);
            else
                updaterThread = new SiriUpdateThread(30, siriDView, OtherProfileActivity.this);

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


    public void resetUserAllThreadsPlayers() {

        try {
            //POST LIST
            if (adapter != null) {
                adapter.resetAllOtherPrevious();
            }

            //FOLLOWERS
            if (followerAdapter != null) {
                followerAdapter.resetAllOtherPrevious();
            }

            //FOLLOWINGS
            if (followingAdapter != null) {
                followingAdapter.resetAllOtherPrevious();
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

            if (binding.playPauseIvOpro != null && binding.durationTxt != null) {

                ImageView img_play_pause = binding.playPauseIvOpro;
                final TextView tv_duration = binding.durationTxt;

                img_play_pause.setImageDrawable(ContextCompat.getDrawable(OtherProfileActivity.this,
                        R.drawable.home_play_a));
                isBioPlayCheck = false;
                tv_duration.setText("00:00");
                tv_duration.setText(bio_time_duration);
            }
            if (bio_timer != null) {
                bio_timer.cancel();
                bio_timer = null;
            }
            if (profileBioListener != null)
                profileBioListener.onBioPlaying(false);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    //ALL API
    private void postAudioServiceComments(final boolean isCommenting, final Dialog the_post_dialog, String the_post_or_audio_id,
                                          String the_audio_time,
                                          String the_desc) {
        try {

            String token = SPreferenceWriter.getInstance(OtherProfileActivity.this).getString(SPreferenceKey.TOKEN);
            String userid = SPreferenceWriter.getInstance(OtherProfileActivity.this).getString(SPreferenceKey.USERID);
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

            MyDialog.getInstance(OtherProfileActivity.this).showDialog(OtherProfileActivity.this);

            Call<ReplyCommentResponse> call = apiInterface.commentOnAudioReq(map, profilePartAudio);
            call.enqueue(new retrofit2.Callback<ReplyCommentResponse>() {
                @Override
                public void onResponse(@NonNull Call<ReplyCommentResponse> call,
                                       Response<ReplyCommentResponse> response) {
                    MyDialog.getInstance(OtherProfileActivity.this).hideDialog();
                    if (response.isSuccessful()) {
                        ReplyCommentResponse responseBody = response.body();

                        if (responseBody.getStatus().equalsIgnoreCase("SUCCESS")) {


                            if (isCommenting)
                                Toast.makeText(OtherProfileActivity.this, "Commented on post successfully", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(OtherProfileActivity.this, "Replied on post successfully", Toast.LENGTH_SHORT).show();

                            the_post_dialog.dismiss();


                            if (Util.showInternetAlert(OtherProfileActivity.this)) {
                                postListService();  //in commenting api
                            }

                        } else {
                            Toast.makeText(OtherProfileActivity.this, "" + responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(OtherProfileActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ReplyCommentResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(OtherProfileActivity.this).hideDialog();
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


    private void postListService() {
        try {
            MyDialog.getInstance(this).showDialog(this);
            String useridLogin = SPreferenceWriter.getInstance(this).getString(SPreferenceKey.USERID);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<PostListResponce> call = apiInterface.post_list_by_id(userId, useridLogin);

            call.enqueue(new retrofit2.Callback<PostListResponce>() {
                @Override
                public void onResponse(@NonNull Call<PostListResponce> call,
                                       @NonNull Response<PostListResponce> response) {
                    MyDialog.getInstance(OtherProfileActivity.this).hideDialog();
                    if (response.isSuccessful()) {
                        PostListResponce postListResponce = response.body();

                        if (postListResponce.getStatus().equalsIgnoreCase("SUCCESS")) {
                            postList = postListResponce.getData();

                            setUpRecyclerPost(postList);

                        }
                    }

                }

                @Override
                public void onFailure(Call<PostListResponce> call, Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(OtherProfileActivity.this).hideDialog();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            MyDialog.getInstance(OtherProfileActivity.this).hideDialog();
        }
    }

    private void viewProfileService(String postId) {

        try {

            MyDialog.getInstance(this).showDialog(this);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<ViewProfileResponce> call = apiInterface.view_profile(postId, SPreferenceWriter.getInstance(this).getString(SPreferenceKey.USERID));
            call.enqueue(new retrofit2.Callback<ViewProfileResponce>() {
                @Override
                public void onResponse(@NonNull Call<ViewProfileResponce> call,
                                       Response<ViewProfileResponce> response) {
                    if (response.isSuccessful()) {
                        ViewProfileResponce loginResponse = response.body();

                        if (loginResponse.getStatus().equalsIgnoreCase("SUCCESS")) {

                            userID = loginResponse.getData().getUser_id();
                            binding.tvUserName.setText(loginResponse.getData().getFullname());
                            binding.tvAtUser.setText("@" + loginResponse.getData().getUsername());
                            binding.txtPostsCount.setText(loginResponse.getData().getNum_of_post());
                            //txtGenre.setText(SPreferenceWriter.getInstance(OtherProfileActivity.this).getString(SPreferenceKey.GENRE));
                            binding.tvGeneric.setText(loginResponse.getData().getTopic_id());
                            binding.txtCountFolowers.setText(loginResponse.getData().getNumber_of_followers());
                            binding.txtCountFollowing.setText(loginResponse.getData().getNumber_of_following());
                            fireBaseChatUserId = loginResponse.getData().getFirebase_id();
                            userName = loginResponse.getData().getFullname();
                            chatUserImg = loginResponse.getData().getImage();
                            String audioUrl = loginResponse.getData().getBio();
                            mediaPlayer = MediaPlayer.create(OtherProfileActivity.this, Uri.parse(audioUrl));

                            flagCheck = loginResponse.getData().getFlag_follow();

                            if (flagCheck != null)

                                if (flagCheck.equals("0")) {
                                    binding.followLayOprofile.setBackground(ContextCompat.getDrawable(OtherProfileActivity.this, R.drawable.btn_gradient));
                                    binding.txtFollowOprofile.setText("Follow");
                                } else {
                                    binding.followLayOprofile.setBackground(ContextCompat.getDrawable(OtherProfileActivity.this, R.drawable.btn_follow_green_gradient));
                                    binding.txtFollowOprofile.setText("UnFollow");
                                }

                            String bio = loginResponse.getData().getBio();
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
                            binding.durationTxt.setText(bio_duration);

                            if (loginResponse.getData().getImage() != null) {
                                if (!(loginResponse.getData().getImage()).isEmpty()) {

                                    Picasso.get().load(loginResponse.getData().getImage())
                                            .resize(100, 100).centerCrop(Gravity.CENTER).into(binding.imgUserOprofile);
                                } else {
                                    binding.imgUserOprofile.setImageResource(R.drawable.default_image);
                                }
                            }
                        } else {

                            Toast.makeText(OtherProfileActivity.this, "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(OtherProfileActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
                    }
                    MyDialog.getInstance(OtherProfileActivity.this).hideDialog();

                }

                @Override
                public void onFailure(@NonNull Call<ViewProfileResponce> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(OtherProfileActivity.this).hideDialog();
                }
            });

        } catch (Exception e) {
            MyDialog.getInstance(OtherProfileActivity.this).hideDialog();
            e.printStackTrace();
        }
    }

    private void postLikeServiceApi(final int position, String audioid, final String statusToLike) {

        try {
            MyDialog.getInstance(this).showDialog(this);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<LikeResponce> call = apiInterface
                    .like_post(SPreferenceWriter.getInstance(this).getString(SPreferenceKey.TOKEN),
                            SPreferenceWriter.getInstance(this).getString(SPreferenceKey.USERID),
                            audioid);
            Log.e(".....", "signUpService: " + call.request().toString());

            call.enqueue(new retrofit2.Callback<LikeResponce>() {
                @Override
                public void onResponse(@NonNull Call<LikeResponce> call,
                                       @NonNull Response<LikeResponce> response) {
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

                                PostListResponce.DataBean model = adapter.getArrayList().get(position);

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
                                /*else{
                                    String like_count = adapter.getArrayList().get(position).getLike_count();
                                    if(like_count!=null && !like_count.isEmpty()){

                                        int lk = Integer.parseInt(like_count);
                                        lk = lk+1;
                                        adapter.getArrayList().get(position).setLike_count(String.valueOf(lk));
                                    }
                                    else {
                                        int lk = 0;
                                        lk = lk+1;
                                        adapter.getArrayList().get(position).setLike_count(String.valueOf(lk));
                                    }
                                }*/

                                model.setLike_flag(status);
                                adapter.notifyDataSetChanged();

                            } else {
                                // postListService();
                            }
                            //adapter.notifyDataSetChanged();
                        } else {

                            //Toast.makeText(this, "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(OtherProfileActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                    MyDialog.getInstance(OtherProfileActivity.this).hideDialog();

                }

                @Override
                public void onFailure(@NonNull Call<LikeResponce> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(OtherProfileActivity.this).hideDialog();
                    String s = "";
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void followerListService() {
        try {
            MyDialog.getInstance(this).showDialog(this);
            String useridLogin = SPreferenceWriter.getInstance(this).getString(SPreferenceKey.USERID);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<FollowReponce> call = apiInterface.follower_list(userId, useridLogin);

            call.enqueue(new retrofit2.Callback<FollowReponce>() {
                @Override
                public void onResponse(Call<FollowReponce> call, Response<FollowReponce> response) {
                    MyDialog.getInstance(OtherProfileActivity.this).hideDialog();
                    if (response.isSuccessful()) {
                        FollowReponce followResponce = response.body();
                        Log.e("response", "" + followResponce.toString());
                        if (followResponce.getStatus().equalsIgnoreCase("SUCCESS")) {
                            followerList = followResponce.getData();

                            if (followerList != null && followerList.size() > 0) {

                                binding.txtCountFolowers.setText("" + followerList.size());
                                recyclerFollower(followerList);
                            } else {
                                binding.recyclerViewProfileList.setVisibility(View.GONE);
                                binding.tvNoDataOPro.setVisibility(View.VISIBLE);
                                binding.txtCountFolowers.setText("" + 0);
//                                Toast.makeText(OtherProfileActivity.this, "No Followers", Toast.LENGTH_SHORT).show();
                            }

//                            recyclerFollower(followerList);
//                            if (followerList.size() < 1) {
//                                Toast.makeText(OtherProfileActivity.this, "No Followers!", Toast.LENGTH_SHORT).show();
//                            }

                        } else {
                        }
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<FollowReponce> call, Throwable t) {
                    MyDialog.getInstance(OtherProfileActivity.this).hideDialog();
                    t.printStackTrace();
                }
            });

        } catch (Exception e) {
            MyDialog.getInstance(OtherProfileActivity.this).hideDialog();
            e.printStackTrace();
        }
    }

    private void followingListService() {
        try {

            MyDialog.getInstance(this).showDialog(this);
            String useridLogin = SPreferenceWriter.getInstance(this).getString(SPreferenceKey.USERID);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<FollowReponce> call = apiInterface.following_list(userId, useridLogin);

            call.enqueue(new retrofit2.Callback<FollowReponce>() {
                @Override
                public void onResponse(Call<FollowReponce> call,
                                       Response<FollowReponce> response) {
                    MyDialog.getInstance(OtherProfileActivity.this).hideDialog();
                    if (response.isSuccessful()) {
                        FollowReponce followResponce = response.body();
                        Log.e("response", "" + followResponce.toString());
                        if (followResponce.getStatus().equalsIgnoreCase("SUCCESS")) {
                            followingList = followResponce.getData();

                            if (followingList != null && followingList.size() > 0) {
                                binding.txtCountFollowing.setText("" + followingList.size());
                                recyclerFollowing(followingList);
                            } else {
                                binding.recyclerViewProfileList.setVisibility(View.GONE);
                                binding.tvNoDataOPro.setVisibility(View.VISIBLE);
                                binding.txtCountFollowing.setText("" + 0);
//                                Toast.makeText(OtherProfileActivity.this, "No Followings", Toast.LENGTH_SHORT).show();
                            }

//
//                            followerList = followResponce.getData();
//                            recyclerFollowing(followerList);
//                            if (followerList.size() < 1) {
//                                Toast.makeText(OtherProfileActivity.this, "No Followings!", Toast.LENGTH_SHORT).show();
//                            }

                        }
                    }
                }

                @Override
                public void onFailure(Call<FollowReponce> call, Throwable t) {
                    MyDialog.getInstance(OtherProfileActivity.this).hideDialog();
                    t.printStackTrace();
                }
            });

        } catch (Exception e) {
            MyDialog.getInstance(OtherProfileActivity.this).hideDialog();
            e.printStackTrace();
        }
    }

    private void fUnfServiceInFollowings(final int position, String followId) {
        try {

            String userId = SPreferenceWriter.getInstance(OtherProfileActivity.this).getString(SPreferenceKey.USERID);
            String token = SPreferenceWriter.getInstance(OtherProfileActivity.this).getString(SPreferenceKey.TOKEN);
            MyDialog.getInstance(OtherProfileActivity.this).showDialog(OtherProfileActivity.this);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<SampleResponce> call = apiInterface.update_follower(userId, followId, token);

            call.enqueue(new retrofit2.Callback<SampleResponce>() {
                @Override
                public void onResponse(Call<SampleResponce> call, Response<SampleResponce> response) {
                    MyDialog.getInstance(OtherProfileActivity.this).hideDialog();
                    if (response.isSuccessful()) {
                        SampleResponce sampleResponce = response.body();

                        //FOLLOWINGS

                        if (sampleResponce.getStatus().equalsIgnoreCase("SUCCESS")) {

                            String status = followingAdapter.getArrayList().get(position).getFollower_flag();
                            if (status.equalsIgnoreCase("1")) {
                                status = "0";
                            } else {
                                status = "1";
                            }

                            if (followingAdapter != null) {
                                FollowReponce.DataBean model = followingAdapter.getArrayList().get(position);
                                model.setFollower_flag(status);
                                followingAdapter.notifyDataSetChanged();

                                if (followingAdapter.getArrayList() != null
                                        && followingAdapter.getArrayList().size() == 1) {
                                    FollowReponce.DataBean bean = followingAdapter.getArrayList().get(0);
                                    if (bean.getFollower_flag().equalsIgnoreCase("0")) {
                                        binding.txtCountFollowing.setText("" + 0);
                                        followingAdapter.removeItem(position);
                                        followingAdapter.notifyDataSetChanged();
                                    }

                                }

                                if (Util.showInternetAlert(OtherProfileActivity.this)) {
                                    followerListService();  //again
                                }

                                if (Util.showInternetAlert(OtherProfileActivity.this)) {
                                    followingListService();  //again
                                }

                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<SampleResponce> call, Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(OtherProfileActivity.this).hideDialog();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void fUnfServiceInFollowers(final int position, String followId) {
        try {

            String userId = SPreferenceWriter.getInstance(OtherProfileActivity.this).getString(SPreferenceKey.USERID);
            String token = SPreferenceWriter.getInstance(OtherProfileActivity.this).getString(SPreferenceKey.TOKEN);
            MyDialog.getInstance(OtherProfileActivity.this).showDialog(OtherProfileActivity.this);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<SampleResponce> call = apiInterface.update_follower(userId, followId, token);

            call.enqueue(new retrofit2.Callback<SampleResponce>() {
                @Override
                public void onResponse(Call<SampleResponce> call,
                                       Response<SampleResponce> response) {
                    MyDialog.getInstance(OtherProfileActivity.this).hideDialog();
                    if (response.isSuccessful()) {
                        SampleResponce sampleResponce = response.body();

                        //FOLLOWERS
                        if (sampleResponce.getStatus().equalsIgnoreCase("SUCCESS")) {
                            String status = followerAdapter.getArrayList().get(position).getFollower_flag();
                            if (status.equalsIgnoreCase("1")) {
                                status = "0";
                            } else {
                                status = "1";
                            }

                            if (followerAdapter != null) {
                                FollowReponce.DataBean model = followerAdapter.getArrayList().get(position);
                                model.setFollower_flag(status);
                                followerAdapter.notifyDataSetChanged();

                                if (followerAdapter.getArrayList() != null
                                        && followerAdapter.getArrayList().size() == 1) {
                                    FollowReponce.DataBean bean = followerAdapter.getArrayList().get(0);
                                    if (bean.getFollower_flag().equalsIgnoreCase("0")) {
                                        binding.txtCountFolowers.setText("" + 0);
                                        followerAdapter.removeItem(position);
                                        followerAdapter.notifyDataSetChanged();
                                    }
                                }


                                if (Util.showInternetAlert(OtherProfileActivity.this)) {
                                    followingListService();  //again
                                }

                                if (Util.showInternetAlert(OtherProfileActivity.this)) {
                                    followerListService();  //again
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<SampleResponce> call, Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(OtherProfileActivity.this).hideDialog();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void updateFollower(final int position, String followerId, final String check) {

        try {

            String userId = SPreferenceWriter.getInstance(this).getString(SPreferenceKey.USERID);
            String token = SPreferenceWriter.getInstance(this).getString(SPreferenceKey.TOKEN);
            MyDialog.getInstance(OtherProfileActivity.this).showDialog(this);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<SampleResponce> call = apiInterface.update_follower(userId, followerId, token);

            call.enqueue(new retrofit2.Callback<SampleResponce>() {
                @Override
                public void onResponse(@NonNull Call<SampleResponce> call,
                                       @NonNull Response<SampleResponce> response) {
                    MyDialog.getInstance(OtherProfileActivity.this).hideDialog();
                    if (response.isSuccessful()) {
                        SampleResponce sampleResponce = response.body();
                        Log.e("resetPassward", "onResponse: " + sampleResponce.toString());

                        if (sampleResponce.getStatus().equalsIgnoreCase("SUCCESS")) {

                            if (check.equals("upFollow")) {
                                String status = sampleResponce.getData().getStatus();
                                if (status.equals("Unfollowed")) {
                                    binding.followLayOprofile.setBackground(ContextCompat.getDrawable(OtherProfileActivity.this, R.drawable.btn_gradient));
                                    binding.txtFollowOprofile.setText("Follow");
                                } else {
                                    binding.followLayOprofile.setBackground(ContextCompat.getDrawable(OtherProfileActivity.this, R.drawable.btn_follow_green_gradient));
                                    binding.txtFollowOprofile.setText("UnFollow");
                                }
                            } else if (check.equals("listFollow")) {
                                String status = followerAdapter.getArrayList().get(position).getFollower_flag();
                                if (sampleResponce.getStatus().equalsIgnoreCase("SUCCESS")) {

                                    if (status.equalsIgnoreCase("1")) {
                                        status = "0";
                                    } else {
                                        status = "1";
                                    }

                                    if (followerAdapter != null) {
                                        FollowReponce.DataBean model = followerAdapter.getArrayList().get(position);
                                        model.setFollower_flag(status);
                                        followerAdapter.notifyDataSetChanged();
                                    }
                                }
                            } else {
                                String status = followingAdapter.getArrayList().get(position).getFollower_flag();
                                if (sampleResponce.getStatus().equalsIgnoreCase("SUCCESS")) {

                                    if (status.equalsIgnoreCase("1")) {
                                        status = "0";
                                    } else {
                                        status = "1";
                                    }

                                    if (followingAdapter != null) {
                                        FollowReponce.DataBean model = followingAdapter.getArrayList().get(position);
                                        model.setFollower_flag(status);
                                        followingAdapter.notifyDataSetChanged();
                                    }
                                }
                            }

                        } else {
                        }
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<SampleResponce> call, Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(OtherProfileActivity.this).hideDialog();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public interface UserProfileBioListener {

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

            comment_convertedTime = converted_time;
            tv_comment_timer.setText(converted_time);
            comment_handler.postDelayed(this, 0);
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

    private void serviceReportUser() {

        try {

            MyDialog.getInstance(OtherProfileActivity.this).showDialog(this);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<ReportedUserModel> call = apiInterface.requestReportUser(SPreferenceWriter.getInstance(OtherProfileActivity.this).getString(SPreferenceKey.TOKEN), SPreferenceWriter.getInstance(getApplicationContext()).getString(SPreferenceKey.USERID), userId);

            call.enqueue(new retrofit2.Callback<ReportedUserModel>() {
                @Override
                public void onResponse(@NonNull Call<ReportedUserModel> call, @NonNull Response<ReportedUserModel> response) {
                    MyDialog.getInstance(OtherProfileActivity.this).hideDialog();
                    if (response.isSuccessful()) {
                        ReportedUserModel ReportedUserModel = response.body();
                        Log.e("", "onResponse: " + ReportedUserModel.toString());

                        if (ReportedUserModel.getStatus().equalsIgnoreCase("SUCCESS")) {
                            Toast.makeText(OtherProfileActivity.this, "" + ReportedUserModel.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();

                        } else {
                            Toast.makeText(OtherProfileActivity.this, "" + ReportedUserModel.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(OtherProfileActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ReportedUserModel> call, Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(OtherProfileActivity.this).hideDialog();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void dispatchCommentAct(String userId, String postID, String position, String postUrl, String postName, String fullName) {
        Intent intentCmt = new Intent(OtherProfileActivity.this, CommentAct.class);
        intentCmt.putExtra("user_id", userId);
        intentCmt.putExtra("post_id", postID);
        intentCmt.putExtra("position", position);
        intentCmt.putExtra("post_url", postUrl);
        intentCmt.putExtra("post_name", postName);
        intentCmt.putExtra("full_name", fullName);
        startActivity(intentCmt);
    }


    private void dispatchOtherUserAct(String userId, String postID, int pos) {
        Intent intent = new Intent(this, OtherProfileActivity.class);
        intent.putExtra("user_id", userId);
        intent.putExtra("post_id", postID);
        intent.putExtra("position", pos);
        startActivity(intent);
    }

}