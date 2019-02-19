package com.escalate.activity;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
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
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.am.siriview.DrawView;
import com.escalate.BuildConfig;
import com.escalate.R;
import com.escalate.adapter.ChatAdapterSocket;
import com.escalate.databinding.ActivityChatBinding;
import com.escalate.model.ChatAudioMsz;
import com.escalate.model.ChatDataBean;
import com.escalate.model.ChatDataModel;
import com.escalate.model.ChatHistoryBean;
import com.escalate.model.ChatResponseBean;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.escalate.utils.AppConstants;
import com.escalate.utils.MyApplication;
import com.escalate.utils.ParamEnum;
import com.escalate.utils.ServiceParameters;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.escalate.utils.MyApplication.getContext;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityChatBinding binding;

    String fireBaseChatUserId = "";
    private String userName = "";
    private String userId = "";
    private Thread theWThread;
    private boolean isCommentPlayCheck = false;
    private boolean isCommentRecording = false;
    private static final int REQUEST_CODE_ASK_ALL_PERMISSIONS = 1010;
    private MediaRecorder comment_mediaRecorder = null;
    private MediaPlayer chatMediaPlayer = null;
    private long comment_timeInMilliseconds = 0L;
    private long comment_timeSwapBuff = 0L;
    private long comment_updatedTime = 0L;
    private int comment_min, comment_sec, comment_milliseconds;
    private long comment_startTime = 0L;
    private String comment_convertedTime = "";
    private CountDownTimer comment_timer = null;
    private Handler comment_handler = null;
    private List<ChatDataModel> chatDataModelList;
    private File chatAudioFile = null;
    private String chatAudioFilePath = null;
    private SiriUpdateThread updaterThread;
    private long comment_elapsedTime = 0L;
    private StorageReference mImagStorage;
    private String myFirebaseUid = "";
    private ChatAdapterSocket adapter;
    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;
    Uri imgUri;
    private final ArrayList<ChatAudioMsz> messageList = new ArrayList<>();
    protected String AUTHORITY_NAME = BuildConfig.APPLICATION_ID + ".provider";
    private ArrayList<ChatDataBean> chatDataList = null;
    private String room_name = "";
    public static boolean isChatActOnForeground = false;
    String converted_time = "";
    private ChatActivityBioListener chatActivityBioListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);

        binding.iVbackChat.setOnClickListener(this);
        binding.btnReplyChat.setOnClickListener(this);
        binding.playPauseChat.setOnClickListener(this);
        binding.sentIview.setOnClickListener(this);
        binding.recordLay.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        init();
       /* LinearLayoutManager manager = new LinearLayoutManager(ChatActivity.this);
        recyclerChat.setLayoutManager(manager);*/
        Intent intent = getIntent();
        if (intent != null) {
            String chatUserImg = intent.getStringExtra("user_img");
            fireBaseChatUserId = intent.getStringExtra("firbase_chat_user_id");
            userName = intent.getStringExtra("user_name");
            userId = intent.getStringExtra("user_id");
            myFirebaseUid = SPreferenceWriter.getInstance(getContext())
                    .getString(SPreferenceKey.FIREBASE_UID);
            mRootRef = FirebaseDatabase.getInstance().getReference();
            mAuth = FirebaseAuth.getInstance();
            mImagStorage = FirebaseStorage.getInstance().getReference();

            binding.recyclerChat.setLayoutManager(new LinearLayoutManager(this));

         /*   if (fireBaseChatUserId!=null){
                loadMessages();
            }
*/
            binding.toolbarTitleName.setText(userName);
            if (chatUserImg != null) {
                if (!chatUserImg.isEmpty()) {
                    Picasso.get().load(chatUserImg)
                            .resize(100, 100).centerCrop(Gravity.CENTER).into(binding.circleImageView);
                }
            } else {
                binding.circleImageView.setImageResource(R.drawable.default_image);
            }
            // initialization
            if (comment_mediaRecorder != null) {
                comment_mediaRecorder.release();
                comment_mediaRecorder = null;
            }

            if (chatMediaPlayer != null) {
                chatMediaPlayer.release();
                chatMediaPlayer = null;
            }
            comment_handler = new Handler();
            chatDataModelList = new ArrayList<>();

            chatMsgHistoryService(ChatActivity.this);

        }
        setRefersh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iVbackChat:
                onBackPressed();
                break;
            case R.id.btnReplyChat:
            case R.id.recordLay:

                if (!isCommentRecording) {
                    // rippleBackground.startRippleAnimation();
                    doAllPermissionChecking();
                    isCommentRecording = true;

                    scaleAnimationComments(binding.micIView);
                    binding.micIView.setImageDrawable(ContextCompat.getDrawable(ChatActivity.this,
                            R.drawable.comment_mic));

                    if (chatActivityBioListener != null)
                        chatActivityBioListener.onBioPlaying(true);

                } else {
//                            rippleBackground.stopRippleAnimation();
                    stopRecordingComments();

                    comment_handler.removeCallbacks(comment_up_timerRunnable);
                    isCommentRecording = false;

                    binding.micIView.setImageDrawable(ContextCompat.getDrawable(ChatActivity.this,
                            R.drawable.comment_mic));
                    binding.btnReplyChat.setText("Reply");

                    //POST AUDIO
                    binding.recordLay.setVisibility(View.GONE);
                    binding.sendLay.setVisibility(View.VISIBLE);
                    binding.txtTimerSend.setText(converted_time);
                    if (chatActivityBioListener != null)
                        chatActivityBioListener.onBioPlaying(false);

                }
                break;
            case R.id.playPauseChat:
                if (chatAudioFilePath != null && !chatAudioFilePath.isEmpty()) {

                    doJustCommentsPlayStopPlay();//PLAYING
                } else {
                    Toast.makeText(ChatActivity.this, "Empty audio", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sentIview:
                if (adapter != null) {
                    adapter.resetAllOtherPrevious();
                }
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sendMessage(ChatActivity.this);
                    }
                }, 500);

                if (chatMediaPlayer != null)
                    chatMediaPlayer.pause();

                binding.playPauseChat.setImageDrawable(ContextCompat.getDrawable(ChatActivity.this,
                        R.drawable.chat_message_play_white));
                isCommentPlayCheck = false;

                binding.txtTimerSend.setText(converted_time);

                if (comment_timer != null) {
                    comment_timer.cancel();
                    comment_timer = null;
                }
                resetSiriView();    //reset SIRI
                if (chatActivityBioListener != null)
                    chatActivityBioListener.onBioPlaying(false);
                break;
        }
    }

    //  BROADCAST RECEIVER CLASS Object : TO HANDLE PUSH
    private BroadcastReceiver pushChatReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (chatDataList != null) {
                if (chatDataList.size() == 0) {
                    chatMsgHistoryService(ChatActivity.this);    //SERVICE HIT..
                }
                chatMsgHistoryService(ChatActivity.this);
            }

            if (adapter != null) {

//                String badge_notify = "";

                String flag = "";
                String unique_id = "";
                String message = "";
                String time = "";
                String messageDuration = "";

                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    message = bundle.getString(ParamEnum.MESSAGE.theValue());
                    time = bundle.getString(ParamEnum.TIME.theValue());
                    unique_id = bundle.getString(ParamEnum.UNIQUE_ID_CHAT.theValue());
                    flag = bundle.getString(ParamEnum.FLAG_CHAT.theValue());
                    messageDuration = bundle.getString(ParamEnum.MSZ_DURATION.theValue());
//                    badge_notify = bundle.getString(ParamEnum.PUSH_HOME_COUNT.theValue());

                    ChatDataBean chatDataBean = new ChatDataBean();

                    chatDataBean.setAudioUrl(message);
                    chatDataBean.setMessage_time(time);
                    chatDataBean.setUnique_id(unique_id);
                    chatDataBean.setUser_flag(flag);
                    chatDataBean.setMessage_duration(messageDuration);
                    chatDataBean.setPlayPauseEnable(true);

                    chatDataList.add(chatDataBean);

                    if (adapter != null) {
                        adapter.setArrayList(chatDataList);
                        adapter.notifyDataSetChanged();
                        binding.recyclerChat.scrollToPosition(adapter.getArrayList().size() - 1);
                    } else {
                        setUpRecycler(chatDataList);    // IN B.R
                        binding.sendLay.setVisibility(View.GONE);
                        binding.recordLay.setVisibility(View.VISIBLE);
                    }
                }

            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        isChatActOnForeground = true;

        //      REGISTER LocalBroadcastManager TO HANDLE PUSH
        LocalBroadcastManager.getInstance(ChatActivity.this).registerReceiver(pushChatReceiver,
                new IntentFilter(ParamEnum.PUSH_CHAT.theValue()));
    }

    @Override
    protected void onStop() {
        super.onStop();

        isChatActOnForeground = false;
        LocalBroadcastManager.getInstance(ChatActivity.this).unregisterReceiver(pushChatReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        isChatActOnForeground = false;
    }


    public void setRefersh() {
        binding.swipeRefreshChat.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefreshChat.setRefreshing(false);
                chatMsgHistoryService(ChatActivity.this);
                //in OnCreateView
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//        getMenuInflater().inflate(R.menu.menu_chat_option, menu);

       /* menu.findItem(R.id.action_notify).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent=new Intent(HomeActivity.this, SearchTabActivity.class);

                startActivity(intent);
                return false;
            }
        });*/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        /*int id = item.getItemId();

            if (id == R.id.action_search) {

            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        }*/
        return super.onOptionsItemSelected(item);
    }


    //ALL METHODS
    private void init() {
        setSupportActionBar(binding.toolbarChat);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    private void recyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(ChatActivity.this);
        binding.recyclerChat.setLayoutManager(manager);
        String userLoginId = SPreferenceWriter.getInstance(ChatActivity.this).getString(SPreferenceKey.ID);

        ArrayList<ChatDataModel> arrayList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            ChatDataModel bean = new ChatDataModel();
            bean.setSender_id("105");
            bean.setMessageText("Hey ... how are you? :)");

            arrayList.add(bean);

        }

        /*adapter = new ChatAdapter(ChatActivity.this,ChatActivity.this, arrayList);
        recyclerChat.setAdapter(adapter);*/


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
                                ActivityCompat.requestPermissions(ChatActivity.this,
                                        permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_ALL_PERMISSIONS);
                            }
                        });
                return;
            }
            ActivityCompat.requestPermissions(ChatActivity.this,
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
                if (!ActivityCompat.shouldShowRequestPermissionRationale(ChatActivity.this, permission))
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
            Toast.makeText(ChatActivity.this, "SD CARD not available.", Toast.LENGTH_SHORT).show();
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


    //          STOP AND SAVE RECORDING
    private void stopRecordingComments() {
        try {
            if (comment_mediaRecorder != null)
                comment_mediaRecorder.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }

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


            converted_time = convertSecondsToHMmSs(comment_updatedTime);

            if (comment_updatedTime >= 41000) {
                Toast.makeText(ChatActivity.this, "Recording limit(40 sec) reached!", Toast.LENGTH_SHORT).show();
//                tv_comment_timer.setVisibility(View.GONE);

                stopRecordingComments();
                comment_handler.removeCallbacks(comment_up_timerRunnable);
                isCommentRecording = false;
                binding.sendLay.setVisibility(View.VISIBLE);
                binding.recordLay.setVisibility(View.GONE);

//                imgAudioMicGlobal.setImageDrawable(ContextCompat.getDrawable(CommentAct.this, R.drawable.create_memo_mic));

                comment_timeInMilliseconds = 0L;
                comment_timeSwapBuff = 0L;
                comment_timeInMilliseconds = 0L;
                binding.btnReplyChat.setText("Reply");

//                dialog.dismiss();

                //POST AUDIO
//                showDialogCommentReplyPostAudio(false, CommentAct.this, postId);
            } else {
                //update the time
                comment_convertedTime = converted_time;
//                tv_comment_timer.setText(converted_time);
                binding.btnReplyChat.setText(converted_time);
                binding.btnReplyChat.setVisibility(View.VISIBLE);
                comment_handler.postDelayed(this, 0);
            }
        }

    };

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

                chatAudioFile = new File(dir, audioFileName);
                chatAudioFilePath = chatAudioFile.getAbsolutePath();

                Log.w("AudioFile_Path :", "" + chatAudioFilePath);

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

    public static String convertSecondsToHMmSs(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
//        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);


        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    // Set Up MEDIA RECORDER to Record Audio
    public void setUpMediaRecorderComments() {

      /*  comment_mediaRecorder = new MediaRecorder();

        comment_mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        comment_mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        comment_mediaRecorder.setOutputFile(chatAudioFilePath);

        comment_mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);//new voice
        comment_mediaRecorder.setAudioEncodingBitRate(16);
        comment_mediaRecorder.setAudioSamplingRate(44100);
*/


        ////new code////////

        comment_mediaRecorder = new MediaRecorder();

        comment_mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        comment_mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

        comment_mediaRecorder.setOutputFile(chatAudioFilePath);
        //new voice
        comment_mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        //  mediaRecorder.setAudioEncodingBitRate(16);
        comment_mediaRecorder.setAudioEncodingBitRate(128000);
        comment_mediaRecorder.setAudioSamplingRate(44100);


    }

    public void startTimerComments() {
        comment_startTime = SystemClock.uptimeMillis();
        comment_handler.postDelayed(comment_up_timerRunnable, 0);

    }

    /*
     * **************************
     * //TODO: PLAY RECORDING
     * **************************
     * */
    private void doJustCommentsPlayStopPlay() {


        if (!isCommentPlayCheck) {

            playLastRecordingComments();

            try {
                final int duration = chatMediaPlayer.getDuration();

                if (comment_timer != null) {
                    comment_timer.cancel();
                    comment_timer = null;
                }

                comment_timer = new CountDownTimer(duration, 1000) {

                    public void onTick(long millisUntilFinished) {
                        comment_elapsedTime = millisUntilFinished;
                        binding.txtTimerSend.setText(convertSecondsToHMmSs(millisUntilFinished));
                        //SIRI LIKE VIEW
                        startSetUpSiriView(false);   //COMMENT
                    }

                    public void onFinish() {

                        binding.txtTimerSend.setText(converted_time);
                        resetSiriView();    //reset SIRI

                        if (chatActivityBioListener != null)
                            chatActivityBioListener.onBioPlaying(false);
                    }
                }.start();


            } catch (Exception e) {
                e.printStackTrace();
            }

            binding.playPauseChat.setImageDrawable(ContextCompat.getDrawable(ChatActivity.this,
                    R.drawable.chat_message_pause_white));
            isCommentPlayCheck = true;


        } else {

            if (chatMediaPlayer != null)
                chatMediaPlayer.pause();

            binding.playPauseChat.setImageDrawable(ContextCompat.getDrawable(ChatActivity.this,
                    R.drawable.chat_message_play_white));
            isCommentPlayCheck = false;


            binding.txtTimerSend.setText(converted_time);

            if (comment_timer != null) {
                comment_timer.cancel();
                comment_timer = null;
            }

            resetSiriView();    //reset SIRI
            if (chatActivityBioListener != null)
                chatActivityBioListener.onBioPlaying(false);

        }
    }

    //        PLAY LAST RECORDING
    private void playLastRecordingComments() {


        if (chatMediaPlayer == null) {
            snippetOfPlayLastRecComments();
        } else {
            chatMediaPlayer.release();
            chatMediaPlayer = null;

            snippetOfPlayLastRecComments();
        }

        if (chatActivityBioListener != null)
            chatActivityBioListener.onBioPlaying(true);
        //SIRI LIKE VIEW
        startSetUpSiriView(false);   //COMMENT


    }

    //        PLAY LAST RECORDING snippet
    private void snippetOfPlayLastRecComments() {

        chatMediaPlayer = new MediaPlayer();

        try {
            chatMediaPlayer.setDataSource(chatAudioFilePath);
            chatMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        chatMediaPlayer.start();

        chatMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                try {
                    chatMediaPlayer.stop();
                    binding.playPauseChat.setImageDrawable(ContextCompat.getDrawable(ChatActivity.this,
                            R.drawable.chat_message_play_white));
                    isCommentPlayCheck = false;
                    if (chatActivityBioListener != null)
                        chatActivityBioListener.onBioPlaying(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }

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

            updaterThread = new SiriUpdateThread(30, binding.siriDViewChat, ChatActivity.this);
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

   /* private void  loadMessages() {

        mRootRef.child("Messages").child(myFirebaseUid +"-"+fireBaseChatUserId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

//                ChatAudioMsz message = dataSnapshot.getValue(ChatAudioMsz.class);
//                messageList.add(message);
//
//                for (int i = 0; i < messageList.size(); i++) {
//                    ChatAudioMsz dataBean = messageList.get(i);
//                    dataBean.setPlayPauseEnable(true);
//                }
//
//                adapter = new ChatAdapter(ChatActivity.this, messageList);
//                recyclerChat.setAdapter(adapter);
//
////                adapter.notifyDataSetChanged();

                if(adapter == null) {
                    for (int i = 0; i < messageList.size(); i++) {
                        ChatAudioMsz dataBean = messageList.get(i);
                        dataBean.setPlayPauseEnable(true);
                    }

                    adapter = new ChatAdapter(ChatActivity.this, messageList);
                    recyclerChat.setAdapter(adapter);
                    ChatAudioMsz message = dataSnapshot.getValue(ChatAudioMsz.class);
                    messageList.add(message);
                }
                else {


                    ChatAudioMsz dataBean = dataSnapshot.getValue(ChatAudioMsz.class);
                    if (dataBean != null) {
                        dataBean.setPlayPauseEnable(true);
                    }
                    messageList.add(dataBean);
                    adapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }*/

    /*private void sendMessage() {

        // Return the file target uri for the photo based on image filename
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //  android:authorities="com.escalate.provider"
            // See https://guides.codepath.com/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
            imgUri = FileProvider.getUriForFile(ChatActivity.this, AUTHORITY_NAME, chatAudioFile);

        } else {
            imgUri = Uri.fromFile(chatAudioFile);
        }
//        imgUri = Uri.fromFile(chatAudioFile);

//        final String currentUserRef = "Messages/" + myFirebaseUid +"-" + fireBaseChatUserId;
//        final String mChatUserRef = "Messages/" + fireBaseChatUserId +"-" + myFirebaseUid;

        final String currentUserRef = "Messages/" + myFirebaseUid +"-" + fireBaseChatUserId;
        final String mChatUserRef = "Messages/" + fireBaseChatUserId +"-" + myFirebaseUid;

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference path = database.getReference("Messages");


       String cr1 = path.child(fireBaseChatUserId).toString();
        String cr2 = String.valueOf(path.child(fireBaseChatUserId));



//        DatabaseReference chatroom1 = mRootRef.child("Messages").child(myFirebaseUid).child(fireBaseChatUserId).getParent();
//        DatabaseReference chatroom2 = mRootRef.child("Messages").child(myFirebaseUid).child(fireBaseChatUserId).getRoot();
//        DatabaseReference chatroom5 = mRootRef.child("Messages").child(myFirebaseUid).child(fireBaseChatUserId).getDatabase().getReference();

        DatabaseReference chatroom1 = mRootRef.child("Messages").child(myFirebaseUid).child(fireBaseChatUserId).getRef();

        DatabaseReference chatroom2 = mRootRef.child("Messages").child(fireBaseChatUserId).child(myFirebaseUid).getRef();


        *//*if (chatroom1.equals("")){
            String messageRef = Database.database().reference().child("Messages").child(currentUserRef);

            if (chatroom1.equals(messageRef)){

                for (int i=0; i<Database.database().reference().child("Messages");i++){

                    if (chatroom1.equals(Database.database().reference().child("Messages"))){
                        Toast.makeText(this, "found chat room"+chatRoom1, Toast.LENGTH_SHORT).show();
                    }
                    else if (chatroom2.equals(Database.database().reference().child("Messages"))){
                        Toast.makeText(this, "found chat room"+chatroom2, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, "No Chat Room", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }
        else if (chatroom2.equals("")){
            String messageRef2 = Database.database().reference().child("Messages").child(mChatUserRef);

        }
        else {

        }
*//*



        DatabaseReference userMszPush = mRootRef.child("audioUrl").child(myFirebaseUid).child(fireBaseChatUserId).push();

        final String pushId = userMszPush.getKey();
        final StorageReference filePath = mImagStorage.child("all_files").child(pushId + ".mp3");
//            StorageReference filePath = mImagStorage.child("images/"+ UUID.randomUUID().toString());

        filePath.putFile(imgUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if (task.isSuccessful()){

                   *//* if (path.child(cr1).){

                    }
                    if snapshot.hasChild(self.chatRoom1){
                        self.currentChatRoom = self.chatRoom1*//*


                    //String downloadUrl = task.getResult().getUploadSessionUri().toString();
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {



                            //Bitmap hochladen
                            String check = uri.toString();
                            Map mszMap = new HashMap();
                            mszMap.put("audioUrl", check);
                            mszMap.put("type",".mp3");
                            mszMap.put("mssgDuration",comment_convertedTime);
                            mszMap.put("receiverId",fireBaseChatUserId);
                            mszMap.put("senderId",myFirebaseUid);
                            mszMap.put("time", System.currentTimeMillis());

                            Map mszUserMap = new HashMap();
                            mszUserMap.put(currentUserRef + "/" + pushId,mszMap);
//                            mszUserMap.put(mChatUserRef + "/" + pushId,mszMap);

                            mRootRef.updateChildren(mszUserMap, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                                    if (databaseError!=null){
                                        Log.d("CHAT_LOG",databaseError.getMessage().toString());
                                    }
                                    }
                            });

                            recordLay.setVisibility(View.VISIBLE);
                            sendLay.setVisibility(View.GONE);
                            Toast.makeText(ChatActivity.this, "Task Success !", Toast.LENGTH_SHORT).show();
                        }
                    });
//                        String downloadUrl = task.getResult().getMetadata().getReference().getDownloadUrl().toString();

                }
                else {
                    // If sign in fails, display a message to the user.
                    Log.w("Check Fail", "image:failure", task.getException());
                    Toast.makeText(ChatActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });

//            uploadImage();
            *//*filePath.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String downloadUrl = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata())
                            .getReference()).getDownloadUrl().toString();
                    Log.w("chat", "url: "+downloadUrl);


                }
            });*//*
    }*/


    //    METHOD: TO REQUEST chatMsgHistory SERVICE..
    public void chatMsgHistoryService(Context context) {

        MyDialog.getInstance(ChatActivity.this).showDialog(ChatActivity.this);

        String token = SPreferenceWriter.getInstance(context).getString(SPreferenceKey.TOKEN);

        if (!token.isEmpty()) {

//                  REQUESTING chatMsgHistory  SERVICE..
            try {
                ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();


                Call<ChatResponseBean> call = apiInterface
                        .chatMsgHistory(userId, SPreferenceWriter.getInstance(getApplicationContext()).getString(SPreferenceKey.USERID), token);

                ServiceParameters.setUpParameters(userId, SPreferenceWriter.getInstance(getApplicationContext()).getString(SPreferenceKey.USERID), token);

                call.enqueue(new Callback<ChatResponseBean>() {
                    @Override
                    public void onResponse(@NonNull Call<ChatResponseBean> call,
                                           @NonNull Response<ChatResponseBean> response) {
                        MyDialog.getInstance(ChatActivity.this).hideDialog();
                        if (response.isSuccessful()) {
                            ChatResponseBean bean = response.body();
                            if (bean != null) {
                                if (bean.getStatus().equalsIgnoreCase("SUCCESS")) {

                                    ChatHistoryBean historyBean = bean.getChatHistoryResponse();
                                    if (historyBean != null) {
                                        chatDataList = historyBean.getChatDataHistoryList();
                                        room_name = historyBean.getRoom_name();
                                        if (room_name != null)
                                            binding.toolbarTitleName.setText(room_name);

                                        setUpRecycler(chatDataList);    // IN chatMsgHistory  SERVICE..
                                        binding.sendLay.setVisibility(View.GONE);
                                        binding.recordLay.setVisibility(View.VISIBLE);
                                    }

                                } else {
                                    Toast.makeText(context, "" + bean.getMessage(), Toast.LENGTH_SHORT).show();
                                    //  Invalid User.
//                                    ApiClientConnection.getInstance().checkIfUserInvalid(ChatActivity.this, context, bean.getMessage());
                                }
                            }
                        } else {
                            Toast.makeText(context, AppConstants.kMessageServerNotRespondingError, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ChatResponseBean> call, @NonNull Throwable t) {
                        MyDialog.getInstance(ChatActivity.this).hideDialog();
                        t.printStackTrace();
                        MyApplication.showOnFailureMessages(context, t);
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    //API SECTION
    private void sendMessage(Context context) {
        try {
            MyDialog.getInstance(ChatActivity.this).showDialog(ChatActivity.this);
            String token = SPreferenceWriter.getInstance(ChatActivity.this).getString(SPreferenceKey.TOKEN);
            String userid = SPreferenceWriter.getInstance(ChatActivity.this).getString(SPreferenceKey.USERID);

            if (true) {
                RequestBody profile_body;
                MultipartBody.Part profilePart;

                RequestBody profile_body_audio;
                MultipartBody.Part profilePartAudio;

                if (chatAudioFilePath != null) {
                    File file = new File(chatAudioFilePath);
                    profile_body_audio = RequestBody.create(MediaType.parse("msg/*"), file);
                    profilePartAudio = MultipartBody.Part.createFormData("msg", file.getName(), profile_body_audio);

                } else {
                    profilePartAudio = MultipartBody.Part.createFormData("msg", "");
                }

                ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
                Map<String, RequestBody> map = setUpMapData(token, userid, comment_convertedTime);
                Call<ChatResponseBean> call = apiInterface.requestSendmessage(map, profilePartAudio);
                call.enqueue(new retrofit2.Callback<ChatResponseBean>() {
                    @Override
                    public void onResponse(@NonNull Call<ChatResponseBean> call, Response<ChatResponseBean> response) {
                        MyDialog.getInstance(ChatActivity.this).hideDialog();

                        if (response.isSuccessful()) {
                            binding.sendLay.setVisibility(View.GONE);
                            binding.recordLay.setVisibility(View.VISIBLE);
                            ChatResponseBean bean = response.body();
                            if (bean != null) {
                                if (bean.getStatus().equalsIgnoreCase("SUCCESS")) {
                                    chatMsgHistoryService(ChatActivity.this);
//                                    isMsgSendEver = true;

                                    ChatDataBean chatDataBean = bean.getChatSendResponse();
                                    if (chatDataBean != null) {

                                        chatDataBean.setPlayPauseEnable(true);
                                        chatDataList.add(chatDataBean);

                                        if (adapter != null) {
                                            adapter.setArrayList(chatDataList);
                                            adapter.notifyDataSetChanged();
                                            binding.recyclerChat.scrollToPosition(adapter.getArrayList().size() - 1);

                                        } else {
                                            setUpRecycler(chatDataList);    // IN chatSendMessage  SERVICE..
                                            binding.sendLay.setVisibility(View.GONE);
                                            binding.recordLay.setVisibility(View.VISIBLE);
                                        }
                                    }

                                } else {
                                    Toast.makeText(context, "" + bean.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        } else {
                            Toast.makeText(context, AppConstants.kMessageServerNotRespondingError, Toast.LENGTH_SHORT).show();
                        }

                        /*if (response.isSuccessful()) {
                            ChatResponseBean viewProfileResponce = response.body();
                            Log.e("response", "" + viewProfileResponce.toString());
                            if (viewProfileResponce.getStatus().equalsIgnoreCase("SUCCESS")) {
                                Toast.makeText(ChatActivity.this, "" + viewProfileResponce.getMessage(), Toast.LENGTH_SHORT).show();

//                                ((HomeActivity) getActivity()).resetToHome();
                            } else {
                                Toast.makeText(ChatActivity.this, "" + viewProfileResponce.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(ChatActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
                        }*/
                    }

                    @Override
                    public void onFailure(@NonNull Call<ChatResponseBean> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        MyDialog.getInstance(ChatActivity.this).hideDialog();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, RequestBody> setUpMapData(String the_token, String senderId, String the_bio_duration) {

        Map<String, RequestBody> fields = new HashMap<>();

        RequestBody sender_id = RequestBody.create(MediaType.parse("text/plain"), senderId);
        RequestBody receiver_id = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), the_token);
        RequestBody duration = RequestBody.create(MediaType.parse("text/plain"), the_bio_duration);


        fields.put("sender_id", sender_id);
        fields.put("receiver_id", receiver_id);
        fields.put("token", token);
        fields.put("message_duration", duration);

        return fields;
    }


   /* //    METHOD: TO REQUEST chatSendMessage SERVICE..
    public void chatSendMessageService(Context context, String messageTxt) {

        final CustomDialog dialog;

        String token = SPreferenceWriter.getInstance(context).getString(SPreferenceKey.TOKEN);
        String userType = MyApplication.getLoginUserType(context);

        if (!token.isEmpty()) {

//                  REQUESTING chatSendMessage  SERVICE..
            try {
                ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();

                Call<ChatResponseBean> call = apiInterface
                        .chatSendMessage(token,
                                chat_sr_user_id,
                                chat_sr_doc_id,
                                messageTxt,
                                userType);

                dialog = CustomDialog.showDialog(context);

                ServiceParameters.setUpParameters(token,
                        chat_sr_user_id,
                        chat_sr_doc_id,
                        messageTxt,
                        userType);

                call.enqueue(new Callback<ChatResponseBean>() {
                    @Override
                    public void onResponse(@NonNull Call<ChatResponseBean> call,
                                           @NonNull Response<ChatResponseBean> response) {
                        CustomDialog.hideCloseDialog(dialog);
                        if (response.isSuccessful()) {
                            ChatResponseBean bean = response.body();
                            if (bean != null) {
                                if (bean.getStatus().equalsIgnoreCase("SUCCESS")) {

                                    binding.etChatResponse.setText("");
                                    isMsgSendEver = true;

                                    ChatDataBean chatDataBean = bean.getChatSendResponse();
                                    if (chatDataBean != null) {

                                        chatDataList.add(chatDataBean);
                                        if (adapter != null)
                                        {
                                            adapter.setArrayList(chatDataList);
                                            adapter.notifyDataSetChanged();
                                            binding.recyclerViewChats.scrollToPosition(adapter.getArrayList().size()-1);

                                        } else {
                                            setUpRecycler(chatDataList);    // IN chatSendMessage  SERVICE..
                                        }
                                    }

                                } else {
                                    Toast.makeText(context, "" + bean.getMessage(), Toast.LENGTH_SHORT).show();
                                    //  Invalid User.
                                    ApiClientConnection.getInstance().checkIfUserInvalid(ChatAct.this, context, bean.getMessage());
                                }
                            }
                        } else {
                            Toast.makeText(context, AppConstants.kMessageServerNotRespondingError, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ChatResponseBean> call, @NonNull Throwable t) {
                        CustomDialog.hideCloseDialog(dialog);
                        t.printStackTrace();
                        MyApplication.showOnFailureMessages(context, t);
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
*/

   /* private void getmsgService() {

        try {

            MyDialog.getInstance(ChatActivity.this).showDialog(this);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<ReportedUserModel> call = apiInterface.requestGetmsg(SPreferenceWriter.getInstance(ChatActivity.this).getString(SPreferenceKey.TOKEN),SPreferenceWriter.getInstance(getApplicationContext()).getString(SPreferenceKey.USERID),userId);

            call.enqueue(new retrofit2.Callback<ReportedUserModel>() {
                @Override
                public void onResponse(@NonNull Call<ReportedUserModel> call, @NonNull Response<ReportedUserModel> response) {
                    MyDialog.getInstance(ChatActivity.this).hideDialog();
                    if (response.isSuccessful()) {
                        ReportedUserModel ReportedUserModel = response.body();
                        Log.e("", "onResponse: "+ ReportedUserModel.toString() );

                        if (ReportedUserModel.getStatus().equalsIgnoreCase("SUCCESS")) {
                            Toast.makeText(ChatActivity.this, "" + ReportedUserModel.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();

                        } else {
                            Toast.makeText(ChatActivity.this, "" + ReportedUserModel.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ChatActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ReportedUserModel> call, Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(ChatActivity.this).hideDialog();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    private void setUpRecycler(ArrayList<ChatDataBean> chatDataList) {

        if (chatDataList != null && chatDataList.size() > 0) {
            binding.recyclerChat.setVisibility(View.VISIBLE);
            binding.noDataTxt.setVisibility(View.GONE);

            RecyclerView recyclerView = binding.recyclerChat;

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);

            for (int i = 0; i < chatDataList.size(); i++) {
                ChatDataBean dataBean = chatDataList.get(i);
                dataBean.setPlayPauseEnable(true);
            }
            adapter = new ChatAdapterSocket(this, chatDataList);
            recyclerView.setAdapter(adapter);

            binding.recyclerChat.scrollToPosition(adapter.getArrayList().size() - 1);

            adapter.setChatPlayingListener(new ChatAdapterSocket.ChatPlayingListener() {
                @Override
                public void onPostPlaying(boolean isPostPlaying) {

                    if (isPostPlaying) {
                        binding.playPauseChat.setEnabled(false);
                    } else {
                        binding.playPauseChat.setEnabled(true);
                    }
                }
            });

            chatActivityBioListener = new ChatActivityBioListener() {
                @Override
                public void onBioPlaying(boolean isBioPlaying) {

                    Log.w(ChatActivity.class.getSimpleName(),
                            "ChatActBioListener : isBioPlaying :->" + isBioPlaying);

                    if (isBioPlaying)
                        adapter.setDisableAll();
                    else
                        adapter.setEnableAll();
                }
            };
            adapter.setChatListener(chatActivityBioListener);

        } else {
            binding.recyclerChat.setVisibility(View.GONE);
            binding.noDataTxt.setVisibility(View.VISIBLE);
            binding.btnReplyChat.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
//        chatMsgHistoryService(ChatActivity.this);
        //POST LIST
        if (adapter != null) {
            adapter.resetAllOtherPrevious();
        }
    }

    //INTERFACE AS LISTENER
    public interface ChatActivityBioListener {

        void onBioPlaying(boolean isBioPlaying);

    }


}
