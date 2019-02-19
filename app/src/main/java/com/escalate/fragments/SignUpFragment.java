package com.escalate.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.escalate.R;
import com.escalate.activity.ProceedActivity;
import com.escalate.activity.SettingActivity;
import com.escalate.activity.TermsConditionAct;
import com.escalate.databinding.FragmentSignUpBinding;
import com.escalate.model.SampleResponce;
import com.escalate.model.SignUpResponce;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.escalate.utils.Util;
import com.escalate.utils.Validator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.hbb20.CountryCodePicker;

import org.bluecabin.textoo.LinksHandler;
import org.bluecabin.textoo.Textoo;

import java.util.HashMap;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class SignUpFragment extends Fragment implements CustomBottomSheetDialogFragment.CallbackSignup, View.OnClickListener {
    private FragmentSignUpBinding binding;

    private String countryCodeAndroid = "+1";
    private boolean flagCountryCode = true;
    String getOtp = "",emailActive="";
    private static boolean b = false;
    private static boolean check = false;

    private boolean flagPassword = false, flagCnfPassword = false, flagsignup = false, isPassVisible = false;
    CustomBottomSheetDialogFragment customBottomSheetDialogFragment;

    public SignUpFragment() {
        // Required empty public constructor
    }

    private FirebaseAuth mAuth;
    DatabaseReference database;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);

        binding.btnSinUp.setOnClickListener(this);
        binding.imgEyePasWd.setOnClickListener(this);
        binding.imgCnfEvePasWd.setOnClickListener(this);
        binding.imgboxSup.setOnClickListener(this);
        binding.imgVnotificatin.setOnClickListener(this);

        //ccp.setDefaultCountryUsingNameCode("UK");

        binding.ccpSinUp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {

                countryCodeAndroid = binding.ccpSinUp.getSelectedCountryCodeWithPlus();
                SPreferenceWriter.getInstance(getContext()).writeStringValue(SPreferenceKey.COUNTRYCODE, countryCodeAndroid);
                flagCountryCode = true;
            }
        });

        TextView termsAndPrivacy = Textoo
                .config((TextView) binding.getRoot().findViewById(R.id.tvRemember))
                .addLinksHandler(new LinksHandler() {
                    @Override
                    public boolean onClick(View view, String url) {
                        if ("terms:".equals(url)) {
                            // Handle terms click
                            Intent intentTc = new Intent(getActivity(), TermsConditionAct.class);
                            intentTc.putExtra("check", "terms_condition");
                            startActivity(intentTc);
                            return true;  // event handled
                        } else if ("privacy:".equals(url)) {
                            // Handle privacy click
                            Intent intentTPp = new Intent(getActivity(), TermsConditionAct.class);
                            intentTPp.putExtra("check", "privecy_policy");
                            startActivity(intentTPp);

                            return true;  // event handled
                        } else {
                            return false;  // event not handled.  continue default processing i.e. launch web browser and display the link
                        }
                    }
                })
                .apply();

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSinUp:
                if (validate()) {

                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    if (Util.showInternetAlert(getContext())) {

                        if (binding.edtPaswrdSinUp.getText().toString().equals(binding.edtCnfPaswrd.getText().toString())) {
                           if (check==false)
                               emailActive ="0";
                           else
                               emailActive = "1";
                            checkAvalibilityAService();
                        } else {
                            Toast.makeText(getContext(), "Password and confirm password does not match!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case R.id.imgEyePasWd:
                doPasswordEyeThing();
                break;
            case R.id.imgCnfEvePasWd:
                doCnfPasswordEyeThing();
                break;
            case R.id.imgboxSup:
                checkVisibility();
                break;
            case R.id.imgVnotificatin:
                if (check) {
                    binding.imgVnotificatin.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.login_tick_un));
                    check = false;
                } else {
                    binding.imgVnotificatin.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.login_tick_slected));
                    check = true;
                }
                break;
        }

    }

    private void checkVisibility() {

        if (b) {
            binding.imgboxSup.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.login_tick_un));
            b = false;
        } else {
            binding.imgboxSup.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.login_tick_slected));
            b = true;
        }

    }

    @Override
    public void sendDetails() {

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        if (Util.showInternetAlert(getContext())) {
//            fireBaseAccount(edtUsername.getText().toString(),edtEmail.getText().toString(),"123456"edtPassword.getText().toString());
//            startActivity(new Intent(getContext(), ProceedActivity.class));
            normalSignUpService();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void showOtpBottomSheet(String getOtp) {

        customBottomSheetDialogFragment = CustomBottomSheetDialogFragment.newInstance(binding.edtUsrNamSinUp.getText().toString(), binding.edtEmailSinUp.getText().toString(), binding.phnEtdTxt.getText().toString(), getOtp, countryCodeAndroid);
        customBottomSheetDialogFragment.callBackListener(this);
        customBottomSheetDialogFragment.setCancelable(false);
        customBottomSheetDialogFragment.show(getFragmentManager(), "Dialog");
    }


    //ALL METHODS
    private void doPasswordEyeThing() {
        if (isPassVisible) {
            isPassVisible = false;
            binding.edtPaswrdSinUp.setTransformationMethod(new PasswordTransformationMethod());
            binding.edtPaswrdSinUp.setSelection(binding.edtPaswrdSinUp.getText().length());
            binding.imgEyePasWd.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.login_unselected_eye));
        } else {
            isPassVisible = true;
            binding.edtPaswrdSinUp.setTransformationMethod(null);
            binding.edtPaswrdSinUp.setSelection(binding.edtPaswrdSinUp.getText().length());
            binding.imgEyePasWd.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.login_selected_eye));
        }
    }

    private void doCnfPasswordEyeThing() {
        if (isPassVisible) {
            isPassVisible = false;
            binding.edtCnfPaswrd.setTransformationMethod(new PasswordTransformationMethod());
            binding.edtCnfPaswrd.setSelection(binding.edtCnfPaswrd.getText().length());
            binding.imgCnfEvePasWd.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.login_unselected_eye));
        } else {
            isPassVisible = true;
            binding.edtCnfPaswrd.setTransformationMethod(null);
            binding.edtCnfPaswrd.setSelection(binding.edtCnfPaswrd.getText().length());
            binding.imgCnfEvePasWd.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.login_selected_eye));
        }
    }

    private boolean validate() {
        boolean isValid = true;
        String username = Util.getProperText(binding.edtUsrNamSinUp);
        String name = Util.getProperText(binding.edtFullName);
        String email = Util.getProperText(binding.edtEmailSinUp);
//        String phoneNumber = Util.getProperText(edtPhoneNo);
        String password = Util.getProperText(binding.edtPaswrdSinUp);
        String cnfPassword = Util.getProperText(binding.edtCnfPaswrd);

        EditText editText = null;
        if (name.isEmpty()) {
            //edtFullName.setError("");
            Toast.makeText(getContext(), getContext().getString(R.string.error_empty_name), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = binding.edtFullName;
            editText.requestFocus();
        } else if (username.isEmpty()) {
            //edtUsername.setError("");
            Toast.makeText(getContext(), getContext().getString(R.string.error_empty_username), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = binding.edtUsrNamSinUp;
            editText.requestFocus();
        } else if (!Validator.isAlphaNumeric(username)) {
            //edtUsername.setError("");
            Toast.makeText(getContext(), getContext().getString(R.string.error_valid_username), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = binding.edtUsrNamSinUp;
            editText.requestFocus();
        } else if (email.isEmpty()) {
            //edtEmail.setError("");
            Toast.makeText(getContext(), getContext().getString(R.string.error_empty_email), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = binding.edtEmailSinUp;
            editText.requestFocus();
        } else if (!Validator.isValidEmail(email)) {
            //edtEmail.setError("");
            Toast.makeText(getContext(), getContext().getString(R.string.error_valid_email), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = binding.edtEmailSinUp;
            editText.requestFocus();
        }/*else if (phoneNumber.isEmpty()) {
            //edtPhoneNo.setError("");
            Toast.makeText(getContext(), getContext().getString(R.string.error_empty_no), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = edtPhoneNo;
            editText.requestFocus();
        }*//*else if (phoneNumber.length() < 6 || phoneNumber.length() > 15) {
            // edtPhoneNo.setError("");
            Toast.makeText(getContext(), getContext().getString(R.string.error_valid_no), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = edtPhoneNo;
            editText.requestFocus();
        }*/ else if (password.isEmpty()) {
            // edtPassword.setError("");
            Toast.makeText(getContext(), getContext().getString(R.string.error_empty_password), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = binding.edtPaswrdSinUp;
            editText.requestFocus();
        } else if (!Validator.validatePassword(password)) {
            //edtPassword.setError("");
            Toast.makeText(getContext(), getContext().getString(R.string.error_valid_password), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = binding.edtPaswrdSinUp;
            editText.requestFocus();
        } else if (cnfPassword.isEmpty()) {
            // edtCnfPass.setError("");
            Toast.makeText(getContext(), getContext().getString(R.string.error_empty_password), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = binding.edtCnfPaswrd;
            editText.requestFocus();
        } else if (!Validator.validatePassword(cnfPassword)) {
            // edtCnfPass.setError("");
            Toast.makeText(getContext(), getContext().getString(R.string.error_valid_password), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = binding.edtCnfPaswrd;
            editText.requestFocus();
        } else if (!flagCountryCode) {
            Toast.makeText(getContext(), "Select country code", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        else if (b==false){
            Toast.makeText(getActivity(), "Select Terms of Service", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }


    private Map<String, RequestBody> setUpMapData(String the_token) {

        Map<String, RequestBody> fields = new HashMap<>();

        RequestBody fullname = RequestBody.create(MediaType.parse("text/plain"), binding.edtFullName.getText().toString());
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), binding.phnEtdTxt.getText().toString());
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), binding.edtUsrNamSinUp.getText().toString());
        RequestBody deviceType = RequestBody.create(MediaType.parse("text/plain"), "android");
        RequestBody deviceToken = RequestBody.create(MediaType.parse("text/plain"), SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.DEVICETOKEN));
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), binding.edtPaswrdSinUp.getText().toString());
        RequestBody password_confirmation = RequestBody.create(MediaType.parse("text/plain"), binding.edtCnfPaswrd.getText().toString());
        RequestBody socialid = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), binding.edtEmailSinUp.getText().toString());
        RequestBody country_code = RequestBody.create(MediaType.parse("text/plain"), countryCodeAndroid);
        RequestBody firebase_id = RequestBody.create(MediaType.parse("text/plain"), "12staticId");
        RequestBody emailActivation = RequestBody.create(MediaType.parse("text/plain"),emailActive );

        fields.put("email", email);
        fields.put("username", username);
        fields.put("fullname", fullname);
        fields.put("phone", phone);
        fields.put("deviceType", deviceType);
        fields.put("deviceToken", deviceToken);
        fields.put("password", password);
        fields.put("password_confirmation", password_confirmation);
        fields.put("socialid", socialid);
        fields.put("country_code", country_code);
        fields.put("firebase_id", firebase_id);
        fields.put("email_activation", emailActivation);

        return fields;
    }

    private Map<String, RequestBody> setUpMapDataCheckAvailable() {

        Map<String, RequestBody> fields = new HashMap<>();

        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), binding.edtEmailSinUp.getText().toString());
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), binding.phnEtdTxt.getText().toString());
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), binding.edtUsrNamSinUp.getText().toString());
        RequestBody country_code = RequestBody.create(MediaType.parse("text/plain"), countryCodeAndroid);

        fields.put("username", username);
        fields.put("email", email);
        fields.put("phone", phone);
        fields.put("country_code", country_code);

        return fields;
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
                                getOtp = signUpResponce.getData().getOtp();
                                showOtpBottomSheet(getOtp);
                            } else {
                                Toast.makeText(getContext(), "" + signUpResponce.getMessage(), Toast.LENGTH_SHORT).show();
                                flagsignup = true;
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


    private void normalSignUpService() {
        try {
            String token = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.DEVICETOKEN);

            MyDialog.getInstance(getContext()).showDialog(getActivity());

            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Map<String, RequestBody> map = setUpMapData(token);

            Call<SignUpResponce> call = apiInterface.normalSignUp(map);
            call.enqueue(new retrofit2.Callback<SignUpResponce>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResponse(@NonNull Call<SignUpResponce> call, @NonNull Response<SignUpResponce> response) {
                    MyDialog.getInstance(getContext()).hideDialog();
                    if (response.isSuccessful()) {
                        //signupFirebase();
                        SignUpResponce signUpResponce = response.body();
                        if (signUpResponce != null) {
                            Log.e("SignUp Response", "onResponse: " + signUpResponce.toString());

                            if (signUpResponce.getStatus().equalsIgnoreCase("SUCCESS")) {
                                SPreferenceWriter.getInstance(getContext()).writeStringValue(SPreferenceKey.LOGINVIA, "signup");
                                SPreferenceWriter.getInstance(getContext()).writeBooleanValue(SPreferenceKey.LOGINKEY, true);
//                                SPreferenceWriter.getInstance(getContext()).writeStringValue(SPreferenceKey.LOGINUSERNAME, edtUsername.getText().toString());
//                                SPreferenceWriter.getInstance(getContext()).writeStringValue(SPreferenceKey.LOGINPASS, edtPassword.getText().toString());
                                SPreferenceWriter.getInstance(getContext())
                                        .writeStringValue(SPreferenceKey.USER_TYPE, "NORMAL_TYPE");
                                SPreferenceWriter.getInstance(getContext()).setSignUpDetails(signUpResponce);
                                // showOtpBottomSheet();

                                String notificationFlag = signUpResponce.getData().getNotification_flag();

                                if (notificationFlag.equals("1")) {
                                    SPreferenceWriter.getInstance(getActivity()).writeStringValue(SPreferenceKey.NOTIFICATION, "ON");

                                } else {
                                    SPreferenceWriter.getInstance(getActivity()).writeStringValue(SPreferenceKey.NOTIFICATION, "OFF");
                                }

                                /////main////////

                                interestPopUp();

                            } else {
                                Toast.makeText(getContext(), "" + signUpResponce.getMessage(), Toast.LENGTH_SHORT).show();
                                flagsignup = true;
                            }
                        }


                    } else {
                        Toast.makeText(getContext(), "Server Error!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SignUpResponce> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(getContext()).hideDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void interestPopUp() {

        Dialog dialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialogue_interest);

        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ProceedActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();

            }
        });

        dialog.show();

    }


}
