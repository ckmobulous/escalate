package com.escalate.fragments;


import android.Manifest;
import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Location;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
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
import com.escalate.activity.CommentAct;
import com.escalate.activity.NotificationActivity;
import com.escalate.activity.OtherProfileActivity;
import com.escalate.activity.ReplyActivity;
import com.escalate.activity.SplashActivity;
import com.escalate.adapter.HomePostsAdapter;
import com.escalate.databinding.FragmentHomeBinding;
import com.escalate.model.LikeResponce;
import com.escalate.model.PostListResponce;
import com.escalate.model.ReplyCommentResponse;
import com.escalate.model.SampleResponce;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;
import com.escalate.services.FetchAddressService;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.escalate.utils.InternetCheck;
import com.escalate.utils.Util;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

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

import static android.app.Activity.RESULT_OK;
import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

public class HomeFragment extends Fragment implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private FragmentHomeBinding binding;

    ImageView imgAudioMicGlobal;
    Dialog dialog;
    private String postIdGlobal = "";
    public static String[] arrStr = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private String statusToLike = "0";
    private HomePostsAdapter adapter;
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

    private UserHomeBioListener profileBioListener;
    private List<PostListResponce.DataBean> postList;

    private String the_lat = "0.0";
    private String the_lng = "0.0";
    private final String TAG = HomeFragment.class.getSimpleName();

    private Location mLastLocation;
    private LocationRequest locationRequest;
    protected final int REQ_CODE_GPS_SETTINGS = 150;
    private final int REQ_CODE_LOCATION = 107;
    private final int CURRENT_LOCATION_REQ_CODE = 210;

    private LatLng currentLocationLatLngs;
    private double lat = 0.0, lng = 0.0;
    private GoogleApiClient googleApiClient;
    private AddressResultReceiver mResultReceiver;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback locationCallback;

    private boolean isLocServiceStarted = false;
    private boolean isLocLatLngUpdated = false;


    public HomeFragment() {
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
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        comment_handler = new Handler();
        postList = new ArrayList<>();


        //startLocationFunctioning(); //LOCATION FUNCTIONING

        setRefersh();

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.showInternetAlert(getActivity())) {
            postListService();  //in OnCreateView
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (googleApiClient != null) {
            startLocationUpdates();
        }

    }

    @Override
    public void onStop() {
        super.onStop();

        isLocLatLngUpdated = false;

        if (googleApiClient != null) {

            if (googleApiClient.isConnected()) {
                stopLocationUpdates();
                googleApiClient.disconnect();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isLocLatLngUpdated = false;
    }

    public void setRefersh() {
        binding.swipeRefreshHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefreshHome.setRefreshing(false);
                if (Util.showInternetAlert(getContext())) {
                    postListService();  //in OnCreateView
                }
            }
        });
    }


    public void postListServiceHit(Context context) {

        if (context != null) {
            if (HomeFragment.this.isAdded()) {
                if (Util.showInternetAlert(context)) {
                    postListService();  //in OnCreateView
                }
            }
        }
    }

    private void getUserLatLng() {

        the_lat = SPreferenceWriter.getInstance(getActivity())
                .getString(SPreferenceKey.THE_LAT);

        the_lng = SPreferenceWriter.getInstance(getActivity())
                .getString(SPreferenceKey.THE_LNG);

    }

    // SERVICES
    private void postListService() {
        try {
            MyDialog.getInstance(getContext()).showDialog(getActivity());
            String userid = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.USERID);
            String token = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.TOKEN);
            String url = "postlist" + "/" + userid;
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<PostListResponce> call = apiInterface.post_listHome(url, token, userid);

            call.enqueue(new retrofit2.Callback<PostListResponce>() {
                @Override
                public void onResponse(@NonNull Call<PostListResponce> call, @NonNull Response<PostListResponce> response) {
                    if (response.isSuccessful()) {
                        PostListResponce postListResponce = response.body();
                        Log.w("HOME response", "" + postListResponce.toString());

                        if (postListResponce.getStatus().equalsIgnoreCase("SUCCESS")) {
                            postList = postListResponce.getData();

                            if (postListResponce.getData() != null &&
                                    postListResponce.getData().size() > 0) {

                                binding.tvNoData.setVisibility(View.GONE);
                                binding.recyclerViewHome.setVisibility(View.VISIBLE);

                                setUpRecyclerPost(postList);

                            } else {

                                binding.tvNoData.setVisibility(View.VISIBLE);
                                binding.recyclerViewHome.setVisibility(View.GONE);
                            }

                        } else {
                            //"Login Token Expire"
                            if (postListResponce.getMessage().equalsIgnoreCase("Login Token Expire")) {
                                goToSplash();
                            }
                        }
                    } else {

                    }
                    MyDialog.getInstance(getContext()).hideDialog();
                }

                @Override
                public void onFailure(@NonNull Call<PostListResponce> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(getContext()).hideDialog();
                }
            });

        } catch (Exception e) {
            MyDialog.getInstance(getContext()).hideDialog();
            e.printStackTrace();
        }
    }


    private void updateLatLngService() {
        try {
            MyDialog.getInstance(getContext()).showDialog(getActivity());

            String user_id = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.USERID);

            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<SampleResponce> call = apiInterface.latLngUpdate("0.0", "0.0", user_id);


            call.enqueue(new Callback<SampleResponce>() {
                @Override
                public void onResponse(Call<SampleResponce> call, @NonNull Response<SampleResponce> response) {
                    MyDialog.getInstance(getContext()).hideDialog();
                    if (response.isSuccessful()) {
                        SampleResponce postListResponce = response.body();

                        Log.w("HOME LAT LNG response", "" + postListResponce.toString());

                        if (postListResponce.getStatus().equalsIgnoreCase("SUCCESS")) {

                            isLocLatLngUpdated = true;
                            postListServiceHit(getActivity());  //SERVICE HIT..

                        } else {
                            isLocLatLngUpdated = false;
                            //"Login Token Expire"
                            if (postListResponce.getMessage().equalsIgnoreCase("Login Token Expire")) {
                                goToSplash();
                            }
                        }
                    } else {
                        isLocLatLngUpdated = false;
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


    ////////////////************************//////////////

    //INTERFACE AS LISTENER
    public interface UserHomeBioListener {

        void onBioPlaying(boolean isBioPlaying);

    }


    // TODO: POSTS LIST
    private void setUpRecyclerPost(List<PostListResponce.DataBean> postList) {

        final String userLoginId =
                SPreferenceWriter.getInstance(getActivity()).getString(SPreferenceKey.USERID);

        if (postList != null && postList.size() > 0) {
            binding.recyclerViewHome.setVisibility(View.VISIBLE);
            binding.tvNoData.setVisibility(View.GONE);

            for (int i = 0; i < postList.size(); i++) {
                PostListResponce.DataBean dataBean = postList.get(i);
                dataBean.setPlayPauseEnable(true);
            }

            adapter = new HomePostsAdapter(getActivity(), postList);

            binding.recyclerViewHome.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recyclerViewHome.setAdapter(adapter);

            //CLICKS CALLBACKS
            adapter.setListener(new HomePostsAdapter.HomeInterface() {
                @Override
                public void onUserClick(View v, int pos) {
                    if (adapter.getArrayList() != null && adapter.getArrayList().size() > 0) {

                        PostListResponce.DataBean
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

                        /*PostListResponce.DataBean
                                bean = adapter.getArrayList().get(pos);
                        if (bean != null) {
                            String username = bean.getUsername();
                            String user_image = bean.getUser_image();
                            String topic_name = bean.getTopic_name();
                            String post_id = bean.getPost_id();

                       showDialogCommentReplyRecordAudio(true,
                                    getActivity(), post_id); //COMMENTS record audio dialog

                    }*/
                        adapter.resetAllOtherPrevious();
                        PostListResponce.DataBean
                                bean = adapter.getArrayList().get(pos);
                        if (bean != null) {
                            String id = bean.getUser_id();
                            String fullName = bean.getFullname();
                            String post_id = bean.getPost_id();
                            String position = String.valueOf(pos);
                            String postUrl = bean.getAudio_url();
                            String postName = bean.getDescription();
                            String duration = bean.getDuration();

                            //LOGIN USER CHECK
                            if (id != null && !id.isEmpty()) {
                                if (userLoginId.equalsIgnoreCase(id)) {
                                    //  go
                                    dispatchCommentAct(id, post_id, position, postUrl, postName, fullName, duration);

                                } else {
                                    //  go
                                    dispatchCommentAct(id, post_id, position, postUrl, postName, fullName, duration);
                                }
                            } else {
                                //  go
                                dispatchCommentAct(id, post_id, position, postUrl, postName, fullName, duration);
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
                            postIdGlobal = bean.getPost_id();

                            showDialogCommentReplyRecordAudio(false,
                                    getActivity(), postIdGlobal); //REPLY record audio dialog

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
                            dispatchReplyAct(id);
                        }*/
                    }
                }

                @Override
                public void onHideClick(View v, int pos) {
                    PostListResponce.DataBean
                            bean = adapter.getArrayList().get(pos);
                    if (bean != null) {
                        String postuploder_id = bean.getUser_id();

                        showDialogHide(false,
                                getActivity(), postuploder_id); //REPLY record audio dialog

                    }
                }
            });

            //POST PLAYINGS CALLBACKS
            adapter.setHomePostPlayingListener(
                    new HomePostsAdapter.HomePostPlayingListener() {
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
            profileBioListener = new UserHomeBioListener() {
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
            binding.recyclerViewHome.setVisibility(View.GONE);
            binding.tvNoData.setVisibility(View.VISIBLE);
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
        Intent intent = new Intent(getActivity(), ReplyActivity.class);
        intent.putExtra("audioid", audio_post_id);
        startActivityForResult(intent, 125);
    }


    private void dispatchOtherUserAct(String userId, String postID, String pos) {
        Intent intent = new Intent(getActivity(), OtherProfileActivity.class);
        intent.putExtra("user_id", userId);
        intent.putExtra("post_id", postID);
        intent.putExtra("position", pos);
        startActivity(intent);
    }

    private void dispatchCommentAct(String userId, String postID, String position, String postUrl, String postName, String fullName, String duration) {
        Intent intentCmt = new Intent(getActivity(), CommentAct.class);
        intentCmt.putExtra("user_id", userId);
        intentCmt.putExtra("post_id", postID);
        intentCmt.putExtra("position", position);
        intentCmt.putExtra("post_url", postUrl);
        intentCmt.putExtra("post_name", postName);
        intentCmt.putExtra("full_name", fullName);
        intentCmt.putExtra("duration", duration);
        startActivity(intentCmt);
    }


    private void setProfilePicSmall(final ProgressBar progressBar,
                                    final String imageUri, final ImageView imageView) {

        progressBar.setVisibility(View.VISIBLE);


        Glide.with((getActivity()).getApplicationContext())
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

                img_audio_mic.setImageDrawable(ContextCompat.getDrawable(getActivity(),
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
                    img_audio_mic.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()),
                            R.drawable.create_memo_hour));
                } else {

                    stopRecordingComments();

                    comment_handler.removeCallbacks(comment_up_timerRunnable);
                    isCommentRecording = false;

                    img_audio_mic.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()),
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

                    if (comment_mediaPlayer != null)
                        comment_mediaPlayer.pause();

                    img_play_pause.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()),
                            R.drawable.create_memo_play));
                    isCommentPlayCheck = false;


                    tv_duration.setText(comment_convertedTime);

                    if (comment_timer != null) {
                        comment_timer.cancel();
                        comment_timer = null;
                    }

                    resetSiriView();
                    adapter.resetAllOtherPrevious();
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

            if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                if (!ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()), permission))
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

            if (comment_updatedTime >= 41000) {
                Toast.makeText(getActivity(), "Recording limit(40 sec) reached!", Toast.LENGTH_SHORT).show();
                tv_comment_timer.setVisibility(View.GONE);

                stopRecordingComments();
                comment_handler.removeCallbacks(comment_up_timerRunnable);

                imgAudioMicGlobal.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()), R.drawable.create_memo_mic));

                comment_timeInMilliseconds = 0L;
                comment_timeSwapBuff = 0L;
                comment_timeInMilliseconds = 0L;

                stopRecordingComments();

                comment_handler.removeCallbacks(comment_up_timerRunnable);
                isCommentRecording = false;

                dialog.dismiss();

                //POST AUDIO
                showDialogCommentReplyPostAudio(false, getActivity(), postIdGlobal);
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

                        tv_duration.setText(comment_convertedTime);

                        resetSiriView();    //reset SIRI
                    }
                }.start();


            } catch (Exception e) {
                e.printStackTrace();
            }

            img_play_pause.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()),
                    R.drawable.create_memo_pause));
            isCommentPlayCheck = true;


        } else {

            if (comment_mediaPlayer != null)
                comment_mediaPlayer.pause();

            img_play_pause.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()),
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


    private void postLikeServiceApi(final int position, String audioid, final String statusToLike) {

        try {
            //MyDialog.getInstance(getContext()).showDialog(getActivity());
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<LikeResponce> call = apiInterface.like_post(SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.TOKEN),
                    SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.USERID), audioid);
            Log.e(".....", "postLikeServiceApi: " + call.request().toString());

            call.enqueue(new retrofit2.Callback<LikeResponce>() {
                @Override
                public void onResponse(@NonNull Call<LikeResponce> call, @NonNull Response<LikeResponce> response) {

                   // MyDialog.getInstance(getContext()).hideDialog();
                    if (response.isSuccessful()) {
                        LikeResponce loginResponse = response.body();

                        if (loginResponse != null) {
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

                                    model.setLike_flag(status);
                                    adapter.notifyDataSetChanged();

                                }
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
                public void onFailure(@NonNull Call<LikeResponce> call, Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(getContext()).hideDialog();
                    String s = "";
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

//            //FOLLOWERS
//            if (followerAdapter != null) {
//                followerAdapter.resetAllOtherPrevious();
//            }
//
//            //FOLLOWINGS
//            if (followingAdapter != null) {
//                followingAdapter.resetAllOtherPrevious();
//            }
//
//
//            //BIO
//            if (bio_mediaPlayer != null) {
//                bio_mediaPlayer.stop();
//                bio_mediaPlayer.reset();
//                bio_mediaPlayer.release();
//                bio_mediaPlayer = null;
//            }

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


//            //RESET BIO
//            if (bio_mediaPlayer != null)
//                bio_mediaPlayer.pause();
//
//            if (imgPlayPause != null && txtRecordTime != null) {
//                ImageView img_play_pause = imgPlayPause;
//                final TextView tv_duration = txtRecordTime;
//
//
//                img_play_pause.setImageDrawable(ContextCompat.getDrawable(getActivity(),
//                        R.drawable.home_play_a));
//                isBioPlayCheck = false;
//                tv_duration.setText(bio_time_duration);
//            }
//
//            if (bio_timer != null) {
//                bio_timer.cancel();
//                bio_timer = null;
//            }
            if (profileBioListener != null)
                profileBioListener.onBioPlaying(false);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    ////////////////*********************
    /**/

    private void goToSplash() {

        SPreferenceWriter.getInstance(getActivity()).clearPreferenceValues("");

        Toast.makeText(getActivity(), "User already logged in some Other device.", Toast.LENGTH_SHORT).show();

        Objects.requireNonNull(getActivity()).finishAffinity();

        Intent intent = new Intent(getActivity(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


//    /***************checking MULTIPLE RUNTIME PERMISSION**********************/
//
//    private void doAllPermissionChecking() {
//        List<String> permissionsNeeded = new ArrayList<>();
//
//        final List<String> permissionsList = new ArrayList<>();
//
//        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
//            permissionsNeeded.add("Read Storage");
//        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
//            permissionsNeeded.add("Write Storage");
//        if (!addPermission(permissionsList, Manifest.permission.RECORD_AUDIO))
//            permissionsNeeded.add("Record Audio");
//
////        for Pre-Marshmallow the permissionsNeeded.size() will always be 0; , if clause don't run  Pre-Marshmallow
//        if (permissionsList.size() > 0) {
//            if (permissionsNeeded.size() > 0) {
//                // Need Rationale
//                String message = "You need to grant access to " + permissionsNeeded.get(0);
//                for (int i = 1; i < permissionsNeeded.size(); i++)
//                    message = message + ", " + permissionsNeeded.get(i);
//                showMessageOKCancel(message,
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                ActivityCompat.requestPermissions(getActivity(),
//                                        permissionsList.toArray(new String[permissionsList.size()]),
//                                        REQUEST_CODE_ASK_ALL_PERMISSIONS);
//                            }
//                        });
//                return;
//            }
//            ActivityCompat.requestPermissions(getActivity(),
//                    permissionsList.toArray(new String[permissionsList.size()]),
//                    REQUEST_CODE_ASK_ALL_PERMISSIONS);
//            return;
//        }
//
////        start doing things if all PERMISSIONS are Granted whensoever
////        for Marshmallow+ and Pre-Marshmallow both
//        startRecording();
//    }
//
//    ////    adding RUNTIME PERMISSION to permissionsList and checking If user has GRANTED Permissions or Not  /////
//    private boolean addPermission(List<String> permissionsList, String permission) {
//        // Marshmallow+
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
//                permissionsList.add(permission);
//                // Check for Rationale Option
//                if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission))
//                    return false;
//            }
//        }
//        // Pre-Marshmallow
//        return true;
//    }
//
//    //    Handle "Don't / Never Ask Again" when asking permission
//    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
//        new AlertDialog.Builder(getContext())
//                .setMessage(message)
//                .setPositiveButton("OK", okListener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
//    }


//    //////    PERMISSION GRANTED callback FUNCTIONALITY   //////
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_CODE_ASK_ALL_PERMISSIONS:
//
//                Map<String, Integer> perms = new HashMap<>();
//                // Initial
//                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
//                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
//                perms.put(Manifest.permission.RECORD_AUDIO, PackageManager.PERMISSION_GRANTED);
//                // Fill with results
//                for (int i = 0; i < permissions.length; i++)
//                    perms.put(permissions[i], grantResults[i]);
//                // Check for permissions
//                if (perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
//                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
//                        && perms.get(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
//
//                    // All Permissions Granted at once
//                    // do start recording for Marshmallow+ case
//                    startRecording();
//                } else {
//                    // Permission Denied
//
//                    Toast.makeText(getActivity(), "Some Permissions is denied.", Toast.LENGTH_SHORT)
//                            .show();
//
//                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//                            Uri.fromParts("package", getActivity().getPackageName(), null));
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                    getActivity().finish();
//                }
//
//                break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//
//    }
//
//    //      START RECORDING
//    private void startRecording() {
//        if (didUserHasSDCard()) {
//            if (isExternalStorageWritable()) {
//
////       get the path to sdcard
//                File sdcard = Environment.getExternalStorageDirectory();
////      to this path add a new directory path
//                File dir = new File(sdcard.getAbsolutePath() + "/" + getResources().getString(R.string.app_name) + "/" + "Audio");
////      create this directory if not already created
//                if (!dir.exists()) {
//                    dir.mkdirs();
//                    Log.w("isDirectoryCREATED :", "YES");
//                }
////     create the file in which we will write the contents
//
//                Log.w("Directory_Path :", "" + dir.getAbsolutePath());
//
//                long time = System.currentTimeMillis();
//                String randomName = String.valueOf(time).substring(4, 12);
//
//                String audioFileName = "RecAudio" + randomName + ".mp3";
//
//                audioFile = new File(dir, audioFileName);
//                audioFilePath = audioFile.getAbsolutePath();
//
//                Log.w("AudioFile_Path :", "" + audioFilePath);
//
//                setUpMediaRecorder();
//
//                try {
//                    mediaRecorder.prepare();
//                    mediaRecorder.start();
//                } catch (IllegalStateException | IOException e) {
//                    e.printStackTrace();
//                }
//
////                recordBtnGlobal.setEnabled(false);
//
//                Toast.makeText(getActivity(), "Recording started", Toast.LENGTH_LONG).show();
//                startTimer();
//            }
//        }
//
//
//    }


//    //    check If User has Mounted the SD CARD or not
//    private boolean didUserHasSDCard() {
//        boolean isSDPresent = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
//        if (isSDPresent) {
//            Log.w("SD_CARD", "YES");
//            return true;
//        } else {
//            Log.w("SD_CARD", "NO");
//            Toast.makeText(getActivity(), "SD CARD not available.", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//    }
//
//    /* Checks if external storage is available for read and write */
//    public boolean isExternalStorageWritable() {
//        String state = Environment.getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED.equals(state)) {
//            return true;
//        }
//        return false;
//    }

//
//    // Set Up MEDIA RECORDER to Record Audio
//    public void setUpMediaRecorder() {
//
//        mediaRecorder = new MediaRecorder();
//
//        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION);
//
//        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//
//        mediaRecorder.setOutputFile(audioFilePath);
//
//        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
//
//    }
//
//
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
//        Toast.makeText(getActivity(), "Recording playing", Toast.LENGTH_LONG).show();
//    }
//
//    //        PLAY LAST RECORDING snippet
//    private void snippetOfPlayLastRec() {
//        mediaPlayer = new MediaPlayer();
//
//        try {
//            mediaPlayer.setDataSource(audioFilePath);
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
//                playPauseDialogGlobal.setImageResource(R.drawable.home_play_a);
//                isplayCheck = false;
//
//            }
//        });
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        if (mediaRecorder != null) {
//            mediaRecorder.release();
//            mediaRecorder = null;
//        }
//
//        if (mediaPlayer != null) {
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
//
//    }
//
//
//    public void startTimer() {
//        startTime = SystemClock.uptimeMillis();
//        customHandler.postDelayed(updateTimerThread, 0);
//
//    }
//
//    private Runnable updateTimerThread = new Runnable() {
//        public void run() {
//
//            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
//
//            updatedTime = timeSwapBuff + timeInMilliseconds;
//            secs = (int) (updatedTime / 1000);
//            mins = secs / 60;
//            secs = secs % 60;
//            milliseconds = (int) (updatedTime % 1000);
//            timerTxtGlobal.setText("00:0" + "" + mins + ":"
//                    + String.format("%02d", secs) /*+ ":"
//                            + String.format("%03d", milliseconds)*/);
//            customHandler.postDelayed(this, 0);
//        }
//
//    };
//
//    private void replyOnAudioService(String audioID) {
//        try {
//            MyDialog.getInstance(getContext()).showDialog(getActivity());
//            String token = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.TOKEN);
//            String userid = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.USERID);
//
//            if (true) {
//                RequestBody profile_body;
//                MultipartBody.Part profilePart;
//
//                RequestBody profile_body_audio;
//                MultipartBody.Part profilePartAudio;
//
//                if (audioFilePath != null) {
//                    File file = new File(audioFilePath);
//                    profile_body_audio = RequestBody.create(MediaType.parse("audio/*"), file);
//                    profilePartAudio = MultipartBody.Part.createFormData("audio", file.getName(), profile_body_audio);
//
//                } else {
//                    profilePartAudio = MultipartBody.Part.createFormData("audio", "");
//                }
//
//                ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
//                Map<String, RequestBody> map = setUpMapData(token, userid, audioID);
//                Call<ReplyCommentResponse> call = apiInterface.commentOnAudioReq(map, profilePartAudio);
//                call.enqueue(new retrofit2.Callback<ReplyCommentResponse>() {
//                    @Override
//                    public void onResponse(@NonNull Call<ReplyCommentResponse> call, Response<ReplyCommentResponse> response) {
//                        MyDialog.getInstance(getContext()).hideDialog();
//                        if (response.isSuccessful()) {
//                            ReplyCommentResponse replyCommentResponse = response.body();
//                            Log.e("responce", "" + replyCommentResponse.toString());
//                            if (replyCommentResponse.getStatus().equalsIgnoreCase("SUCCESS")) {
//                                Toast.makeText(getContext(), "" + replyCommentResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                                postListService();
//                            } else {
//                                Toast.makeText(getContext(), "" + replyCommentResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//
//                        } else {
//                            Toast.makeText(getContext(), "Server Error!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ReplyCommentResponse> call, Throwable t) {
//                        t.printStackTrace();
//                        MyDialog.getInstance(getContext()).hideDialog();
//                    }
//                });
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private Map<String, RequestBody> setUpMapData(String the_token, String userid, String audioID) {
//
//        Map<String, RequestBody> fields = new HashMap<>();
//        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), the_token);
//        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), userid);
//        RequestBody audioId = RequestBody.create(MediaType.parse("text/plain"), audioID);
//        RequestBody duration = RequestBody.create(MediaType.parse("text/plain"), timerTxtGlobal.getText().toString());
//        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), edtDescriptionStr);
//
//        fields.put("token", token);
//        fields.put("user_id", userId);
//        fields.put("audio_id", audioId);
//        fields.put("msg_duration", duration);
//        fields.put("description", description);
//
//        return fields;
//    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cart_option, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_notify) {

            Intent intent = new Intent(getContext(), NotificationActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }



    /*
     * **************************
     * //TODO: LOCATION FUNCTIONING
     * **************************
     * */

    //    METHOD: TO START FULL PROCESS FOR FIRST TIME..
    private void startLocationFunctioning() {

        /*if (Util.showInternetAlert(MyApplication.getInstance().getApplicationContext())) {
            if (isGPlayServicesOK()) {
                buildGoogleApiClient();
            }
        }*/

//
        if (!new InternetCheck(getActivity().getApplicationContext()).isConnect()) {
            Toast.makeText(getActivity().getApplicationContext(), "Internet not available.", Toast.LENGTH_SHORT).show();
        } else {
            if (isGPlayServicesOK()) {
                buildGoogleApiClient();
            }

        }

    }


    //  METHOD: TO CHECK IF DEVICE HAS UPDATED GOOGLE PLAY SERVICES
    public boolean isGPlayServicesOK() {
        int isAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(isAvailable)) {
            /* The returned review_dialog displays a localized message about the error and upon
             user confirmation (by tapping on review_dialog) will direct them to the Play Store
              if Google Play services is out of date or missing, */
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), isAvailable, 1001);
            dialog.show();
        } else {
            Toast.makeText(getActivity(), "Can't connect to Google Play Services", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    //   METHOD: TO  Create an instance of the GoogleAPIClient AND LocationRequest
    private void buildGoogleApiClient() {

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        googleApiClient.connect();

        createLocationRequest();

    }

    //Method: To create location request and set its priorities
    private void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(2000);
        locationRequest.setFastestInterval(10 * 1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // CREATE A FUSED LOCATION CLIENT PROVIDER OBJECT
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        //      Initialize AddressResultReceiver class object
        mResultReceiver = new AddressResultReceiver(null);

    }

    private void stopLocationUpdates() {
        boolean b = mFusedLocationClient != null;
        if (b && locationCallback != null)
            mFusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        boolean b = mFusedLocationClient != null && locationRequest != null;
        if (b && locationCallback != null)
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    // LOCATION SETTINGS SET UP
    private void setUpLocationSettingsTaskStuff() {
        /* Initialize the pending result and LocationSettingsRequest */
        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder()
                        .addLocationRequest(locationRequest);

        builder.setAlwaysShow(true);

        SettingsClient client = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());


        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.

                // GPS is already enabled no need of review_dialog
                Log.w(TAG, "onResult: Success 1");

                loadCurrentLoc(); //GET CURRENT LOCATION
            }
        });

        task.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a review_dialog.
                    Log.w(TAG, "REQ_CODE_GPS_SETTINGS: REQ " + 0);

                    try {
                        // Show the review_dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        Log.w(TAG, "REQ_CODE_GPS_SETTINGS: REQ " + 1);
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(getActivity(), REQ_CODE_GPS_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                        sendEx.printStackTrace();
                    }
                }
            }
        });
    }

    /*******   GoogleApiClient  ConnectionCallback and ConnectionFailedListener CALLBACK METHODS ******   */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.w(TAG, "onConnected");


        if (getActivity() != null) {
            if (HomeFragment.this.isAdded()) {
                if (Util.showInternetAlert(getActivity())) {
                    setUpLocationSettingsTaskStuff();   // On Connected
                }
            }
        }
//        setUpLocationSettingsTaskStuff();   // On Connected

    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.w(TAG, "onConnectionSuspended: " + i);
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.w(TAG, "onConnectionFailed: " + connectionResult);
    }


    //    Handle "Never Ask Again" when asking permission
    private void showDenyRationaleDialog(String message, DialogInterface.OnClickListener okListener) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.app_name))
                        .setIcon(ContextCompat.getDrawable(getActivity(), R.mipmap.ic_launcher))
                        .setMessage(message)
                        .setCancelable(false)
                        .setPositiveButton("OK", okListener)
                        .setNegativeButton("Cancel", okListener);

        AlertDialog dialog = builder.create();
       /* //   ANIMATION
        Window window = dialog.getWindow();
        if (window != null) {
//            window.getAttributes().windowAnimations = R.style.dialogAnimation_Enter;
        }*/

        dialog.show();

        // Alert Buttons
        Button positive_button = dialog.getButton(BUTTON_POSITIVE);
        Button negative_button = dialog.getButton(BUTTON_NEGATIVE);

        positive_button.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        negative_button.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));


    }

    //    METHOD TO GET CURRENT LOCATION OF DEVICE
    private void loadCurrentLoc() {

        //      Marshmallow +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    &&
                    getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {

                    showDenyRationaleDialog("You need to allow access to Device Location", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            switch (which) {
                                case BUTTON_POSITIVE:
                                    dialog.dismiss();
                                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                            REQ_CODE_LOCATION);

                                    break;
                                case BUTTON_NEGATIVE:
                                    dialog.dismiss();
                                    getActivity().finish();
                                    break;
                            }

                        }
                    });

                    return;
                }

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQ_CODE_LOCATION);

                return;
            }


            /*DO THE LOCATION STUFF*/

            try {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(),
                        new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    // Logic to handle location object

                                    Log.w(TAG, "addOnSuccessListener: location: " + location);

                                    mLastLocation = location;

                                    lat = mLastLocation.getLatitude();
                                    lng = mLastLocation.getLongitude();

                                    saveUserLatLng(lat, lng);
//                                    dispatchFetchAddressService();


                                    isLocServiceStarted = true;

                                    currentLocationLatLngs = new LatLng(lat, lng);

                                } else {
//                                    Toast.makeText(getActivity(), "Make sure that Location is Enabled on the device.", Toast.LENGTH_LONG).show();

                                    isLocServiceStarted = false;
                                    Log.w(TAG, "addOnSuccessListener: location: " + null);
//                                    MyApplication.makeASnack(getActivity().binding.getRoot(), getString(R.string.no_location_detected));
                                }
                            }
                        });


                locationCallback = new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        for (Location location : locationResult.getLocations()) {
                            // Update UI with location data
                            if (location != null) {
                                Log.w(TAG, "LocationCallback:" + location);

                                if (!isLocServiceStarted) {

                                    mLastLocation = location;

                                    lat = mLastLocation.getLatitude();
                                    lng = mLastLocation.getLongitude();

                                    saveUserLatLng(lat, lng);
//                                    dispatchFetchAddressService();

                                    isLocServiceStarted = true;

                                    currentLocationLatLngs = new LatLng(lat, lng);
                                }
                            }

                        }
                    }

                };


            } catch (Exception e) {
                e.printStackTrace();
            }


        } else    //    PRE-Marshmallow
        {

            /*DO THE LOCATION STUFF*/

            try {

                mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(),
                        new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    // Logic to handle location object

                                    Log.w(TAG, "addOnSuccessListener: location: " + location);

                                    mLastLocation = location;

                                    lat = mLastLocation.getLatitude();
                                    lng = mLastLocation.getLongitude();

                                    saveUserLatLng(lat, lng);
//                                    dispatchFetchAddressService();


                                    isLocServiceStarted = true;

                                    currentLocationLatLngs = new LatLng(lat, lng);

                                } else {
//                                    Toast.makeText(getActivity(), "Make sure that Location is Enabled on the device.", Toast.LENGTH_LONG).show();

                                    isLocServiceStarted = false;
                                    Log.w(TAG, "addOnSuccessListener: location: " + null);
//                                    MyApplication.makeASnack(getActivity().binding.getRoot(), getString(R.string.no_location_detected));
                                }
                            }
                        });


                locationCallback = new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        for (Location location : locationResult.getLocations()) {
                            // Update UI with location data
                            if (location != null) {
                                Log.w(TAG, "LocationCallback:" + location);

                                if (!isLocServiceStarted) {

                                    mLastLocation = location;

                                    lat = mLastLocation.getLatitude();
                                    lng = mLastLocation.getLongitude();

                                    saveUserLatLng(lat, lng);
//                                    dispatchFetchAddressService();

                                    isLocServiceStarted = true;

                                    currentLocationLatLngs = new LatLng(lat, lng);
                                }
                            }

                        }
                    }

                };

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void dispatchFetchAddressService() {
        Intent intent = new Intent(getActivity(), FetchAddressService.class);
        intent.putExtra(FetchAddressService.FIND_BY, FetchAddressService.FIND_BY_LOCATION);
        intent.putExtra(FetchAddressService.RECEIVER, mResultReceiver);
        intent.putExtra(FetchAddressService.LOCATION, mLastLocation);
        Objects.requireNonNull(getActivity()).startService(intent);


    }


    /* ****  RUNTIME requestPermissions CALLBACK  ***** */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case CURRENT_LOCATION_REQ_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission is granted
//                    enableMyLocation();
                    return;
                } else {
                    Toast.makeText(getActivity(), "Device Location needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getActivity().getPackageName(), null));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                }
                break;

            case REQ_CODE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.w(TAG, "onResult: Success 3");
                    loadCurrentLoc();  //GET CURRENT LOCATION

                } else {
                    Toast.makeText(getActivity(), "Device Location needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getActivity().getPackageName(), null));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                }
                break;
        }
    }

    //    METHOD : TO SHOW GPS DIALOG ,WHEN USER CANCEL's THE GPS LOCATION SETTING REQUEST
    private void showGPSDialog(String message, DialogInterface.OnClickListener okListener) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.app_name))
                        .setIcon(ContextCompat.getDrawable(getActivity(), R.mipmap.ic_launcher))
                        .setMessage(message)
                        .setCancelable(false)
                        .setPositiveButton("Okay", okListener)
                        .setNegativeButton("Cancel", okListener);

        AlertDialog dialog = builder.create();
        //   ANIMATION
        Window window = dialog.getWindow();
        if (window != null) {
//            window.getAttributes().windowAnimations = R.style.dialogAnimation_Enter;
        }

        dialog.show();

        // Alert Buttons
        Button positive_button = dialog.getButton(BUTTON_POSITIVE);
        Button negative_button = dialog.getButton(BUTTON_NEGATIVE);

        positive_button.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        negative_button.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_CODE_GPS_SETTINGS:
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "onResult: GPS IS ENABLED");
                    Log.w(TAG, "onResult: Success 2");

                    loadCurrentLoc(); //GET CURRENT LOCATION

                } else {
                    Log.i(TAG, "onResult: GPS IS DISABLED");

                    showGPSDialog("To continue, please turn on your Device Location", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case BUTTON_POSITIVE:
                                    dialog.dismiss();

                                    setUpLocationSettingsTaskStuff();   // in showGPSDialog ,GPS IS DISABLED

                                    break;
                                case BUTTON_NEGATIVE:
                                    dialog.dismiss();
                                    getActivity().finish();
                                    break;
                            }
                        }
                    });

                }
                break;


            default:
                super.onActivityResult(requestCode, resultCode, data);

        }
    }

    private void saveUserLatLng(double the_lat, double the_lng) {
        SPreferenceWriter.getInstance(getActivity())
                .writeStringValue(SPreferenceKey.THE_LAT,
                        String.valueOf(the_lat));

        SPreferenceWriter.getInstance(getActivity())
                .writeStringValue(SPreferenceKey.THE_LNG,
                        String.valueOf(the_lng));

        //getUserLatLng();

        if (!isLocLatLngUpdated) {

            if (getActivity() != null) {
                if (HomeFragment.this.isAdded()) {
                    if (Util.showInternetAlert(getActivity())) {
                        updateLatLngService();  //SERVICE HIT..
                    }
                }
            }

        }

    }


    /*************** ResultReceiver CLASS ***************/
    private class AddressResultReceiver extends ResultReceiver {

        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, final Bundle resultData) {

            switch (resultCode) {
                case FetchAddressService.SUCCESS_RESULT:

                    if (getActivity() != null) {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Address address = resultData.getParcelable(FetchAddressService.RESULT_ADDRESS);

                                String msg = resultData.getString(FetchAddressService.RESULT_MSG);
                                String city = resultData.getString(FetchAddressService.CITY);
                                String area = resultData.getString(FetchAddressService.AREA);
                                String country_name = resultData.getString(FetchAddressService.COUNTRY_NAME);
                                String full_loc_via_json = resultData.getString(FetchAddressService.FULL_LOCATION_VIA_JSON);

                                String pin = resultData.getString(FetchAddressService.PIN_CODE);
                                double the_lat = resultData.getDouble(FetchAddressService.LOC_LAT);
                                double the_lng = resultData.getDouble(FetchAddressService.LOC_LNG);

                                SPreferenceWriter.getInstance(getActivity()).writeStringValue(SPreferenceKey.THE_LAT, String.valueOf(the_lat));
                                SPreferenceWriter.getInstance(getActivity()).writeStringValue(SPreferenceKey.THE_LNG, String.valueOf(the_lng));

                                ArrayList<String> addressArrayList = resultData.getStringArrayList(FetchAddressService.ADDRESS_ARRAY_LIST);

                                /*
                                 *******
                                 * SAVE USER LOCATION
                                 * *******/
                                String fullAddress = "";
                                String locality;
                                int max;
                                if (address != null) {
                                    max = address.getMaxAddressLineIndex();

                                    for (int i = 0; i <= max - 1; i++) {
                                        fullAddress = fullAddress + address.getAddressLine(i) + ", ";
                                    }

                                    fullAddress = fullAddress + address.getAddressLine(address.getMaxAddressLineIndex());

                                    if (address.getLocality() != null && !address.getLocality().isEmpty()) {
                                        locality = address.getLocality();
                                    } else {
                                        locality = address.getSubLocality();
                                    }

                                    Log.w(TAG, "Address +++++: " + fullAddress);

                                    Log.w(TAG, "+++++++++++++++++ AddressResultReceiver +++++++++++++++++++");
                                    Log.w("Results ARE: \n",

                                            "MESSAGE: \n" + msg
                                                    + "\nADDRESS: \n" + address
                                                    + "\nLOCALITY: \n" + locality
                                                    + "\nCITY: \n" + city
                                                    + "\nAREA: \n" + area
                                                    + "\nCOUNTRY_NAME: \n" + country_name
                                                    + "\nPIN: \n" + pin
                                                    + "\nADDRESS_ARRAY_LIST: \n" + addressArrayList
                                                    + "\nLOC_LAT: \n" + the_lat
                                                    + "\nLOC_LNG: \n" + the_lng);

//                                    binding.etBookClubLocation.setText(fullAddress);


//                                    SPreferenceWriter.getInstance(getActivity()).writeStringValue(SPreferenceKey.USER_PERSONAL_LOCATION, fullAddress);
//                                    SPreferenceWriter.getInstance(getActivity()).writeStringValue(SPreferenceKey.USER_LOCATION_LOCALITY, locality);
//
//                                    SPreferenceWriter.getInstance(getActivity()).writeStringValue(SPreferenceKey.USER_CITY, city);
//                                    SPreferenceWriter.getInstance(getActivity()).writeStringValue(SPreferenceKey.USER_STATE, area);
//                                    SPreferenceWriter.getInstance(getActivity()).writeStringValue(SPreferenceKey.USER_PIN_CODE, pin);


                                } else {
                                    Log.e(TAG, "---- AddressResultReceiver : Address is NULL ----");

                                    Log.w(TAG, "---- full_loc_via_json : ----" + full_loc_via_json);

//                                    binding.etBookClubLocation.setText(fullAddress);

                                }
                            }
                        });
                    }

                    break;

                case FetchAddressService.FAILURE_RESULT:
                    Log.w("Results ARE:", "FAILED"); //Timed out waiting for response from server
                    break;
            }

        }
    }

    // **************HIDE DIALOG
    private void showDialogHide(final boolean isCommenting, final Context context,
                                final String postuploder_id) {

        dialog = new Dialog(context);

        dialog.setContentView(R.layout.hide_dialog);
        Objects.requireNonNull(dialog.getWindow())
                .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);


        //CANCEL
        dialog.findViewById(R.id.yesBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecordingComments();

                comment_handler.removeCallbacks(comment_up_timerRunnable);
                isCommentRecording = false;

                hideServiceApi(postuploder_id); //service hit

                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.noTxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment_handler.removeCallbacks(comment_up_timerRunnable);
                isCommentRecording = false;


                dialog.dismiss();
            }
        });


        dialog.show();

    }

    private void hideServiceApi(String postuploder_id) {

        try {
            MyDialog.getInstance(getContext()).showDialog(getActivity());
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<LikeResponce> call = apiInterface.hide_post(SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.USERID),
                    SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.TOKEN), postuploder_id);
            Log.e(".....", "hideServiceApi: " + call.request().toString());

            call.enqueue(new retrofit2.Callback<LikeResponce>() {
                @Override
                public void onResponse(@NonNull Call<LikeResponce> call, @NonNull Response<LikeResponce> response) {

                    MyDialog.getInstance(getContext()).hideDialog();
                    if (response.isSuccessful()) {
                        LikeResponce loginResponse = response.body();

                        Log.e("onResponse", "" + loginResponse.toString());
                        Toast.makeText(getActivity(), "Audio hide successfully", Toast.LENGTH_SHORT).show();

                        if (Util.showInternetAlert(getContext())) {
                            postListService();  //in OnCreateView
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LikeResponce> call, Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(getContext()).hideDialog();
                    String s = "";
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
