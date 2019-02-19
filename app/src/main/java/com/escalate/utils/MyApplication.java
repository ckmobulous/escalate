package com.escalate.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.escalate.R;
import com.escalate.fontbind.FontCache;
import com.escalate.fontbind.TypefaceUtil;
import com.escalate.model.RecentSBean;
import com.escalate.model.TopSearchResponce;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.escalate.utils.AppConstants.kDefaultAppName;


/**
 * Created by Rajeev Kr. Sharma [rajeevrocker7@gmail.com] on 30/8/18.
 */
public class MyApplication extends MultiDexApplication {



    /********* How to make the perfect Singleton?: ***********/
//    Link:  https://medium.com/exploring-code/how-to-make-the-perfect-singleton-de6b951dfdb0

    private static volatile MyApplication myApplication = null;
    //    private final String TAG = MyApplication.class.getSimpleName();
    private static Context context = null;

    public static List<Object> list = new ArrayList<>();

    // This flag should be set to true to enable VectorDrawable support for API < 21
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    //private constructor.
//    private MyApplication() {

//        //Prevent form the reflection api.
//        if (myApplication != null){
//            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
//        }
//    }

    public static MyApplication getInstance() {

        if (myApplication == null) {     //Check for the first time

            synchronized (MyApplication.class) {    //Check for the second time.
                if (myApplication == null)  // if there is no instance available... create new one
                    myApplication = new MyApplication();
            }
        }

        return myApplication;

    }

    //Make singleton from serialize and deserialize operation.
    protected MyApplication readResolve() {
        return getInstance();
    }

    public static synchronized Context getContext() {
        if (context == null) {
            return myApplication.getApplicationContext();
        } else
            return context;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this;
        context = this.getApplicationContext();

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF",
                "fonts/segoe_regular.ttf");

        FontCache.getInstance().addFont("bold", "fonts/segoe_bold.ttf");
        FontCache.getInstance().addFont("bold_italic", "fonts/segoe_bold_italic.ttf");

        FontCache.getInstance().addFont("regular", "fonts/segoe_regular.ttf");
        FontCache.getInstance().addFont("regular_condense", "fonts/segoe_regular_condense.ttf");

        FontCache.getInstance().addFont("italic", "fonts/segoe_italic.ttf");
        FontCache.getInstance().addFont("italic_condense", "fonts/segoe_italic_condense.ttf");

        FontCache.getInstance().addFont("light", "fonts/segoe_light.ttf");
        FontCache.getInstance().addFont("light_italic", "fonts/segoe_light_italic.ttf");

        FontCache.getInstance().addFont("semi_light", "fonts/segoe_semi_light.ttf");
        FontCache.getInstance().addFont("semi_light_italic", "fonts/segoe_semi_light_italic.ttf");

        FontCache.getInstance().addFont("semibold", "fonts/segoe_semibold.ttf");
        FontCache.getInstance().addFont("semibold_italic", "fonts/segoe_semibold_italic.ttf");

    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    /********* HELPER METHODS: ***********/


    public static void addSearchDataBean(TopSearchResponce.DataBean bean) {
        list.add(bean);
    }

   public static void addRecentSBean(RecentSBean bean) {
        list.add(bean);
    }

    public static List<Object> getList() {
        return list;
    }

    public static void setList(List<Object> list) {
        MyApplication.list = list;
    }

    //  METHOD: TO SHOW FAILURE MESSAGES ,Used IN RETROFIT SERVICE CALL onFailure() Method
    public static void showOnFailureMessages(Context context, Throwable t) {
        if (context != null) {
            if (t instanceof UnknownHostException)
                Toast.makeText(context, "Internet not available", Toast.LENGTH_SHORT).show();
            else if (t instanceof SocketTimeoutException)
                Toast.makeText(context, "Internet is slow! Try again", Toast.LENGTH_SHORT).show();
            else if (t instanceof ConnectException)
                Toast.makeText(context, "Failed to connect to " + kDefaultAppName + " Server!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }

    }

    //  METHOD: TO HIDE SOFT INPUT KEYBOARD
    public static void hideSoftKeyBoard(@NonNull Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null)
            if (imm != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }

    }

    //  METHOD: TO SHOW SOFT INPUT KEYBOARD
    public static void openSoftKeyBoard(@NonNull Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null)
            if (inputMethodManager != null) {
                inputMethodManager.toggleSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.SHOW_FORCED, 0);
            }

    }


    public static void replaceFrag(AppCompatActivity compatActivity, Fragment frag, String nameTag, Bundle bundle) {

        if (compatActivity != null) {
            if (bundle != null)
                frag.setArguments(bundle);
            else
                frag.setArguments(null);

            FragmentTransaction fragmentTransaction = compatActivity.getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction
                    .replace(R.id.fragment_frame, frag, nameTag)
//                    .addToBackStack(nameTag)
                    .commitAllowingStateLoss();

        }
    }

    public static void replaceFragWithBackStack(AppCompatActivity compatActivity, Fragment frag, String nameTag, Bundle bundle) {

        if (compatActivity != null) {
            if (bundle != null)
                frag.setArguments(bundle);
            else
                frag.setArguments(null);

            FragmentTransaction fragmentTransaction = compatActivity.getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction
                    .replace(R.id.fragment_frame, frag, nameTag)
                    .addToBackStack(nameTag)
                    .commitAllowingStateLoss();

        }
    }


    public static void replaceFragWithFadeAnim(AppCompatActivity compatActivity, Fragment frag, String nameTag, Bundle bundle) {

        if (compatActivity != null) {
            if (bundle != null)
                frag.setArguments(bundle);
            else
                frag.setArguments(null);

            FragmentTransaction fragmentTransaction = compatActivity.getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.fragment_frame, frag, nameTag)
                    .addToBackStack(nameTag)
                    .commitAllowingStateLoss();

        }
    }

    public static String getLoginUserType(Context context) {
        String userType;
        String login_user_type_ = SPreferenceWriter.getInstance(context.getApplicationContext())
                .getString(SPreferenceKey.USER_TYPE);
        switch (login_user_type_) {
            case "None":
                userType = "";
                break;

            case "Patient":
                userType = "user";
                break;

            case "Doctor":
                userType = "doctor";
                break;

            default:
                userType = "";
        }
        return userType;
    }

    public static String getReverseLoginUserTypeForNotification(Context context) {
        String userType;
        String login_user_type_ = SPreferenceWriter.getInstance(context.getApplicationContext())
                .getString(SPreferenceKey.USER_TYPE);
        switch (login_user_type_) {
            case "None":
                userType = "";
                break;

            case "Patient":
                userType = "doctor";
                break;

            case "Doctor":
                userType = "user";
                break;

            default:
                userType = "";
        }
        return userType;
    }

//    public Animation animateArrow(boolean up) {
//
//        /*
//        //global:
//            boolean up = true;
//         binding.imgLock.setImageResource(R.drawable.draw_vector_arrow_up);
//
//                if (!up) {
//                    up = true;
//                    binding.imgLock.startAnimation(animate(true));
//                } else {
//                    up = false;
//                    binding.imgLock.startAnimation(animate(false));
//                }
//        * */
//
//        Animation anim = AnimationUtils.loadAnimation(this, up ? R.anim.anim_rotate_down : R.anim.anim_rotate_right);
//        anim.setInterpolator(new LinearInterpolator()); // for smooth animation
//        return anim;
//    }
//
//    public Animation animateLToRBounce(boolean up) {
//
//
//        Animation anim = AnimationUtils.loadAnimation(this, up ? R.anim.anim_left_to_right_bounce : R.anim.anim_textv_blink);
//        anim.setInterpolator(new LinearInterpolator()); // for smooth animation
//        return anim;
//    }
//
//    public Animation animateVibrateBlink(boolean isVibrateShake) {
//
//        Animation anim = AnimationUtils.loadAnimation(this, isVibrateShake ? R.anim.anim_shake : R.anim.anim_textv_blink);
//        anim.setInterpolator(new LinearInterpolator()); // for smooth animation
//        return anim;
//    }

    public void rotateImage360(ImageView imageView) {

        RotateAnimation rotate = new RotateAnimation(0,
                360,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        rotate.setDuration(800);
        rotate.setInterpolator(new LinearInterpolator());

        imageView.startAnimation(rotate);
    }


    @NonNull
    public static String removeAllSpacesInBwFromMob(String str, String TAG) {
        str = str.replace(" ", "");

        String builder = str.trim();

        Log.w(TAG, "removeAllSpacesInBwFrom Mob Number:->" + builder + "<-\n");

        return builder.trim();
    }


    public static void makeASnack(View view, String message) {
        if (view != null)
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void makeASuccessSnack(View view, String message) {
        if (view != null) {
            Snackbar snackbar;
            snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(ContextCompat.getColor(MyApplication.getContext(), R.color.colorPrimaryDark));
            TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }
    }

    public static void makeAFailureSnack(View view, String message) {
        if (view != null) {
            Snackbar snackbar;
            snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(ContextCompat.getColor(MyApplication.getContext(),
                    R.color.failure_red));
            TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void makeATopSnack(ViewGroup viewGroup, String message) {
        if (viewGroup != null) {
            Snackbar snackbar;
            snackbar = Snackbar.make(viewGroup, message, Snackbar.LENGTH_SHORT);
            View snackBarView = snackbar.getView();
//            snackBarView.setBackgroundColor(ContextCompat.getColor(MyApplication.getContext(),
//                    R.color.white));
//
            snackBarView.setBackgroundColor(Color.WHITE);
            TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.BLACK);

////////////////////////////////////
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackBarView.getLayoutParams();
            params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
            // calculate actionbar height
            TypedValue tv = new TypedValue();
            int actionBarHeight = 0;
            if (getInstance().getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
                actionBarHeight =
                        TypedValue.complexToDimensionPixelSize
                                (tv.data, getInstance().getResources().getDisplayMetrics());
            }
            // set margin
            params.setMargins(0, actionBarHeight, 0, 0);
            snackBarView.setLayoutParams(params);
/////////////////////////////

            snackbar.show();
        }
    }

    public static void makeATopSnackInFrag(ViewGroup viewGroup, String message, int status_bar_height) {
        if (viewGroup != null) {
            Snackbar snackbar;
            snackbar = Snackbar.make(viewGroup, message, Snackbar.LENGTH_SHORT);
            View snackBarView = snackbar.getView();
//            snackBarView.setBackgroundColor(ContextCompat.getColor(MyApplication.getContext(),
//                    R.color.white));
//
            snackBarView.setBackgroundColor(Color.WHITE);
            TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.BLACK);

////////////////////////////////////
            if (snackBarView.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackBarView.getLayoutParams();
                params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                // calculate actionbar height
                TypedValue tv = new TypedValue();
                int actionBarHeight = 0;
                if (getInstance().getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
                    actionBarHeight =
                            TypedValue.complexToDimensionPixelSize
                                    (tv.data, getInstance().getResources().getDisplayMetrics());
                }

                int top = actionBarHeight + status_bar_height;
                // set margin
                params.setMargins(0, top, 0, 0);
                snackBarView.setLayoutParams(params);
            } else {

                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackBarView.getLayoutParams();
                params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                // calculate actionbar height
                TypedValue tv = new TypedValue();
                int actionBarHeight = 0;
                if (getInstance().getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
                    actionBarHeight =
                            TypedValue.complexToDimensionPixelSize
                                    (tv.data, getInstance().getResources().getDisplayMetrics());
                }
                // set margin
                params.setMargins(0, actionBarHeight, 0, 0);
                snackBarView.setLayoutParams(params);
            }

/////////////////////////////

            snackbar.show();
        }
    }

    public static void makeCenterToast(String message) {
        //Creating the Toast object
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void makeBottomToast(String message) {
        //Creating the Toast object
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 180);
        toast.show();
    }


    public static List<ApplicationInfo> getAppInfo() {
        PackageManager packageManager = MyApplication.getInstance().getPackageManager();
        return packageManager.getInstalledApplications(0);
    }

    /**
     * Get the app name if available under android:label tag in Manifest file. if not specified then return kDefaultAppName
     */
    public static String getAppName() {

        String appName = kDefaultAppName;

        try {
            ApplicationInfo applicationInfo = MyApplication.getInstance().getApplicationInfo();
            int stringId = applicationInfo.labelRes;
            appName = (stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : MyApplication.getInstance().getString(stringId));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            appName = kDefaultAppName;

        }

        return appName;
    }

}
