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

public abstract class FragmentTagsBinding extends ViewDataBinding {
  @NonNull
  public final TextView noDataTxtTags;

  @NonNull
  public final RecyclerView recViewTags;

  @NonNull
  public final SwipeRefreshLayout swipeRefreshTags;

  protected FragmentTagsBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, TextView noDataTxtTags, RecyclerView recViewTags,
      SwipeRefreshLayout swipeRefreshTags) {
    super(_bindingComponent, _root, _localFieldCount);
    this.noDataTxtTags = noDataTxtTags;
    this.recViewTags = recViewTags;
    this.swipeRefreshTags = swipeRefreshTags;
  }

  @NonNull
  public static FragmentTagsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentTagsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentTagsBinding>inflate(inflater, com.escalate.R.layout.fragment_tags, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentTagsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentTagsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentTagsBinding>inflate(inflater, com.escalate.R.layout.fragment_tags, null, false, component);
  }

  public static FragmentTagsBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static FragmentTagsBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (FragmentTagsBinding)bind(component, view, com.escalate.R.layout.fragment_tags);
  }
}
