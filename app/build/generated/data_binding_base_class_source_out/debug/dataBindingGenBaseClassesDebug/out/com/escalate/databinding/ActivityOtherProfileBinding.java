package com.escalate.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.am.siriview.DrawView;
import com.makeramen.roundedimageview.RoundedImageView;
import rm.com.audiowave.AudioWaveView;

public abstract class ActivityOtherProfileBinding extends ViewDataBinding {
  @NonNull
  public final SeekBar SeekBar01;

  @NonNull
  public final AppBarLayout appbarlayout;

  @NonNull
  public final ImageView backIv;

  @NonNull
  public final RelativeLayout bioLay;

  @NonNull
  public final CardView cardView;

  @NonNull
  public final TextView durationTxt;

  @NonNull
  public final LinearLayout followLay;

  @NonNull
  public final LinearLayout followLayOprofile;

  @NonNull
  public final ImageView img;

  @NonNull
  public final ImageView imgI;

  @NonNull
  public final RoundedImageView imgUserOprofile;

  @NonNull
  public final LinearLayout layFollowers;

  @NonNull
  public final LinearLayout layFollowing;

  @NonNull
  public final LinearLayout layPostOprofile;

  @NonNull
  public final LinearLayout linearBox;

  @NonNull
  public final LinearLayout linearProfile;

  @NonNull
  public final LinearLayout linearReport;

  @NonNull
  public final LinearLayout linearViewOther;

  @NonNull
  public final ImageView playPauseIvOpro;

  @NonNull
  public final RecyclerView recyclerViewProfileList;

  @NonNull
  public final RelativeLayout relativeIncludeOP;

  @NonNull
  public final LinearLayout reportLay;

  @NonNull
  public final DrawView siriDViewOprofile;

  @NonNull
  public final Toolbar toolbarOProfile;

  @NonNull
  public final TextView tvAtUser;

  @NonNull
  public final TextView tvGeneric;

  @NonNull
  public final TextView tvNoDataOPro;

  @NonNull
  public final TextView tvUserName;

  @NonNull
  public final TextView txtBio;

  @NonNull
  public final TextView txtCountFollowing;

  @NonNull
  public final TextView txtCountFolowers;

  @NonNull
  public final TextView txtFollowOprofile;

  @NonNull
  public final TextView txtFollower;

  @NonNull
  public final TextView txtFollowing;

  @NonNull
  public final TextView txtPostOprofile;

  @NonNull
  public final TextView txtPostsCount;

  @NonNull
  public final TextView txtReport;

  @NonNull
  public final AudioWaveView wavePro;

  protected ActivityOtherProfileBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, SeekBar SeekBar01, AppBarLayout appbarlayout, ImageView backIv,
      RelativeLayout bioLay, CardView cardView, TextView durationTxt, LinearLayout followLay,
      LinearLayout followLayOprofile, ImageView img, ImageView imgI,
      RoundedImageView imgUserOprofile, LinearLayout layFollowers, LinearLayout layFollowing,
      LinearLayout layPostOprofile, LinearLayout linearBox, LinearLayout linearProfile,
      LinearLayout linearReport, LinearLayout linearViewOther, ImageView playPauseIvOpro,
      RecyclerView recyclerViewProfileList, RelativeLayout relativeIncludeOP,
      LinearLayout reportLay, DrawView siriDViewOprofile, Toolbar toolbarOProfile,
      TextView tvAtUser, TextView tvGeneric, TextView tvNoDataOPro, TextView tvUserName,
      TextView txtBio, TextView txtCountFollowing, TextView txtCountFolowers,
      TextView txtFollowOprofile, TextView txtFollower, TextView txtFollowing,
      TextView txtPostOprofile, TextView txtPostsCount, TextView txtReport, AudioWaveView wavePro) {
    super(_bindingComponent, _root, _localFieldCount);
    this.SeekBar01 = SeekBar01;
    this.appbarlayout = appbarlayout;
    this.backIv = backIv;
    this.bioLay = bioLay;
    this.cardView = cardView;
    this.durationTxt = durationTxt;
    this.followLay = followLay;
    this.followLayOprofile = followLayOprofile;
    this.img = img;
    this.imgI = imgI;
    this.imgUserOprofile = imgUserOprofile;
    this.layFollowers = layFollowers;
    this.layFollowing = layFollowing;
    this.layPostOprofile = layPostOprofile;
    this.linearBox = linearBox;
    this.linearProfile = linearProfile;
    this.linearReport = linearReport;
    this.linearViewOther = linearViewOther;
    this.playPauseIvOpro = playPauseIvOpro;
    this.recyclerViewProfileList = recyclerViewProfileList;
    this.relativeIncludeOP = relativeIncludeOP;
    this.reportLay = reportLay;
    this.siriDViewOprofile = siriDViewOprofile;
    this.toolbarOProfile = toolbarOProfile;
    this.tvAtUser = tvAtUser;
    this.tvGeneric = tvGeneric;
    this.tvNoDataOPro = tvNoDataOPro;
    this.tvUserName = tvUserName;
    this.txtBio = txtBio;
    this.txtCountFollowing = txtCountFollowing;
    this.txtCountFolowers = txtCountFolowers;
    this.txtFollowOprofile = txtFollowOprofile;
    this.txtFollower = txtFollower;
    this.txtFollowing = txtFollowing;
    this.txtPostOprofile = txtPostOprofile;
    this.txtPostsCount = txtPostsCount;
    this.txtReport = txtReport;
    this.wavePro = wavePro;
  }

  @NonNull
  public static ActivityOtherProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityOtherProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityOtherProfileBinding>inflate(inflater, com.escalate.R.layout.activity_other_profile, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityOtherProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityOtherProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityOtherProfileBinding>inflate(inflater, com.escalate.R.layout.activity_other_profile, null, false, component);
  }

  public static ActivityOtherProfileBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityOtherProfileBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityOtherProfileBinding)bind(component, view, com.escalate.R.layout.activity_other_profile);
  }
}
