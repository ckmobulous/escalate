package com.escalate.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class ActivityResetPassBinding extends ViewDataBinding {
  @NonNull
  public final AppBarLayout appbarlayout;

  @NonNull
  public final ImageView backIv;

  @NonNull
  public final Button btnSaveReset;

  @NonNull
  public final EditText edtCnfPassReset;

  @NonNull
  public final EditText edtNewPass;

  @NonNull
  public final LinearLayout linear;

  @NonNull
  public final Toolbar toolbar;

  @NonNull
  public final TextView toolbarTitle;

  protected ActivityResetPassBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, AppBarLayout appbarlayout, ImageView backIv, Button btnSaveReset,
      EditText edtCnfPassReset, EditText edtNewPass, LinearLayout linear, Toolbar toolbar,
      TextView toolbarTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.appbarlayout = appbarlayout;
    this.backIv = backIv;
    this.btnSaveReset = btnSaveReset;
    this.edtCnfPassReset = edtCnfPassReset;
    this.edtNewPass = edtNewPass;
    this.linear = linear;
    this.toolbar = toolbar;
    this.toolbarTitle = toolbarTitle;
  }

  @NonNull
  public static ActivityResetPassBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityResetPassBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityResetPassBinding>inflate(inflater, com.escalate.R.layout.activity_reset_pass, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityResetPassBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityResetPassBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityResetPassBinding>inflate(inflater, com.escalate.R.layout.activity_reset_pass, null, false, component);
  }

  public static ActivityResetPassBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityResetPassBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityResetPassBinding)bind(component, view, com.escalate.R.layout.activity_reset_pass);
  }
}
