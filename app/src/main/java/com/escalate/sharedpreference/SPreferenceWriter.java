package com.escalate.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.escalate.model.LoginResponse;
import com.escalate.model.SignUpResponce;
import com.escalate.model.SocialLoginResponce;
import com.escalate.model.ViewProfileResponce;


public class SPreferenceWriter {


    public void setUserDetails(LoginResponse loginResponse) {

        SPreferenceWriter.getInstance(context).writeBooleanValue(SPreferenceKey.LOGINKEY, true);
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.TOKEN, loginResponse.getData().getToken());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.USERID, loginResponse.getData().getUser_id());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.USERNAME, loginResponse.getData().getUsername());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.LOGINUSERNAME, loginResponse.getData().getUsername());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.NAME, loginResponse.getData().getFullname());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.PHONENUMBER, loginResponse.getData().getPhone());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.IMAGE, loginResponse.getData().getImage());

        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.EMAIL, loginResponse.getData().getEmail());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.BIO, loginResponse.getData().getBio());

    }

    public void setEditDetails(ViewProfileResponce loginResponse) {

        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.USERNAME, loginResponse.getData().getUsername());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.NAME, loginResponse.getData().getFullname());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.LOGINUSERNAME, loginResponse.getData().getUsername());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.PHONENUMBER, loginResponse.getData().getPhone());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.EMAIL, loginResponse.getData().getEmail());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.IMAGE, loginResponse.getData().getImage());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.BIO, loginResponse.getData().getBio());

        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.EMAIL, loginResponse.getData().getEmail());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.BIO, loginResponse.getData().getBio());

    }

    public void setSignUpDetails(SignUpResponce loginResponse) {

        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.TOKEN, loginResponse.getData().getToken());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.USERID, loginResponse.getData().getUser_id());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.USERNAME, loginResponse.getData().getUsername());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.LOGINUSERNAME, loginResponse.getData().getUsername());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.NAME, loginResponse.getData().getFullname());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.PHONENUMBER, loginResponse.getData().getPhone());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.IMAGE, loginResponse.getData().getImage());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.EMAIL, loginResponse.getData().getEmail());

        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.EMAIL, loginResponse.getData().getEmail());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.BIO, loginResponse.getData().getBio());


    }

      public void setFbUserDetails(SocialLoginResponce loginResponse) {

/*
          {
              "status": "SUCCESS",
                  "data": {
              "fullname": "Mobulous Apps",
                      "phone": "",
                      "country_code": "91",
                      "username": "",
                      "image": "https:\/\/mobulous.app\/escalate\/public\/users-photos\/6b0d557065a654e.jpg",
                      "is_admin": "0",
                      "email": "testmobulous12123@gmail.com",
                      "bio": "",
                      "bio_duration": "00:00",
                      "topic_id": "",
                      "autoplay": "off",
                      "lat": "0",
                      "log": "0",
                      "admin_status": "1",
                      "created_at": "2018-08-23 10:30:55",
                      "updated_at": "2018-08-23 10:30:55",
                      "user_id": "2",
                      "token": "AiczzwNslyAE0qLt4kw4Z9Yqr",
                      "status": "1"
          },
              "message": "User login successfully",
                  "requestKey": "api\/social_login"
          }*/

        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.TOKEN, loginResponse.getData().getToken());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.USERID, loginResponse.getData().getUser_id());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.USERNAME, loginResponse.getData().getUsername());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.NAME, loginResponse.getData().getFullname());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.PHONENUMBER, loginResponse.getData().getPhone());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.IMAGE, loginResponse.getData().getImage());
        SPreferenceWriter.getInstance(context).writeStringValue(SPreferenceKey.EMAIL, loginResponse.getData().getEmail());

    }




    private static Context context = null;
    private static SPreferenceWriter sharePref = null;
    private static SharedPreferences mPrefs;
    private static SharedPreferences.Editor prefEditor = null;

    public static synchronized SPreferenceWriter getInstance(Context c) {

        if (sharePref == null) {

            if (context == null)
                context = c;

            sharePref = new SPreferenceWriter();
            mPrefs = context.getSharedPreferences("EscalatePreferences", Context.MODE_PRIVATE);
            prefEditor = mPrefs.edit();

//            prefEditor.putString(SPreferenceKey.START, "ok");
//            prefEditor.apply();

            return sharePref;
        } else {
            return sharePref;
        }

    }


    private SPreferenceWriter() {
    }

    public void writeStringValue(String key, String value) {

        prefEditor.putString(key, value);
        prefEditor.commit();
    }

    public void writeIntValue(String key, int value) {

        prefEditor.putInt(key, value);
        prefEditor.commit();
    }


    public void writeBooleanValue(String key, boolean value) {

        prefEditor.putBoolean(key, value);
        prefEditor.commit();
    }

    public void writeLongValue(String key, long value) {

        prefEditor.putLong(key, value);
        prefEditor.commit();
    }

    public void clearPreferenceValue(String key, String value) {

        prefEditor.putString(key, "");
        prefEditor.commit();

    }

    public String getString(String key) {

        return mPrefs.getString(key, "");

    }

    public int getInt(String key) {

        return mPrefs.getInt(key, 0);
    }

    public boolean getBoolean(String key) {

        return mPrefs.getBoolean(key, false);
    }

    public long getLong(String key) {

        return mPrefs.getLong(key, (long) 0.0);
    }

    public void clearPreferenceValues(String key) {
        prefEditor.clear();
        prefEditor.commit();

    }

//	public void clearPreferenceValues(String key) {
//
//		prefEditor.remove(SPreferenceKey.LOGINKEY);
//		prefEditor.remove(SPreferenceKey.LOGINVIA);
//		prefEditor.remove(SPreferenceKey.FBID);
//		prefEditor.remove(SPreferenceKey.facebook_key);
//	    prefEditor.commit();
//
//	}
}

