package com.escalate.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class SearchCategoryBinding extends ViewDataBinding {
  @NonNull
  public final RelativeLayout rlMoreP;

  @NonNull
  public final TextView tvTitle;

  protected SearchCategoryBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, RelativeLayout rlMoreP, TextView tvTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.rlMoreP = rlMoreP;
    this.tvTitle = tvTitle;
  }

  @NonNull
  public static SearchCategoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static SearchCategoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<SearchCategoryBinding>inflate(inflater, com.escalate.R.layout.search_category, root, attachToRoot, component);
  }

  @NonNull
  public static SearchCategoryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static SearchCategoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<SearchCategoryBinding>inflate(inflater, com.escalate.R.layout.search_category, null, false, component);
  }

  public static SearchCategoryBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static SearchCategoryBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (SearchCategoryBinding)bind(component, view, com.escalate.R.layout.search_category);
  }
}
