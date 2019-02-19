package com.escalate.fcm;


import android.util.Log;

import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FCMInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "FCMInstanceIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */

    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
//        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed FCM device token: " + refreshedToken);

        //saving token to shared preference
        SPreferenceWriter.getInstance(this).writeStringValue(SPreferenceKey.DEVICETOKEN,refreshedToken);

//        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]


    /*
     *
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
//    private void sendRegistrationToServer(String token) {
//        // Add custom implementation, as needed.
//    }

}
