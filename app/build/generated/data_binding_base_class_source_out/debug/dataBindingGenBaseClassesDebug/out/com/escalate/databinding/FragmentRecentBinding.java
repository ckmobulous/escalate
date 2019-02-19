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
import android.widget.TextView;

public abstract class FragmentRecentBinding extends ViewDataBinding {
  @NonNull
  public final RecyclerView recViewRecent;

  @NonNull
  public final SwipeRefreshLayout swipeRefreshRecent;

  @NonNull
  public final TextView tvNoDataRecent;

  protected FragmentRecentBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, RecyclerView recViewRecent, SwipeRefreshLayout swipeRefreshRecent,
      TextView tvNoDataRecent) {
    super(_bindingComponent, _root, _localFieldCount);
    this.recViewRecent = recViewRecent;
    this.swipeRefreshRecent = swipeRefreshRecent;
    this.tvNoDataRecent = tvNoDataRecent;
  }

  @NonNull
  public static FragmentRecentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentRecentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentRecentBinding>inflate(inflater, com.escalate.R.layout.fragment_recent, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentRecentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentRecentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentRecentBinding>inflate(inflater, com.escalate.R.layout.fragment_recent, null, false, component);
  }

  public static FragmentRecentBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static FragmentRecentBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (FragmentRecentBinding)bind(component, view, com.escalate.R.layout.fragment_recent);
  }
}
