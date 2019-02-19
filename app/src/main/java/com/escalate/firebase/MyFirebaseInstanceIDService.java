package com.escalate.firebase;

import android.util.Log;

import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by akaMahesh on 23/3/17.
 * Copyright to Mobulous Technology Pvt. Ltd.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken= FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG+"token", "sendRegistrationToServer: " + refreshedToken);

        //saving token to shared preference
        SPreferenceWriter mPreference = SPreferenceWriter.getInstance(getApplicationContext());
        mPreference.writeStringValue(SPreferenceKey.DEVICETOKEN, refreshedToken);
        //sendRegistrationToServer(refreshedToken);

    }

    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */

}
