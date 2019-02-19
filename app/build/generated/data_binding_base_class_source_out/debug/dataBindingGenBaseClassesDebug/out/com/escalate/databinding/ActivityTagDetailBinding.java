package com.escalate.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public abstract class ActivityTagDetailBinding extends ViewDataBinding {
  @NonNull
  public final AppBarLayout appbarlayout;

  @NonNull
  public final ImageButton backImgTag;

  @NonNull
  public final TextView img;

  @NonNull
  public final RecyclerView recViewTag;

  @NonNull
  public final Toolbar toolbar;

  @NonNull
  public final TextView tvNoDataTag;

  protected ActivityTagDetailBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, AppBarLayout appbarlayout, ImageButton backImgTag, TextView img,
      RecyclerView recViewTag, Toolbar toolbar, TextView tvNoDataTag) {
    super(_bindingComponent, _root, _localFieldCount);
    this.appbarlayout = appbarlayout;
    this.backImgTag = backImgTag;
    this.img = img;
    this.recViewTag = recViewTag;
    this.toolbar = toolbar;
    this.tvNoDataTag = tvNoDataTag;
  }

  @NonNull
  public static ActivityTagDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityTagDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityTagDetailBinding>inflate(inflater, com.escalate.R.layout.activity_tag_detail, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityTagDetailBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityTagDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityTagDetailBinding>inflate(inflater, com.escalate.R.layout.activity_tag_detail, null, false, component);
  }

  public static ActivityTagDetailBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityTagDetailBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityTagDetailBinding)bind(component, view, com.escalate.R.layout.activity_tag_detail);
  }
}
