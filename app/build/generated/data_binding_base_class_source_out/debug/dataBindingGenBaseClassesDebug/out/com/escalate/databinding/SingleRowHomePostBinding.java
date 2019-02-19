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
import android.widget.SeekBar;
import android.widget.TextView;
import com.am.siriview.DrawView;
import com.makeramen.roundedimageview.RoundedImageView;

public abstract class SingleRowHomePostBinding extends ViewDataBinding {
  @NonNull
  public final SeekBar SeekBar01;

  @NonNull
  public final CardView cardView;

  @NonNull
  public final LinearLayout eventLl;

  @NonNull
  public final ImageView hideIv;

  @NonNull
  public final ImageView imgComment;

  @NonNull
  public final ImageView imgHeart;

  @NonNull
  public final ImageView imgOptions;

  @NonNull
  public final ImageView imgPlayPause;

  @NonNull
  public final ImageView imgReply;

  @NonNull
  public final ImageView imgShare;

  @NonNull
  public final RoundedImageView imgUser;

  @NonNull
  public final LinearLayout lLay;

  @NonNull
  public final RelativeLayout linearViewOther;

  @NonNull
  public final LinearLayout llUserInBtw;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final RelativeLayout rlInBtw;

  @NonNull
  public final RelativeLayout rlPlayer;

  @NonNull
  public final RelativeLayout rlUser;

  @NonNull
  public final DrawView siriDView;

  @NonNull
  public final TextView tvCategoryHash;

  @NonNull
  public final TextView tvName;

  @NonNull
  public final TextView txtDescription;

  @NonNull
  public final TextView txtLike;

  @NonNull
  public final TextView txtReplies;

  @NonNull
  public final TextView txtTimer;

  protected SingleRowHomePostBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, SeekBar SeekBar01, CardView cardView, LinearLayout eventLl,
      ImageView hideIv, ImageView imgComment, ImageView imgHeart, ImageView imgOptions,
      ImageView imgPlayPause, ImageView imgReply, ImageView imgShare, RoundedImageView imgUser,
      LinearLayout lLay, RelativeLayout linearViewOther, LinearLayout llUserInBtw,
      ProgressBar progressBar, RelativeLayout rlInBtw, RelativeLayout rlPlayer,
      RelativeLayout rlUser, DrawView siriDView, TextView tvCategoryHash, TextView tvName,
      TextView txtDescription, TextView txtLike, TextView txtReplies, TextView txtTimer) {
    super(_bindingComponent, _root, _localFieldCount);
    this.SeekBar01 = SeekBar01;
    this.cardView = cardView;
    this.eventLl = eventLl;
    this.hideIv = hideIv;
    this.imgComment = imgComment;
    this.imgHeart = imgHeart;
    this.imgOptions = imgOptions;
    this.imgPlayPause = imgPlayPause;
    this.imgReply = imgReply;
    this.imgShare = imgShare;
    this.imgUser = imgUser;
    this.lLay = lLay;
    this.linearViewOther = linearViewOther;
    this.llUserInBtw = llUserInBtw;
    this.progressBar = progressBar;
    this.rlInBtw = rlInBtw;
    this.rlPlayer = rlPlayer;
    this.rlUser = rlUser;
    this.siriDView = siriDView;
    this.tvCategoryHash = tvCategoryHash;
    this.tvName = tvName;
    this.txtDescription = txtDescription;
    this.txtLike = txtLike;
    this.txtReplies = txtReplies;
    this.txtTimer = txtTimer;
  }

  @NonNull
  public static SingleRowHomePostBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static SingleRowHomePostBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<SingleRowHomePostBinding>inflate(inflater, com.escalate.R.layout.single_row_home_post, root, attachToRoot, component);
  }

  @NonNull
  public static SingleRowHomePostBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static SingleRowHomePostBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<SingleRowHomePostBinding>inflate(inflater, com.escalate.R.layout.single_row_home_post, null, false, component);
  }

  public static SingleRowHomePostBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static SingleRowHomePostBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (SingleRowHomePostBinding)bind(component, view, com.escalate.R.layout.single_row_home_post);
  }
}
