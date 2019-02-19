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
import com.hbb20.CountryCodePicker;

public abstract class FragmentSignUpBinding extends ViewDataBinding {
  @NonNull
  public final Button btnSinUp;

  @NonNull
  public final CountryCodePicker ccpSinUp;

  @NonNull
  public final CardView cv1;

  @NonNull
  public final EditText edtCnfPaswrd;

  @NonNull
  public final EditText edtEmailSinUp;

  @NonNull
  public final EditText edtFullName;

  @NonNull
  public final EditText edtPaswrdSinUp;

  @NonNull
  public final EditText edtUsrNamSinUp;

  @NonNull
  public final ImageView imgCnfEvePasWd;

  @NonNull
  public final ImageView imgEyePasWd;

  @NonNull
  public final ImageView imgVnotificatin;

  @NonNull
  public final ImageView imgboxSup;

  @NonNull
  public final LinearLayout linear;

  @NonNull
  public final RelativeLayout notifiLay;

  @NonNull
  public final EditText phnEtdTxt;

  @NonNull
  public final RelativeLayout termsLay;

  @NonNull
  public final TextView tvNotification;

  @NonNull
  public final TextView tvRemember;

  protected FragmentSignUpBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, Button btnSinUp, CountryCodePicker ccpSinUp, CardView cv1,
      EditText edtCnfPaswrd, EditText edtEmailSinUp, EditText edtFullName, EditText edtPaswrdSinUp,
      EditText edtUsrNamSinUp, ImageView imgCnfEvePasWd, ImageView imgEyePasWd,
      ImageView imgVnotificatin, ImageView imgboxSup, LinearLayout linear, RelativeLayout notifiLay,
      EditText phnEtdTxt, RelativeLayout termsLay, TextView tvNotification, TextView tvRemember) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnSinUp = btnSinUp;
    this.ccpSinUp = ccpSinUp;
    this.cv1 = cv1;
    this.edtCnfPaswrd = edtCnfPaswrd;
    this.edtEmailSinUp = edtEmailSinUp;
    this.edtFullName = edtFullName;
    this.edtPaswrdSinUp = edtPaswrdSinUp;
    this.edtUsrNamSinUp = edtUsrNamSinUp;
    this.imgCnfEvePasWd = imgCnfEvePasWd;
    this.imgEyePasWd = imgEyePasWd;
    this.imgVnotificatin = imgVnotificatin;
    this.imgboxSup = imgboxSup;
    this.linear = linear;
    this.notifiLay = notifiLay;
    this.phnEtdTxt = phnEtdTxt;
    this.termsLay = termsLay;
    this.tvNotification = tvNotification;
    this.tvRemember = tvRemember;
  }

  @NonNull
  public static FragmentSignUpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentSignUpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentSignUpBinding>inflate(inflater, com.escalate.R.layout.fragment_sign_up, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentSignUpBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentSignUpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentSignUpBinding>inflate(inflater, com.escalate.R.layout.fragment_sign_up, null, false, component);
  }

  public static FragmentSignUpBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static FragmentSignUpBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (FragmentSignUpBinding)bind(component, view, com.escalate.R.layout.fragment_sign_up);
  }
}
