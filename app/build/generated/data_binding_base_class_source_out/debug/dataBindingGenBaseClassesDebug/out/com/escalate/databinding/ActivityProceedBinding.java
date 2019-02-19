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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public abstract class ActivityProceedBinding extends ViewDataBinding {
  @NonNull
  public final AppBarLayout appbarlayout;

  @NonNull
  public final Button btnProceed;

  @NonNull
  public final ImageView img;

  @NonNull
  public final ImageButton imgArrow;

  @NonNull
  public final LinearLayout linear;

  @NonNull
  public final RecyclerView recyclerViewProc;

  @NonNull
  public final Toolbar toolbar;

  protected ActivityProceedBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, AppBarLayout appbarlayout, Button btnProceed, ImageView img,
      ImageButton imgArrow, LinearLayout linear, RecyclerView recyclerViewProc, Toolbar toolbar) {
    super(_bindingComponent, _root, _localFieldCount);
    this.appbarlayout = appbarlayout;
    this.btnProceed = btnProceed;
    this.img = img;
    this.imgArrow = imgArrow;
    this.linear = linear;
    this.recyclerViewProc = recyclerViewProc;
    this.toolbar = toolbar;
  }

  @NonNull
  public static ActivityProceedBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityProceedBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityProceedBinding>inflate(inflater, com.escalate.R.layout.activity_proceed, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityProceedBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityProceedBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityProceedBinding>inflate(inflater, com.escalate.R.layout.activity_proceed, null, false, component);
  }

  public static ActivityProceedBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityProceedBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityProceedBinding)bind(component, view, com.escalate.R.layout.activity_proceed);
  }
}
