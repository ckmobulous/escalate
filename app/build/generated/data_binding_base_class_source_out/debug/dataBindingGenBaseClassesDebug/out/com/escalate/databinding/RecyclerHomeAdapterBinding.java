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
import android.widget.SeekBar;
import android.widget.TextView;
import com.am.siriview.DrawView;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.makeramen.roundedimageview.RoundedImageView;
import rm.com.audiowave.AudioWaveView;

public abstract class RecyclerHomeAdapterBinding extends ViewDataBinding {
  @NonNull
  public final SeekBar SeekBar01;

  @NonNull
  public final CardView cardView;

  @NonNull
  public final ImageView commentIv;

  @NonNull
  public final LinearLayout eventLl;

  @NonNull
  public final ImageView imgHeart;

  @NonNull
  public final ImageView imgPlayPause;

  @NonNull
  public final ImageView imgReply;

  @NonNull
  public final ImageView imgShare;

  @NonNull
  public final RoundedImageView imgUser;

  @NonNull
  public final JcPlayerView jcPlayer;

  @NonNull
  public final LinearLayout linearViewOther;

  @NonNull
  public final RelativeLayout rlPlayer;

  @NonNull
  public final DrawView siriDView;

  @NonNull
  public final TextView tv;

  @NonNull
  public final TextView tvName;

  @NonNull
  public final TextView txtDiscription;

  @NonNull
  public final TextView txtLike;

  @NonNull
  public final TextView txtReplies;

  @NonNull
  public final TextView txtTimer;

  @NonNull
  public final AudioWaveView wave;

  protected RecyclerHomeAdapterBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, SeekBar SeekBar01, CardView cardView, ImageView commentIv,
      LinearLayout eventLl, ImageView imgHeart, ImageView imgPlayPause, ImageView imgReply,
      ImageView imgShare, RoundedImageView imgUser, JcPlayerView jcPlayer,
      LinearLayout linearViewOther, RelativeLayout rlPlayer, DrawView siriDView, TextView tv,
      TextView tvName, TextView txtDiscription, TextView txtLike, TextView txtReplies,
      TextView txtTimer, AudioWaveView wave) {
    super(_bindingComponent, _root, _localFieldCount);
    this.SeekBar01 = SeekBar01;
    this.cardView = cardView;
    this.commentIv = commentIv;
    this.eventLl = eventLl;
    this.imgHeart = imgHeart;
    this.imgPlayPause = imgPlayPause;
    this.imgReply = imgReply;
    this.imgShare = imgShare;
    this.imgUser = imgUser;
    this.jcPlayer = jcPlayer;
    this.linearViewOther = linearViewOther;
    this.rlPlayer = rlPlayer;
    this.siriDView = siriDView;
    this.tv = tv;
    this.tvName = tvName;
    this.txtDiscription = txtDiscription;
    this.txtLike = txtLike;
    this.txtReplies = txtReplies;
    this.txtTimer = txtTimer;
    this.wave = wave;
  }

  @NonNull
  public static RecyclerHomeAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static RecyclerHomeAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<RecyclerHomeAdapterBinding>inflate(inflater, com.escalate.R.layout.recycler_home_adapter, root, attachToRoot, component);
  }

  @NonNull
  public static RecyclerHomeAdapterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static RecyclerHomeAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<RecyclerHomeAdapterBinding>inflate(inflater, com.escalate.R.layout.recycler_home_adapter, null, false, component);
  }

  public static RecyclerHomeAdapterBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static RecyclerHomeAdapterBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (RecyclerHomeAdapterBinding)bind(component, view, com.escalate.R.layout.recycler_home_adapter);
  }
}
