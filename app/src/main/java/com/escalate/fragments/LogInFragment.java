package com.escalate.fragments;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.escalate.R;
import com.escalate.activity.ForgotPasswordActivity;
import com.escalate.activity.HomeActivity;
import com.escalate.activity.ProceedActivity;
import com.escalate.activity.TermsConditionAct;
import com.escalate.databinding.FragmentLogInBinding;
import com.escalate.model.LoginResponse;
import com.escalate.model.SignUpResponce;
import com.escalate.model.SocialLoginResponce;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.escalate.utils.FacebookLogin;
import com.escalate.utils.GoogleLogin;
import com.escalate.utils.Util;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import org.bluecabin.textoo.LinksHandler;
import org.bluecabin.textoo.Textoo;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class LogInFragment extends Fragment implements View.OnClickListener {
    private FragmentLogInBinding binding;
    private long requestId;
    private boolean isPassVisible = false;
    private static boolean b = false;
    private static boolean termsChk = false;
    private static boolean check = false;
    private String fb_id, name = "", f_name, l_name, email = "", image = null, gender, type, socialId = "", flagStrLoginCheck;
    private DownloadManager.Request request;
    private String fileName = "";
    private String SocailImagePath = "";
    BroadcastReceiver onDownloadComplete;
    private final int GOOGLE_REQ_CODE = 122;
    Dialog dialog;

    public LogInFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_log_in, container, false);

//        checkVisibility();
        binding.btnLogin.setOnClickListener(this);
        binding.imgEye.setOnClickListener(this);
        binding.txtForgotPass.setOnClickListener(this);
        binding.gmailLogin.setOnClickListener(this);
        binding.btnLoginFb.setOnClickListener(this);
        binding.tvRemember.setOnClickListener(this);
        binding.imagebox.setOnClickListener(this);

        if (SPreferenceWriter.getInstance(getContext()).getBoolean(SPreferenceKey.REMEMBERPASS)) {
            binding.edtUsrNamLogin.setText(SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.LOGINUSERNAME));
            binding.edtPaswrdLogin.setText(SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.LOGINPASS));
            binding.imagebox.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.login_tick_slected));
            b = true;
        } else {
            binding.imagebox.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.login_tick_un));
            b = false;
        }

        // BroadcastReceiver TO RECEIVE A COMPLETION CALLBACK
        onDownloadComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                // Get the referenceId from the download manager
                if (requestId == referenceId) {
//                    if (Util.showInternetAlert(getContext())) {
//                        fbSignUpService();
//                    }
                }
            }
        };

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                if (validate()) {
                    if (Util.showInternetAlert(getContext())) {
                        loginService();
                    }
                }
                break;
            case R.id.imgEye:
                doPasswordEyeThing();
                break;
            case R.id.txtForgotPass:
                startActivity(new Intent(getContext(), ForgotPasswordActivity.class));
                break;
            case R.id.gmailLogin:
                //Gmail starts
                if (Util.showInternetAlert(getContext())) {
                    flagStrLoginCheck = "gmail";
                    startActivityForResult(new Intent(getContext(), GoogleLogin.class), GOOGLE_REQ_CODE);
                } else {
                    Toast.makeText(getActivity(), "Please check Internet connection.", Toast.LENGTH_SHORT).show();
                }
                //showTermsConditionDialog();
                break;
            case R.id.btnLoginFb:
                //FB starts
                if (Util.showInternetAlert(getContext())) {
                    flagStrLoginCheck = "facebook";
                    startActivityForResult(new Intent(getContext(), FacebookLogin.class), 121);
                } else {
                    Toast.makeText(getActivity(), "Please check Internet connection.", Toast.LENGTH_SHORT).show();
                }

////       socialId = SPreferenceWriter.getInstance(getApplicationContext()).getString(SPreferenceKey.FBID);
//       flagStrLoginCheck = "facebook";
//       if(socialId.isEmpty()){
//           startActivityForResult(new Intent(getContext(), FacebookLogin.class), 121);
//       }
//       else{
//           if(Util.showInternetAlert(getContext())){
//               loginSocial();
//           }
//       }
                break;
            case R.id.tvRemember:
            case R.id.imagebox:
                checkVisibility();
                break;


        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public void onStop() {
        super.onStop();
        Objects.requireNonNull(getActivity()).unregisterReceiver(onDownloadComplete);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void doPasswordEyeThing() {
        if (isPassVisible) {
            isPassVisible = false;
            binding.edtPaswrdLogin.setTransformationMethod(new PasswordTransformationMethod());
            binding.edtPaswrdLogin.setSelection(binding.edtPaswrdLogin.getText().length());
            binding.imgEye.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.login_unselected_eye));
        } else {
            isPassVisible = true;
            binding.edtPaswrdLogin.setTransformationMethod(null);
            binding.edtPaswrdLogin.setSelection(binding.edtPaswrdLogin.getText().length());
            binding.imgEye.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.login_selected_eye));
        }
    }

    private void checkVisibility() {

        if (b) {
            binding.imagebox.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.login_tick_un));
            b = false;
        } else {
            binding.imagebox.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.login_tick_slected));
            b = true;
        }

    }

    private boolean validate() {
        boolean isValid = true;
        String username = Util.getProperText(binding.edtUsrNamLogin);
        String password = Util.getProperText(binding.edtPaswrdLogin);
        EditText editText = null;
        if (username.isEmpty()) {
            //edtUsername.setError(getString(R.string.error_empty_username));
            Toast.makeText(getContext(), getContext().getString(R.string.error_empty_username), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = binding.edtUsrNamLogin;
            editText.requestFocus();
        } else if (password.isEmpty()) {
            //edtPassword.setError(getString(R.string.error_empty_password));
            Toast.makeText(getContext(), getContext().getString(R.string.error_empty_password), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = binding.edtPaswrdLogin;
            editText.requestFocus();
        }

        return isValid;
    }

    private void goToChatUsersActivity() {

//        updateFirebaseToken(uid,
//                new SharedPrefUtil(getApplicationContext()).getString(Constants.ARG_FIREBASE_TOKEN, null));

        Toast.makeText(getActivity(), "Logged in successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();

    }

    //    **
//            * Logout From Facebook
// */
    public static void callFacebookLogout() {
        Profile profile = Profile.getCurrentProfile().getCurrentProfile();
        if (profile != null) {
            // user has logged in
            LoginManager.getInstance().logOut();

        } else {
            // user has not logged in
            LoginManager.getInstance().logOut();
        }
    }


    private void loginService() {
        try {
            MyDialog.getInstance(getContext()).showDialog(getActivity());
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<LoginResponse> call = apiInterface.login(binding.edtUsrNamLogin.getText().toString(), binding.edtPaswrdLogin.getText().toString(),
                    SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.DEVICETOKEN), "android", "firebase_id");
            Log.e(".....", "signUpService: " + call.request().toString());

            call.enqueue(new retrofit2.Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    MyDialog.getInstance(getContext()).hideDialog();

                    if (response.isSuccessful()) {
                        LoginResponse loginResponse = response.body();
                        Log.e("response", "" + loginResponse.toString());

                        if (loginResponse.getStatus().equalsIgnoreCase("SUCCESS")) {
                            MyDialog.getInstance(getContext()).showDialog(getActivity());

                            SPreferenceWriter.getInstance(getContext()).setUserDetails(loginResponse);
                            SPreferenceWriter.getInstance(getContext()).writeBooleanValue(SPreferenceKey.REMEMBERPASS, b);
                            SPreferenceWriter.getInstance(getContext()).writeStringValue(SPreferenceKey.LOGINUSERNAME, binding.edtUsrNamLogin.getText().toString());
                            SPreferenceWriter.getInstance(getContext()).writeStringValue(SPreferenceKey.LOGINPASS, binding.edtPaswrdLogin.getText().toString());

                            SPreferenceWriter.getInstance(getContext())
                                    .writeStringValue(SPreferenceKey.USER_TYPE, "NORMAL_TYPE");
                            String getEmail = loginResponse.getData().getEmail();
                            String notificationFlag = loginResponse.getData().getNotification_flag();

                            if (notificationFlag.equals("1")) {
                                SPreferenceWriter.getInstance(getActivity()).writeStringValue(SPreferenceKey.NOTIFICATION, "ON");

                            } else {
                                SPreferenceWriter.getInstance(getActivity()).writeStringValue(SPreferenceKey.NOTIFICATION, "OFF");
                            }
                            goToChatUsersActivity();

                        } else {

                            Toast.makeText(getContext(), "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(getContext()).hideDialog();
                    String s = "";
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loginSocial(String loginType) {
        try {
            String deviceToken = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.DEVICETOKEN);
            MyDialog.getInstance(getContext()).showDialog(getActivity());
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<SocialLoginResponce> call = apiInterface
                    .login_social(socialId,
                            deviceToken,
                            "android");

            Log.w("login_social", "" + socialId + "," +
                    deviceToken + "," + "android");

            call.enqueue(new retrofit2.Callback<SocialLoginResponce>() {
                @Override
                public void onResponse(@NonNull Call<SocialLoginResponce> call, @NonNull Response<SocialLoginResponce> response) {

                    MyDialog.getInstance(getContext()).hideDialog();
                    if (response.isSuccessful()) {
                        SocialLoginResponce loginResponse = response.body();
                        Log.e("onResponse", "" + loginResponse.toString());

                        if (loginResponse.getStatus().equalsIgnoreCase("SUCCESS")) {

                            String userID = loginResponse.getData().getUser_id();
                           /* String notificationFlag = loginResponse.getData().getNotification_flag();

                            if (notificationFlag.equals("1")){
                                SPreferenceWriter.getInstance(getActivity()).writeStringValue(SPreferenceKey.NOTIFICATION, "ON");

                            }
                            else {
                                SPreferenceWriter.getInstance(getActivity()).writeStringValue(SPreferenceKey.NOTIFICATION, "OFF");
                            }*/

                            SPreferenceWriter.getInstance(getActivity())
                                    .writeStringValue(SPreferenceKey.USERID, userID);

                            if (loginType.equalsIgnoreCase("GMAIL")) {
                                SPreferenceWriter.getInstance(getActivity())
                                        .writeStringValue(SPreferenceKey.USER_TYPE, "G_MAIL");
                            } else {
                                SPreferenceWriter.getInstance(getActivity())
                                        .writeStringValue(SPreferenceKey.USER_TYPE, "FB_TYPE");
                            }


                            SPreferenceWriter.getInstance(getContext()).setFbUserDetails(loginResponse);


                            if (loginResponse.getData().getStatus().equals("0")) {

                                //callFacebookLogout();
                                showTermsConditionDialog();
                            } else {
                                //HOME
                                SPreferenceWriter.getInstance(getContext()).writeBooleanValue(SPreferenceKey.LOGINKEY, true);
                                startActivity(new Intent(getContext(), HomeActivity.class));
                                getActivity().finish();
                            }

                        } else {

                            Toast.makeText(getContext(), "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(@NonNull Call<SocialLoginResponce> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(getContext()).hideDialog();
                    String s = "";
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, RequestBody> setUpMapData(String the_token) {

        Log.e("..signUpService...", "" + fb_id + "...." + name + "..." + email);

        Map<String, RequestBody> fields = new HashMap<>();

        RequestBody fullname = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody deviceType = RequestBody.create(MediaType.parse("text/plain"), "android");
        RequestBody deviceToken = RequestBody.create(MediaType.parse("text/plain"), the_token);
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody password_confirmation = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody socialid = RequestBody.create(MediaType.parse("text/plain"), socialId);
        RequestBody email1 = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody emailActivation = RequestBody.create(MediaType.parse("text/plain"), "1");

        fields.put("email", email1);
        fields.put("username", username);
        fields.put("fullname", fullname);
        fields.put("phone", phone);
        fields.put("deviceType", deviceType);
        fields.put("deviceToken", deviceToken);
        fields.put("password", password);
        fields.put("password_confirmation", password_confirmation);
        fields.put("socialid", socialid);
        fields.put("email_activation", emailActivation);

        return fields;
    }

    private void fbSignUpService() {
        try {

            String token = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.DEVICETOKEN);

            if (true) {
                MyDialog.getInstance(getContext()).showDialog(getActivity());
                RequestBody profile_body;
                MultipartBody.Part profilePart;

                if (image != null && !image.equalsIgnoreCase("")) {
                    File file = new File(image);
                    profile_body = RequestBody.create(MediaType.parse("image/*"), file);
                    profilePart = MultipartBody.Part.createFormData("image", file.getName(), profile_body);
                } else {
                    profilePart = MultipartBody.Part.createFormData("image", "");
                }

                ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
                Map<String, RequestBody> map = setUpMapData(token);

                Call<SignUpResponce> call = apiInterface.fbSignUp(map, profilePart);
                call.enqueue(new retrofit2.Callback<SignUpResponce>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(@NonNull Call<SignUpResponce> call, @NonNull Response<SignUpResponce> response) {
                        MyDialog.getInstance(getContext()).hideDialog();
                        if (response.isSuccessful()) {
                            //signupFirebase();
                            SignUpResponce signUpResponce = response.body();
                            Log.e("SignUpResponce", "onResponse: " + signUpResponce.toString());
                            if (signUpResponce.getStatus().equalsIgnoreCase("SUCCESS")) {
                                //SPreferenceWriter.getInstance(getContext()).writeStringValue(SPreferenceKey.LOGINVIA, "facebook");
//                                SPreferenceWriter.getInstance(getContext()).writeStringValue(SPreferenceKey.FBID, fb_id);
//                                SPreferenceWriter.getInstance(getApplicationContext()).writeBooleanValue(SPreferenceKey.LOGINKEY, true);
                                SPreferenceWriter.getInstance(getContext()).setSignUpDetails(signUpResponce);

                                callFacebookLogout();

                                String genre = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.GENRE);
                                if (genre.isEmpty()) {
                                    startActivity(new Intent(getContext(), ProceedActivity.class));
                                    getActivity().finish();
                                } else {
                                    startActivity(new Intent(getContext(), HomeActivity.class));
                                    getActivity().finish();
                                }

                            } else {
                                Toast.makeText(getContext(), "" + signUpResponce.getMessage(), Toast.LENGTH_SHORT).show();
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
            }
        } catch (Exception e) {
            e.printStackTrace();
            MyDialog.getInstance(getContext()).hideDialog();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 121) {
            try {

                name = data.getExtras().getString("f_name") + " " + data.getExtras().getString("l_name");
                f_name = data.getExtras().getString("f_name");
                l_name = data.getExtras().getString("l_name");
                fb_id = data.getExtras().getString("socialid");
                email = data.getExtras().getString("email");
                image = data.getExtras().getString("image");
                gender = data.getExtras().getString("gender");
                socialId = data.getExtras().getString("socialid");
                type = ("facebook");

                request = new DownloadManager.Request(Uri.parse(image));
                request.allowScanningByMediaScanner();
                image = SaveSocailImage();

                Log.e(".....FB........", "onActivityResult:" + name + "...." + email + "....." + image + "......" + socialId + "......");

                if (Util.showInternetAlert(getContext())) {
                    loginSocial("FB_TYPE");  //service hit..
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == GOOGLE_REQ_CODE) {
            try {

                if (data != null) {

                    name = data.getExtras().getString("name") /*+ " " + data.getExtras().getString("l_name")*/;
                    f_name = data.getExtras().getString("f_name");
                    l_name = data.getExtras().getString("l_name");
                    socialId = data.getExtras().getString("socialid");
                    email = data.getExtras().getString("email");
                    image = data.getExtras().getString("image");
                    gender = data.getExtras().getString("gender");
                    type = ("gmail");

                    if (image.equalsIgnoreCase("")) {

                    } else {
                        request = new DownloadManager.Request(Uri.parse(image));
                        request.allowScanningByMediaScanner();
                        image = SaveSocailImage();
                    }


                    Log.e(".....Gmail........", "onActivityResult:" + name + "...." + email + "....." + image + "......" + socialId + "......");

                    if (Util.showInternetAlert(getContext())) {
                        loginSocial("GMAIL");  ////SERVICE HIT.. for Google
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String SaveSocailImage() {

        fileName = fileName.replace("%20", " ");

        //SocailImagePath = Environment.getExternalStorageDirectory() + "/SocialImage/temp_image.jpg";
        File file = new File(SocailImagePath);
        if (file.exists()) {
            file.delete();
        }
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        request.setDestinationInExternalPublicDir("/SocialImage/", fileName);
        DownloadManager manager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        requestId = manager.enqueue(request);

        return SocailImagePath;
    }

    private void showTermsConditionDialog() {
        dialog = new Dialog(Objects.requireNonNull(getActivity()));
        dialog.setContentView(R.layout.terms_condi_dialog);
        Objects.requireNonNull(dialog.getWindow())
                .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        ImageView imgboxTerms = dialog.findViewById(R.id.imgboxTerms);
        ImageView imgAdrs = dialog.findViewById(R.id.imgAdrs);
        //TextView tvDetail = dialog.findViewById(R.id.tvDetail);

        //DONE
        dialog.findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                if (termsChk == false) {
                    Toast.makeText(getActivity(), "Select Terms and Condition!", Toast.LENGTH_SHORT).show();
                } else {
                    //FB sign up
                    if (Util.showInternetAlert(getContext())) {
                        showGenraDialog();
                        //fbSignUpService();
                        //Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Please check Internet connection.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        //COMMUNICATION ADDRESS
        dialog.findViewById(R.id.imgAdrs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chechCommunicationAddress();
            }

            private void chechCommunicationAddress() {
                if (check) {
                    imgAdrs.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.login_tick_un));
                    check = false;
                } else {
                    imgAdrs.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.login_tick_slected));
                    check = true;
                }
            }
        });

        //Terms CLICK
        dialog.findViewById(R.id.imgboxTerms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTerms();
            }

            private void checkTerms() {
                if (termsChk) {
                    imgboxTerms.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.login_tick_un));
                    termsChk = false;
                } else {
                    imgboxTerms.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.login_tick_slected));
                    termsChk = true;
                }

            }
        });

        if (termsChk == false) {
            Toast.makeText(getActivity(), "Select Terms and Condition!", Toast.LENGTH_SHORT).show();
        } else {
            //FB sign up
            if (Util.showInternetAlert(getContext())) {
                showGenraDialog();
                //fbSignUpService();
                //Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Please check Internet connection.", Toast.LENGTH_SHORT).show();
            }
        }

        TextView termsCondition = Textoo.config((TextView) dialog.findViewById(R.id.tvDetail)).addLinksHandler(new LinksHandler() {
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
    }

    private void showGenraDialog() {
        dialog = new Dialog(Objects.requireNonNull(getActivity()));
        dialog.setContentView(R.layout.genre_dialog);
        Objects.requireNonNull(dialog.getWindow())
                .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        ImageView imgboxTerms = dialog.findViewById(R.id.imgboxTerms);
        ImageView imgAdrs = dialog.findViewById(R.id.imgAdrs);
        //TextView tvDetail = dialog.findViewById(R.id.tvDetail);

        //DONE
        dialog.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                if (Util.showInternetAlert(getContext())) {
                    fbSignUpService();
                    //Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Please check Internet connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
