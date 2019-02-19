package com.escalate.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public abstract class ActivityHomeBinding extends ViewDataBinding {
  @NonNull
  public final AppBarLayout appbarlayout;

  @NonNull
  public final FrameLayout fragmentFrame;

  @NonNull
  public final ImageView imgArrow;

  @NonNull
  public final BottomNavigationView navigation;

  @NonNull
  public final Toolbar toolbarHome;

  protected ActivityHomeBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, AppBarLayout appbarlayout, FrameLayout fragmentFrame,
      ImageView imgArrow, BottomNavigationView navigation, Toolbar toolbarHome) {
    super(_bindingComponent, _root, _localFieldCount);
    this.appbarlayout = appbarlayout;
    this.fragmentFrame = fragmentFrame;
    this.imgArrow = imgArrow;
    this.navigation = navigation;
    this.toolbarHome = toolbarHome;
  }

  @NonNull
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityHomeBinding>inflate(inflater, com.escalate.R.layout.activity_home, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityHomeBinding>inflate(inflater, com.escalate.R.layout.activity_home, null, false, component);
  }

  public static ActivityHomeBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityHomeBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityHomeBinding)bind(component, view, com.escalate.R.layout.activity_home);
  }
}
