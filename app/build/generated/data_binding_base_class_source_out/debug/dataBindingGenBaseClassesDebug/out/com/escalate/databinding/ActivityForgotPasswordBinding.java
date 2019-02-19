package com.escalate.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hbb20.CountryCodePicker;

public abstract class ActivityForgotPasswordBinding extends ViewDataBinding {
  @NonNull
  public final Button btnSendFgt;

  @NonNull
  public final CountryCodePicker ccpFgt;

  @NonNull
  public final ImageView circleImageView;

  @NonNull
  public final EditText edtEmailFgt;

  @NonNull
  public final EditText edtPhoneno;

  @NonNull
  public final ImageView imgBackFgt;

  @NonNull
  public final LinearLayout linearEdtPhoneno;

  @NonNull
  public final TabLayout tabsFgt;

  @NonNull
  public final TextView textview;

  @NonNull
  public final TextView toolbarTitle;

  @NonNull
  public final RelativeLayout topLay;

  protected ActivityForgotPasswordBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, Button btnSendFgt, CountryCodePicker ccpFgt, ImageView circleImageView,
      EditText edtEmailFgt, EditText edtPhoneno, ImageView imgBackFgt,
      LinearLayout linearEdtPhoneno, TabLayout tabsFgt, TextView textview, TextView toolbarTitle,
      RelativeLayout topLay) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnSendFgt = btnSendFgt;
    this.ccpFgt = ccpFgt;
    this.circleImageView = circleImageView;
    this.edtEmailFgt = edtEmailFgt;
    this.edtPhoneno = edtPhoneno;
    this.imgBackFgt = imgBackFgt;
    this.linearEdtPhoneno = linearEdtPhoneno;
    this.tabsFgt = tabsFgt;
    this.textview = textview;
    this.toolbarTitle = toolbarTitle;
    this.topLay = topLay;
  }

  @NonNull
  public static ActivityForgotPasswordBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityForgotPasswordBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityForgotPasswordBinding>inflate(inflater, com.escalate.R.layout.activity_forgot_password, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityForgotPasswordBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityForgotPasswordBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityForgotPasswordBinding>inflate(inflater, com.escalate.R.layout.activity_forgot_password, null, false, component);
  }

  public static ActivityForgotPasswordBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityForgotPasswordBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityForgotPasswordBinding)bind(component, view, com.escalate.R.layout.activity_forgot_password);
  }
}
