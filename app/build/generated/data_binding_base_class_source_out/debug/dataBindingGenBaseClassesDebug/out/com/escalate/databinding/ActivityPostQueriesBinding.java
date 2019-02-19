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
import android.widget.TextView;

public abstract class ActivityPostQueriesBinding extends ViewDataBinding {
  @NonNull
  public final AppBarLayout appbarlayout;

  @NonNull
  public final Button btnSubmit;

  @NonNull
  public final EditText edtPostQuery;

  @NonNull
  public final ImageView iViewBackQuery;

  @NonNull
  public final Toolbar toolbar;

  @NonNull
  public final TextView toolbarTitle;

  protected ActivityPostQueriesBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, AppBarLayout appbarlayout, Button btnSubmit, EditText edtPostQuery,
      ImageView iViewBackQuery, Toolbar toolbar, TextView toolbarTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.appbarlayout = appbarlayout;
    this.btnSubmit = btnSubmit;
    this.edtPostQuery = edtPostQuery;
    this.iViewBackQuery = iViewBackQuery;
    this.toolbar = toolbar;
    this.toolbarTitle = toolbarTitle;
  }

  @NonNull
  public static ActivityPostQueriesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityPostQueriesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityPostQueriesBinding>inflate(inflater, com.escalate.R.layout.activity_post_queries, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityPostQueriesBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityPostQueriesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityPostQueriesBinding>inflate(inflater, com.escalate.R.layout.activity_post_queries, null, false, component);
  }

  public static ActivityPostQueriesBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityPostQueriesBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityPostQueriesBinding)bind(component, view, com.escalate.R.layout.activity_post_queries);
  }
}
