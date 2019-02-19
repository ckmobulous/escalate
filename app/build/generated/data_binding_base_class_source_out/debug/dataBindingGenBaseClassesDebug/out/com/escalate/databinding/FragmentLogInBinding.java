package com.escalate.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class FragmentLogInBinding extends ViewDataBinding {
  @NonNull
  public final Button btnLogin;

  @NonNull
  public final ImageView btnLoginFb;

  @NonNull
  public final CardView cv1;

  @NonNull
  public final EditText edtPaswrdLogin;

  @NonNull
  public final EditText edtUsrNamLogin;

  @NonNull
  public final ImageView gmailLogin;

  @NonNull
  public final ImageView imagebox;

  @NonNull
  public final ImageView imgEye;

  @NonNull
  public final LinearLayout linear;

  @NonNull
  public final RelativeLayout relative;

  @NonNull
  public final TextView textviewOrSignInWith;

  @NonNull
  public final TextView tvRemember;

  @NonNull
  public final TextView txtForgotPass;

  protected FragmentLogInBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, Button btnLogin, ImageView btnLoginFb, CardView cv1,
      EditText edtPaswrdLogin, EditText edtUsrNamLogin, ImageView gmailLogin, ImageView imagebox,
      ImageView imgEye, LinearLayout linear, RelativeLayout relative, TextView textviewOrSignInWith,
      TextView tvRemember, TextView txtForgotPass) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnLogin = btnLogin;
    this.btnLoginFb = btnLoginFb;
    this.cv1 = cv1;
    this.edtPaswrdLogin = edtPaswrdLogin;
    this.edtUsrNamLogin = edtUsrNamLogin;
    this.gmailLogin = gmailLogin;
    this.imagebox = imagebox;
    this.imgEye = imgEye;
    this.linear = linear;
    this.relative = relative;
    this.textviewOrSignInWith = textviewOrSignInWith;
    this.tvRemember = tvRemember;
    this.txtForgotPass = txtForgotPass;
  }

  @NonNull
  public static FragmentLogInBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentLogInBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentLogInBinding>inflate(inflater, com.escalate.R.layout.fragment_log_in, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentLogInBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentLogInBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentLogInBinding>inflate(inflater, com.escalate.R.layout.fragment_log_in, null, false, component);
  }

  public static FragmentLogInBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static FragmentLogInBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (FragmentLogInBinding)bind(component, view, com.escalate.R.layout.fragment_log_in);
  }
}
