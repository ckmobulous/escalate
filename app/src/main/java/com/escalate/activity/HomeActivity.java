package com.escalate.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import com.escalate.R;
import com.escalate.databinding.ActivityHomeBinding;
import com.escalate.fragments.ChatFragment;
import com.escalate.fragments.HomeFragment;
import com.escalate.fragments.PostFragment;
import com.escalate.fragments.SearchFragment;
import com.escalate.fragments.UserProfileFragment;
import java.lang.reflect.Field;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private Fragment fragment;
    private android.support.v4.app.FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private PostFragment postFragment;
    private ChatFragment chatFragment;
    private UserProfileFragment userProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        //INITIALIZATION
        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        postFragment = new PostFragment();
        chatFragment = new ChatFragment();
        userProfileFragment = new UserProfileFragment();

        init();
        doThePushStuff();
    }

    private void doThePushStuff() {

        //FOR PUSH NOTIFY
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                String push_type = bundle.getString("push_type");
                if (push_type != null) {
                    if ("FOLLOW_USER".equalsIgnoreCase(push_type)) {

                        //FOR PUSH NOTIFY
                        Intent intent = new Intent(this, NotificationActivity.class);
                        startActivity(intent);

                    }   // FOR CHAT PUSH NOTIFY
                    else if ("PUSH_CHAT".equalsIgnoreCase(push_type)) {

                        fragment = chatFragment;
                        loadFragment();
                    }//
                    else if ("LIKE".equalsIgnoreCase(push_type)) {

                        Intent intent = new Intent(this, NotificationActivity.class);
                        startActivity(intent);
                    } else if ("COMMENT".equalsIgnoreCase(push_type)) {

                        Intent intent = new Intent(this, NotificationActivity.class);
                        startActivity(intent);
                    }
                }
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);

        doThePushStuff();   // onNewIntent

    }

    private void init() {

        fragmentManager = getSupportFragmentManager();
        fragment = homeFragment;

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.fragment_frame, fragment);
        ft.commit();

        setSupportActionBar(binding.toolbarHome);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        /*TextView textView = (TextView) navigation.findViewById(R.id.menu_item_home).findViewById(R.id.largeLabel);
        textView.setTextSize(8);*/
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.removeShiftMode(navigation);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            resetUserFragThreads(fragment);

            switch (item.getItemId()) {

                case R.id.navigation_home:
                    // item.setIcon(getApplicationContext().getDrawable(R.drawable.home_s));
                    fragment = homeFragment;
                    loadFragment();
                    return true;

                case R.id.navigation_trending:
                    fragment = searchFragment;
                    loadFragment();
                    return true;

                case R.id.navigation_camera:
                    fragment = postFragment;
                    loadFragment();

//                    Fragment f = fragmentManager.findFragmentById(R.id.fragment_frame);
//                    if(f instanceof PostFragment)
//                    {
//                        postFragment.postStuff(HomeActivity.this);
//                    }
//                    else {
//                        loadFragment();
//                        postFragment.postStuff(HomeActivity.this);
//                    }
                    return true;

                case R.id.navigation_notifications:
                    fragment = chatFragment;
                    loadFragment();
                    return true;

                case R.id.navigation_more:
                    fragment = userProfileFragment;
                    loadFragment();
                    return true;

            }
            return false;
        }
    };

    public void loadFragment() {

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void resetUserFragThreads(Fragment f) {
        if (f != null) {
            if (f instanceof UserProfileFragment) {
                ((UserProfileFragment) f).resetUserAllThreadsPlayers();
            } else if (f instanceof HomeFragment) {
                ((HomeFragment) f).resetUserAllThreadsPlayers();
            }
           /* else if (f instanceof RecentFrag){
                ((RecentFrag) f).resetUserAllThreadsPlayers();
            }*/

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        resetUserFragThreads(fragment);
    }

    /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_cart_option, menu);

        menu.findItem(R.id.action_notify).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
               *//* Intent intent=new Intent(HomeActivity.this, SearchTabActivity.class);

                startActivity(intent);*//*
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

           *//* if (id == R.id.action_search) {

            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        }*//*
        return super.onOptionsItemSelected(item);
    }*/

    static class BottomNavigationViewHelper {

        @SuppressLint("RestrictedApi")
        static void removeShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    item.setFocusableInTouchMode(false);
                    item.setFocusable(false);
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
            } catch (IllegalAccessException e) {
                Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
            }
        }
    }


    public void resetToHome() {

        try {
            fragment = homeFragment;

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_frame, fragment).commitAllowingStateLoss();

            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setSelectedItemId(R.id.navigation_home);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Fragment f = fragmentManager.findFragmentById(R.id.fragment_frame);
        if (f != null) {
            if (f instanceof HomeFragment) {
                super.onBackPressed();
            } else if (f instanceof PostFragment
                    || f instanceof SearchFragment
                    || f instanceof ChatFragment
                    || f instanceof UserProfileFragment) {

                resetToHome();
            }
        } else {
            super.onBackPressed();
        }
    }
}
