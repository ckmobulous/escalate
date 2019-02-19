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
import com.makeramen.roundedimageview.RoundedImageView;

public abstract class RecyclerFollowerAdapterBinding extends ViewDataBinding {
  @NonNull
  public final CardView cardView;

  @NonNull
  public final LinearLayout detailLay;

  @NonNull
  public final RoundedImageView imgUser;

  @NonNull
  public final ImageView imgVPlayPause;

  @NonNull
  public final LinearLayout linearFollowBack;

  @NonNull
  public final RelativeLayout linearViewOther;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final TextView tvName;

  @NonNull
  public final TextView txtFollow;

  protected RecyclerFollowerAdapterBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, CardView cardView, LinearLayout detailLay, RoundedImageView imgUser,
      ImageView imgVPlayPause, LinearLayout linearFollowBack, RelativeLayout linearViewOther,
      ProgressBar progressBar, TextView tvName, TextView txtFollow) {
    super(_bindingComponent, _root, _localFieldCount);
    this.cardView = cardView;
    this.detailLay = detailLay;
    this.imgUser = imgUser;
    this.imgVPlayPause = imgVPlayPause;
    this.linearFollowBack = linearFollowBack;
    this.linearViewOther = linearViewOther;
    this.progressBar = progressBar;
    this.tvName = tvName;
    this.txtFollow = txtFollow;
  }

  @NonNull
  public static RecyclerFollowerAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static RecyclerFollowerAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<RecyclerFollowerAdapterBinding>inflate(inflater, com.escalate.R.layout.recycler_follower_adapter, root, attachToRoot, component);
  }

  @NonNull
  public static RecyclerFollowerAdapterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static RecyclerFollowerAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<RecyclerFollowerAdapterBinding>inflate(inflater, com.escalate.R.layout.recycler_follower_adapter, null, false, component);
  }

  public static RecyclerFollowerAdapterBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static RecyclerFollowerAdapterBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (RecyclerFollowerAdapterBinding)bind(component, view, com.escalate.R.layout.recycler_follower_adapter);
  }
}
