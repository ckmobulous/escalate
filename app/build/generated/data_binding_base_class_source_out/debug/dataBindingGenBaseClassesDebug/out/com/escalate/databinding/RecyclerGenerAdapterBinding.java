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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class RecyclerGenerAdapterBinding extends ViewDataBinding {
  @NonNull
  public final CardView cardView;

  @NonNull
  public final ImageView imgGeneric;

  @NonNull
  public final ImageView imgTick;

  @NonNull
  public final LinearLayout llCardP;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final RelativeLayout relativeBackground;

  @NonNull
  public final TextView txtGenericName;

  protected RecyclerGenerAdapterBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, CardView cardView, ImageView imgGeneric, ImageView imgTick,
      LinearLayout llCardP, ProgressBar progressBar, RelativeLayout relativeBackground,
      TextView txtGenericName) {
    super(_bindingComponent, _root, _localFieldCount);
    this.cardView = cardView;
    this.imgGeneric = imgGeneric;
    this.imgTick = imgTick;
    this.llCardP = llCardP;
    this.progressBar = progressBar;
    this.relativeBackground = relativeBackground;
    this.txtGenericName = txtGenericName;
  }

  @NonNull
  public static RecyclerGenerAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static RecyclerGenerAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<RecyclerGenerAdapterBinding>inflate(inflater, com.escalate.R.layout.recycler_gener_adapter, root, attachToRoot, component);
  }

  @NonNull
  public static RecyclerGenerAdapterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static RecyclerGenerAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<RecyclerGenerAdapterBinding>inflate(inflater, com.escalate.R.layout.recycler_gener_adapter, null, false, component);
  }

  public static RecyclerGenerAdapterBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static RecyclerGenerAdapterBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (RecyclerGenerAdapterBinding)bind(component, view, com.escalate.R.layout.recycler_gener_adapter);
  }
}
