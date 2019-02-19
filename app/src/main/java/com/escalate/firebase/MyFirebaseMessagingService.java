package com.escalate.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by akaMahesh on 23/3/17.
 * Copyright to Mobulous Technology Pvt. Ltd.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "PUSH";
    //private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        /*if (remoteMessage == null)
            return;

        try {
            handleMyDataMessage(remoteMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        //Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
       /* if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            String title = remoteMessage.getData().get("title");
            String message = remoteMessage.getData().get("text");
            String username = remoteMessage.getData().get("username");
            String uid = remoteMessage.getData().get("uid");
            String fcmToken = remoteMessage.getData().get("fcm_token");

            // Don't show notification if chat activity is open.
            if (!FirebaseChatMainApp.isChatActivityOpen()) {
                sendNotification(title,
                        message,
                        username,
                        uid,
                        fcmToken);
            } else {
                EventBus.getDefault().post(new PushNotificationEvent(title,
                        message,
                        username,
                        uid,
                        fcmToken));
            }
        }*/
    }
    /**
     * Create and show a simple notification containing the received FCM message.
     */
  /*  private void sendNotification(String title,
                                  String message,
                                  String receiver,
                                  String receiverUid,
                                  String firebaseToken) {
        Intent intent = new Intent(this, OneToOneChatActivity.class);
        intent.putExtra(Constants.ARG_RECEIVER, receiver);
        intent.putExtra(Constants.ARG_RECEIVER_UID, receiverUid);
        intent.putExtra(Constants.ARG_FIREBASE_TOKEN, firebaseToken);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_messaging)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }*/

   /* private void handleMyDataMessage(RemoteMessage remoteMessage) throws Exception {

        String body = remoteMessage.getNotification().getBody();
        String title = remoteMessage.getNotification().getTitle();
        Log.v(TAG," body "+body);
        Log.v(TAG," title "+title);

        *//*if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra(kBody,body);
            pushNotification.putExtra(kTitle,title);

            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();

        } else {
            Intent resultIntent = SplashActivity.getIntent(getApplicationContext());
            showNotificationMessage(getApplicationContext(), title, body, Utils.generateTimeStamp(), resultIntent);
        }
*//*

    }
*/
    private Integer convertIntoInteger(String value){
        Integer intvalue = 0;
        try {
            intvalue = Integer.parseInt(value);
        }catch (Exception e){
            e.printStackTrace();
        }
        return intvalue;
    }

    /**
     * Showing notification with text only
     */
    /*  private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    */ /**
     * Showing notification with text and image
     *//*
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }*/

}
