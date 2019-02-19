package com.escalate.fragments;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.escalate.R;
import com.escalate.model.SampleResponce;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class CustomBottomSheetDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private EditText edtOtp;
    private ImageView imgCancelDialog;
    private TextView txtResendOtp;
    CallbackSignup callbackSignup;
    FirebaseAuth auth;
    String getusername = "", getemail = "", getphone = "", getotp = "", getcountry_code = "";
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    int mins, secs;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private String verificationCode;

    private CountDownTimer cd_timer;

    public static CustomBottomSheetDialogFragment newInstance(String userName, String email, String phoneNumber, String getOtp, String countryCode) {

        CustomBottomSheetDialogFragment myFragment = new CustomBottomSheetDialogFragment();


        Bundle args = new Bundle();
        args.putString("username", userName);
        args.putString("email", email);
        args.putString("phone", phoneNumber);
        args.putString("otp", getOtp);
        args.putString("country_code", countryCode);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.content_dialog_bottom_sheet, container, false);

        edtOtp = v.findViewById(R.id.edt_otp);
        imgCancelDialog = v.findViewById(R.id.img_cancel_dialog);
        txtResendOtp = v.findViewById(R.id.txtResendOtp);
        imgCancelDialog.setOnClickListener(this);
        txtResendOtp.setOnClickListener(this);
        addTextChangeListner();

        if (getArguments() != null) {
            getusername = getArguments().getString("username");
            getemail = getArguments().getString("email");
            getphone = getArguments().getString("phone");
            getotp = getArguments().getString("otp");
            getcountry_code = getArguments().getString("country_code");

            Log.e("onCreateView", ": " + getemail);
        }
        StartFirebaseLogin();
//        GenerateOtp();

       /* SmsReceiver.bindListener(new SmsReceiver.SmsListener() {
            @Override
            public void messageReceived(String messageText) {

                otpStr = Util.getOtp(messageText);
                if(!otpStr.isEmpty()){
                    updateUI(otpStr);
                }
//                Toast.makeText(getContext(),"Message: "+otp,Toast.LENGTH_LONG).show();
            }
        });*/


//        startTimer();

        startCDTimer();


        return v;
    }

    public static String convertSecondsToHMmSs(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
//        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);


        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }


    private void startCDTimer() {
        cd_timer = new CountDownTimer(150000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String t = convertSecondsToHMmSs(millisUntilFinished);
                txtResendOtp.setText(t);
                txtResendOtp.setEnabled(false);
            }

            @Override
            public void onFinish() {
                txtResendOtp.setText("Resend OTP");
                txtResendOtp.setEnabled(true);
            }
        };
        cd_timer.start();

    }

    public void updateUI(final String str) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                edtOtp.setText(str);
            }
        });
    }

   /* private void GenerateOtp(){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,                     // Phone number to verify
                60,                           // Timeout duration
                TimeUnit.SECONDS,                // Unit of timeout
                getActivity(),        // Activity (for callback binding)
                mCallback);
    }*/

    private void StartFirebaseLogin() {
        try {
            auth = FirebaseAuth.getInstance();git checkout
            mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    //Toast.makeText(getContext(),"verification completed",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
//                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    verificationCode = s;
//                    Toast.makeText(getContext(),"Code sent",Toast.LENGTH_SHORT).show();
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*private void SigninWithPhone(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dismiss();
                            callbackSignup.sendDetails();
                        } else {
                            Toast.makeText(getContext(), "Incorrect OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void addTextChangeListner() {

        edtOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (edtOtp.getText().toString().length() == 6) {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    edtOtp.setCursorVisible(false);
                    edtOtp.requestFocus();
                    edtOtp.setCursorVisible(true);

                    if (edtOtp.getText().toString().equals(getotp)) {
                        dismiss();
                        callbackSignup.sendDetails();
                    } else {
//                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, edtOtp.getText().toString());
//                    SigninWithPhone(credential);
                        Toast.makeText(getActivity(), "Please Enter Correct OTP", Toast.LENGTH_SHORT).show();

                    }
                } else {

                }

              /*  if (otpStr.equals(edtOtp.getText().toString())){
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    edtOtp.setCursorVisible(false);
                    edtOtp.requestFocus();
                    edtOtp.setCursorVisible(true);
                    dismiss();
                    callbackSignup.sendDetails();
                }
                else {
//                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, edtOtp.getText().toString());
//                    SigninWithPhone(credential);
                    Toast.makeText(getActivity(), "Please Enter Correct OTP", Toast.LENGTH_SHORT).show();
                }*/

                   /* if (edtOtp.getText().toString().length() == 6){
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                        edtOtp.setCursorVisible(false);
                        edtOtp.requestFocus();
                        edtOtp.setCursorVisible(true);

                        if (edtOtp.getText().toString().equals(otp*//*"123456"*//*)){
                            dismiss();
                            callbackSignup.sendDetails();
                        }else {
                            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, edtOtp.getText().toString());
                            SigninWithPhone(credential);
                        }
                    } else {

                    } */
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_cancel_dialog:

                if (cd_timer != null)
                    cd_timer.cancel();

                dismiss();

                break;
            case R.id.txtResendOtp:
//                StartFirebaseLogin();
//                GenerateOtp();
                checkAvalibilityAService();

                break;
        }
    }

    public void callBackListener(CallbackSignup callbackSignup) {

        this.callbackSignup = callbackSignup;
    }

    public interface CallbackSignup {

        void sendDetails();
    }

    //ALL API
    private void checkAvalibilityAService() {
        try {

//            String token = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.DEVICETOKEN);

            if (true) {
                MyDialog.getInstance(getContext()).showDialog(getActivity());

                ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
                Map<String, RequestBody> map = setUpMapDataCheckAvailable();

                Call<SampleResponce> call = apiInterface.check_avalibility(map);
                call.enqueue(new retrofit2.Callback<SampleResponce>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(@NonNull Call<SampleResponce> call, @NonNull Response<SampleResponce> response) {
                        MyDialog.getInstance(getContext()).hideDialog();
                        if (response.isSuccessful()) {
                            //signupFirebase();
                            SampleResponce signUpResponce = response.body();
                            Log.e("SignUpResponce", "onResponse: " + signUpResponce.toString());
                            if (signUpResponce.getStatus().equalsIgnoreCase("SUCCESS")) {
                                getotp = signUpResponce.getData().getOtp();

                            } else {
                                Toast.makeText(getContext(), "" + signUpResponce.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getContext(), "Server Error!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SampleResponce> call, Throwable t) {
                        t.printStackTrace();
                        MyDialog.getInstance(getContext()).hideDialog();
                        String s = "";
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, RequestBody> setUpMapDataCheckAvailable() {

        Map<String, RequestBody> fields = new HashMap<>();

        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), getusername);
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), getemail);
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), getphone);
        RequestBody country_code = RequestBody.create(MediaType.parse("text/plain"), getcountry_code);

        fields.put("username", username);
        fields.put("email", email);
        fields.put("phone", phone);
        fields.put("country_code", country_code);

        return fields;
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

            String s = "0" + "" + mins + ":"
                    + String.format(Locale.getDefault(), "%02d", secs);

            txtResendOtp.setText(s);

            if (updatedTime >= 100000) {
                txtResendOtp.setVisibility(View.VISIBLE);
                customHandler.removeCallbacks(updateTimerThread);

                updatedTime = 0L;
                timeSwapBuff = 0L;
                timeInMilliseconds = 0L;
                txtResendOtp.setText("Resend OTP");
                txtResendOtp.setEnabled(true);

            } else {
                //update the time
                customHandler.postDelayed(this, 0);

            }
        }
    };

}