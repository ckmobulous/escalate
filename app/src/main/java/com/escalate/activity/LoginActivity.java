package com.escalate.activity;

import android.Manifest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.escalate.R;
import com.escalate.databinding.ActivityLoginBinding;
import com.escalate.fragments.LogInFragment;
import com.escalate.fragments.SignUpFragment;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.google.firebase.iid.FirebaseInstanceId;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private android.support.v4.app.FragmentManager fragmentManager;
    private static Bundle savedInstance;
    private LogInFragment logInFragment;
    private SignUpFragment signUpFragment;
    private ViewPagerAdapter adapter;
   // private int selectedTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        getDeviceToken();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        /*InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);*/
         savedInstance = savedInstanceState;
        fragmentManager = getSupportFragmentManager();
        logInFragment = new LogInFragment();
        signUpFragment = new SignUpFragment();
        binding.viewpagerLogin.setOffscreenPageLimit(2);
        setupViewPager(binding.viewpagerLogin);
        binding.tabsLogin.setupWithViewPager(binding.viewpagerLogin);
        tabLayout();
        gethashKey();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (/*ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED
                    && */ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED

                    ) {

                checkPermission();
            }
        }
    }

    //    METHOD: TO GET DEVICE_TOKEN FROM FCM
    private void getDeviceToken() {

        final String TAG = SplashActivity.class.getSimpleName();
        Runnable runnableObj = () -> {

            SPreferenceWriter mPreference = SPreferenceWriter.getInstance(LoginActivity.this.getApplicationContext());
            mPreference.writeStringValue(SPreferenceKey.DEVICETOKEN, "");

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


        };

        Thread thread = new Thread(runnableObj);
        thread.start();

        // [START crashlytics_log_event]
//        Crashlytics.log("Splash Activity created");

    }


    private void tabLayout() {

        binding.tabsLogin.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //selectedTab = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.viewpagerLogin.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Log.w("onPageSelected", "position" + position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {

        adapter = new ViewPagerAdapter(fragmentManager);
        adapter.addFragment(logInFragment, getResources().getString(R.string.sign_in));
        adapter.addFragment(signUpFragment, getResources().getString(R.string.sign_up));

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private boolean checkPermission() {

        String[] permission = new String[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            permission = new String[]{
                    Manifest.permission.READ_SMS,
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
        }
        int permissionCode = 23;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    ) {
            } else {
                requestPermissions(permission, permissionCode);
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 23: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("PermissionGranted", ": " + grantResults[0]);
                } else {
                    Log.e("PermissionGranted", "else: " + grantResults[0]);
                    checkPermission();
                }
            }
        }
    }

    private void gethashKey() {

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.fametale", PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sign = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.e("MY KEY HASH:", sign);
                //  Toast.makeText(getApplicationContext(),sign,     Toast.LENGTH_LONG).show();
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
