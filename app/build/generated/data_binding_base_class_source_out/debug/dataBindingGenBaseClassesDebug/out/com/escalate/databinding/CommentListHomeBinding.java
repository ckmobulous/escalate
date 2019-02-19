package com.escalate.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public abstract class CommentListHomeBinding extends ViewDataBinding {
  @NonNull
  public final SeekBar SeekBar01;

  @NonNull
  public final LinearLayout deailLay;

  @NonNull
  public final LinearLayout eventLl;

  @NonNull
  public final ImageView imgOptions;

  @NonNull
  public final ImageView imgPlayPauseCmtList;

  @NonNull
  public final RoundedImageView imgUserList;

  @NonNull
  public final RelativeLayout linearViewOther;

  @NonNull
  public final ImageView muteVolIview;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final RelativeLayout rlInBtw;

  @NonNull
  public final RelativeLayout rlPlayer;

  @NonNull
  public final RelativeLayout rlUser;

  @NonNull
  public final DrawView siriDViewCmt;

  @NonNull
  public final TextView tvCategoryComnt;

  @NonNull
  public final TextView tvName;

  @NonNull
  public final TextView txtDescriptionCmt;

  @NonNull
  public final TextView txtTimerList;

  protected CommentListHomeBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, SeekBar SeekBar01, LinearLayout deailLay, LinearLayout eventLl,
      ImageView imgOptions, ImageView imgPlayPauseCmtList, RoundedImageView imgUserList,
      RelativeLayout linearViewOther, ImageView muteVolIview, ProgressBar progressBar,
      RelativeLayout rlInBtw, RelativeLayout rlPlayer, RelativeLayout rlUser, DrawView siriDViewCmt,
      TextView tvCategoryComnt, TextView tvName, TextView txtDescriptionCmt,
      TextView txtTimerList) {
    super(_bindingComponent, _root, _localFieldCount);
    this.SeekBar01 = SeekBar01;
    this.deailLay = deailLay;
    this.eventLl = eventLl;
    this.imgOptions = imgOptions;
    this.imgPlayPauseCmtList = imgPlayPauseCmtList;
    this.imgUserList = imgUserList;
    this.linearViewOther = linearViewOther;
    this.muteVolIview = muteVolIview;
    this.progressBar = progressBar;
    this.rlInBtw = rlInBtw;
    this.rlPlayer = rlPlayer;
    this.rlUser = rlUser;
    this.siriDViewCmt = siriDViewCmt;
    this.tvCategoryComnt = tvCategoryComnt;
    this.tvName = tvName;
    this.txtDescriptionCmt = txtDescriptionCmt;
    this.txtTimerList = txtTimerList;
  }

  @NonNull
  public static CommentListHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static CommentListHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<CommentListHomeBinding>inflate(inflater, com.escalate.R.layout.comment_list_home, root, attachToRoot, component);
  }

  @NonNull
  public static CommentListHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static CommentListHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<CommentListHomeBinding>inflate(inflater, com.escalate.R.layout.comment_list_home, null, false, component);
  }

  public static CommentListHomeBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static CommentListHomeBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (CommentListHomeBinding)bind(component, view, com.escalate.R.layout.comment_list_home);
  }
}
