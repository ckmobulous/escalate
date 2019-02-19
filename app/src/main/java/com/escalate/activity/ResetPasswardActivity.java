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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.escalate.R;
import com.escalate.databinding.ActivityResetPassBinding;
import com.escalate.model.SampleResponce;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.escalate.utils.Util;
import com.escalate.utils.Validator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;


public class ResetPasswardActivity extends AppCompatActivity implements View.OnClickListener{
   ActivityResetPassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_reset_pass);

        binding.backIv.setOnClickListener(this);
        binding.btnSaveReset.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.backIv:
                   finish();
                    break;
                case R.id.btnSaveReset:
                    if(validatePass()){
                        if(Util.showInternetAlert(this)){
                            resetPasswordService();
                        }
                    }
                    break;

            }
    }

    private void resetPasswordService() {
        try {
            String userId = SPreferenceWriter.getInstance(getApplicationContext()).getString(SPreferenceKey.USERID);
            MyDialog.getInstance(ResetPasswardActivity.this).showDialog(this);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<SampleResponce> call = apiInterface.reset_password(userId, binding.edtNewPass.getText().toString(),
                    binding.edtCnfPassReset.getText().toString());

            call.enqueue(new retrofit2.Callback<SampleResponce>() {
                @Override
                public void onResponse(@NonNull Call<SampleResponce> call, @NonNull Response<SampleResponce> response) {
                    if (response.isSuccessful()) {
                        SampleResponce SampleResponce = response.body();
                        Log.e("resetPassward", "onResponse: "+ SampleResponce.toString());

                        if (SampleResponce.getStatus().equalsIgnoreCase("SUCCESS")) {
//                            Toast.makeText(ResetPasswardActivity.this, "" + SampleResponce.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(ResetPasswardActivity.this, "Password Reset Successfully.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ResetPasswardActivity.this, LoginActivity.class));
                            SPreferenceWriter.getInstance(ResetPasswardActivity.this.getApplicationContext()).clearPreferenceValues("");
                            finish();
                        } else {
                            Toast.makeText(ResetPasswardActivity.this, "" + SampleResponce.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ResetPasswardActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
                    }
                    MyDialog.getInstance(ResetPasswardActivity.this).hideDialog();
                }

                @Override
                public void onFailure(@NonNull Call<SampleResponce> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(ResetPasswardActivity.this).hideDialog();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validatePass() {

        boolean isValid = true;
        String newPassword = Util.getProperText(binding.edtNewPass);
        String cnfPassword = Util.getProperText(binding.edtCnfPassReset);
        EditText editText = null;

        if (newPassword.isEmpty()) {
            // edtNewpass.setError(getString(R.string.error_new_password));
            Toast.makeText(this, getString(R.string.error_new_password), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = binding.edtNewPass;
            editText.requestFocus();
        }else if (!Validator.validatePassword(newPassword)) {
            //  edtNewpass.setError(getString(R.string.error_valid_password));
            Toast.makeText(this, getString(R.string.error_valid_password), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = binding.edtNewPass;
            editText.requestFocus();
        }else if (cnfPassword.isEmpty()) {
            //  edtCnfpass.setError(getString(R.string.error_cnf_password));
            Toast.makeText(this, getString(R.string.error_cnf_password), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = binding.edtCnfPassReset;
            editText.requestFocus();
        } else if (!Validator.validatePassword(cnfPassword)) {
            //  edtCnfpass.setError(getString(R.string.error_valid_password));
            Toast.makeText(this, getString(R.string.error_valid_password), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = binding.edtCnfPassReset;
            editText.requestFocus();
        }
        return isValid;
    }
}
