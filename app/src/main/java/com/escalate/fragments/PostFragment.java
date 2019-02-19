package com.escalate.fragments;


import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
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
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.am.siriview.DrawView;
import com.escalate.R;
import com.escalate.activity.HomeActivity;
import com.escalate.adapter.CategorySearchAdapter;
import com.escalate.adapter.CatetagoryPostAdapter;
import com.escalate.databinding.FragmentPostBinding;
import com.escalate.model.GenerResponce;
import com.escalate.model.PostAudioResponce;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.escalate.utils.Util;
import com.volokh.danylo.hashtaghelper.HashTagHelper;

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
import omrecorder.Recorder;
import retrofit2.Call;
import retrofit2.Response;

public class PostFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private FragmentPostBinding binding;

    String selected_category_id = "";
    String selected_category_name = "";
    String s = "";
    private CategorySearchAdapter categorySearchAdapter;
    private MenuItem cancelPostMenuItem;
    //POST AUDIO
    private static final int REQUEST_CODE_ASK_ALL_PERMISSIONS = 1010;
    private CountDownTimer comment_timer = null;
    private String the_bio_duration = "";
    //SIRI VIEW
    private SiriUpdateThread updaterThread;
    private Thread theWThread;
    // recording variable
    private String _strMessage;
    Recorder recorder;
    private List<GenerResponce.DataBean> genreList = new ArrayList<>();

    CatetagoryPostAdapter catetagoryPostAdapter;
    String[] eventlist_array = {};
    private Handler customHandler = new Handler();
    boolean checkRecoed, isplayCheck, muteCheck;
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
    private CountDownTimer timerUp = null;
    private long elapsedTime = 0L;

    private HashTagHelper mHashTagHelper;

    public PostFragment() {

        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false);

        binding.imgRePlay.setOnClickListener(this);
        binding.recordButton.setOnClickListener(this);
        binding.btnPost.setOnClickListener(this);
        binding.imgPlayPausePost.setOnClickListener(this);
        binding.relativePostMic.setOnClickListener(this);
        binding.edtTxtSearchPost.setOnClickListener(this);
        init();

        //HASH TAGS
        mHashTagHelper = HashTagHelper.Creator.create(getResources()
                .getColor(R.color.blue), null);
        mHashTagHelper.handle(binding.edtDesc);

        /*
        * //Pattern to find if there's a hash tag in the message
				//i.e. any word starting with a # and containing letter or numbers or _
				Pattern tagMatcher = Pattern.compile("[#]+[A-Za-z0-9-_]+\\b");
        * */
/*

        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                seekChange(v);
                return false; }
        });
*/


        /*spinnerPost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

//                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPrimary));
                ((TextView) adapterView.getChildAt(0)).setTextSize(14);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/


        //CITY SEARCH
        binding.edtTxtSearchPost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0) {
                    if (categorySearchAdapter != null) {
                        binding.recViewCateg.setVisibility(View.VISIBLE);
                        binding.tvNoDataCategories.setVisibility(View.GONE);
                        categorySearchAdapter.getFilter().filter(s.toString());

                    } else {
                        binding.recViewCateg.setVisibility(View.VISIBLE);
                        setUpCategoriesRecycler(binding.edtTxtSearchPost, binding.recViewCateg,
                                (ArrayList<com.escalate.model.GenerResponce.DataBean>) genreList);
                    }
                } else {
                    binding.recViewCateg.setVisibility(View.GONE);
                    binding.tvNoDataCategories.setVisibility(View.GONE);
                    selected_category_id = "";
                    selected_category_name = "";
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recordButton:
                hideSoftInputFromWindow(getActivity());
                if (!checkRecoed) {
                    cancelPostMenuItem.setVisible(false);

                    binding.recoedRelLay.setVisibility(View.VISIBLE);
                    binding.txtTimerP.setVisibility(View.VISIBLE);
                    binding.relativePostMic.setVisibility(View.GONE);
                    doAllPermissionChecking();
                    checkRecoed = true;
                    scaleAnimation(binding.recordButton);
                    binding.recordButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.create_memo_hour));
                } else {
                    cancelPostMenuItem.setVisible(true);
                    binding.txtTimerPost.setText(s);
                    binding.txtTimerP.setVisibility(View.GONE);
                    binding.relativePostMic.setVisibility(View.VISIBLE);
                    binding.recoedRelLay.setVisibility(View.GONE);
                    stopRecording();
                    binding.recoedRelLay.setVisibility(View.GONE);
//                    timeSwapBuff += timeInMilliseconds;
                    customHandler.removeCallbacks(updateTimerThread);
                    checkRecoed = false;

                    binding.recordButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.create_memo_mic));
                }
                break;
            case R.id.btnPost:

                hideSoftInputFromWindow(getActivity());
                if (validate()) {
                    if (Util.showInternetAlert(getActivity())) {
//                        doJustPlayStopPlay();
                        resetAllOtherPrevious();
                        postAudioService(the_bio_duration);
                    }
                }
                break;
            case R.id.imgRePlay:
                //doThePlaying(true); //RE-PLAYING
                break;
            case R.id.imgPlayPausePost:

                hideSoftInputFromWindow(getActivity());

                if (audioFilePath != null && !audioFilePath.isEmpty()) {

                    doJustPlayStopPlay();//PLAYING
                } else {
                    Toast.makeText(getActivity(), "Audio is empty.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.relativePostMic:
                hideSoftInputFromWindow(getActivity());
                break;

            case R.id.edtTxtSearchPost:
                binding.recViewCateg.setVisibility(View.VISIBLE);
                binding.edtTxtSearchPost.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void hideSoftInputFromWindow(@NonNull Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } else {
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }


    }

    private void doJustPlayStopPlay() {
        if (!isplayCheck) {

            playLastRecording();

            try {
                final int duration = mediaPlayer.getDuration();

                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                timer = new CountDownTimer(duration, 1000) {

                    public void onTick(long millisUntilFinished) {
                        elapsedTime = millisUntilFinished;
                        binding.txtTimerPost.setText(convertSecondsToHMmSs(millisUntilFinished));
                        //SIRI LIKE VIEW
                        startSetUpSiriView(false);   //COMMENT
                    }

                    public void onFinish() {

                        binding.txtTimerPost.setText(s);

                        resetSiriView();    //reset SIRI
                    }
                }.start();


            } catch (Exception e) {
                e.printStackTrace();
            }

            binding.imgPlayPausePost.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.create_memo_pause));
            isplayCheck = true;


        } else {

            if (mediaPlayer != null)
                mediaPlayer.pause();

            binding.imgPlayPausePost.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.create_memo_play));
            isplayCheck = false;

            binding.txtTimerPost.setText(s);

            if (timer != null) {
                timer.cancel();
                timer = null;
            }

            resetSiriView();    //reset SIRI
        }
    }

    private void doJustPlayPause() {
        if (!isplayCheck) {

            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                if (elapsedTime != 0L)
                    mediaPlayer.start();
                else
                    playLastRecording();
            } else {
                playLastRecording();
            }

            try {
//                final int duration = mediaPlayer.getDuration();

                if (elapsedTime == 0L) {
                    elapsedTime = mediaPlayer.getDuration();
                }

                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                timer = new CountDownTimer(elapsedTime, 1000) {

                    public void onTick(long millisUntilFinished) {
                        elapsedTime = millisUntilFinished;
                        binding.txtTimerPost.setText(convertSecondsToHMmSs(millisUntilFinished));
                    }

                    public void onFinish() {
                        elapsedTime = 0L;
                        binding.txtTimerPost.setText("00:00");
                    }
                }.start();

            } catch (Exception e) {
                e.printStackTrace();
            }

            binding.imgPlayPausePost.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.create_memo_pause));
            isplayCheck = true;


        } else {

            if (mediaPlayer != null && mediaPlayer.isPlaying())
                mediaPlayer.pause();

            binding.imgPlayPausePost.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.create_memo_play));
            isplayCheck = false;

            if (elapsedTime != 0L)
                binding.txtTimerPost.setText(convertSecondsToHMmSs(elapsedTime));


            else
                binding.txtTimerPost.setText("00:00");

            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }
    }

    private void doThePlaying(boolean isRePlaying) {
        if (isRePlaying) {
            if (audioFilePath != null && !audioFilePath.isEmpty()) {
                doJustPlayPause();   //RE-PLAYING
            } else {
                Toast.makeText(getActivity(), "Please record sound first.", Toast.LENGTH_SHORT).show();
            }
        } else {
            doJustPlayPause();   //PLAYING
        }

    }

    public static String convertSecondsToHMmSs(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
//        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);


        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }


    private void setSpinner() {

        try {
//            List<String> categories = new ArrayList<String>();
//            categories.add("Categories");
//            for (int i = 0; i < genreList.size(); i++) {
//                categories.add(genreList.get(i).getName());
//            }
//            categories.add("category 3");
//            categories.add("category 4");
//            categories.add("category 5");
//            categories.add("6");
//            categories.add("7");
//            categories.add("8");
//            categories.add("9");
//            categories.add("10");
//
//            if (categories != null) {
//                // Creating adapter for spinner
//                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext()
//                        , R.layout.spinner_text_style, categories);
//                // Drop down layout style - list view with radio button
//                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                // attaching data adapter to spinner
//                spinnerPost.setAdapter(dataAdapter);
//                spinnerPost.setOnItemSelectedListener(this);
//
//
//            }

            eventlist_array = new String[genreList.size()];
            for (int i = 0; i < genreList.size(); i++) {

                eventlist_array[i] = genreList.get(i).getName();
            }

            if (genreList.size() > 0) {
                catetagoryPostAdapter = new CatetagoryPostAdapter(getActivity(),
                        android.R.layout.simple_list_item_1);
                catetagoryPostAdapter.addAll(eventlist_array);
                catetagoryPostAdapter.add("Categories");
                /*spinnerPost.setAdapter(catetagoryPostAdapter);
                spinnerPost.setOnItemSelectedListener(this);
                spinnerPost.setSelection(catetagoryPostAdapter.getCount());*/
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String s = parent.getItemAtPosition(position).toString();
        if (s.equalsIgnoreCase("Categories")) {
            ((TextView) view).setTextColor(Color.parseColor("#808080"));
        } else {
            ((TextView) view).setTextColor(Color.parseColor("#000000"));
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean validate() {

        boolean isValid = true;

        String discription = Util.getProperText(binding.edtDesc);
        EditText editText = null;

       /* if (spinnerPost.getSelectedItem().toString().equals(Objects.requireNonNull(getActivity()).getString(R.string.add_categories))) {
            isValid = false;
            Toast.makeText(getActivity(), "Please select Category", Toast.LENGTH_SHORT).show();
        } else*/
        if (discription.isEmpty()) {
            // edtDescription.setError(getString(R.string.error_empty_description));
            Toast.makeText(getActivity(), getActivity().getString(R.string.error_empty_description), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = binding.edtDesc;
            editText.requestFocus();
        }

        if (editText != null)
            editText.requestFocus();
        return isValid;
    }


    private void init() {

        String strMessage = _strMessage == null ? "00:00" : _strMessage;

        binding.txtTimerP.setText(strMessage);

    }


    public void setMessage(String strMessage) {
        _strMessage = strMessage;
    }


//    private PullableSource mic() {
//        return new PullableSource.Default(
//                new AudioRecordConfig.Default(
//                        MediaRecorder.AudioSource.MIC, AudioFormat.ENCODING_PCM_16BIT,
//                        AudioFormat.CHANNEL_IN_MONO, 44100
//                )
//        );
//    }

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


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void scaleAnimation(final ImageView recordBtn) {
        final Interpolator interpolador = AnimationUtils.loadInterpolator(getContext(),
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cancel_option, menu);
        cancelPostMenuItem = menu.findItem(R.id.retakePost);
        cancelPostMenuItem.setVisible(false);

        SpannableString s = new SpannableString(cancelPostMenuItem.getTitle());
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFFFF")), 0, s.length(), 0);
        cancelPostMenuItem.setTitle(s);
        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.retakePost:
                cancelPostMenuItem.setVisible(false);
                resetAllPost();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void resetAllPost() {
        try {
            if (mediaRecorder != null) {
                mediaRecorder.release();
                mediaRecorder = null;
            }

            if (mediaPlayer != null) {
                mediaPlayer.pause();
                mediaPlayer.release();
                mediaPlayer = null;
            }

            /*if(cancelPostMenuItem!=null)
                cancelPostMenuItem.setVisible(false);*/

            audioFilePath = "";
            the_bio_duration = "";
            checkRecoed = false;
            binding.recoedRelLay.setVisibility(View.VISIBLE);
            binding.relativePostMic.setVisibility(View.GONE);

            binding.imgPlayPausePost.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.create_memo_play));
            isplayCheck = false;


            binding.txtTimerPost.setText("00:00");

            if (timer != null) {
                timer.cancel();
                timer = null;
            }

            resetSiriView();    //reset SIRI

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void resetAllStop() {
        try {
            if (mediaRecorder != null) {
                mediaRecorder.release();
                mediaRecorder = null;
            }

            if (mediaPlayer != null) {
                mediaPlayer.pause();
                mediaPlayer.release();
                mediaPlayer = null;
            }

           /* if(cancelPostMenuItem!=null)
                cancelPostMenuItem.setVisible(false);*/

            audioFilePath = "";
            the_bio_duration = "";
            checkRecoed = false;
            binding.recoedRelLay.setVisibility(View.VISIBLE);
            binding.relativePostMic.setVisibility(View.GONE);

            binding.imgPlayPausePost.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.create_memo_play));
            isplayCheck = false;


            binding.txtTimerPost.setText("00:00");

            if (timer != null) {
                timer.cancel();
                timer = null;
            }

            resetSiriView();    //reset SIRI

        } catch (Exception e) {
            e.printStackTrace();
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
        startRecording();
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

    // Set Up MEDIA RECORDER to Record Audio
    public void setUpMediaRecorder() {

        ////old code///////////
       /* mediaRecorder = new MediaRecorder();

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        mediaRecorder.setOutputFile(audioFilePath);
        //new voice
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
      //  mediaRecorder.setAudioEncodingBitRate(16);
        mediaRecorder.setAudioEncodingBitRate(128000);
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

        //SIRI LIKE VIEW
        startSetUpSiriView(false);   //COMMENT

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
                mediaPlayer.stop();
                binding.imgPlayPausePost.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.create_memo_play));
                isplayCheck = false;

            }
        });

        //SEEK BAR
        final int totalTime = mediaPlayer.getDuration();
        binding.SeekBarPost.setMax(totalTime / 100);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.SeekBarPost.setProgress(mediaPlayer.getCurrentPosition() / 100);
            }
        }, 50);


        binding.SeekBarPost.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && !fromUser) {
                    mediaPlayer.seekTo(progress * 100);
                    seekBar.setProgress(progress);

                    Log.w("onProgressChanged", " progress: " + progress);
                } else {
                    Log.w("onProgressChanged", " from user: " + fromUser);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.SeekBarPost.setEnabled(false);

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
            if (!isBio) {
                updaterThread = new SiriUpdateThread(30, binding.siriDViewPost, getActivity());
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

        private int isPlayingStatus = -1;
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
                        if (PostFragment.SiriUpdateThread.this.v != null) {
                            PostFragment.SiriUpdateThread.this.v.setMaxAmplitude(PostFragment.SiriUpdateThread.this.tr);
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

            s = "0" + "" + mins + ":"
                    + String.format(Locale.getDefault(), "%02d", secs);


            binding.txtTimerP.setText(s);

            if (updatedTime >= 41000) {
                Toast.makeText(getActivity(), "Recording limit(40 sec) reached!", Toast.LENGTH_SHORT).show();
//                cancelPostMenuItem.setVisible(true);

                binding.txtTimerP.setVisibility(View.GONE);
                binding.relativePostMic.setVisibility(View.VISIBLE);
                binding.recoedRelLay.setVisibility(View.GONE);
                stopRecording();
                binding.recoedRelLay.setVisibility(View.GONE);
//                    timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
                checkRecoed = false;

                binding.recordButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.create_memo_mic));
                updatedTime = 0L;
                timeSwapBuff = 0L;
                timeInMilliseconds = 0L;
                binding.txtTimerPost.setText(s);
            } else {
                //update the time
                the_bio_duration = convertSecondsToHMmSs(updatedTime);
                customHandler.postDelayed(this, 0);
            }

        }

    };

    private Map<String, RequestBody> setUpMapData(String the_token, String userid, String the_bio_duration) {

        Map<String, RequestBody> fields = new HashMap<>();

        RequestBody topic_name = RequestBody.create(MediaType.parse("text/plain"), selected_category_name);
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userid);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), binding.edtDesc.getText().toString().replaceAll(",", " "));

        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), the_token);
        RequestBody duration = RequestBody.create(MediaType.parse("text/plain"), the_bio_duration);

        fields.put("topic_name", topic_name);
        fields.put("user_id", user_id);
        fields.put("description", description);
        fields.put("token", token);
        fields.put("duration", duration);

        return fields;
    }

    //API SECTION
    private void postAudioService(String the_bio_duration) {
        try {
            MyDialog.getInstance(getActivity()).showDialog(getActivity());
            String token = SPreferenceWriter.getInstance(getActivity()).getString(SPreferenceKey.TOKEN);
            String userid = SPreferenceWriter.getInstance(getActivity()).getString(SPreferenceKey.USERID);

            if (true) {
                RequestBody profile_body;
                MultipartBody.Part profilePart;

                RequestBody profile_body_audio;
                MultipartBody.Part profilePartAudio;

                if (audioFilePath != null) {
                    File file = new File(audioFilePath);
                    profile_body_audio = RequestBody.create(MediaType.parse("audio/*"), file);
                    profilePartAudio = MultipartBody.Part.createFormData("audio", file.getName(), profile_body_audio);

                } else {
                    profilePartAudio = MultipartBody.Part.createFormData("audio", "");
                }

                ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
                Map<String, RequestBody> map = setUpMapData(token, userid, the_bio_duration);
                Call<PostAudioResponce> call = apiInterface.post_audio(map, profilePartAudio);
                call.enqueue(new retrofit2.Callback<PostAudioResponce>() {
                    @Override
                    public void onResponse(@NonNull Call<PostAudioResponce> call, Response<PostAudioResponce> response) {
                        if (response.isSuccessful()) {
                            PostAudioResponce viewProfileResponce = response.body();
                            Log.e("response", "" + viewProfileResponce.toString());
                            if (viewProfileResponce.getStatus().equalsIgnoreCase("SUCCESS")) {
                                Toast.makeText(getActivity(), "" + viewProfileResponce.getMessage(), Toast.LENGTH_SHORT).show();
                                binding.edtTxtSearchPost.setText("");
                                ((HomeActivity) getActivity()).resetToHome();
                            } else {
                                Toast.makeText(getActivity(), "" + viewProfileResponce.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getActivity(), "Server Error!", Toast.LENGTH_SHORT).show();
                        }
                        MyDialog.getInstance(getActivity()).hideDialog();
                        }

                    @Override
                    public void onFailure(@NonNull Call<PostAudioResponce> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        MyDialog.getInstance(getActivity()).hideDialog();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //CITY SEARCH
    private void setUpCategoriesRecycler(EditText editText,
                                         RecyclerView recyclerView, ArrayList<GenerResponce.DataBean> arrayList) {
        try {


            /*binding.recViewCateg.setVisibility(View.VISIBLE);
            tv_noDataCategories.setVisibility(View.GONE);*/


            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(manager);


            categorySearchAdapter = new CategorySearchAdapter(getActivity(), arrayList, "");
            recyclerView.setAdapter(categorySearchAdapter);

            categorySearchAdapter.setListener(new CategorySearchAdapter.HomeCVListener() {
                @Override
                public void onTheItemClick(View view, int pos) {
                    if (categorySearchAdapter.getFilteredList() != null && categorySearchAdapter.getFilteredList().size() > 0) {
                        GenerResponce.DataBean cityBean = categorySearchAdapter.getFilteredList().get(pos);

                        selected_category_id = cityBean.getTopic_id();
                        selected_category_name = cityBean.getName();

                        Log.w("TAG", "City Name: " + cityBean.getName());
                        Log.w("TAG", "City Id: " + cityBean.getTopic_id());

                        editText.setText(cityBean.getName());
                        editText.setSelection(cityBean.getName().length());

                        binding.recViewCateg.setVisibility(View.GONE);

//                        getSpecialityListByCityService();   //SERVICE HIT...

                    }
                }

                @Override
                public void onEmptyFilters(String data) {
                    Log.w("TAG", " filter : " + data);
                    binding.tvNoDataCategories.setVisibility(View.VISIBLE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void genre_list_by_id() {
        try {

            String userid = SPreferenceWriter.getInstance(getActivity()).getString(SPreferenceKey.USERID);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<GenerResponce> call = apiInterface.genre_list_by_id(userid);

            call.enqueue(new retrofit2.Callback<GenerResponce>() {
                @Override
                public void onResponse(Call<GenerResponce> call, Response<GenerResponce> response) {

                    if (response.isSuccessful()) {
                        GenerResponce GenerResponce = response.body();
                        Log.e("response", "" + GenerResponce.toString());

                        if (GenerResponce.getStatus().equalsIgnoreCase("SUCCESS")) {
                            genreList = GenerResponce.getData();
//                            setSpinner();

                            binding.recViewCateg.setVisibility(View.GONE);
                            /////////*******************************

                            setUpCategoriesRecycler(binding.edtTxtSearchPost,
                                    binding.recViewCateg,
                                    (ArrayList<com.escalate.model.GenerResponce.DataBean>) genreList);


                        } else {

                            // Toast.makeText(this, "" + GenerResponce.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    MyDialog.getInstance(getActivity()).hideDialog();
                }

                @Override
                public void onFailure(Call<GenerResponce> call, Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(getActivity()).hideDialog();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPause() {
        super.onPause();

        resetAllPost();
    }

    @Override
    public void onResume() {
        super.onResume();

        binding.edtDesc.setHint("Description(120 characters)");
        binding.edtDesc.setText("");
//        binding.recViewCateg.setVisibility(View.GONE);
//        edtTxtSearch.setVisibility(View.VISIBLE);
        binding.edtTxtSearchPost.setHint("Search Categories");

        if (Util.showInternetAlert(getActivity())) {
            genre_list_by_id();
        }

    }

//    public void postStuff(Context context)
//    {
//        try {
//            if(categorySearchAdapter == null){
//                if (Util.showInternetAlert(context)) {
//                    genre_list_by_id();
//                }
//            }
//            else {
//
//            }
//
//        }catch (Exception e)
//        {e.printStackTrace();}
//
//    }

    @Override
    public void onStop() {
        super.onStop();

        resetAllPost();

        genreList.clear();
        binding.recViewCateg.setVisibility(View.GONE);
        binding.edtTxtSearchPost.setVisibility(View.VISIBLE);
        binding.edtTxtSearchPost.setHint("Search Categories");
        binding.edtTxtSearchPost.setText("");
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

            if (mediaRecorder != null) {
                mediaRecorder.stop();
                mediaRecorder.reset();
                mediaRecorder.release();
                mediaRecorder = null;
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
