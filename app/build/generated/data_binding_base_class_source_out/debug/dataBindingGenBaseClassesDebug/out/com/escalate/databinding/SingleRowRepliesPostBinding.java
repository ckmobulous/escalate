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

public abstract class SingleRowRepliesPostBinding extends ViewDataBinding {
  @NonNull
  public final SeekBar SeekBar01;

  @NonNull
  public final CardView cardView;

  @NonNull
  public final LinearLayout eventLl;

  @NonNull
  public final ImageView imgOptions;

  @NonNull
  public final ImageView imgPlayPause;

  @NonNull
  public final RoundedImageView imgUser;

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
  public final TextView txtTimer;

  protected SingleRowRepliesPostBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, SeekBar SeekBar01, CardView cardView, LinearLayout eventLl,
      ImageView imgOptions, ImageView imgPlayPause, RoundedImageView imgUser,
      RelativeLayout linearViewOther, LinearLayout llUserInBtw, ProgressBar progressBar,
      RelativeLayout rlInBtw, RelativeLayout rlPlayer, RelativeLayout rlUser, DrawView siriDView,
      TextView tvCategoryHash, TextView tvName, TextView txtDescription, TextView txtTimer) {
    super(_bindingComponent, _root, _localFieldCount);
    this.SeekBar01 = SeekBar01;
    this.cardView = cardView;
    this.eventLl = eventLl;
    this.imgOptions = imgOptions;
    this.imgPlayPause = imgPlayPause;
    this.imgUser = imgUser;
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
    this.txtTimer = txtTimer;
  }

  @NonNull
  public static SingleRowRepliesPostBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static SingleRowRepliesPostBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<SingleRowRepliesPostBinding>inflate(inflater, com.escalate.R.layout.single_row_replies_post, root, attachToRoot, component);
  }

  @NonNull
  public static SingleRowRepliesPostBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static SingleRowRepliesPostBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<SingleRowRepliesPostBinding>inflate(inflater, com.escalate.R.layout.single_row_replies_post, null, false, component);
  }

  public static SingleRowRepliesPostBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static SingleRowRepliesPostBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (SingleRowRepliesPostBinding)bind(component, view, com.escalate.R.layout.single_row_replies_post);
  }
}
