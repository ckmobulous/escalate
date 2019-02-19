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

public abstract class FragmentTopicBinding extends ViewDataBinding {
  @NonNull
  public final TextView noDataTxtTopic;

  @NonNull
  public final RecyclerView recViewTopic;

  @NonNull
  public final SwipeRefreshLayout swipeRefreshTopic;

  protected FragmentTopicBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, TextView noDataTxtTopic, RecyclerView recViewTopic,
      SwipeRefreshLayout swipeRefreshTopic) {
    super(_bindingComponent, _root, _localFieldCount);
    this.noDataTxtTopic = noDataTxtTopic;
    this.recViewTopic = recViewTopic;
    this.swipeRefreshTopic = swipeRefreshTopic;
  }

  @NonNull
  public static FragmentTopicBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentTopicBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentTopicBinding>inflate(inflater, com.escalate.R.layout.fragment_topic, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentTopicBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentTopicBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentTopicBinding>inflate(inflater, com.escalate.R.layout.fragment_topic, null, false, component);
  }

  public static FragmentTopicBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static FragmentTopicBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (FragmentTopicBinding)bind(component, view, com.escalate.R.layout.fragment_topic);
  }
}
