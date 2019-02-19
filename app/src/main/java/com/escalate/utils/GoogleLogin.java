package com.escalate.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * Created by mobulous1 on 5/9/16.
 */
public class GoogleLogin extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LoginModel";
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    private String image;
    private String gender;
    private String socialid;
    private String email;
    private String name;
    private String f_name;
    private String l_name;
    private Intent signInIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        gmailSignIn();
    }

    //    gmail sigin
    private void gmailSignIn() {
        signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
        } else {
              // showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                   // hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

//    private void showProgressDialog() {
//        ProgressDialogUtils.showProgressDialog(this, "Please Wait...", false);
//    }
//
//    private void hideProgressDialog() {
//        ProgressDialogUtils.dismissProgressDialog();
//    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.i("user name", "" + acct.getDisplayName());
            Uri personPhoto = acct.getPhotoUrl();
            if (personPhoto != null) {
                image = personPhoto.toString();
            } else {
                image = "";
            }
            gender = "Male";
            socialid = acct.getId();
            email = acct.getEmail();
            name = acct.getDisplayName();
            String[] u_name = null;
            if (name != null && name.contains(" ")) {
                u_name = name.split(" ");
                if (u_name.length > 1) {
                    f_name = u_name[0];
                    l_name = u_name[1];
                } else {
                    f_name = u_name[0];
                    l_name = u_name[0];
                }


            }


            signOut();
            Log.i("Gmail LoginModel", "" + name + "" + email + "" + socialid + "" + image);
        } else if (result.getStatus().getStatusCode() == 12501) {

            Toast.makeText(this, "Request Cancel", Toast.LENGTH_SHORT).show();
           // SnackBar.getSnackBar().showBar(GoogleLogin.this, "Request Cancel", Toast.LENGTH_SHORT);
            Intent intent = new Intent();
            intent = null;
            setResult(122, intent);
            finish();

        } else {

        }
    }

    private void signOut() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (mGoogleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            if (status.getStatus().getStatusCode() == 0) {
                                Intent intent = new Intent();
                                intent.putExtra("socialid", socialid);
                                intent.putExtra("email", email);
                                intent.putExtra("name", name);
                                intent.putExtra("f_name", f_name);
                                intent.putExtra("l_name", l_name);
                                intent.putExtra("image", image);
                                intent.putExtra("gender", gender);
                                setResult(122, intent);
                                finish();
                            }
                        }
                    });
                } else {
                    signOut();
                }
            }
        });
    }
    //gmail signin finish

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
//        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
