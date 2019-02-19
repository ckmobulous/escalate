package com.escalate.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.escalate.R;
import com.escalate.databinding.ActivityForgotPasswordBinding;
import com.escalate.fragments.CustomBottomSheetDialogFragment;
import com.escalate.fragments.EmailCustomBottomSheetDialog;
import com.escalate.model.EmailResponce;
import com.escalate.model.OtpVerifyResponce;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.escalate.utils.Util;
import com.escalate.utils.Validator;
import com.hbb20.CountryCodePicker;

import retrofit2.Call;
import retrofit2.Response;


public class ForgotPasswordActivity extends AppCompatActivity implements CustomBottomSheetDialogFragment.CallbackSignup, EmailCustomBottomSheetDialog.CallbackEmail, View.OnClickListener {
    private ActivityForgotPasswordBinding binding;
    private boolean flagCountryCode = false;
    private String countryCodeAndroid = "";
    public boolean flagVisibility = false;

    CustomBottomSheetDialogFragment customBottomSheetDialogFragment;
    EmailCustomBottomSheetDialog emailCustomBottomSheetDialog;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);

        binding.btnSendFgt.setOnClickListener(this);
        binding.imgBackFgt.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        tabLayout();

        binding.ccpFgt.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {

                countryCodeAndroid = binding.ccpFgt.getSelectedCountryCodeWithPlus();
                flagCountryCode = true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSendFgt:
                if (validate()) {

                    if (flagVisibility) {
                        if (Util.showInternetAlert(this))
                            verifyPhoneNo();
                    } else {
                        if (Util.showInternetAlert(this))
                            emailForgotService();
                    }
                }
                break;
            case R.id.imgBackFgt:
                finish();
                break;

        }
    }

    private void tabLayout() {

        binding.tabsFgt.addTab(binding.tabsFgt.newTab().setText(this.getString(R.string.email_)));
//        tabs.addTab(tabs.newTab().setText(this.getString(R.string.phone_no)));

        binding.tabsFgt.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (binding.tabsFgt.getSelectedTabPosition() == 0) {

                    edittextVisibility();
                } else if (binding.tabsFgt.getSelectedTabPosition() == 1) {
//                    edittextVisibility();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void edittextVisibility() {

        if (binding.edtEmailFgt.getVisibility() == View.VISIBLE) {

            binding.edtEmailFgt.setVisibility(View.GONE);
            binding.linearEdtPhoneno.setVisibility(View.VISIBLE);
            flagVisibility = true;
        } else {
            binding.edtEmailFgt.setVisibility(View.VISIBLE);
            binding.linearEdtPhoneno.setVisibility(View.GONE);
            flagVisibility = false;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void showOtpBottomSheet() {

        customBottomSheetDialogFragment = CustomBottomSheetDialogFragment.newInstance("", binding.edtPhoneno.getText().toString(), "", "", countryCodeAndroid);

//        customBottomSheetDialogFragment = CustomBottomSheetDialogFragment.newInstance(countryCodeAndroid+""+edtPhoneno.getText().toString(),"");
        customBottomSheetDialogFragment.callBackListener(this);
        customBottomSheetDialogFragment.show(getSupportFragmentManager(), "Dialog");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void showEmailBottomSheet() {

        emailCustomBottomSheetDialog = EmailCustomBottomSheetDialog.newInstance(binding.edtEmailFgt.getText().toString());
        emailCustomBottomSheetDialog.callBackListener(this);
        emailCustomBottomSheetDialog.setCancelable(false);
        emailCustomBottomSheetDialog.show(getSupportFragmentManager(), "Dialog");
    }

    private boolean validate() {
        boolean isValid = true;

        String email = Util.getProperText(binding.edtEmailFgt);
        String phoneNumber = Util.getProperText(binding.edtPhoneno);
        EditText editText = null;

        if (flagVisibility) {
            if (phoneNumber.isEmpty()) {
                // edtPhoneno.setError(getString(R.string.error_empty_no));
                Toast.makeText(this, getString(R.string.error_empty_no), Toast.LENGTH_SHORT).show();
                isValid = false;
                editText = binding.edtPhoneno;
                editText.requestFocus();
            } else if (phoneNumber.length() < 6 || phoneNumber.length() > 15) {
                //edtPhoneno.setError(getString(R.string.error_valid_no));
                Toast.makeText(this, getString(R.string.error_valid_no), Toast.LENGTH_SHORT).show();
                isValid = false;
                editText = binding.edtPhoneno;
                editText.requestFocus();
            } else if (!flagCountryCode) {
                Toast.makeText(this, "select country code", Toast.LENGTH_SHORT).show();
                isValid = false;
            }
        } else {
            if (email.isEmpty()) {
                //edtEmail.setError(getString(R.string.error_empty_email));
                Toast.makeText(this, getString(R.string.error_empty_email), Toast.LENGTH_SHORT).show();
                isValid = false;
                editText = binding.edtEmailFgt;
                editText.requestFocus();
            } else if (!Validator.isValidEmail(email)) {
                //edtEmail.setError(getString(R.string.error_valid_email));
                Toast.makeText(this, getString(R.string.error_valid_email), Toast.LENGTH_SHORT).show();
                isValid = false;
                editText = binding.edtEmailFgt;
                editText.requestFocus();
            }
        }

        return isValid;
    }


    @Override
    public void sendDetails() {

        startActivity(new Intent(this, ResetPasswardActivity.class));
        finish();
    }

    @Override
    public void sendEmailOtpDetails(String otp) {

        verifyEmailOtpService(otp);
    }

    private void emailForgotService() {
        try {

            if (true) {
                MyDialog.getInstance(this).showDialog(this);

                ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
                Call<EmailResponce> call = apiInterface.forgot_password(binding.edtEmailFgt.getText().toString());
                call.enqueue(new retrofit2.Callback<EmailResponce>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(Call<EmailResponce> call, Response<EmailResponce> response) {
                        MyDialog.getInstance(getApplicationContext()).hideDialog();
                        if (response.isSuccessful()) {

                            EmailResponce signUpResponce = response.body();
                            Log.e("emailForgotService", "onResponse:" + signUpResponce.toString());
                            if (signUpResponce.getStatus().equalsIgnoreCase("SUCCESS")) {
                                Toast.makeText(getApplicationContext(), "" + signUpResponce.getMessage(), Toast.LENGTH_SHORT).show();
                                showEmailBottomSheet();
                            } else {
                                Toast.makeText(getApplicationContext(), "" + signUpResponce.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Server Error!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<EmailResponce> call, Throwable t) {
                        t.printStackTrace();
                        MyDialog.getInstance(getApplicationContext()).hideDialog();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void verifyEmailOtpService(String otp) {
        try {

            if (true) {
                MyDialog.getInstance(this).showDialog(this);

                ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
                Call<OtpVerifyResponce> call = apiInterface.verify_email_otp(binding.edtEmailFgt.getText().toString(), otp);
                call.enqueue(new retrofit2.Callback<OtpVerifyResponce>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(Call<OtpVerifyResponce> call, Response<OtpVerifyResponce> response) {
                        MyDialog.getInstance(getApplicationContext()).hideDialog();
                        if (response.isSuccessful()) {

                            OtpVerifyResponce otpVerifyResponce = response.body();
                            Log.e("verifyEmailOtpService", "onResponse:" + otpVerifyResponce.toString());
                            if (otpVerifyResponce.getStatus().equalsIgnoreCase("SUCCESS")) {

                                SPreferenceWriter.getInstance(getApplicationContext()).writeStringValue(SPreferenceKey.USERID, otpVerifyResponce.getData().getUser_id());
                                Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswardActivity.class);
                                //intent.putExtra("user_id", otpVerifyResponce.getData().getUser_id());
                                startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(), "" + otpVerifyResponce.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Server Error!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OtpVerifyResponce> call, Throwable t) {
                        t.printStackTrace();
                        MyDialog.getInstance(getApplicationContext()).hideDialog();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void verifyPhoneNo() {
        try {

            if (true) {
                MyDialog.getInstance(this).showDialog(this);

                ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
                Call<OtpVerifyResponce> call = apiInterface.verify_email_otp(binding.edtPhoneno.getText().toString());
                call.enqueue(new retrofit2.Callback<OtpVerifyResponce>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(Call<OtpVerifyResponce> call, Response<OtpVerifyResponce> response) {
                        MyDialog.getInstance(getApplicationContext()).hideDialog();
                        if (response.isSuccessful()) {

                            OtpVerifyResponce otpVerifyResponce = response.body();
                            Log.e("verifyEmailOtpService", "onResponse:" + otpVerifyResponce.toString());
                            if (otpVerifyResponce.getStatus().equalsIgnoreCase("SUCCESS")) {

                                SPreferenceWriter.getInstance(getApplicationContext()).writeStringValue(SPreferenceKey.USERID, otpVerifyResponce.getData().getUser_id());
                                showOtpBottomSheet();
                                /*Intent intent = new Intent (ForgotPasswordActivity.this, ResetPasswardActivity.class);
                                startActivity(intent);*/

                            } else {
                                Toast.makeText(getApplicationContext(), "" + otpVerifyResponce.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Server Error!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OtpVerifyResponce> call, Throwable t) {
                        t.printStackTrace();
                        MyDialog.getInstance(getApplicationContext()).hideDialog();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /*private void verificationService(){

        if(validate()){
            *//*Intent intent = new Intent(ForgotPasswordActivity.this , OtpActivity.class);
            intent.putExtra("number" , countryCodeAndroid+"  "+edtPhoneNumber.getText().toString());
            startActivity(intent);
            flafForgotpassword = true;*//*
            //attachmentService();
        }
    }*/

    /* private boolean validate() {
        boolean isValid = true;
        String phoneNumber = Util.getProperText(edtPhoneNumber);
        EditText editText = null;

        if (phoneNumber.isEmpty()) {
            edtPhoneNumber.setError(getString(R.string.error_empty_no));
            isValid = false;
            editText = edtPhoneNumber;
        }else if (phoneNumber.length() < 3) {
            edtPhoneNumber.setError(getString(R.string.error_valid_no));
            isValid = false;
            editText = edtPhoneNumber;
        }
        *//*if (editText != null)
            editText.requestFocus();*//*
        return isValid;
    }*/
}
