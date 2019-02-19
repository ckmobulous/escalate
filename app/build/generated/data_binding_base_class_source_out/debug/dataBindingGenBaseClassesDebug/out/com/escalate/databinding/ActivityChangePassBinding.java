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

public abstract class ActivityChangePassBinding extends ViewDataBinding {
  @NonNull
  public final AppBarLayout appbarlayout;

  @NonNull
  public final Button btnSave;

  @NonNull
  public final EditText edtCnfass;

  @NonNull
  public final EditText edtNewPass;

  @NonNull
  public final EditText edtOldPass;

  @NonNull
  public final ImageView iViewBack;

  @NonNull
  public final LinearLayout linear;

  @NonNull
  public final Toolbar toolbar;

  @NonNull
  public final TextView toolbarTitle;

  protected ActivityChangePassBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, AppBarLayout appbarlayout, Button btnSave, EditText edtCnfass,
      EditText edtNewPass, EditText edtOldPass, ImageView iViewBack, LinearLayout linear,
      Toolbar toolbar, TextView toolbarTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.appbarlayout = appbarlayout;
    this.btnSave = btnSave;
    this.edtCnfass = edtCnfass;
    this.edtNewPass = edtNewPass;
    this.edtOldPass = edtOldPass;
    this.iViewBack = iViewBack;
    this.linear = linear;
    this.toolbar = toolbar;
    this.toolbarTitle = toolbarTitle;
  }

  @NonNull
  public static ActivityChangePassBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityChangePassBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityChangePassBinding>inflate(inflater, com.escalate.R.layout.activity_change_pass, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityChangePassBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityChangePassBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityChangePassBinding>inflate(inflater, com.escalate.R.layout.activity_change_pass, null, false, component);
  }

  public static ActivityChangePassBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityChangePassBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityChangePassBinding)bind(component, view, com.escalate.R.layout.activity_change_pass);
  }
}
