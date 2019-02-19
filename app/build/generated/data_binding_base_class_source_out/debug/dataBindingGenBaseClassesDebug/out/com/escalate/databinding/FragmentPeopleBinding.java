package com.escalate.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class FragmentPeopleBinding extends ViewDataBinding {
  @NonNull
  public final RecyclerView recViewPeople;

  @NonNull
  public final SwipeRefreshLayout swipeRefreshPeople;

  protected FragmentPeopleBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, RecyclerView recViewPeople, SwipeRefreshLayout swipeRefreshPeople) {
    super(_bindingComponent, _root, _localFieldCount);
    this.recViewPeople = recViewPeople;
    this.swipeRefreshPeople = swipeRefreshPeople;
  }

  @NonNull
  public static FragmentPeopleBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentPeopleBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentPeopleBinding>inflate(inflater, com.escalate.R.layout.fragment_people, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentPeopleBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentPeopleBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentPeopleBinding>inflate(inflater, com.escalate.R.layout.fragment_people, null, false, component);
  }

  public static FragmentPeopleBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static FragmentPeopleBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (FragmentPeopleBinding)bind(component, view, com.escalate.R.layout.fragment_people);
  }
}
