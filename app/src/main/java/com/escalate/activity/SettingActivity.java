package com.escalate.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.escalate.R;
import com.escalate.databinding.ActivitySettingBinding;
import com.escalate.model.ReportedUserModel;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;

import retrofit2.Call;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySettingBinding binding;

    boolean checkStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);

        binding.backIvSeting.setOnClickListener(this);
        binding.logoutLay.setOnClickListener(this);
        binding.changePassLay.setOnClickListener(this);
        binding.swStatusCustom.setOnClickListener(this);
        binding.termsCondLay.setOnClickListener(this);
        binding.relPrivacyPpolicy.setOnClickListener(this);
        binding.relPostQueries.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        String userType = SPreferenceWriter.getInstance(this).getString(SPreferenceKey.USER_TYPE);

        if (userType != null && !userType.isEmpty()) {
            if ("FB_TYPE".equalsIgnoreCase(userType)) {
                //change pass
                binding.changePassLay.setVisibility(View.GONE);
                binding.lineChange.setVisibility(View.GONE);

            } else {
                //change pass
                binding.changePassLay.setVisibility(View.VISIBLE);
                binding.lineChange.setVisibility(View.VISIBLE);
            }
        }

        if (SPreferenceWriter.getInstance(this).getString(SPreferenceKey.NOTIFICATION).equals("ON")) {
            binding.swStatusCustom.setChecked(true);
            checkStatus = true;
        } else {
            binding.swStatusCustom.setChecked(false);
            checkStatus = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIvSeting:
                onBackPressed();
                break;
            case R.id.logoutLay:
                logout();
                break;
            case R.id.changePassLay:
                startActivity(new Intent(this, ChangePassActivity.class));
                break;

            case R.id.reportedUserLay:

                break;
            case R.id.swStatusCustom:

                if (checkStatus == false)
                    notificationListService();
                else
                    notificationListService();

                break;
            case R.id.termsCondLay:
                Intent intentTc = new Intent(SettingActivity.this, TermsConditionAct.class);
                intentTc.putExtra("check", "terms_condition");
                startActivity(intentTc);
                break;
            case R.id.relPrivacyPpolicy:
                Intent intentTPp = new Intent(SettingActivity.this, TermsConditionAct.class);
                intentTPp.putExtra("check", "privecy_policy");
                startActivity(intentTPp);
                break;
            case R.id.relPostQueries:
                 startActivity(new Intent(SettingActivity.this,PostQueriesAct.class));
                break;
        }
    }

    private void logout() {

//        SPreferenceWriter.getInstance(getApplicationContext()).clearPreferenceValues("");
        boolean b =
                SPreferenceWriter.getInstance(SettingActivity.this)
                        .getBoolean(SPreferenceKey.REMEMBERPASS);
        String u_name =
                SPreferenceWriter.getInstance(SettingActivity.this).getString(SPreferenceKey.LOGINUSERNAME);
        String pass =
                SPreferenceWriter.getInstance(SettingActivity.this).getString(SPreferenceKey.LOGINPASS);

        SPreferenceWriter.getInstance(SettingActivity.this.getApplicationContext()).clearPreferenceValues("");

        SPreferenceWriter.getInstance(SettingActivity.this)
                .writeBooleanValue(SPreferenceKey.REMEMBERPASS, b);
        SPreferenceWriter.getInstance(SettingActivity.this)
                .writeStringValue(SPreferenceKey.LOGINUSERNAME, u_name);
        SPreferenceWriter.getInstance(SettingActivity.this)
                .writeStringValue(SPreferenceKey.LOGINPASS, pass);

        Toast.makeText(this, "Logged Out Successfully.", Toast.LENGTH_SHORT).show();

        finishAffinity();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }


    //API
    public void notificationListService() {
        try {
            MyDialog.getInstance(SettingActivity.this).showDialog(SettingActivity.this);

            String userid = SPreferenceWriter.getInstance(SettingActivity.this).getString(SPreferenceKey.USERID);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<ReportedUserModel> call = apiInterface.requestNotificationTrigger(userid);

            call.enqueue(new retrofit2.Callback<ReportedUserModel>() {
                @Override
                public void onResponse(@NonNull Call<ReportedUserModel> call, Response<ReportedUserModel> response) {
                    MyDialog.getInstance(SettingActivity.this).hideDialog();

                    if (response.isSuccessful()) {
                        ReportedUserModel replyPostList = response.body();
                        Log.e("response", "" + replyPostList.toString());
                        if (replyPostList.getStatus().equalsIgnoreCase("SUCCESS")) {

                            String status = replyPostList.getData().getStatus();
                            if (status.equals("OFF")) {
                                binding.swStatusCustom.setChecked(false);
                                SPreferenceWriter.getInstance(SettingActivity.this).writeStringValue(SPreferenceKey.NOTIFICATION, status);
                                checkStatus = true;
                            } else {
                                binding.swStatusCustom.setChecked(true);
                                checkStatus = false;
                                SPreferenceWriter.getInstance(SettingActivity.this).writeStringValue(SPreferenceKey.NOTIFICATION, status);

                            }

                        } else {
                            // Toast.makeText(this, "" + replyPostList.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //  Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ReportedUserModel> call, Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(SettingActivity.this).hideDialog();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
