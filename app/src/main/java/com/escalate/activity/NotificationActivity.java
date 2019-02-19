package com.escalate.activity;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.escalate.R;
import com.escalate.adapter.NotificationAdapter;
import com.escalate.databinding.ActivityNotificationBinding;
import com.escalate.model.NotificationModel;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.escalate.utils.ParamEnum;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import me.leolin.shortcutbadger.ShortcutBadger;
import retrofit2.Call;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityNotificationBinding binding;
    private List<NotificationModel> mNotificationModel = new ArrayList<>();
    private NotificationAdapter adapter;
    private List<NotificationModel.DataBean> notificationList;
    public static boolean isNotifyOnForeground = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        binding.iVBackNoti.setOnClickListener(this);

        notificationListService();
        ShortcutBadger.removeCount(NotificationActivity.this);

        setRefersh();
    }


    @OnClick({R.id.iVBackNoti})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iVBackNoti:
                onBackPressed();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        isNotifyOnForeground = true;

//      REGISTER LocalBroadcastManager TO HANDLE PUSH
        LocalBroadcastManager.getInstance(NotificationActivity.this).registerReceiver(pushBookingReceiver,
                new IntentFilter(ParamEnum.PUSH_BOOKING.theValue()));


        NotificationManager notificationManager =
                (NotificationManager) NotificationActivity.this.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancelAll();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        isNotifyOnForeground = false;

        LocalBroadcastManager.getInstance(NotificationActivity.this).unregisterReceiver(pushBookingReceiver);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isNotifyOnForeground = false;
    }

    //  BROADCAST RECEIVER CLASS Object : TO HANDLE PUSH
    private BroadcastReceiver pushBookingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                notificationListService();    //SERVICE HIT..
            }
        }
    };


    public void setRefersh() {
        binding.swipeRefreshNotification.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefreshNotification.setRefreshing(false);
                notificationListService();  //in OnCreateView
            }
        });
    }

    public void notificationListService() {
        try {
            MyDialog.getInstance(NotificationActivity.this).showDialog(NotificationActivity.this);

            String userid = SPreferenceWriter.getInstance(NotificationActivity.this).getString(SPreferenceKey.USERID);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<NotificationModel> call = apiInterface.requestnotifylist(userid);

            call.enqueue(new retrofit2.Callback<NotificationModel>() {
                @Override
                public void onResponse(@NonNull Call<NotificationModel> call, Response<NotificationModel> response) {
                    MyDialog.getInstance(NotificationActivity.this).hideDialog();

                    if (response.isSuccessful()) {
                        NotificationModel replyPostList = response.body();
                        Log.e("response", "" + replyPostList.toString());
                        if (replyPostList.getStatus().equalsIgnoreCase("SUCCESS")) {
                            notificationList = replyPostList.getData();
                            recyclerView(notificationList);
                        } else {
                            // Toast.makeText(this, "" + replyPostList.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //  Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<NotificationModel> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(NotificationActivity.this).hideDialog();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void recyclerView(List<NotificationModel.DataBean> notificationList) {

        if (adapter == null) {

            if (notificationList != null && notificationList.size() > 0) {
                binding.recViewNotification.setVisibility(View.VISIBLE);
                binding.noDataTxtNoti.setVisibility(View.GONE);
            } else {
                binding.recViewNotification.setVisibility(View.GONE);
                binding.noDataTxtNoti.setVisibility(View.VISIBLE);
            }
            adapter = new NotificationAdapter(notificationList, NotificationActivity.this);

        }

        binding.recViewNotification.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
        binding.recViewNotification.setAdapter(adapter);

    }

}
