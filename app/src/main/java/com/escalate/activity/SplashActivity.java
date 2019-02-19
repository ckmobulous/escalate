package com.escalate.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.escalate.R;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.google.firebase.iid.FirebaseInstanceId;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        getDeviceToken();

//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this /* Current context */);

        SharedPreferences sharedpreferences = getSharedPreferences("escalate", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor;
        prefEditor = sharedpreferences.edit();
        prefEditor.putString(SPreferenceKey.START,"START");
        prefEditor.commit();

        Thread timerThread = new Thread() {
            public void run() {
                try {

                    sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    if(SPreferenceWriter.getInstance(SplashActivity.this).getBoolean(SPreferenceKey.LOGINKEY)){
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    }
                    else if ( SPreferenceWriter.getInstance(SplashActivity.this).getBoolean(SPreferenceKey.facebook_key))
                    {
                        startActivity(new Intent(SplashActivity.this, ProceedActivity.class));

                    }
                    else{
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    finish();
                }
            }
        };
        timerThread.start();
    }

    //    METHOD: TO GET DEVICE_TOKEN FROM FCM
    private void getDeviceToken() {

        final String TAG = SplashActivity.class.getSimpleName();
        Runnable runnableObj = new Runnable() {
            @Override
            public void run() {

                SPreferenceWriter mPreference = SPreferenceWriter.getInstance(SplashActivity.this.getApplicationContext());
                Log.w(TAG, "Previous Device Token : " + mPreference.getString(SPreferenceKey.DEVICETOKEN));

                try {
                    if (mPreference.getString(SPreferenceKey.DEVICETOKEN).isEmpty()) {
                        String device_token = FirebaseInstanceId.getInstance().getToken();
                        Log.e(TAG, "Generated Device Token : " + device_token);
                        if (device_token == null) {
                            getDeviceToken();
                        } else {
                            mPreference.writeStringValue(SPreferenceKey.DEVICETOKEN, device_token);
                        }
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();

                    // [START Crashlytics log_and_report]
//                    Crashlytics.log(Log.ERROR, TAG, "NPE caught!");
//                    Crashlytics.logException(e1);
                }


            }
        };

        Thread thread = new Thread(runnableObj);
        thread.start();

        // [START crashlytics_log_event]
//        Crashlytics.log("Splash Activity created");

    }
}
