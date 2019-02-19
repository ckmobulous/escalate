package com.escalate.fragments;


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
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
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
import com.escalate.activity.OtherProfileActivity;
import com.escalate.activity.SettingActivity;
import com.escalate.activity.UpdateUserActivity;
import com.escalate.adapter.FollowerAdapter;
import com.escalate.adapter.FollowingAdapter;
import com.escalate.adapter.ProfilePostsAdapter;
import com.escalate.databinding.FragmentUserFrofileBinding;
import com.escalate.model.FarmerList;
import com.escalate.model.FollowReponce;
import com.escalate.model.LikeResponce;
import com.escalate.model.PostListResponce;
import com.escalate.model.ReplyCommentResponse;
import com.escalate.model.SampleResponce;
import com.escalate.model.ViewProfileResponce;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.escalate.utils.AppConstants;
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
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

public class UserProfileFragment extends Fragment implements View.OnClickListener {
    private FragmentUserFrofileBinding binding;
    private List<FarmerList> mFarmerList = new ArrayList<>();
    public static String[] arrStr = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private Fragment fragment;
    private ProfilePostsAdapter adapter;
    private FollowerAdapter followerAdapter;
    private FollowingAdapter followingAdapter;
    private String _AudioSavePathInDevice = null;
    private String statusToLike = "0";
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
    //SIRI VIEW
    private SiriUpdateThread updaterThread;
    private Thread theWThread;
    private DrawView siriDView;
    //BIO
    private String bio_audioFilePath = null;
    private MediaPlayer bio_mediaPlayer = null;
    private boolean isBioPlayCheck = false;
    private CountDownTimer bio_timer = null;
    private long bio_elapsedTime = 0L;
    private String bio_time_duration = "";
    private String post_counts = "0";
    private String countryCodeStr = "";
    private UserProfileBioListener profileBioListener;

    /* @BindView(R.id.txt_apply)
     TextView txtApply;
     @BindView(R.id.txt_cancel)
     TextView txtCancel;*/
    private List<PostListResponce.DataBean> postList;
    private List<FollowReponce.DataBean> followerList;
    private List<FollowReponce.DataBean> followingList;

    public UserProfileFragment() {
        // Required empty public constructor

        if (comment_mediaRecorder != null) {
            comment_mediaRecorder.release();
            comment_mediaRecorder = null;
        }
        if (comment_mediaPlayer != null) {
            comment_mediaPlayer.release();
            comment_mediaPlayer = null;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_frofile, container, false);

        binding.linearPostUsr.setOnClickListener(this);
        binding.linearFollowerUsr.setOnClickListener(this);
        binding.linearFollowingUsr.setOnClickListener(this);
        binding.editProLay.setOnClickListener(this);
        binding.imgPlayPauseUsr.setOnClickListener(this);
        //txtCancel.setOnClickListener(this);
        //txtApply.setOnClickListener(this);
        binding.imgPlayPauseUsr.setOnClickListener(this);

        comment_handler = new Handler();
        postList = new ArrayList<>();
        followerList = new ArrayList<>();
        followerList = new ArrayList<>();

        if (Util.showInternetAlert(getContext())) {
            viewProfile();
        }
        if (Util.showInternetAlert(getContext())) {
            postListService();  //in OnCreateView
        }

        return binding.getRoot();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.linearPostUsr:
                binding.linearPostUsr.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.save_rectangle_back));
                binding.txtPostsCountUsr.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                binding.txtPostUsr.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                binding.linearFollowerUsr.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.save_rectangle_back_white));
                binding.txtCountFolowerU.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                binding.txtFollowerU.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                binding.linearFollowingUsr.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.save_rectangle_back_white));
                binding.txtCountFollowingU.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                binding.txtFollowingU.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

                if (Util.showInternetAlert(getContext())) {
                    postListService();
                }
                break;
            case R.id.linearFollowerUsr:
                binding.linearPostUsr.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.save_rectangle_back_white));
                binding.txtPostsCountUsr.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                binding.txtPostUsr.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                binding.linearFollowerUsr.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.save_rectangle_back));
                binding.txtCountFolowerU.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                binding.txtFollowerU.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                binding.linearFollowingUsr.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.save_rectangle_back_white));
                binding.txtCountFollowingU.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                binding.txtFollowingU.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

                resetUserAllThreadsPlayers();

                if (Util.showInternetAlert(getContext())) {
                    followerListService();
                }

                break;
            case R.id.linearFollowingUsr:
                binding.linearPostUsr.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.save_rectangle_back_white));
                binding.txtPostsCountUsr.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                binding.txtPostUsr.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                binding.linearFollowerUsr.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.save_rectangle_back_white));
                binding.txtCountFolowerU.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                binding.txtFollowerU.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                binding.linearFollowingUsr.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.save_rectangle_back));
                binding.txtCountFollowingU.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                binding.txtFollowingU.setTextColor(ContextCompat.getColor(getContext(), R.color.white));

                resetUserAllThreadsPlayers();

                if (Util.showInternetAlert(getContext())) {
                    followingListService();
                }
                break;
            case R.id.editProLay:
                Intent i = new Intent(getActivity(), UpdateUserActivity.class);
                i.putExtra("the_bio_duration", bio_time_duration);
                i.putExtra("country_code", countryCodeStr);
                startActivityForResult(i, 111);
                /*
                Intent intent1 = new Intent(getContext(), UpdateUserActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("genreList", ClassType);
                intent1.putExtras(bundle);
                startActivityForResult(intent1, 111);
                resumeCheck = true;*/
                break;
           /* case R.id.txt_apply:
                //startActivity(new Intent(getContext(), UpdateUserActivity.class));
                break;
            case R.id.txt_cancel:
                //filterIncludeLayout();
                break;*/
            case R.id.imgPlayPauseUsr:

                if (bio_audioFilePath != null && !bio_audioFilePath.isEmpty()) {

                    //BIO
                    doJustPlayStopPlayBio();//PLAYING BIO
                } else {
                    Toast.makeText(getActivity(), "Update Bio.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

//    //        PLAY LAST RECORDING
//    private void playLastRecording() {
//
//        if (mediaPlayer == null) {
//            snippetOfPlayLastRec();
//        } else {
//            mediaPlayer.release();
//            mediaPlayer = null;
//
//            snippetOfPlayLastRec();
//        }
//    }
//
//    //        PLAY LAST RECORDING snippet
//    private void snippetOfPlayLastRec() {
//        mediaPlayer = new MediaPlayer();
//
//        try {
//            mediaPlayer.setDataSource(_AudioSavePathInDevice);
//            mediaPlayer.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        mediaPlayer.start();
//
//        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mediaPlayer.pause();
//                imgPlayPause.setImageResource(R.drawable.home_play_a);
//                checkPlay = false;
//
//            }
//        });
//
//        //SEEK BAR
//        final int totalTime = mediaPlayer.getDuration();
//        seekBar.setMax(totalTime / 1000);
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                seekBar.setProgress(mediaPlayer.getCurrentPosition() / 1000);
//            }
//        }, 50);
//
//
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if (mediaPlayer != null && !fromUser) {
//                    mediaPlayer.seekTo(progress * 1000);
//                    seekBar.setProgress(progress);
//
//                    Log.w("onProgressChanged", " progress: " + progress);
//                } else {
//                    Log.w("onProgressChanged", " from user: " + fromUser);
//                }
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
////        seekBar.setEnabled(false);
//
//    }
//


//    private void recyclerView(List<PostListResponce.DataBean> postList){
//
//        if(adapter == null) {
//
//            adapter = new HomeAdapter(getContext(), postList);
//            adapter.setListener(this);
//        }
//        tvNoData.setVisibility(View.GONE);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(adapter);
//    }

    private void recyclerFollower(final List<FollowReponce.DataBean> followerList) {

        for (int i = 0; i < followerList.size(); i++) {
            FollowReponce.DataBean dataBean = followerList.get(i);
            dataBean.setPlayPauseEnable(true);
        }

        followerAdapter = new FollowerAdapter(getContext(), followerList, new FollowerAdapter.FollowerAdapterListener() {
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

                Intent intent = new Intent(getActivity(), OtherProfileActivity.class);
                intent.putExtra("user_id", audioid);
                intent.putExtra("post_id", "");
                intent.putExtra("position", position);
                startActivity(intent);
            }

            @Override
            public void onUserClick(int position, FollowReponce.DataBean data) {

                dispatchOtherUserAct(data.getUser_id(), "", position);

            }
        });

        binding.recyVewUserPro.setVisibility(View.VISIBLE);
        binding.tvNoDataUsr.setVisibility(View.GONE);

        binding.recyVewUserPro.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyVewUserPro.setAdapter(followerAdapter);

        followerAdapter.setFfUserBioPlayingListener(new FollowerAdapter.FFUserBioPlayingListener() {
            @Override
            public void onUserBioPlaying(boolean isUserBioPlaying) {

                if (isUserBioPlaying) {
                    binding.imgPlayPauseUsr.setEnabled(false);
                } else {
                    binding.imgPlayPauseUsr.setEnabled(true);
                }
            }
        });


        profileBioListener = new UserProfileBioListener() {
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
        followerAdapter.setProfileBioListener(profileBioListener);
    }

    private void recyclerFollowing(List<FollowReponce.DataBean> followingList) {

        for (int i = 0; i < followingList.size(); i++) {
            FollowReponce.DataBean dataBean = followingList.get(i);
            dataBean.setPlayPauseEnable(true);
        }

        binding.recyVewUserPro.setVisibility(View.VISIBLE);
        binding.tvNoDataUsr.setVisibility(View.GONE);

        followingAdapter = new FollowingAdapter(getContext(), followingList, new FollowingAdapter.FollowingAdapterListener() {
            @Override
            public void followingUnFollowService(int position, String audioid) {
                String followingId = audioid;
                fUnfServiceInFollowings(position, followingId); //SERVICE IN FOLLOWINGS
            }

            @Override
            public void folowingProfileService(int position, String audioid) {

                Intent intent = new Intent(getActivity(), OtherProfileActivity.class);
                intent.putExtra("user_id", audioid);
                intent.putExtra("post_id", "");
                intent.putExtra("position", position);
                startActivity(intent);
            }

            @Override
            public void onUserClick(int position, FollowReponce.DataBean data) {


                dispatchOtherUserAct(data.getUser_id(), "", position);


            }
        });

        binding.recyVewUserPro.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyVewUserPro.setAdapter(followingAdapter);


        followingAdapter.setFfUserBioPlayingListener(new FollowingAdapter.FFUserBioPlayingListener() {
            @Override
            public void onUserBioPlaying(boolean isUserBioPlaying) {

                if (isUserBioPlaying) {
                    binding.imgPlayPauseUsr.setEnabled(false);
                } else {
                    binding.imgPlayPauseUsr.setEnabled(true);
                }
            }
        });

        profileBioListener = new UserProfileBioListener() {
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
        followingAdapter.setProfileBioListener(profileBioListener);
    }


    ////////////////************************//////////////

    /*
     * **************************
     * //TODO: PLAY BIO RECORDING
     * **************************
     * */
    private void doJustPlayStopPlayBio() {
        ImageView img_play_pause = binding.imgPlayPauseUsr;
        final TextView tv_duration = binding.txtTimerUsr;

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
                        tv_duration.setText(bio_time_duration);

                        resetSiriView();    //reset SIRI

                        if (profileBioListener != null)
                            profileBioListener.onBioPlaying(false);
                    }
                }.start();


            } catch (Exception e) {
                e.printStackTrace();
            }

            img_play_pause.setImageDrawable(ContextCompat.getDrawable(getActivity(),
                    R.drawable.home_pause_a));
            isBioPlayCheck = true;


        } else {

            if (bio_mediaPlayer != null)
                bio_mediaPlayer.pause();

            img_play_pause.setImageDrawable(ContextCompat.getDrawable(getActivity(),
                    R.drawable.home_play_a));
            isBioPlayCheck = false;


            tv_duration.setText("00:00");
            tv_duration.setText(bio_time_duration);

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

            final ImageView img_play_pause = binding.imgPlayPauseUsr;
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
                        img_play_pause.setImageDrawable(ContextCompat.getDrawable(getActivity(),
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

    //INTERFACE AS LISTENER
    public interface UserProfileBioListener {

        void onBioPlaying(boolean isBioPlaying);

    }


    // TODO: POSTS LIST
    private void setUpRecyclerPost(List<PostListResponce.DataBean> postList) {

        if (postList != null && postList.size() > 0) {
            binding.recyVewUserPro.setVisibility(View.VISIBLE);
            binding.tvNoDataUsr.setVisibility(View.GONE);

            post_counts = String.valueOf(postList.size());
            binding.txtPostsCountUsr.setText(post_counts);

            for (int i = 0; i < postList.size(); i++) {
                PostListResponce.DataBean dataBean = postList.get(i);
                dataBean.setPlayPauseEnable(true);
            }

            adapter = new ProfilePostsAdapter(getActivity(), postList);

            binding.recyVewUserPro.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recyVewUserPro.setAdapter(adapter);

            adapter.setListener(new ProfilePostsAdapter.ProfileInterface() {
                @Override
                public void onUserClick(View v, int pos) {
                    if (adapter.getArrayList() != null && adapter.getArrayList().size() > 0) {

                        PostListResponce.DataBean
                                bean = adapter.getArrayList().get(pos);

                        if (bean != null) {
                           /* String url = bean.getAudio_url();
                            Intent intent = new Intent(getActivity(), OtherProfileActivity.class);
                            intent.putExtra("user_id", adapter.getArrayList().get(pos).getUser_id());
                            intent.putExtra("post_id", adapter.getArrayList().get(pos).getPost_id());
                            intent.putExtra("position", pos);
                            startActivity(intent);*/

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

                        PostListResponce.DataBean
                                bean = adapter.getArrayList().get(pos);
                        if (bean != null) {
                            String username = bean.getUsername();
                            String user_image = bean.getUser_image();
                            String topic_name = bean.getTopic_name();
                            String post_id = bean.getPost_id();


                            showDialogCommentReplyRecordAudio(true,
                                    getActivity(), post_id); //COMMENTS record audio dialog

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
                                Toast.makeText(getActivity(), "Url empty", Toast.LENGTH_SHORT).show();
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
                                    getActivity(), post_id); //REPLY record audio dialog

                        }
                    }
                }

                @Override
                public void onRepliesTextClick(View v, int pos) {
                    if (adapter.getArrayList() != null && adapter.getArrayList().size() > 0) {

                       /* PostListResponce.DataBean
                                bean = adapter.getArrayList().get(pos);
                        if (bean != null) {

                            String id = bean.getPost_id();
                            Intent i = new Intent(getActivity(), ReplyActivity.class);
                            i.putExtra("audioid", id);
                            startActivityForResult(i, 125);
                        }*/
                    }
                }

                @Override
                public void onOptionDeleteClick(View v, int pos) {
                    if (adapter.getArrayList() != null && adapter.getArrayList().size() > 0) {

                        PostListResponce.DataBean
                                bean = adapter.getArrayList().get(pos);
                        if (bean != null) {

                            String post_id = bean.getPost_id();

                            showDeletePostDialog(
                                    "Do you want to delete this Post?",
                                    post_id,
                                    pos);

                        }
                    }
                }
            });

            adapter.setProfilePostPlayingListener(new ProfilePostsAdapter.ProfilePostPlayingListener() {
                @Override
                public void onPostPlaying(boolean isPostPlaying) {

                    if (isPostPlaying) {
                        binding.imgPlayPauseUsr.setEnabled(false);
                    } else {
                        binding.imgPlayPauseUsr.setEnabled(true);
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
            binding.recyVewUserPro.setVisibility(View.GONE);
            binding.tvNoDataUsr.setVisibility(View.VISIBLE);

            post_counts = "0";
            binding.txtPostsCountUsr.setText(post_counts);
        }
    }


    private void dispatchShare(String url) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "Escalate");

        i.putExtra(Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(i, "Choose one"));
    }


    //    SHOW DELETE POST DIALOG
    private void showDeletePostDialog(String message, String post_id, int pos) {

        resetUserAllThreadsPlayers();   // in delete dialog and service


        DialogInterface.OnClickListener okListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                deletePostService(post_id, pos); //SERVICE HIT..
            }
        };

        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        };

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.app_name))
                        .setIcon(ContextCompat.getDrawable(getActivity(), R.mipmap.ic_launcher))
                        .setMessage(message)
                        .setCancelable(false)
                        .setPositiveButton("YES", okListener)
                        .setNegativeButton("NO", cancelListener);

        AlertDialog dialog = builder.create();
        //   ANIMATION
        Window window = dialog.getWindow();
        if (window != null) {
            window.getAttributes().dimAmount = 0.5F;
        }


        dialog.show();

        // Alert Buttons
        Button positive_button = dialog.getButton(BUTTON_POSITIVE);
        Button negative_button = dialog.getButton(BUTTON_NEGATIVE);

        positive_button.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        negative_button.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));


    }


    private void setProfilePicSmall(final ProgressBar progressBar,
                                    final String imageUri, final ImageView imageView) {

        progressBar.setVisibility(View.VISIBLE);


        Glide.with(getActivity().getApplicationContext())
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

    // **************RECORD DIALOG
    private void showDialogCommentReplyRecordAudio(final boolean isCommenting, final Context context,
                                                   final String post_id) {

        final Dialog dialog = new Dialog(context);

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
                    img_audio_mic.setImageDrawable(ContextCompat.getDrawable(getActivity(),
                            R.drawable.create_memo_hour));
                } else {

                    stopRecordingComments();

                    comment_handler.removeCallbacks(comment_up_timerRunnable);
                    isCommentRecording = false;

                    img_audio_mic.setImageDrawable(ContextCompat.getDrawable(getActivity(),
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
            Toast.makeText(getActivity(), "Enter description", Toast.LENGTH_SHORT).show();
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
                                ActivityCompat.requestPermissions(getActivity(),
                                        permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_ALL_PERMISSIONS);
                            }
                        });
                return;
            }
            ActivityCompat.requestPermissions(getActivity(),
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
                if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission))
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
            Toast.makeText(getActivity(), "SD CARD not available.", Toast.LENGTH_SHORT).show();
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

            comment_convertedTime = converted_time;
            tv_comment_timer.setText(converted_time);
            comment_handler.postDelayed(this, 0);
        }

    };


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
/*
        comment_mediaRecorder = new MediaRecorder();

        comment_mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        comment_mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        comment_mediaRecorder.setOutputFile(comment_audioFilePath);

        comment_mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        comment_mediaRecorder.setAudioEncodingBitRate(16);
        comment_mediaRecorder.setAudioSamplingRate(44100);*/

//////new code/////////

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

                        resetSiriView();    //reset SIRI
                    }
                }.start();


            } catch (Exception e) {
                e.printStackTrace();
            }

            img_play_pause.setImageDrawable(ContextCompat.getDrawable(getActivity(),
                    R.drawable.create_memo_pause));
            isCommentPlayCheck = true;


        } else {

            if (comment_mediaPlayer != null)
                comment_mediaPlayer.pause();

            img_play_pause.setImageDrawable(ContextCompat.getDrawable(getActivity(),
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
                img_play_pause.setImageDrawable(ContextCompat.getDrawable(getActivity(),
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
                updaterThread = new SiriUpdateThread(30, binding.siriDViewUsrPro, getActivity());
            else
                updaterThread = new SiriUpdateThread(30, siriDView, getActivity());

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


    /*API SERVICES*/
    private Map<String, RequestBody> setUpMapDataComments(String the_token,
                                                          String the_user_id,
                                                          String the_post_or_audio_id,
                                                          String the_audio_time,
                                                          String the_desc) {


        /*
        * token:required
          user_id:required
          audio_id:required
          audio:(this is comment audio file)
          msg_duration
          description
        * */

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

    private void postAudioServiceComments(final boolean isCommenting, final Dialog the_post_dialog, String the_post_or_audio_id,
                                          String the_audio_time,
                                          String the_desc) {
        try {

            String token = SPreferenceWriter.getInstance(getActivity()).getString(SPreferenceKey.TOKEN);
            String userid = SPreferenceWriter.getInstance(getActivity()).getString(SPreferenceKey.USERID);
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

            MyDialog.getInstance(getActivity()).showDialog(getActivity());

            Call<ReplyCommentResponse> call = apiInterface.commentOnAudioReq(map, profilePartAudio);
            call.enqueue(new retrofit2.Callback<ReplyCommentResponse>() {
                @Override
                public void onResponse(@NonNull Call<ReplyCommentResponse> call,
                                       Response<ReplyCommentResponse> response) {
                    MyDialog.getInstance(getActivity()).hideDialog();
                    if (response.isSuccessful()) {
                        ReplyCommentResponse responseBody = response.body();

                        if (responseBody.getStatus().equalsIgnoreCase("SUCCESS")) {


                            if (isCommenting)
                                Toast.makeText(getActivity(), "Commented on post successfully", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getActivity(), "Replied on post successfully", Toast.LENGTH_SHORT).show();

                            the_post_dialog.dismiss();


                            if (Util.showInternetAlert(getContext())) {
                                postListService();  //in commenting api
                            }

                        } else {
                            Toast.makeText(getActivity(), "" + responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "Server Error!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ReplyCommentResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(getActivity()).hideDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
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

            if (binding.imgPlayPauseUsr != null && binding.txtTimerUsr != null) {
                ImageView img_play_pause = binding.imgPlayPauseUsr;
                final TextView tv_duration = binding.txtTimerUsr;


                img_play_pause.setImageDrawable(ContextCompat.getDrawable(getActivity(),
                        R.drawable.home_play_a));
                isBioPlayCheck = false;
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

//////////////************************//////////////


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 111) {
            try {
                String status = Objects.requireNonNull(data.getExtras()).getString("status");
                assert status != null;
                if (status.equals("1"))
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, new UserProfileFragment()).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

   /* private void filterIncludeLayout() {

        if (binding.relativeIncludeUsr.getVisibility() == View.VISIBLE) {
            binding.relativeIncludeUsr.setVisibility(View.GONE);
        } else {
            binding.relativeIncludeUsr.setVisibility(View.VISIBLE);
        }
    }*/


    //API SERVICES
    private void viewProfile() {

        try {

            final String userid = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.USERID);
            MyDialog.getInstance(getContext()).showDialog(getActivity());
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<ViewProfileResponce> call = apiInterface.view_profile(userid, SPreferenceWriter.getInstance(getActivity()).getString(SPreferenceKey.USERID));
            Log.e("ViewProfile", "" + call.request().toString());
            call.enqueue(new retrofit2.Callback<ViewProfileResponce>() {
                @Override
                public void onResponse(@NonNull Call<ViewProfileResponce> call,
                                       Response<ViewProfileResponce> response) {
                    MyDialog.getInstance(getContext()).hideDialog();
                    if (response.isSuccessful()) {
                        ViewProfileResponce loginResponse = response.body();
                        assert loginResponse != null;
                        Log.e("loginResponse", "" + loginResponse.toString());

                        if (loginResponse.getStatus().equalsIgnoreCase("SUCCESS")) {

                            binding.nameTxtUsr.setText(loginResponse.getData().getFullname());

                            if (loginResponse.getData().getNum_of_post() != null
                                    && !loginResponse.getData().getNum_of_post().isEmpty()) {
                                post_counts = loginResponse.getData().getNum_of_post();
                            } else {
                                post_counts = "0";
                            }

                            binding.txtPostsCountUsr.setText(post_counts);
                            binding.txtPostsCountUsr.setText(loginResponse.getData().getNum_of_post());
                            binding.txtCountFolowerU.setText(loginResponse.getData().getNumber_of_followers());
                            binding.txtCountFollowingU.setText(loginResponse.getData().getNumber_of_following());

                            binding.txtPhone.setText(loginResponse.getData().getPhone());
                            binding.txtMailUsr.setText(loginResponse.getData().getEmail());
                            binding.txtGenreusr.setText(loginResponse.getData().getTopic_id());
                            SPreferenceWriter.getInstance(getContext())
                                    .writeStringValue(SPreferenceKey.GENRE, loginResponse.getData().getTopic_id());

                            String bio = loginResponse.getData().getBio();
                            String bio_duration = loginResponse.getData().getBio_duration();
                            countryCodeStr = loginResponse.getData().getCountry_code();

                            if (bio != null) {
                                if (!bio.isEmpty()) {
                                    _AudioSavePathInDevice = bio;
                                    bio_audioFilePath = bio;

                                    SPreferenceWriter.getInstance(getContext())
                                            .writeStringValue(SPreferenceKey.BIO, bio);
                                } else {
                                    binding.bioLayUsr.setVisibility(View.INVISIBLE);
                                    SPreferenceWriter.getInstance(getContext())
                                            .writeStringValue(SPreferenceKey.BIO, "");
                                }
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
                            binding.txtTimerUsr.setText(bio_duration);

                            String socialId = loginResponse.getData().getSocialid();
                            String username = loginResponse.getData().getUsername();

                            if (username != null && !username.isEmpty()) {
                                binding.txtUsername.setText(username);
                                SPreferenceWriter.getInstance(getContext())
                                        .writeStringValue(SPreferenceKey.USER_ID_NAME, username);
                            } else {
                                SPreferenceWriter.getInstance(getContext())
                                        .writeStringValue(SPreferenceKey.USER_ID_NAME, "");
                            }

                            if (socialId != null && !socialId.isEmpty()) {
                                //SOCIAL USER
                                SPreferenceWriter.getInstance(getActivity())
                                        .writeStringValue(SPreferenceKey.USER_TYPE, "FB_TYPE");

                            } else {  //normal USER
                                SPreferenceWriter.getInstance(getActivity())
                                        .writeStringValue(SPreferenceKey.USER_TYPE, "NORMAL_TYPE");

                            }

                            if (loginResponse.getData().getImage() != null) {
                                if (!(loginResponse.getData().getImage()).isEmpty()) {

                            /* if (SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.LOGINVIA).equalsIgnoreCase("signup")) {
                                    Picasso.get().load(loginResponse.getData().getImage())
                                            .resize(100, 100).centerCrop(Gravity.CENTER).into(imguser);
                                } else if (SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.LOGINVIA).equalsIgnoreCase("facebook")) {

                                    String fbid = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.FBID);
                                    if(!fbid.isEmpty()){
                                        Picasso.get()
                                                .load("https://graph.facebook.com/" + fbid + "/picture?type=large")
                                                .resize(100, 100).centerCrop(Gravity.CENTER)
                                                .into(imguser);

                                    }
                                }else {
                                    Picasso.get().load(loginResponse.getData().getImage())
                                            .resize(100, 100).centerCrop(Gravity.CENTER).into(imguser);
                                }*/
                                    Picasso.get().load(loginResponse.getData().getImage())
                                            .resize(100, 100).centerCrop(Gravity.CENTER).into(binding.imgUserPro);
                                } else {
                                    binding.imgUserPro.setImageResource(R.drawable.default_image);
                                }
                            }
                        } else {

                            Toast.makeText(getContext(), "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getContext(), "Server Error!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ViewProfileResponce> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(getContext()).hideDialog();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void postListService() {
        try {

            resetUserAllThreadsPlayers();   // in post list service

            MyDialog.getInstance(getContext()).showDialog(getActivity());
            String userid = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.USERID);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<PostListResponce> call = apiInterface.post_list_by_id(userid, userid);

            call.enqueue(new retrofit2.Callback<PostListResponce>() {
                @Override
                public void onResponse(@NonNull Call<PostListResponce> call,
                                       @NonNull Response<PostListResponce> response) {
                    MyDialog.getInstance(getContext()).hideDialog();
                    if (response.isSuccessful()) {
                        PostListResponce postListResponce = response.body();

                        assert postListResponce != null;
                        if (postListResponce.getStatus().equalsIgnoreCase("SUCCESS")) {
                            postList = postListResponce.getData();
                            setUpRecyclerPost(postList);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PostListResponce> call, Throwable t) {
                    MyDialog.getInstance(getContext()).hideDialog();
                    t.printStackTrace();
                }
            });

        } catch (Exception e) {
            MyDialog.getInstance(getContext()).hideDialog();
            e.printStackTrace();
        }
    }

    private void deletePostService(String post_id, int pos) {
        try {
            MyDialog.getInstance(getContext()).showDialog(getActivity());

            String user_id = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.USERID);
            String token = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.TOKEN);

            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<SampleResponce> call = apiInterface.deletePost(post_id, user_id, token);


            call.enqueue(new Callback<SampleResponce>() {
                @Override
                public void onResponse(Call<SampleResponce> call, Response<SampleResponce> response) {
                    MyDialog.getInstance(getContext()).hideDialog();
                    if (response.isSuccessful()) {
                        SampleResponce postListResponce = response.body();

                        Log.w("deletePost response", "" + postListResponce.toString());

                        if (postListResponce.getStatus().equalsIgnoreCase("SUCCESS")) {

                            if (adapter.getArrayList() != null && adapter.getArrayList().size() > 0) {

                                adapter.removeItem(pos);
                                adapter.notifyDataSetChanged();

                                int s = adapter.getArrayList().size();

                                post_counts = String.valueOf(adapter.getArrayList().size());
                                binding.txtPostsCountUsr.setText(post_counts);

                                if (s == 0) {
                                    if (Util.showInternetAlert(getContext())) {
                                        postListService();  //in delete post
                                    }
                                }

                            }

                            Toast.makeText(getActivity(), "Post deleted successfully.", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), AppConstants.kError, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), AppConstants.kMessageSomeWentWrong, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SampleResponce> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(getContext()).hideDialog();
                }
            });

        } catch (Exception e) {
            MyDialog.getInstance(getContext()).hideDialog();
            e.printStackTrace();
        }
    }

    private void followerListService() {
        try {
            MyDialog.getInstance(getContext()).showDialog(getActivity());
            String userid = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.USERID);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<FollowReponce> call = apiInterface.follower_list(userid, userid);

            call.enqueue(new retrofit2.Callback<FollowReponce>() {
                @Override
                public void onResponse(@NonNull Call<FollowReponce> call,
                                       @NonNull Response<FollowReponce> response) {
                    MyDialog.getInstance(getContext()).hideDialog();
                    if (response.isSuccessful()) {
                        FollowReponce followResponce = response.body();
                        assert followResponce != null;
                        if (followResponce.getStatus().equalsIgnoreCase("SUCCESS")) {
                            followerList = followResponce.getData();

                            if (followerList != null && followerList.size() > 0) {

                                binding.txtCountFolowerU.setText("" + followerList.size());
                                recyclerFollower(followerList);
                            } else {
                                binding.recyVewUserPro.setVisibility(View.GONE);
                                binding.tvNoDataUsr.setVisibility(View.VISIBLE);
                                binding.txtCountFolowerU.setText("" + 0);
//                                Toast.makeText(getActivity(), "No Followers", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FollowReponce> call, @NonNull Throwable t) {
                    MyDialog.getInstance(getContext()).hideDialog();
                    t.printStackTrace();
                }
            });

        } catch (Exception e) {
            MyDialog.getInstance(getContext()).hideDialog();
            e.printStackTrace();
        }
    }

    private void followingListService() {
        try {

            MyDialog.getInstance(getContext()).showDialog(getActivity());
            String userid = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.USERID);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<FollowReponce> call = apiInterface.following_list(userid, userid);

            call.enqueue(new retrofit2.Callback<FollowReponce>() {
                @Override
                public void onResponse(@NonNull Call<FollowReponce> call,
                                       @NonNull Response<FollowReponce> response) {
                    MyDialog.getInstance(getContext()).hideDialog();
                    if (response.isSuccessful()) {
                        FollowReponce followResponce = response.body();
                        assert followResponce != null;
                        if (followResponce.getStatus().equalsIgnoreCase("SUCCESS")) {

                            followingList = followResponce.getData();

                            if (followingList != null && followingList.size() > 0) {
                                binding.txtCountFollowingU.setText("" + followingList.size());
                                recyclerFollowing(followingList);
                            } else {
                                binding.recyVewUserPro.setVisibility(View.GONE);
                                binding.tvNoDataUsr.setVisibility(View.VISIBLE);
                                binding.txtCountFollowingU.setText("" + 0);
//                                Toast.makeText(getActivity(), "No Followings", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FollowReponce> call, @NonNull Throwable t) {
                    MyDialog.getInstance(getContext()).hideDialog();
                    t.printStackTrace();
                }
            });

        } catch (Exception e) {
            MyDialog.getInstance(getContext()).hideDialog();
            e.printStackTrace();
        }
    }

//    private void updateFollower(final int position, String followId, final String check) {
//
//        try {
//
//            String userId = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.USERID);
//            String token = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.TOKEN);
//            MyDialog.getInstance(getContext()).showDialog(getActivity());
//            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
//            Call<SampleResponce> call = apiInterface.update_follower(userId, followId, token);
//
//            call.enqueue(new retrofit2.Callback<SampleResponce>() {
//                @Override
//                public void onResponse(Call<SampleResponce> call, Response<SampleResponce> response) {
//                    MyDialog.getInstance(getContext()).hideDialog();
//                    if (response.isSuccessful()) {
//                        SampleResponce sampleResponce = response.body();
//
//                        if (sampleResponce.getStatus().equalsIgnoreCase("SUCCESS")) {
//
//                            //FOLLOWERS
//                            if (check.equals("listFollow")) {
//                                String status = followerAdapter.getArrayList().get(position).getFollower_flag();
//                                if (sampleResponce.getStatus().equalsIgnoreCase("SUCCESS")) {
//
//                                    if (status.equalsIgnoreCase("1")) {
//                                        status = "0";
//                                    } else {
//                                        status = "1";
//                                    }
//
//                                    if (followerAdapter != null) {
//                                        FollowReponce.DataBean model = followerAdapter.getArrayList().get(position);
//                                        model.setFollower_flag(status);
//                                        followerAdapter.notifyDataSetChanged();
//
//                                        if(followerAdapter.getArrayList()!=null
//                                                && followerAdapter.getArrayList().size()==1)
//                                        {
//                                            FollowReponce.DataBean bean = followerAdapter.getArrayList().get(0);
//                                            if (bean.getFollower_flag().equalsIgnoreCase("0")) {
//                                                txtCountFolower.setText("" + 0);
//                                                followerAdapter.removeItem(position);
//                                                followerAdapter.notifyDataSetChanged();
//                                            }
//                                        }
//                                    }
//                                }
//                            } else {
//                                //FOLLOWINGS
//                                String status = followingAdapter.getArrayList().get(position).getFollower_flag();
//                                if (sampleResponce.getStatus().equalsIgnoreCase("SUCCESS")) {
//
//                                    if (status.equalsIgnoreCase("1")) {
//                                        status = "0";
//                                    } else {
//                                        status = "1";
//                                    }
//
//                                    if (followingAdapter != null) {
//                                        FollowReponce.DataBean model = followingAdapter.getArrayList().get(position);
//                                        model.setFollower_flag(status);
//                                        followingAdapter.notifyDataSetChanged();
//
//                                        if (followingAdapter.getArrayList() != null
//                                                && followingAdapter.getArrayList().size() == 1)
//                                        {
//                                            FollowReponce.DataBean bean = followingAdapter.getArrayList().get(0);
//                                            if (bean.getFollower_flag().equalsIgnoreCase("0")) {
//                                                txtCountFollowing.setText("" + 0);
//                                                followingAdapter.removeItem(position);
//                                                followingAdapter.notifyDataSetChanged();
//                                            }
//
//                                        }
//
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<SampleResponce> call, Throwable t) {
//                    t.printStackTrace();
//                    MyDialog.getInstance(getContext()).hideDialog();
//                }
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void fUnfServiceInFollowings(final int position, String followId) {
        try {

            String userId = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.USERID);
            String token = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.TOKEN);
            MyDialog.getInstance(getContext()).showDialog(getActivity());
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<SampleResponce> call = apiInterface.update_follower(userId, followId, token);

            call.enqueue(new retrofit2.Callback<SampleResponce>() {
                @Override
                public void onResponse(Call<SampleResponce> call, Response<SampleResponce> response) {
                    MyDialog.getInstance(getContext()).hideDialog();
                    if (response.isSuccessful()) {
                        SampleResponce sampleResponce = response.body();

                        //FOLLOWINGS

                        if (sampleResponce.getStatus().equalsIgnoreCase("SUCCESS")) {

                            if (followingAdapter != null) {
                                String status = followingAdapter.getArrayList().get(position).getFollower_flag();
                                if (status.equalsIgnoreCase("1")) {
                                    status = "0";
                                } else {
                                    status = "1";
                                }

                                FollowReponce.DataBean model = followingAdapter.getArrayList().get(position);
                                model.setFollower_flag(status);
                                followingAdapter.notifyDataSetChanged();

                                if (followingAdapter.getArrayList() != null
                                        && followingAdapter.getArrayList().size() == 1) {
                                    FollowReponce.DataBean bean = followingAdapter.getArrayList().get(0);
                                    if (bean.getFollower_flag().equalsIgnoreCase("0")) {
                                        binding.txtCountFollowingU.setText("" + 0);
                                        followingAdapter.removeItem(position);
                                        followingAdapter.notifyDataSetChanged();
                                    }

                                }

                                if (Util.showInternetAlert(getActivity())) {
                                    followerListService();  //again
                                }

                                if (Util.showInternetAlert(getActivity())) {
                                    followingListService();  //again
                                }

                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<SampleResponce> call, Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(getContext()).hideDialog();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void fUnfServiceInFollowers(final int position, String followId) {
        try {

            String userId = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.USERID);
            String token = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.TOKEN);
            MyDialog.getInstance(getContext()).showDialog(getActivity());
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<SampleResponce> call = apiInterface.update_follower(userId, followId, token);

            call.enqueue(new retrofit2.Callback<SampleResponce>() {
                @Override
                public void onResponse(Call<SampleResponce> call, Response<SampleResponce> response) {
                    MyDialog.getInstance(getContext()).hideDialog();
                    if (response.isSuccessful()) {
                        SampleResponce sampleResponce = response.body();

                        //FOLLOWERS
                        if (sampleResponce.getStatus().equalsIgnoreCase("SUCCESS")) {

                            if (followerAdapter != null) {
                                String status = followerAdapter.getArrayList().get(position).getFollower_flag();
                                if (status.equalsIgnoreCase("1")) {
                                    status = "0";
                                } else {
                                    status = "1";
                                }

                                FollowReponce.DataBean model = followerAdapter.getArrayList().get(position);
                                model.setFollower_flag(status);
                                followerAdapter.notifyDataSetChanged();

                                if (followerAdapter.getArrayList() != null
                                        && followerAdapter.getArrayList().size() == 1) {
                                    FollowReponce.DataBean bean = followerAdapter.getArrayList().get(0);
                                    if (bean.getFollower_flag().equalsIgnoreCase("0")) {
                                        binding.txtCountFolowerU.setText("" + 0);
                                        followerAdapter.removeItem(position);
                                        followerAdapter.notifyDataSetChanged();
                                    }
                                }

                                if (Util.showInternetAlert(getActivity())) {
                                    followingListService();  //again
                                }

                                if (Util.showInternetAlert(getActivity())) {
                                    followerListService();  //again
                                }

                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<SampleResponce> call, Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(getContext()).hideDialog();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void postLikeServiceApi(final int position, String audioid, final String statusToLike) {

        try {
            MyDialog.getInstance(getContext()).showDialog(getActivity());
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<LikeResponce> call = apiInterface.like_post(SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.TOKEN),
                    SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.USERID),
                    audioid);
            Log.e(".....", "postLikeServiceApi: " + call.request().toString());

            call.enqueue(new retrofit2.Callback<LikeResponce>() {
                @Override
                public void onResponse(Call<LikeResponce> call,
                                       Response<LikeResponce> response) {

                    MyDialog.getInstance(getContext()).hideDialog();
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

                            //Toast.makeText(getContext(), "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<LikeResponce> call, Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(getContext()).hideDialog();
                    String s = "";
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_user_profile_option, menu);

       /* menu.findItem(R.id.action_filter).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                //filterIncludeLayout();
                return false;
            }
        });*/

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_setting) {

            Intent intent = new Intent(getContext(), SettingActivity.class);
            startActivity(intent);
        }

        /*if (id == R.id.action_filter) {
            filterIncludeLayout();
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();

        resetUserAllThreadsPlayers();
    }

    private void dispatchOtherUserAct(String userId, String postID, int pos) {
        Intent intent = new Intent(getContext(), OtherProfileActivity.class);
        intent.putExtra("user_id", userId);
        intent.putExtra("post_id", postID);
        intent.putExtra("position", pos);
        startActivity(intent);
    }
}
