package com.escalate.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.makeramen.roundedimageview.RoundedImageView;

public abstract class RecyclerNotificationAdapterBinding extends ViewDataBinding {
  @NonNull
  public final CardView cardView;

  @NonNull
  public final RoundedImageView imgUser;

  @NonNull
  public final LinearLayout linearViewOther;

  @NonNull
  public final TextView tvNotification;

  @NonNull
  public final TextView tvTime;

  protected RecyclerNotificationAdapterBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, CardView cardView, RoundedImageView imgUser,
      LinearLayout linearViewOther, TextView tvNotification, TextView tvTime) {
    super(_bindingComponent, _root, _localFieldCount);
    this.cardView = cardView;
    this.imgUser = imgUser;
    this.linearViewOther = linearViewOther;
    this.tvNotification = tvNotification;
    this.tvTime = tvTime;
  }

  @NonNull
  public static RecyclerNotificationAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static RecyclerNotificationAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<RecyclerNotificationAdapterBinding>inflate(inflater, com.escalate.R.layout.recycler_notification_adapter, root, attachToRoot, component);
  }

  @NonNull
  public static RecyclerNotificationAdapterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static RecyclerNotificationAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<RecyclerNotificationAdapterBinding>inflate(inflater, com.escalate.R.layout.recycler_notification_adapter, null, false, component);
  }

  public static RecyclerNotificationAdapterBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static RecyclerNotificationAdapterBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (RecyclerNotificationAdapterBinding)bind(component, view, com.escalate.R.layout.recycler_notification_adapter);
  }
}
