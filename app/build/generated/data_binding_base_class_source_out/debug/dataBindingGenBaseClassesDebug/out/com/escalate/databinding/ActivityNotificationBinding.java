package com.escalate.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class ActivityNotificationBinding extends ViewDataBinding {
  @NonNull
  public final ImageView iVBackNoti;

  @NonNull
  public final TextView noDataTxtNoti;

  @NonNull
  public final RecyclerView recViewNotification;

  @NonNull
  public final SwipeRefreshLayout swipeRefreshNotification;

  @NonNull
  public final TextView toolbarTitle;

  @NonNull
  public final RelativeLayout topLay;

  protected ActivityNotificationBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, ImageView iVBackNoti, TextView noDataTxtNoti,
      RecyclerView recViewNotification, SwipeRefreshLayout swipeRefreshNotification,
      TextView toolbarTitle, RelativeLayout topLay) {
    super(_bindingComponent, _root, _localFieldCount);
    this.iVBackNoti = iVBackNoti;
    this.noDataTxtNoti = noDataTxtNoti;
    this.recViewNotification = recViewNotification;
    this.swipeRefreshNotification = swipeRefreshNotification;
    this.toolbarTitle = toolbarTitle;
    this.topLay = topLay;
  }

  @NonNull
  public static ActivityNotificationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityNotificationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityNotificationBinding>inflate(inflater, com.escalate.R.layout.activity_notification, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityNotificationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityNotificationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityNotificationBinding>inflate(inflater, com.escalate.R.layout.activity_notification, null, false, component);
  }

  public static ActivityNotificationBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityNotificationBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityNotificationBinding)bind(component, view, com.escalate.R.layout.activity_notification);
  }
}
