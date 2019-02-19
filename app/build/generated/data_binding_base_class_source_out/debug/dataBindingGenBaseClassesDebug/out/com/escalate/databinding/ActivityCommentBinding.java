package com.escalate.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
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

public abstract class ActivityCommentBinding extends ViewDataBinding {
  @NonNull
  public final SeekBar SeekBar01;

  @NonNull
  public final ImageView backImgView;

  @NonNull
  public final TextView cmtTxt;

  @NonNull
  public final RelativeLayout commentLay;

  @NonNull
  public final RecyclerView commentRecView;

  @NonNull
  public final LinearLayout eventLl;

  @NonNull
  public final ImageView imgOptions;

  @NonNull
  public final RoundedImageView imgUserCmt;

  @NonNull
  public final LinearLayout linLay;

  @NonNull
  public final RelativeLayout linearViewOther;

  @NonNull
  public final LinearLayout llUserInBtw;

  @NonNull
  public final ImageView micIviewCmnt;

  @NonNull
  public final ImageView playPauseIvCmt;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final RelativeLayout rlInBtw;

  @NonNull
  public final RelativeLayout rlPlayer;

  @NonNull
  public final RelativeLayout rlUser;

  @NonNull
  public final DrawView siriDViewCmnt;

  @NonNull
  public final RelativeLayout topLay;

  @NonNull
  public final TextView tvCategoryHash;

  @NonNull
  public final TextView tvNameCmt;

  @NonNull
  public final TextView tvNoDataCmt;

  @NonNull
  public final TextView txtDescriptionCmt;

  @NonNull
  public final TextView txtTimerCmt;

  @NonNull
  public final View view;

  protected ActivityCommentBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, SeekBar SeekBar01, ImageView backImgView, TextView cmtTxt,
      RelativeLayout commentLay, RecyclerView commentRecView, LinearLayout eventLl,
      ImageView imgOptions, RoundedImageView imgUserCmt, LinearLayout linLay,
      RelativeLayout linearViewOther, LinearLayout llUserInBtw, ImageView micIviewCmnt,
      ImageView playPauseIvCmt, ProgressBar progressBar, RelativeLayout rlInBtw,
      RelativeLayout rlPlayer, RelativeLayout rlUser, DrawView siriDViewCmnt, RelativeLayout topLay,
      TextView tvCategoryHash, TextView tvNameCmt, TextView tvNoDataCmt, TextView txtDescriptionCmt,
      TextView txtTimerCmt, View view) {
    super(_bindingComponent, _root, _localFieldCount);
    this.SeekBar01 = SeekBar01;
    this.backImgView = backImgView;
    this.cmtTxt = cmtTxt;
    this.commentLay = commentLay;
    this.commentRecView = commentRecView;
    this.eventLl = eventLl;
    this.imgOptions = imgOptions;
    this.imgUserCmt = imgUserCmt;
    this.linLay = linLay;
    this.linearViewOther = linearViewOther;
    this.llUserInBtw = llUserInBtw;
    this.micIviewCmnt = micIviewCmnt;
    this.playPauseIvCmt = playPauseIvCmt;
    this.progressBar = progressBar;
    this.rlInBtw = rlInBtw;
    this.rlPlayer = rlPlayer;
    this.rlUser = rlUser;
    this.siriDViewCmnt = siriDViewCmnt;
    this.topLay = topLay;
    this.tvCategoryHash = tvCategoryHash;
    this.tvNameCmt = tvNameCmt;
    this.tvNoDataCmt = tvNoDataCmt;
    this.txtDescriptionCmt = txtDescriptionCmt;
    this.txtTimerCmt = txtTimerCmt;
    this.view = view;
  }

  @NonNull
  public static ActivityCommentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityCommentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityCommentBinding>inflate(inflater, com.escalate.R.layout.activity_comment, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityCommentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityCommentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityCommentBinding>inflate(inflater, com.escalate.R.layout.activity_comment, null, false, component);
  }

  public static ActivityCommentBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityCommentBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityCommentBinding)bind(component, view, com.escalate.R.layout.activity_comment);
  }
}
