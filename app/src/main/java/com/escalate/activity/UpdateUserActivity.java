package com.escalate.activity;

import android.Manifest;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
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
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.am.siriview.DrawView;
import com.escalate.R;
import com.escalate.databinding.ActivityUpdateUserBinding;
import com.escalate.model.ViewProfileResponce;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.escalate.utils.TakeImage;
import com.escalate.utils.Util;
import com.hbb20.CountryCodePicker;
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
import retrofit2.Response;

public class UpdateUserActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityUpdateUserBinding binding;

    private static int RESULT_LOAD_IMAGE = 1;
    private static final int CAMERA_REQUEST = 1888;
    private String image = null;
    public static String editGenre = "";
    private Dialog dialog;
    // recording variable
    private String _strMessage;
    private ImageView _recordButton;
    private TextView _timerView;
    boolean checkRecoed, isplayCheck;
    private static final int REQUEST_CODE_ASK_ALL_PERMISSIONS = 1010;
    private String audioFilePath = null;
    private MediaRecorder mediaRecorder = null;
    private File audioFile = null;
    private MediaPlayer mediaPlayer = null;
    private long startTime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    int mins, secs, milliseconds;
    private CountDownTimer timer = null;
    private Handler customHandler = new Handler();
    private String countryCodeAndroid = "";
    String s = "";
    private boolean flagCountryCode = false;
    //BIO
    private String the_bio_duration = "";
    private String getCode = "";
    private String bio_audioFilePath = null;
    private MediaPlayer bio_mediaPlayer = null;
    private boolean isBioPlayCheck = false;
    private CountDownTimer bio_timer = null;
    private long bio_elapsedTime = 0L;
    private UserProfileBioListener profileBioListener;
    //SIRI VIEW
    private SiriUpdateThread updaterThread;
    private Thread theWThread;
    private boolean isGenreUpdated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_user);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        if (getIntent() != null) {
            the_bio_duration = getIntent().getStringExtra("the_bio_duration");
            binding.txtTimerUpdt.setText(the_bio_duration);
            countryCodeAndroid = getIntent().getStringExtra("country_code");
            //ccp.setCountryForNameCode("+91");
            if (countryCodeAndroid != null)
                binding.ccpProfile.setCountryForPhoneCode(Integer.parseInt(countryCodeAndroid));
        }

        init();

        binding.ccpProfile.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                //ccp.setDefaultCountryUsingNameCode(countryCodeAndroid);

                countryCodeAndroid = binding.ccpProfile.getSelectedCountryCodeWithPlus();
                SPreferenceWriter.getInstance(UpdateUserActivity.this).writeStringValue(SPreferenceKey.COUNTRYCODE, countryCodeAndroid);
                flagCountryCode = true;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.editBioIv:
                resetUserAllThreadsPlayers();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                            ) {
                        checkPermission();
                    } else {
                        showEditDialog(this);
                    }
                } else {
                    showEditDialog(this);
                }

                break;
            case R.id.backIvew:
                if (!isGenreUpdated) {
                    Intent intent = new Intent();
                    intent.putExtra("status", "0");
                    setResult(111, intent);
                    finish();
                } else {
                    Toast.makeText(this, "Click on save button to update Genre.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnSave:
                if (Util.showInternetAlert(this)) {
                    editAttachmentService(the_bio_duration);    //SAVE PROFILE SERVICE
                }
                break;
            case R.id.frameProfilePick:
                showDialogForUpload();
                break;
            case R.id.relGenreLay:
                editGenre = "edit";
                binding.txtTimerUpdt.setText(the_bio_duration);

                Intent i = new Intent(this, ProceedActivity.class);
                i.putExtra("from", "my_profile");
                startActivityForResult(i, 121);
                break;
            case R.id.imgPlayPsUpdt:
                if (bio_audioFilePath != null && !bio_audioFilePath.isEmpty()) {
                    //BIO
                    doJustPlayStopPlayBio();//PLAYING BIO
                } else {
                    Toast.makeText(UpdateUserActivity.this, "Update Bio.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.edtName:
                binding.edtName.setFocusable(true);
                binding.edtName.setClickable(true);
                binding.edtName.setFocusableInTouchMode(true);
                binding.edtName.requestFocus();
                binding.edtName.setSelection(binding.edtName.getText().toString().length());
                /*InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);*/
                break;
            case R.id.imgUsrNameEdit:
                binding.edtUsrName.setEnabled(true);
                binding.edtUsrName.setFocusable(true);
                binding.edtUsrName.setClickable(true);
                binding.edtUsrName.setFocusableInTouchMode(true);
                binding.edtUsrName.setSelection(binding.edtUsrName.getText().toString().length());
                break;
            case R.id.imgEmailUpdt:
                binding.edtEmailU.setFocusable(true);
                binding.edtEmailU.setClickable(true);
                binding.edtEmailU.setFocusableInTouchMode(true);
                binding.edtEmailU.requestFocus();
                binding.edtEmailU.setSelection(binding.edtEmailU.getText().toString().length());
                break;
            case R.id.imgPhnupdt:
                binding.edtPhoneUpdt.setFocusable(true);
                binding.edtPhoneUpdt.setClickable(true);
                binding.edtPhoneUpdt.setFocusableInTouchMode(true);
                binding.edtPhoneUpdt.requestFocus();
                binding.edtPhoneUpdt.setSelection(binding.edtPhoneUpdt.getText().toString().length());
                break;
           /* case R.id.imgPhnupdt:
                binding.edtPhoneUpdt.setFocusable(true);
                binding.edtPhoneUpdt.setClickable(true);
                binding.edtPhoneUpdt.setFocusableInTouchMode(true);
                binding.edtPhoneUpdt.requestFocus();
                binding.edtPhoneUpdt.setSelection(binding.edtPhoneUpdt.getText().toString().length());
                break;*/
        }
    }

    @SuppressLint("WrongViewCast")
    private void init() {

        binding.editBioIv.setOnClickListener(this);
        binding.backIvew.setOnClickListener(this);
        binding.btnSave.setOnClickListener(this);
        binding.frameProfilePick.setOnClickListener(this);
        binding.relGenreLay.setOnClickListener(this);
        binding.imgPlayPsUpdt.setOnClickListener(this);
        binding.imgPlayPsUpdt.setImageResource(R.drawable.home_play_a);
        binding.edtName.setOnClickListener(this);
        binding.imgNameEdit.setOnClickListener(this);
        binding.imgUsrNameEdit.setOnClickListener(this);
        binding.imgEmailUpdt.setOnClickListener(this);
        binding.imgPhnupdt.setOnClickListener(this);
       //binding.ccpProfile.setOnClickListener(this);

        binding.edtName.setText(SPreferenceWriter.getInstance(getApplicationContext()).getString(SPreferenceKey.NAME));
        binding.edtUsrName.setText(SPreferenceWriter.getInstance(getApplicationContext()).getString(SPreferenceKey.USERNAME));
        binding.edtPhoneUpdt.setText(SPreferenceWriter.getInstance(getApplicationContext()).getString(SPreferenceKey.PHONENUMBER));
        binding.edtEmailU.setText(SPreferenceWriter.getInstance(getApplicationContext()).getString(SPreferenceKey.EMAIL));
        binding.txtGenreU.setText(SPreferenceWriter.getInstance(getApplicationContext()).getString(SPreferenceKey.GENRE));
        String bio = SPreferenceWriter.getInstance(getApplicationContext()).getString(SPreferenceKey.BIO);
        if (!bio.equals("")) {
            if (!bio.isEmpty()) {
                audioFilePath = bio;
                //BIO
                bio_audioFilePath = bio;
            }
        } else {
            binding.imgPlayPsUpdt.setVisibility(View.INVISIBLE);


        }
        String image = SPreferenceWriter.getInstance(getApplicationContext()).getString(SPreferenceKey.IMAGE);
        if (image != null) {
            if (!image.isEmpty()) {
                Picasso.get().load(image)
                        .resize(100, 100).centerCrop(Gravity.CENTER).into(binding.cirImgUpdt);
            }
        } else {
            binding.cirImgUpdt.setImageResource(R.drawable.default_image);
        }

        if (audioFilePath != null) {
            if (!audioFilePath.isEmpty()) {

            }
        }

        String userType = SPreferenceWriter.getInstance(this).getString(SPreferenceKey.USER_TYPE);
        String user_id_name = SPreferenceWriter.getInstance(this).getString(SPreferenceKey.USER_ID_NAME);

        if (userType != null && !userType.isEmpty()) {
            if ("FB_TYPE".equalsIgnoreCase(userType)) {
                //username
                binding.imgUsrNameEdit.setVisibility(View.VISIBLE);
                binding.edtUsrName.setEnabled(false);

                //email
                binding.imgEmailUpdt.setEnabled(false);
                binding.imgEmailUpdt.setVisibility(View.GONE);

            } else {
                //username
                binding.imgUsrNameEdit.setVisibility(View.GONE);
                binding.edtUsrName.setEnabled(false);

                //email
                binding.imgEmailUpdt.setEnabled(true);
                binding.imgEmailUpdt.setVisibility(View.VISIBLE);
            }
        }

        // FOR ONE TIME USER_ID_NAME EDITING
        if (user_id_name != null && !user_id_name.isEmpty()) {
            //username
            binding.imgUsrNameEdit.setVisibility(View.GONE);
            binding.edtUsrName.setEnabled(false);
        } else {
            //username
            binding.imgUsrNameEdit.setVisibility(View.VISIBLE);
            binding.edtUsrName.setEnabled(false);
        }


    }


    @Override
    public void onBackPressed() {

        if (!isGenreUpdated) {
            Intent intent = new Intent();
            intent.putExtra("status", "0");
            setResult(111, intent);
            finish();
        } else {
            Toast.makeText(this, "Click on save button to update Genre.", Toast.LENGTH_SHORT).show();
        }
    }


    ////////////////************************//////////////


    @Override
    protected void onPause() {
        super.onPause();

        resetUserAllThreadsPlayers();
    }

    /*
     * **************************
     * //TODO: PLAY BIO RECORDING
     * **************************
     * */
    private void doJustPlayStopPlayBio() {
        ImageView img_play_pause = binding.imgPlayPsUpdt;
        final TextView tv_duration = binding.txtTimerUpdt;

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

                        tv_duration.setText(the_bio_duration);

                        resetSiriView();    //reset SIRI

                        if (profileBioListener != null)
                            profileBioListener.onBioPlaying(false);
                    }
                }.start();


            } catch (Exception e) {
                e.printStackTrace();
            }

            img_play_pause.setImageDrawable(ContextCompat.getDrawable(UpdateUserActivity.this,
                    R.drawable.home_pause_a));
            isBioPlayCheck = true;


        } else {

            if (bio_mediaPlayer != null)
                bio_mediaPlayer.pause();

            img_play_pause.setImageDrawable(ContextCompat.getDrawable(UpdateUserActivity.this,
                    R.drawable.home_play_a));
            isBioPlayCheck = false;


            tv_duration.setText(the_bio_duration);

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

    public static String convertSecondsToHMmSs(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
//        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);


        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    //        PLAY LAST RECORDING snippet
    private void snippetOfPlayLastRecBio() {

        if (bio_audioFilePath != null && !bio_audioFilePath.isEmpty()) {

            final ImageView img_play_pause = binding.imgPlayPsUpdt;
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
                        img_play_pause.setImageDrawable(ContextCompat.getDrawable(UpdateUserActivity.this,
                                R.drawable.home_play_a));
                        isBioPlayCheck = false;
                        binding.txtTimerUpdt.setText(the_bio_duration);

                        if (profileBioListener != null)
                            profileBioListener.onBioPlaying(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }


    }


    public interface UserProfileBioListener {

        void onBioPlaying(boolean isBioPlaying);

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
            if (isBio) {
                updaterThread = new SiriUpdateThread(30, binding.siriDViewUpdt, UpdateUserActivity.this);
                updaterThread.setPRog(10000);
            }
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


    public void resetUserAllThreadsPlayers() {

        try {


            //BIO
            if (bio_mediaPlayer != null) {
                bio_mediaPlayer.stop();
                bio_mediaPlayer.reset();
                bio_mediaPlayer.release();
                bio_mediaPlayer = null;
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


            if (binding.imgPlayPsUpdt != null && binding.txtTimerUpdt != null) {
                ImageView img_play_pause = binding.imgPlayPsUpdt;
                final TextView tv_duration = binding.txtTimerUpdt;

                img_play_pause.setImageDrawable(ContextCompat.getDrawable(UpdateUserActivity.this,
                        R.drawable.home_play_a));
                isBioPlayCheck = false;
                tv_duration.setText("00:00");
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


    //////////////


    public void showDialogForUpload() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Upload Image");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alertDialogBuilder.setNeutralButton("Gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(getApplicationContext(), TakeImage.class);
                intent.putExtra("from", "gallery");
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
                dialog.cancel();
            }
        });
        alertDialogBuilder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), TakeImage.class);
                intent.putExtra("from", "camera");
                startActivityForResult(intent, CAMERA_REQUEST);
                dialog.dismiss();
                binding.txtTimerUpdt.setText(s);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESULT_LOAD_IMAGE || requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                image = data.getStringExtra("filePath");
                if (image != null) {
                    File imgFile = new File(data.getStringExtra("filePath"));
                    if (imgFile.exists()) {
                        //imgProfile.setImageURI(Uri.fromFile(imgFile));
                        Picasso.get().load(Uri.fromFile(imgFile))
                                .resize(100, 100).centerCrop(Gravity.CENTER).into(binding.cirImgUpdt);
                    }
                } else {
                    image = null;
                }

            }
        } else if (requestCode == 121) {
            if (resultCode == RESULT_OK) {
                try {
                    Bundle b = data.getExtras();
                    if (b != null) {
                        String genre = b.getString("genre");
                        binding.txtGenreU.setText(genre);
                        isGenreUpdated = true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Map<String, RequestBody> setUpMapData(String the_token, String the_bio_duration) {

        Map<String, RequestBody> fields = new HashMap<>();

        RequestBody fullname = RequestBody.create(MediaType.parse("text/plain"), binding.edtName.getText().toString());
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), binding.edtPhoneUpdt.getText().toString());
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), binding.edtUsrName.getText().toString().trim());
        RequestBody deviceToken = RequestBody.create(MediaType.parse("text/plain"), the_token);
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), binding.edtEmailU.getText().toString());
        RequestBody bio_duration = RequestBody.create(MediaType.parse("text/plain"), the_bio_duration);
        RequestBody country_code = RequestBody.create(MediaType.parse("text/plain"), countryCodeAndroid);

        fields.put("email", email);
        fields.put("username", username);
        fields.put("fullname", fullname);
        fields.put("phone", phone);
        fields.put("token", deviceToken);
        fields.put("bio_duration", bio_duration);
        fields.put("country_code", country_code);

        return fields;
    }

    private void editAttachmentService(String the_bio_duration) {
        try {
            MyDialog.getInstance(UpdateUserActivity.this).showDialog(this);
            String token = SPreferenceWriter.getInstance(UpdateUserActivity.this).getString(SPreferenceKey.TOKEN);
            String userid = SPreferenceWriter.getInstance(getApplicationContext()).getString(SPreferenceKey.USERID);

            if (true) {
                RequestBody profile_body;
                MultipartBody.Part profilePart;

                if (image != null) {
                    File file = new File(image);
                    profile_body = RequestBody.create(MediaType.parse("image/*"), file);
                    profilePart = MultipartBody.Part.createFormData("image", file.getName(), profile_body);
                } else {
                    profilePart = MultipartBody.Part.createFormData("image", "");
                }

                RequestBody profile_body_audio;
                MultipartBody.Part profilePartAudio;

                if (audioFilePath != null) {
                    if (!audioFilePath.contains("http")) {
                        File file = new File(audioFilePath);
                        profile_body_audio = RequestBody.create(MediaType.parse("bio/*"), file);
                        profilePartAudio = MultipartBody.Part.createFormData("bio", file.getName(), profile_body_audio);
                    } else {
                        profilePartAudio = MultipartBody.Part.createFormData("bio", "");
                    }
                } else {
                    profilePartAudio = MultipartBody.Part.createFormData("bio", "");
                }

                ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
                Map<String, RequestBody> map = setUpMapData(token, the_bio_duration);
                Call<ViewProfileResponce> call = apiInterface.edit_attachment(userid, map, profilePart, profilePartAudio);
                call.enqueue(new retrofit2.Callback<ViewProfileResponce>() {
                    @Override
                    public void onResponse(Call<ViewProfileResponce> call, Response<ViewProfileResponce> response) {
                        MyDialog.getInstance(UpdateUserActivity.this).hideDialog();
                        if (response.isSuccessful()) {
                            ViewProfileResponce viewProfileResponce = response.body();
                            Log.e("responce", "" + viewProfileResponce.toString());
                            if (viewProfileResponce.getStatus().equalsIgnoreCase("SUCCESS")) {

                                SPreferenceWriter.getInstance(getApplicationContext())
                                        .setEditDetails(viewProfileResponce);

                                Intent intent = new Intent();
                                intent.putExtra("status", "1");
                                setResult(111, intent);
                                finish();
                                Toast.makeText(UpdateUserActivity.this, "" + viewProfileResponce.getMessage(), Toast.LENGTH_SHORT).show();

                            } else {
                                //"Login Token Expire"
                                Toast.makeText(UpdateUserActivity.this, "" + viewProfileResponce.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(UpdateUserActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ViewProfileResponce> call, Throwable t) {
                        t.printStackTrace();
                        MyDialog.getInstance(UpdateUserActivity.this).hideDialog();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showEditDialog(Context context) {

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_update_bio_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        final ImageView btnRecordDialog = dialog.findViewById(R.id.btnRecord);
        TextView timerTxtDialog = dialog.findViewById(R.id._txtTimer);
        _timerView = timerTxtDialog;
        _recordButton = btnRecordDialog;
        dialog.findViewById(R.id.img_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bio = SPreferenceWriter.getInstance(getApplicationContext()).getString(SPreferenceKey.BIO);

                dialog.dismiss();
                binding.txtTimerUpdt.setText(s);
                stopRecording();
                Toast.makeText(UpdateUserActivity.this, "Click on save button to update Bio.", Toast.LENGTH_SHORT).show();
//                    timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
                checkRecoed = false;
                binding.imgPlayPsUpdt.setVisibility(View.VISIBLE);
                _recordButton.setImageDrawable(ContextCompat.getDrawable(UpdateUserActivity.this, R.drawable.create_memo_mic));

            }
        });

        btnRecordDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkRecoed) {
                    _timerView.setVisibility(View.VISIBLE);
                    doAllPermissionChecking();
                    checkRecoed = true;
                    scaleAnimation();
                    _recordButton.setImageDrawable(ContextCompat.getDrawable(UpdateUserActivity.this, R.drawable.create_memo_hour));

                } else {
                    dialog.dismiss();
                    binding.txtTimerUpdt.setText(s);
                    stopRecording();
                    Toast.makeText(UpdateUserActivity.this, "Click on save button to update Bio.", Toast.LENGTH_SHORT).show();
//                    timeSwapBuff += timeInMilliseconds;
                    customHandler.removeCallbacks(updateTimerThread);
                    checkRecoed = false;
                    binding.imgPlayPsUpdt.setVisibility(View.VISIBLE);
                    _recordButton.setImageDrawable(ContextCompat.getDrawable(UpdateUserActivity.this, R.drawable.create_memo_mic));

                }

            }
        });

//        init(dialog);


        dialog.show();
    }

    private void init(final Dialog dialog) {

        String strMessage = _strMessage == null ? "00:00" : _strMessage;
        _timerView = dialog.findViewById(R.id._txtTimer);
        _timerView.setText(strMessage);
        _recordButton = dialog.findViewById(R.id.btnRecord);

        _recordButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                scaleAnimation();

            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void scaleAnimation() {
        final Interpolator interpolador = AnimationUtils.loadInterpolator(this,
                android.R.interpolator.fast_out_slow_in);
        _recordButton.animate()
                .scaleX(1.1f)
                .scaleY(1.1f)
                .setInterpolator(interpolador)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        _recordButton.animate().scaleX(1f).scaleY(1f).start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    public interface ClickListener {
        void OnClickListener(String path);
    }


    private boolean checkPermission() {

        String[] permission = new String[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            permission = new String[]{
                    Manifest.permission.RECORD_AUDIO,

            };
        }
        int permissionCode = 23;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (
                    ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                    ) {
            } else {
                requestPermissions(permission, permissionCode);
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 23: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("PermissionGranted", ": " + grantResults[0]);
                    showEditDialog(this);
                } else {
                    Log.e("PermissionGranted", "else: " + grantResults[0]);
                    checkPermission();
                }
            }
        }
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
                                ActivityCompat.requestPermissions(UpdateUserActivity.this,
                                        permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_ALL_PERMISSIONS);
                            }
                        });
                return;
            }
            ActivityCompat.requestPermissions(UpdateUserActivity.this,
                    permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_ALL_PERMISSIONS);
            return;
        }

//        start doing things if all PERMISSIONS are Granted whensoever
//        for Marshmallow+ and Pre-Marshmallow both
        startRecording();
    }

    ////    adding RUNTIME PERMISSION to permissionsList and checking If user has GRANTED Permissions or Not  /////
    private boolean addPermission(List<String> permissionsList, String permission) {
        // Marshmallow+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(UpdateUserActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                if (!ActivityCompat.shouldShowRequestPermissionRationale(UpdateUserActivity.this, permission))
                    return false;
            }
        }
        // Pre-Marshmallow
        return true;
    }

    //    Handle "Don't / Never Ask Again" when asking permission
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(UpdateUserActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    //      START RECORDING
    private void startRecording() {
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

                audioFile = new File(dir, audioFileName);
                audioFilePath = audioFile.getAbsolutePath();

                bio_audioFilePath = audioFilePath;

                Log.w("AudioFile_Path :", "" + audioFilePath);

                setUpMediaRecorder();

                try {
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                }

//                recordBtnGlobal.setEnabled(false);

                startTimer();
            }
        }


    }

    //    check If User has Mounted the SD CARD or not
    private boolean didUserHasSDCard() {
        boolean isSDPresent = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (isSDPresent) {
            Log.w("SD_CARD", "YES");
            return true;
        } else {
            Log.w("SD_CARD", "NO");
            Toast.makeText(UpdateUserActivity.this, "SD CARD not available.", Toast.LENGTH_SHORT).show();
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

    // Set Up MEDIA RECORDER to Record Audio
    public void setUpMediaRecorder() {

      /*  mediaRecorder = new MediaRecorder();

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        mediaRecorder.setOutputFile(audioFilePath);

        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setAudioEncodingBitRate(16);
        mediaRecorder.setAudioSamplingRate(44100);

        */

        //////new code/////////

        mediaRecorder = new MediaRecorder();

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

        mediaRecorder.setOutputFile(audioFilePath);
        //new voice
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        //  mediaRecorder.setAudioEncodingBitRate(16);
        mediaRecorder.setAudioEncodingBitRate(128000);
        mediaRecorder.setAudioSamplingRate(44100);

    }


    //        PLAY LAST RECORDING
    private void playLastRecording() {

        if (mediaPlayer == null) {
            snippetOfPlayLastRec();
        } else {
            mediaPlayer.release();
            mediaPlayer = null;

            snippetOfPlayLastRec();
        }
    }

    //        PLAY LAST RECORDING snippet
    private void snippetOfPlayLastRec() {
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(audioFilePath);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.pause();
                binding.imgPlayPsUpdt.setImageResource(R.drawable.home_play_a);
                isplayCheck = false;

            }
        });


    }

    //          STOP AND SAVE RECORDING
    private void stopRecording() {
        try {
            if (mediaRecorder != null)
                mediaRecorder.stop();

//        recordBtnGlobal.setEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void startTimer() {
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);

    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;
            secs = (int) (updatedTime / 1000);
            mins = secs / 60;
            secs = secs % 60;
            milliseconds = (int) (updatedTime % 1000);


            if (updatedTime >= 41000) {
                Toast.makeText(UpdateUserActivity.this, "Recording limit(40 sec) reached!", Toast.LENGTH_SHORT).show();

                _timerView.setVisibility(View.GONE);

                dialog.dismiss();
                binding.txtTimerUpdt.setText(s);
                stopRecording();
                Toast.makeText(UpdateUserActivity.this, "Click on save button to update Bio.", Toast.LENGTH_SHORT).show();
//                    timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
                checkRecoed = false;

                _recordButton.setImageDrawable(ContextCompat.getDrawable(UpdateUserActivity.this, R.drawable.create_memo_mic));

                updatedTime = 0L;
                timeSwapBuff = 0L;
                timeInMilliseconds = 0L;
            } else {
                //update the time
                the_bio_duration = convertSecondsToHMmSs(updatedTime);
                customHandler.postDelayed(this, 0);
            }

            s = mins + ":"
                    + String.format(Locale.getDefault(), "%02d", secs) + ":"
                    + String.format(Locale.getDefault(), "%02d", milliseconds);


            /*s = "00:0" + "" + mins + ":"
                    + String.format(Locale.getDefault(), "%02d", secs));*/

            s = the_bio_duration;
            _timerView.setText(the_bio_duration);


        }

    };

    @Override
    protected void onStart() {
        super.onStart();
        binding.txtTimerUpdt.setText(the_bio_duration);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.txtTimerUpdt.setText(the_bio_duration);
    }
}
