package com.escalate.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.escalate.R;
import com.escalate.databinding.ActivityPostQueriesBinding;
import com.escalate.model.EmailResponce;
import com.escalate.model.EmailResponce;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.escalate.utils.Util;
import com.escalate.utils.Validator;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class PostQueriesAct extends AppCompatActivity implements View.OnClickListener {
   private ActivityPostQueriesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_post_queries);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        binding.iViewBackQuery.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iViewBackQuery:
                onBackPressed();
                break;
            case R.id.btnSubmit:
                if (validation()) {
                    if (Util.showInternetAlert(this)) {
                        postQueryService();
                    }
                }
                break;
        }
    }

    private boolean validation() {
        boolean isValid = true;
        String edtPostQuery = Util.getProperText(binding.edtPostQuery);
        EditText editText = null;
        if (edtPostQuery.isEmpty()) {
            binding.edtPostQuery.setError(getString(R.string.error_query));
            isValid = false;
            editText = binding.edtPostQuery;
            editText.requestFocus();
        }
        return isValid;
    }

    private void postQueryService() {
        try {
            MyDialog.getInstance(PostQueriesAct.this).showDialog(this);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<EmailResponce> call = apiInterface.submitqueryReq(binding.edtPostQuery.getText().toString(),SPreferenceWriter.getInstance(getApplicationContext()).getString(SPreferenceKey.USERID),
                    SPreferenceWriter.getInstance(getApplicationContext()).getString(SPreferenceKey.TOKEN));

            call.enqueue(new retrofit2.Callback<EmailResponce>() {
                @Override
                public void onResponse(@NonNull Call<EmailResponce> call, @NonNull Response<EmailResponce> response) {
                    if (response.isSuccessful()) {
                        EmailResponce EmailResponce = response.body();
                        Log.e("", "onResponse: " + EmailResponce.toString());

                        if (EmailResponce.getStatus().equalsIgnoreCase("SUCCESS")) {
//                            Toast.makeText(ChangePassActivity.this, "" + EmailResponce.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(PostQueriesAct.this, "Submit Successfully.", Toast.LENGTH_SHORT).show();
                            finish();

                        } else {
                            Toast.makeText(PostQueriesAct.this, "" + EmailResponce.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PostQueriesAct.this, "Server Error!", Toast.LENGTH_SHORT).show();
                    }
                    MyDialog.getInstance(PostQueriesAct.this).hideDialog();
                }

                @Override
                public void onFailure(@NotNull Call<EmailResponce> call, @NotNull Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(PostQueriesAct.this).hideDialog();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToSplash() {
        SPreferenceWriter.getInstance(PostQueriesAct.this).clearPreferenceValues("");
        Objects.requireNonNull(PostQueriesAct.this).finishAffinity();
        Intent intent = new Intent(PostQueriesAct.this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
