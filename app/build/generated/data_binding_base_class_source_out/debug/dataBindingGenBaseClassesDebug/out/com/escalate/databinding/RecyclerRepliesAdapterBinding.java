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
import android.widget.RelativeLayout;
import android.widget.TextView;
import rm.com.audiowave.AudioWaveView;

public abstract class RecyclerRepliesAdapterBinding extends ViewDataBinding {
  @NonNull
  public final CardView cardView;

  @NonNull
  public final TextView descptnTxt;

  @NonNull
  public final LinearLayout eventLl;

  @NonNull
  public final LinearLayout linearViewOther;

  @NonNull
  public final TextView nameTxt;

  @NonNull
  public final ImageView playPauseIv;

  @NonNull
  public final RelativeLayout rlPlayer;

  @NonNull
  public final TextView tvHashTag;

  @NonNull
  public final TextView txtTimer;

  @NonNull
  public final ImageView userIv;

  @NonNull
  public final AudioWaveView wave;

  protected RecyclerRepliesAdapterBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, CardView cardView, TextView descptnTxt, LinearLayout eventLl,
      LinearLayout linearViewOther, TextView nameTxt, ImageView playPauseIv,
      RelativeLayout rlPlayer, TextView tvHashTag, TextView txtTimer, ImageView userIv,
      AudioWaveView wave) {
    super(_bindingComponent, _root, _localFieldCount);
    this.cardView = cardView;
    this.descptnTxt = descptnTxt;
    this.eventLl = eventLl;
    this.linearViewOther = linearViewOther;
    this.nameTxt = nameTxt;
    this.playPauseIv = playPauseIv;
    this.rlPlayer = rlPlayer;
    this.tvHashTag = tvHashTag;
    this.txtTimer = txtTimer;
    this.userIv = userIv;
    this.wave = wave;
  }

  @NonNull
  public static RecyclerRepliesAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static RecyclerRepliesAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<RecyclerRepliesAdapterBinding>inflate(inflater, com.escalate.R.layout.recycler_replies_adapter, root, attachToRoot, component);
  }

  @NonNull
  public static RecyclerRepliesAdapterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static RecyclerRepliesAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<RecyclerRepliesAdapterBinding>inflate(inflater, com.escalate.R.layout.recycler_replies_adapter, null, false, component);
  }

  public static RecyclerRepliesAdapterBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static RecyclerRepliesAdapterBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (RecyclerRepliesAdapterBinding)bind(component, view, com.escalate.R.layout.recycler_replies_adapter);
  }
}
