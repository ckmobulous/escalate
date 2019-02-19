package com.escalate.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public abstract class FragmentSearchBinding extends ViewDataBinding {
  @NonNull
  public final EditText etSearch;

  @NonNull
  public final TabLayout tabsSearch;

  @NonNull
  public final ViewPager viewPager;

  protected FragmentSearchBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, EditText etSearch, TabLayout tabsSearch, ViewPager viewPager) {
    super(_bindingComponent, _root, _localFieldCount);
    this.etSearch = etSearch;
    this.tabsSearch = tabsSearch;
    this.viewPager = viewPager;
  }

  @NonNull
  public static FragmentSearchBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentSearchBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentSearchBinding>inflate(inflater, com.escalate.R.layout.fragment_search, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentSearchBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentSearchBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentSearchBinding>inflate(inflater, com.escalate.R.layout.fragment_search, null, false, component);
  }

  public static FragmentSearchBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static FragmentSearchBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (FragmentSearchBinding)bind(component, view, com.escalate.R.layout.fragment_search);
  }
}
