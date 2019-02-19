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
import android.widget.EditText;
import android.widget.Toast;

import com.escalate.R;
import com.escalate.databinding.ActivityChangePassBinding;
import com.escalate.model.LoginResponse;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.escalate.utils.Util;
import com.escalate.utils.Validator;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;


public class ChangePassActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityChangePassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_pass);

        binding.iViewBack.setOnClickListener(this);
        binding.btnSave.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iViewBack:
                finish();
                break;
            case R.id.btnSave:
                if (validatePass()) {
                    if (Util.showInternetAlert(this)) {
                        changePasswordService();
                    }
                }
                break;
        }
    }

    private boolean validatePass() {
        boolean isValid = true;
        String password = Util.getProperText(binding.edtOldPass);
        String newPassword = Util.getProperText(binding.edtNewPass);
        String cnfPassword = Util.getProperText(binding.edtCnfass);
        EditText editText = null;
        if (password.isEmpty()) {
            //edtOldpass.setError(getString(R.string.error_old_pass));
            Toast.makeText(this, getString(R.string.error_old_pass), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = binding.edtOldPass;
            editText.requestFocus();
        } else if (!Validator.validatePassword(password)) {
            // edtOldpass.setError(getString(R.string.error_valid_password));
            Toast.makeText(this, getString(R.string.error_valid_password), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = binding.edtOldPass;
            editText.requestFocus();
        } else if (newPassword.isEmpty()) {
            // edtNewpass.setError(getString(R.string.error_new_password));
            Toast.makeText(this, getString(R.string.error_new_password), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = binding.edtNewPass;
            editText.requestFocus();
        } else if (!Validator.validatePassword(newPassword)) {
            //  edtNewpass.setError(getString(R.string.error_valid_password));
            Toast.makeText(this, getString(R.string.error_valid_password), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = binding.edtNewPass;
            editText.requestFocus();
        } else if (cnfPassword.isEmpty()) {
            //  edtCnfpass.setError(getString(R.string.error_cnf_password));
            Toast.makeText(this, getString(R.string.error_cnf_password), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = binding.edtCnfass;
            editText.requestFocus();
        } else if (!Validator.validatePassword(cnfPassword)) {
            //  edtCnfpass.setError(getString(R.string.error_valid_password));
            Toast.makeText(this, getString(R.string.error_valid_password), Toast.LENGTH_SHORT).show();
            isValid = false;
            editText = binding.edtCnfass;
            editText.requestFocus();
        }
        return isValid;
    }

    private void changePasswordService() {
        try {
            MyDialog.getInstance(ChangePassActivity.this).showDialog(this);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<LoginResponse> call = apiInterface.changePassword(SPreferenceWriter.getInstance(getApplicationContext()).getString(SPreferenceKey.USERID),
                    binding.edtOldPass.getText().toString(), binding.edtNewPass.getText().toString(), binding.edtCnfass.getText().toString(),
                    SPreferenceWriter.getInstance(getApplicationContext()).getString(SPreferenceKey.TOKEN));

            call.enqueue(new retrofit2.Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        LoginResponse loginResponse = response.body();
                        Log.e("", "onResponse: " + loginResponse.toString());

                        if (loginResponse.getStatus().equalsIgnoreCase("SUCCESS")) {
//                            Toast.makeText(ChangePassActivity.this, "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(ChangePassActivity.this, "Password Changed Successfully.", Toast.LENGTH_SHORT).show();
                            goToSplash();

                        } else {
                            Toast.makeText(ChangePassActivity.this, "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ChangePassActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
                    }
                    MyDialog.getInstance(ChangePassActivity.this).hideDialog();
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(ChangePassActivity.this).hideDialog();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToSplash() {
        SPreferenceWriter.getInstance(ChangePassActivity.this).clearPreferenceValues("");
        Objects.requireNonNull(ChangePassActivity.this).finishAffinity();
        Intent intent = new Intent(ChangePassActivity.this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
