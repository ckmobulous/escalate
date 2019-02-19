package com.escalate.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public abstract class InlcudeFilterLayoutBinding extends ViewDataBinding {
  @NonNull
  public final TextView tv;

  @NonNull
  public final TextView tv1;

  @NonNull
  public final TextView txtApply;

  @NonNull
  public final TextView txtCancel;

  protected InlcudeFilterLayoutBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, TextView tv, TextView tv1, TextView txtApply, TextView txtCancel) {
    super(_bindingComponent, _root, _localFieldCount);
    this.tv = tv;
    this.tv1 = tv1;
    this.txtApply = txtApply;
    this.txtCancel = txtCancel;
  }

  @NonNull
  public static InlcudeFilterLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static InlcudeFilterLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<InlcudeFilterLayoutBinding>inflate(inflater, com.escalate.R.layout.inlcude_filter_layout, root, attachToRoot, component);
  }

  @NonNull
  public static InlcudeFilterLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static InlcudeFilterLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<InlcudeFilterLayoutBinding>inflate(inflater, com.escalate.R.layout.inlcude_filter_layout, null, false, component);
  }

  public static InlcudeFilterLayoutBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static InlcudeFilterLayoutBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (InlcudeFilterLayoutBinding)bind(component, view, com.escalate.R.layout.inlcude_filter_layout);
  }
}
