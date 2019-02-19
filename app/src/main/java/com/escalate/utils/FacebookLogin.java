package com.escalate.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.escalate.BuildConfig;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import org.json.JSONObject;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by mobulous51 on 16/3/17.
 */

public class FacebookLogin extends AppCompatActivity {

    private static final String TAG = FacebookLogin.class.getSimpleName();
    CallbackManager callbackManager;
    AccessToken accessToken;
    private String email;
    private String username = "";
    private String name;
    private String socialid;
    private String image;
    private String f_name;
    private String l_name;
    private String id;
    private String gender;
    private List<String> values = new CopyOnWriteArrayList<>();
    LoginButton mLoginButton;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        //AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        accessToken = AccessToken.getCurrentAccessToken();
        //AccessToken.getCurrentAccessToken().getPermissions();
       /* mLoginButton = findViewById(R.id.fb_login_button);
        mLoginButton.setReadPermissions("email", "public_profile");*/
        fbSignIn();
    }

    private void printKeyHash()
    {  try {
        PackageInfo info = getPackageManager().getPackageInfo("com.escalate", PackageManager.GET_SIGNATURES);
        for (Signature signature : info.signatures) {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(signature.toByteArray());
            Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    //fb signin
    private void fbSignIn() {

        printKeyHash();

        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email")); /*  "user_friends",*/
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult result) {
                accessToken = AccessToken.getCurrentAccessToken();
                Log.e("....Facebook onSuccess.", "onSuccess: ");

                String accessTokenStr = result.getAccessToken().getToken();
                Log.e(TAG, " accessTokenStr : "+accessTokenStr);
                Log.e(TAG, " accessToken : "+accessToken.getToken());
                Log.e(TAG, " accessToken getApplicationId: "+accessToken.getApplicationId());
                Log.e(TAG, " accessToken getUserId: "+accessToken.getUserId());

                getUserDetail(result);

                handleFacebookAccessToken(accessToken);


                //Toast.makeText(FacebookLogin.this, "" + accessToken+"Login Success", Toast.LENGTH_SHORT).show();
                /*Intent intent=new Intent(FacebookLogin.this, HomeActivity.class);
                intent.putExtra("criteria_list_key","selectClimate");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                Toast.makeText(FacebookLogin.this, "" + accessToken+"Login Success", Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(FacebookLogin.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Facebook Error----->", error.toString());

            }

            @Override
            public void onCancel() {
                Toast.makeText(FacebookLogin.this, "Request Cancel", Toast.LENGTH_SHORT).show();
                Log.e("facebook onCancel----->", "cancel");
                Intent intent = new Intent();
                intent = null;
                setResult(121, intent);
                finish();
            }
        });

//        if (SPreferenceWriter.getInstance(FacebookLogin.this).getBoolean(SharedPreferenceKey.SAVE_SESSION,false)) {
//            LoginManager.getInstance().logOut();
//        }

        return;

    }

    private void getUserDetail(LoginResult loginResult) {

        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            Log.e("JSON------>>>", object.toString());

                            if (response.getError() != null) {
                                // handle error

                                Log.e("ERORRS.....", "onCompleted: ");
                            } else {
                                id = object.getString("id");
                                socialid = object.getString("id");
                                if (object.has("email")) {
                                    email = object.getString("email");
                                    int index = email.indexOf('@');
                                    username = email.substring(0, index);
                                } else {
                                    username = socialid;
                                }
                                //gender=object.optString("gender");
                                name = object.getString("name");
                                f_name = object.getString("first_name");
                                l_name = object.getString("last_name");

                                image = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                image = "https://graph.facebook.com/" + socialid + "/picture?type=large";

                                Log.e("fbbb", "" + image);
                                if (image == null) {
                                    image = "";
                                }

                                Log.e("......", "onCompleted: " + image);
                                Intent intent = new Intent();
                                intent.putExtra("id", id);
                                intent.putExtra("email", email);
                                intent.putExtra("username", username);
                                intent.putExtra("name", name);
                                intent.putExtra("f_name", f_name);
                                intent.putExtra("l_name", l_name);
                                intent.putExtra("socialid", socialid);
                                intent.putExtra("image", image);
                                intent.putExtra("gender", gender);

                                Log.e(TAG,"\nname------>>>"+ name);
                                Log.e(TAG,"email------>>>"+ email);
                                Log.e(TAG,"uid------>>>"+ id);
                                setResult(121, intent);


                                finish();

                            }
                            // Log.e("......", "onCompleted: "+image );
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("", "Error");
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,birthday,first_name,last_name,gender,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.w(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.w(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();


                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(FacebookLogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }


                    }
                });
    }

    private void updateUI(Object o) {
        if(o!=null)
        { FirebaseUser user = (FirebaseUser) o;
            String uid = user.getUid();
            String pid = user.getProviderId();
            String name = user.getDisplayName();
            String email = user.getEmail();

            Log.e(TAG,"name------>>>"+ name);
            Log.e(TAG,"email------>>>"+ email);
            Log.w(TAG,"uid------>>>"+ uid);
            Log.w(TAG,"pid------>>>"+ pid);
        }

    }


    //fb sign in finish
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
