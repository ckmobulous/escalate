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

import com.escalate.R;
import com.escalate.databinding.ActivityTermsConditionBinding;
import com.escalate.model.ReportedUserModel;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;

import retrofit2.Call;
import retrofit2.Response;

public class TermsConditionAct extends AppCompatActivity implements View.OnClickListener {
    private ActivityTermsConditionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_terms_condition);
        binding.backIvTandC.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        Intent intent = getIntent();
        if (intent != null) {
            String check = getIntent().getStringExtra("check");
            if (check.equals("terms_condition")) {
                binding.toolbarTitle.setText("Terms and Condition");
                termsConditionService();
            } else {
                binding.toolbarTitle.setText("Privacy Policy");
                privayPolicyService();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIvTandC:
                finish();
                break;
        }
    }

    //API
    public void termsConditionService() {
        try {
            MyDialog.getInstance(TermsConditionAct.this).showDialog(TermsConditionAct.this);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<ReportedUserModel> call = apiInterface.requesttandccontent();

            call.enqueue(new retrofit2.Callback<ReportedUserModel>() {
                @Override
                public void onResponse(@NonNull Call<ReportedUserModel> call, Response<ReportedUserModel> response) {

                    if (response.isSuccessful()) {
                        ReportedUserModel replyPostList = response.body();
                        Log.e("response", "" + replyPostList.toString());
                        if (replyPostList.getStatus().equalsIgnoreCase("SUCCESS")) {

                            String description = replyPostList.getData().getDescription();
                            binding.descriptionTxt.setText(description);

                        } else {
                            // Toast.makeText(this, "" + replyPostList.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //  Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                    MyDialog.getInstance(TermsConditionAct.this).hideDialog();
                }

                @Override
                public void onFailure(@NonNull Call<ReportedUserModel> call, Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(TermsConditionAct.this).hideDialog();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //API
    public void privayPolicyService() {
        try {
            MyDialog.getInstance(TermsConditionAct.this).showDialog(TermsConditionAct.this);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<ReportedUserModel> call = apiInterface.requestPrivacypolicycontent();

            call.enqueue(new retrofit2.Callback<ReportedUserModel>() {
                @Override
                public void onResponse(@NonNull Call<ReportedUserModel> call, Response<ReportedUserModel> response) {
                    if (response.isSuccessful()) {
                        ReportedUserModel replyPostList = response.body();
                        Log.e("response", "" + replyPostList.toString());
                        if (replyPostList.getStatus().equalsIgnoreCase("SUCCESS")) {

                            String description = replyPostList.getData().getDescription();
                            binding.descriptionTxt.setText(description);

                        } else {
                            // Toast.makeText(this, "" + replyPostList.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //  Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                    MyDialog.getInstance(TermsConditionAct.this).hideDialog();
                }

                @Override
                public void onFailure(@NonNull Call<ReportedUserModel> call, Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(TermsConditionAct.this).hideDialog();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
