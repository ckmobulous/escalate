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
import android.widget.ImageView;
import android.widget.TextView;

public abstract class ActivityTermsConditionBinding extends ViewDataBinding {
  @NonNull
  public final AppBarLayout appbarlayout;

  @NonNull
  public final ImageView backIvTandC;

  @NonNull
  public final TextView descriptionTxt;

  @NonNull
  public final Toolbar toolbar;

  @NonNull
  public final TextView toolbarTitle;

  protected ActivityTermsConditionBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, AppBarLayout appbarlayout, ImageView backIvTandC,
      TextView descriptionTxt, Toolbar toolbar, TextView toolbarTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.appbarlayout = appbarlayout;
    this.backIvTandC = backIvTandC;
    this.descriptionTxt = descriptionTxt;
    this.toolbar = toolbar;
    this.toolbarTitle = toolbarTitle;
  }

  @NonNull
  public static ActivityTermsConditionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityTermsConditionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityTermsConditionBinding>inflate(inflater, com.escalate.R.layout.activity_terms_condition, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityTermsConditionBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityTermsConditionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityTermsConditionBinding>inflate(inflater, com.escalate.R.layout.activity_terms_condition, null, false, component);
  }

  public static ActivityTermsConditionBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityTermsConditionBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityTermsConditionBinding)bind(component, view, com.escalate.R.layout.activity_terms_condition);
  }
}
