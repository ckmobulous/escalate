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

public abstract class RecentSearchListBinding extends ViewDataBinding {
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
  public final ImageView imgOptions;

  @NonNull
  public final ImageView imgPlayPause;

  @NonNull
  public final ImageView imgReply;

  @NonNull
  public final ImageView imgShare;

  @NonNull
  public final RoundedImageView imgUserRecent;

  @NonNull
  public final RelativeLayout linearViewOther;

  @NonNull
  public final LinearLayout llUserInBtw;

  @NonNull
  public final TextView nameTxtRecent;

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
  public final TextView txtDescription;

  @NonNull
  public final TextView txtLike;

  @NonNull
  public final TextView txtReplies;

  @NonNull
  public final TextView txtTimer;

  protected RecentSearchListBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, SeekBar SeekBar01, CardView cardView, ImageView commentIv,
      LinearLayout eventLl, ImageView imgHeart, ImageView imgOptions, ImageView imgPlayPause,
      ImageView imgReply, ImageView imgShare, RoundedImageView imgUserRecent,
      RelativeLayout linearViewOther, LinearLayout llUserInBtw, TextView nameTxtRecent,
      ProgressBar progressBar, RelativeLayout rlInBtw, RelativeLayout rlPlayer,
      RelativeLayout rlUser, DrawView siriDView, TextView tvCategoryHash, TextView txtDescription,
      TextView txtLike, TextView txtReplies, TextView txtTimer) {
    super(_bindingComponent, _root, _localFieldCount);
    this.SeekBar01 = SeekBar01;
    this.cardView = cardView;
    this.commentIv = commentIv;
    this.eventLl = eventLl;
    this.imgHeart = imgHeart;
    this.imgOptions = imgOptions;
    this.imgPlayPause = imgPlayPause;
    this.imgReply = imgReply;
    this.imgShare = imgShare;
    this.imgUserRecent = imgUserRecent;
    this.linearViewOther = linearViewOther;
    this.llUserInBtw = llUserInBtw;
    this.nameTxtRecent = nameTxtRecent;
    this.progressBar = progressBar;
    this.rlInBtw = rlInBtw;
    this.rlPlayer = rlPlayer;
    this.rlUser = rlUser;
    this.siriDView = siriDView;
    this.tvCategoryHash = tvCategoryHash;
    this.txtDescription = txtDescription;
    this.txtLike = txtLike;
    this.txtReplies = txtReplies;
    this.txtTimer = txtTimer;
  }

  @NonNull
  public static RecentSearchListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static RecentSearchListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<RecentSearchListBinding>inflate(inflater, com.escalate.R.layout.recent_search_list, root, attachToRoot, component);
  }

  @NonNull
  public static RecentSearchListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static RecentSearchListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<RecentSearchListBinding>inflate(inflater, com.escalate.R.layout.recent_search_list, null, false, component);
  }

  public static RecentSearchListBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static RecentSearchListBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (RecentSearchListBinding)bind(component, view, com.escalate.R.layout.recent_search_list);
  }
}
