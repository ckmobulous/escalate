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

public abstract class FragmentChatBinding extends ViewDataBinding {
  @NonNull
  public final TextView noDataTxt;

  @NonNull
  public final RecyclerView recViewHistory;

  @NonNull
  public final SwipeRefreshLayout swipeRefreshChatt;

  protected FragmentChatBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, TextView noDataTxt, RecyclerView recViewHistory,
      SwipeRefreshLayout swipeRefreshChatt) {
    super(_bindingComponent, _root, _localFieldCount);
    this.noDataTxt = noDataTxt;
    this.recViewHistory = recViewHistory;
    this.swipeRefreshChatt = swipeRefreshChatt;
  }

  @NonNull
  public static FragmentChatBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentChatBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentChatBinding>inflate(inflater, com.escalate.R.layout.fragment_chat, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentChatBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentChatBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentChatBinding>inflate(inflater, com.escalate.R.layout.fragment_chat, null, false, component);
  }

  public static FragmentChatBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static FragmentChatBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (FragmentChatBinding)bind(component, view, com.escalate.R.layout.fragment_chat);
  }
}
