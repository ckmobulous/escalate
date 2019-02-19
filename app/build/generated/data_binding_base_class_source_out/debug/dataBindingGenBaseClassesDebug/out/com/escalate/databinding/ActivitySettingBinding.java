package com.escalate.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class ActivitySettingBinding extends ViewDataBinding {
  @NonNull
  public final AppBarLayout appbarlayout;

  @NonNull
  public final ImageView backIvSeting;

  @NonNull
  public final RelativeLayout changePassLay;

  @NonNull
  public final View lineChange;

  @NonNull
  public final RelativeLayout logoutLay;

  @NonNull
  public final RelativeLayout relPostQueries;

  @NonNull
  public final RelativeLayout relPrivacyPpolicy;

  @NonNull
  public final RelativeLayout relUserProfile;

  @NonNull
  public final RelativeLayout reportedUserLay;

  @NonNull
  public final SwitchCompat swStatusCustom;

  @NonNull
  public final RelativeLayout termsCondLay;

  @NonNull
  public final TextView textTitle;

  @NonNull
  public final Toolbar toolbar;

  @NonNull
  public final TextView toolbarTitle;

  protected ActivitySettingBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, AppBarLayout appbarlayout, ImageView backIvSeting,
      RelativeLayout changePassLay, View lineChange, RelativeLayout logoutLay,
      RelativeLayout relPostQueries, RelativeLayout relPrivacyPpolicy,
      RelativeLayout relUserProfile, RelativeLayout reportedUserLay, SwitchCompat swStatusCustom,
      RelativeLayout termsCondLay, TextView textTitle, Toolbar toolbar, TextView toolbarTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.appbarlayout = appbarlayout;
    this.backIvSeting = backIvSeting;
    this.changePassLay = changePassLay;
    this.lineChange = lineChange;
    this.logoutLay = logoutLay;
    this.relPostQueries = relPostQueries;
    this.relPrivacyPpolicy = relPrivacyPpolicy;
    this.relUserProfile = relUserProfile;
    this.reportedUserLay = reportedUserLay;
    this.swStatusCustom = swStatusCustom;
    this.termsCondLay = termsCondLay;
    this.textTitle = textTitle;
    this.toolbar = toolbar;
    this.toolbarTitle = toolbarTitle;
  }

  @NonNull
  public static ActivitySettingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivitySettingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivitySettingBinding>inflate(inflater, com.escalate.R.layout.activity_setting, root, attachToRoot, component);
  }

  @NonNull
  public static ActivitySettingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivitySettingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivitySettingBinding>inflate(inflater, com.escalate.R.layout.activity_setting, null, false, component);
  }

  public static ActivitySettingBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivitySettingBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivitySettingBinding)bind(component, view, com.escalate.R.layout.activity_setting);
  }
}
