package com.escalate.services;

///**
// * Created by Rajeev Sharma [rajeevrocker7@gmail.com] on 27/7/17.
// */

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.escalate.sharedpreference.SPreferenceWriter;

public class CallStateService extends IntentService {

    private final String TAG = CallStateService.class.getSimpleName();

    public CallStateService() {
        super("CallStateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            if (telephonyManager != null) {
                if(TelephonyManager.CALL_STATE_IDLE == telephonyManager.getCallState())
                {
                    Log.i(TAG,"In TelephonyManager.CALL_STATE_IDLE State");

                    SPreferenceWriter.getInstance(this).writeBooleanValue("isCalled", true);

                    stopSelf(); //STOP THIS VERY SERVICE

    ////              START RATINGS DIALOG AS AN ACTIVITY
    //                Intent ratingsIntent = new Intent(this, RatingDialogActivity.class);
    //
    ////              GET DATA
    //                String token = intent.getStringExtra(ParamEnum.TOKEN_SAVED.theValue());
    //                String doc_id = intent.getStringExtra(ParamEnum.DOC_ID.theValue());
    //                String user_id = intent.getStringExtra(ParamEnum.USER_ID.theValue());
    //
    ////              PUT DATA IN RATINGS INTENT
    //                ratingsIntent.putExtra(ParamEnum.TOKEN_SAVED.theValue(), token);
    //                ratingsIntent.putExtra(ParamEnum.DOC_ID.theValue(), doc_id);
    //                ratingsIntent.putExtra(ParamEnum.USER_ID.theValue(), user_id);
    //
    //                ratingsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //                startActivity(ratingsIntent);

                }
                else {
                    Log.i(TAG,"NOT in TelephonyManager.CALL_STATE_IDLE State");

                    stopSelf();

                    startService(intent);

                }
            }
        }
    }


}
