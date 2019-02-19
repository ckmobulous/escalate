package com.escalate.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.escalate.R;
import com.escalate.activity.ChatActivity;
import com.escalate.activity.HomeActivity;
import com.escalate.activity.NotificationActivity;
import com.escalate.activity.SplashActivity;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.escalate.utils.AppConstants;
import com.escalate.utils.MyApplication;
import com.escalate.utils.ParamEnum;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import me.leolin.shortcutbadger.ShortcutBadger;

public class FCMMessagingService extends FirebaseMessagingService {
    private static final String TAG = FCMMessagingService.class.getSimpleName();
    private Handler mHandler;

    private final int FOLLOW = 1;
    private final int CHAT = 2;
    private final int LIKES = 3;
    private final int COMMENT = 4;

    private void sendMsgToUIThread(int what, int arg1, int arg2, Object obj) {
        // And this is how you call it from the worker thread:
        Message mess = new Message();
        mess.what = what;
        mess.arg1 = arg1;
        mess.arg2 = arg2;
        mess.obj = obj;

        if (mHandler != null)
            mHandler.sendMessage(mess);
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]


        // Set this up in the UI thread.
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                // This is where you do your work in the UI thread.
                // Your worker tells you in the message what to do.
                if(message!=null)
                {
                    int what = message.what;
//                    int arg1 = message.arg1;
//                    int arg2 = message.arg2;
                    Object object = message.obj;

                    switch (what)
                    {
                        case FOLLOW:   //FOLLOW

                            try {
                                JSONObject jsonObject = (JSONObject) object;
//                                Toast.makeText(FCMMessagingService.this, ""+jsonObject.toString(), Toast.LENGTH_SHORT).show();

                                if (!NotificationUtils.isAppIsInBackground(FCMMessagingService.this)) {

                                    if(NotificationActivity.isNotifyOnForeground)
                                    {
                                        Bundle bundle = makeUserDocNotifyBundle(jsonObject);    //to the doctor FOREGROUND
                                        Intent intent = new Intent(ParamEnum.PUSH_BOOKING.theValue());
                                        intent.putExtras(bundle);
                                        LocalBroadcastManager.getInstance(FCMMessagingService.this).sendBroadcast(intent);

                                        int badge = SPreferenceWriter.getInstance(FCMMessagingService.this).getInt(SPreferenceKey.BADGE_COUNT);
                                        int badgeCount = badge + 1;
                                        SPreferenceWriter.getInstance(FCMMessagingService.this).writeIntValue(SPreferenceKey.BADGE_COUNT, badgeCount);
                                        ShortcutBadger.applyCount(FCMMessagingService.this.getApplicationContext(), badgeCount); //for 1.1.4+
                                    }
                                    else {
                                        Bundle bundle = makeUserDocNotifyBundle(jsonObject);    //to the doctor
                                        sendNotification(bundle, ParamEnum.HOME);
                                    }

                                } else {

                                    Bundle bundle = makeUserDocNotifyBundle(jsonObject);    //to the doctor
                                    sendNotification(bundle, ParamEnum.HOME);

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;

                        case CHAT:    //CHAT

                            try {
                                JSONObject jsonObject = (JSONObject) object;
//                                Toast.makeText(FCMMessagingService.this, ""+jsonObject.toString(), Toast.LENGTH_SHORT).show();

                                if (!NotificationUtils.isAppIsInBackground(FCMMessagingService.this)) {

                                    if(ChatActivity.isChatActOnForeground)
                                    {
                                        Bundle bundle = makeUserDocNotifyBundle(jsonObject);    //to the user FOREGROUND
                                        Intent intent = new Intent(ParamEnum.PUSH_CHAT.theValue());
                                        intent.putExtras(bundle);
                                        LocalBroadcastManager.getInstance(FCMMessagingService.this).sendBroadcast(intent);

                                        int badge = SPreferenceWriter.getInstance(FCMMessagingService.this).getInt(SPreferenceKey.BADGE_COUNT);
                                        int badgeCount = badge + 1;
                                        SPreferenceWriter.getInstance(FCMMessagingService.this).writeIntValue(SPreferenceKey.BADGE_COUNT, badgeCount);
                                        ShortcutBadger.applyCount(FCMMessagingService.this.getApplicationContext(), badgeCount); //for 1.1.4+
                                    }
                                    else {
                                        Bundle bundle = makeUserDocNotifyBundle(jsonObject);    //to the user
                                        sendNotification(bundle, ParamEnum.HOME);

                                    }

                                } else {

                                    Bundle bundle = makeUserDocNotifyBundle(jsonObject);    //to the user
                                    sendNotification(bundle, ParamEnum.HOME);

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;

                        case LIKES:
                            try {
                                JSONObject jsonObject = (JSONObject) object;
//                                Toast.makeText(FCMMessagingService.this, ""+jsonObject.toString(), Toast.LENGTH_SHORT).show();

                                if (!NotificationUtils.isAppIsInBackground(FCMMessagingService.this)) {


                                    if(NotificationActivity.isNotifyOnForeground)
                                    {
                                        Bundle bundle = makeUserDocNotifyBundle(jsonObject);    //to the doctor FOREGROUND
                                        Intent intent = new Intent(ParamEnum.LIKES.theValue());
                                        intent.putExtras(bundle);
                                        LocalBroadcastManager.getInstance(FCMMessagingService.this).sendBroadcast(intent);

                                        int badge = SPreferenceWriter.getInstance(FCMMessagingService.this).getInt(SPreferenceKey.BADGE_COUNT);
                                        int badgeCount = badge + 1;
                                        SPreferenceWriter.getInstance(FCMMessagingService.this).writeIntValue(SPreferenceKey.BADGE_COUNT, badgeCount);
                                        ShortcutBadger.applyCount(FCMMessagingService.this.getApplicationContext(), badgeCount); //for 1.1.4+
                                    }
                                    else {
                                        Bundle bundle = makeUserDocNotifyBundle(jsonObject);    //to the doctor
                                        sendNotification(bundle, ParamEnum.HOME);
                                        }
                                        }
                                        else {

                                    Bundle bundle = makeUserDocNotifyBundle(jsonObject);    //to the doctor
                                    sendNotification(bundle, ParamEnum.HOME);
                                    }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;

                        case COMMENT:
                            try {
                                JSONObject jsonObject = (JSONObject) object;
//                                Toast.makeText(FCMMessagingService.this, ""+jsonObject.toString(), Toast.LENGTH_SHORT).show();
                                if (!NotificationUtils.isAppIsInBackground(FCMMessagingService.this)) {

                                    if(NotificationActivity.isNotifyOnForeground)
                                    {
                                        Bundle bundle = makeUserDocNotifyBundle(jsonObject);    //to the doctor FOREGROUND
                                        Intent intent = new Intent(ParamEnum.COMMENT.theValue());
                                        intent.putExtras(bundle);
                                        LocalBroadcastManager.getInstance(FCMMessagingService.this).sendBroadcast(intent);

                                        int badge = SPreferenceWriter.getInstance(FCMMessagingService.this).getInt(SPreferenceKey.BADGE_COUNT);
                                        int badgeCount = badge + 1;
                                        SPreferenceWriter.getInstance(FCMMessagingService.this).writeIntValue(SPreferenceKey.BADGE_COUNT, badgeCount);
                                        ShortcutBadger.applyCount(FCMMessagingService.this.getApplicationContext(), badgeCount); //for 1.1.4+
                                    }
                                    else {
                                        Bundle bundle = makeUserDocNotifyBundle(jsonObject);    //to the doctor
                                        sendNotification(bundle, ParamEnum.HOME);
                                        }

                                } else {

                                    Bundle bundle = makeUserDocNotifyBundle(jsonObject);    //to the doctor
                                    sendNotification(bundle, ParamEnum.HOME);

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;


                    }
                }
            }
        };

        Log.d(TAG, "FCM remoteMessage.getFrom(): " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) // Check if message contains a data payload.
        {
            try {
                Log.d(TAG, "FCM Message data payload: " + remoteMessage.getData());
                Map<String, String> data = remoteMessage.getData();
                JSONObject jsonObject = new JSONObject(data);

                boolean isLogin = SPreferenceWriter.getInstance(FCMMessagingService.this).getBoolean(SPreferenceKey.LOGINKEY);
//                String userType = MyApplication.getLoginUserType(getApplicationContext());

                if (isLogin) {

                    if (jsonObject.getString("type")!=null)
                    {

                        if (jsonObject.getString("type").equalsIgnoreCase("follow")) {
                            sendMsgToUIThread(FOLLOW, 1, 10, jsonObject);   //to the user
                        }
                        else if (jsonObject.getString("type").equalsIgnoreCase("chat")) {
                            sendMsgToUIThread(CHAT, 2, 11, jsonObject);   //to the user
                        }
                        else if (jsonObject.getString("type").equalsIgnoreCase("likes")) {
                            sendMsgToUIThread(LIKES, 3, 12, jsonObject);   //to the user
                        }
                        else if (jsonObject.getString("type").equalsIgnoreCase("comments")) {
                            sendMsgToUIThread(COMMENT, 4, 13, jsonObject);   //to the user
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (remoteMessage.getNotification() != null) {
            String body = remoteMessage.getNotification().getBody();
            if (body != null) {
                Log.d(TAG, "FCM Notification body: " + body);
                String userType = MyApplication.getLoginUserType(getApplicationContext());

                if (!userType.isEmpty()) {
                   /* switch (userType) {
                        case "Patient":
                        case "user":

                            sendMsgToUIThread(LIKES, 3, 12, body);
                            break;

                        case "Doctor":
                        case "doctor":
                            sendMsgToUIThread(LIKES, 3, 12, body);
                            break;

                    }*/
                    sendMsgToUIThread(LIKES, 3, 12, body);
                }
            }
        }

    }


    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.


        Log.d(TAG, "Refreshed FCM device token: " + token);

        //saving token to shared preference
        SPreferenceWriter.getInstance(this).writeStringValue(SPreferenceKey.DEVICETOKEN,token);

    }

//  METHOD: TO MAKE DATA BUNDLE WHENEVER certification TYPE PUSH COMES
    private Bundle makeUserDocNotifyBundle(JSONObject jsonObject)
    {
        String type;
        String message = "";
        String image = "";
        String time = "";
        String flag = "";
        String unique_id="";
        String mszDuration ="";
        JSONObject batch_object ;
        String badge_notify = "";
        Bundle bundle = new Bundle();


        try {
            type = jsonObject.getString("type");
            badge_notify = jsonObject.getString("badge");

            String batch = jsonObject.getString("batch");
            batch_object = new JSONObject(batch);

            if (type.equalsIgnoreCase("chat")){
                message = batch_object.getString("msg");
                time = batch_object.getString("time");
                flag = batch_object.getString("flag");
                mszDuration = batch_object.getString("message_duration");

                bundle.putString("push_type","PUSH_CHAT");
                bundle.putString(ParamEnum.UNIQUE_ID_CHAT.theValue(),unique_id);
                bundle.putString(ParamEnum.FLAG_CHAT.theValue(),flag);
                bundle.putString(ParamEnum.MSZ_DURATION.theValue(),mszDuration);

            }
            else if (type.equalsIgnoreCase("follow")){
                message = batch_object.getString("msg");
                image = batch_object.getString("image");
                time = batch_object.getString("time");

//                bundle.putString(ParamEnum.PUSH_TYPE.theValue(),ParamEnum.FOLLOW_USER.theValue());


                bundle.putString("push_type","FOLLOW_USER");
            }
            else if (type.equalsIgnoreCase("likes")){
                message = batch_object.getString("msg");
                image = batch_object.getString("image");
                time = batch_object.getString("time");
                bundle.putString("push_type","LIKE");
            }
            else if (type.equalsIgnoreCase("comments")){
                message = batch_object.getString("msg");
                image = batch_object.getString("image");
                time = batch_object.getString("time");
                bundle.putString("push_type","COMMENT");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        bundle.putString(ParamEnum.MESSAGE.theValue(),message);
        bundle.putString(ParamEnum.USER_IMAGE.theValue(),image);
        bundle.putString(ParamEnum.TIME.theValue(),time);
        bundle.putString(ParamEnum.MSZ_DURATION.theValue(),mszDuration);
        bundle.putString(ParamEnum.PUSH_HOME_COUNT.theValue(),badge_notify);
        bundle.putSerializable(ParamEnum.FROM_WHERE.theValue(),ParamEnum.PUSH_CERTIFICATION);

        return bundle;

    }




    //    METHOD: TO CREATE NOTIFICATION WHENSOEVER FCM PUSH COMES
    private void sendNotification(Bundle bundle, ParamEnum forWhichAct) {

        Intent intent = null;
        if(forWhichAct!=null)
        {
            if(forWhichAct.equals(ParamEnum.HOME))
            {
                intent = new Intent(this, HomeActivity.class);
            }

        }


        if (intent != null) {
            intent.putExtras(bundle);

            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }


        int badge = SPreferenceWriter.getInstance(FCMMessagingService.this).getInt(SPreferenceKey.BADGE_COUNT);
        int badgeCount = badge + 1;     //new change
        SPreferenceWriter.getInstance(FCMMessagingService.this).writeIntValue(SPreferenceKey.BADGE_COUNT, badgeCount);
        ShortcutBadger.applyCount(this.getApplicationContext(), badgeCount); //for 1.1.4+
        String message = "";
        if (bundle != null) {
            message = bundle.getString(ParamEnum.MESSAGE.theValue());
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.noti)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(message)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setColor(ContextCompat.getColor(FCMMessagingService.this, R.color.colorPrimary))
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            String NOTIFICATION_CHANNEL_ID = "0";
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        assert notificationManager != null;
        notificationManager.notify(TAG,0 /* Request Code */, notificationBuilder.build());



//        notificationManager.notify(TAG ,1 /* notification_ID */, notificationBuilder.build());
    }


    private void sendPictureNotify(Bundle bundle, ParamEnum forWhichAct, String imgUrl) {
        Intent intent = null;
        if(forWhichAct!=null)
        {
            if(forWhichAct.equals(ParamEnum.HOME))
            {
                intent = new Intent(this, HomeActivity.class);
            }
        }

        if (intent != null) {
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }


        String message = "";
        if (bundle != null) {
            message = bundle.getString(ParamEnum.MESSAGE.theValue());
        }

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        int icon = R.drawable.noti;
        Bitmap normalLargeIcon = BitmapFactory.decodeResource(getResources(),R.drawable.noti);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(FCMMessagingService.this)
                        .setLargeIcon(normalLargeIcon)
                        .setSmallIcon(icon)
                        .setPriority(Notification.PRIORITY_DEFAULT)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setColor(ContextCompat.getColor(FCMMessagingService.this, R.color.colorPrimary))
                        .setSound(defaultSoundUri);

        //LARGE IMAGE STYLE
        Bitmap original = null;
        Bitmap decoded = null;
        try {
//            original = BitmapFactory.decodeStream(getAssets().open("one.png"));
            original = getBitmapFromUrl(imgUrl);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            original.compress(Bitmap.CompressFormat.PNG, 50, out);
            decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));

        } catch (Exception e) {
            e.printStackTrace();
        }


        NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle()
                .bigPicture(decoded)
                .bigLargeIcon(null);

        mBuilder.setStyle(s);

//        //LARGE TEXT STYLE
//        NotificationCompat.BigTextStyle s1 = new NotificationCompat.BigTextStyle()
//            .bigText("Check here full text : \"Summary text appears on expanding the notification\" " +
//                    "\"Summary text appears on expanding the notification\"");
//        mBuilder.setStyle(s1);

        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

    /*
     * To get a Bitmap image from the URL received
     * */
    public Bitmap getBitmapFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

}