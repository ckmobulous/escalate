package com.escalate.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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

public abstract class FragmentUserFrofileBinding extends ViewDataBinding {
  @NonNull
  public final SeekBar SeekBarUssr;

  @NonNull
  public final RelativeLayout bioLayUsr;

  @NonNull
  public final CardView cardView;

  @NonNull
  public final LinearLayout editProLay;

  @NonNull
  public final ImageView imgPlayPauseUsr;

  @NonNull
  public final RoundedImageView imgUserPro;

  @NonNull
  public final LinearLayout linearBox;

  @NonNull
  public final LinearLayout linearFollowerUsr;

  @NonNull
  public final LinearLayout linearFollowingUsr;

  @NonNull
  public final LinearLayout linearPostUsr;

  @NonNull
  public final LinearLayout linearProfile;

  @NonNull
  public final LinearLayout linearViewOther;

  @NonNull
  public final TextView nameTxtUsr;

  @NonNull
  public final RecyclerView recyVewUserPro;

  @NonNull
  public final DrawView siriDViewUsrPro;

  @NonNull
  public final TextView tvNoDataUsr;

  @NonNull
  public final TextView txtBio;

  @NonNull
  public final TextView txtCountFollowingU;

  @NonNull
  public final TextView txtCountFolowerU;

  @NonNull
  public final TextView txtFollowerU;

  @NonNull
  public final TextView txtFollowingU;

  @NonNull
  public final TextView txtGenreusr;

  @NonNull
  public final TextView txtMailUsr;

  @NonNull
  public final TextView txtPhone;

  @NonNull
  public final TextView txtPostUsr;

  @NonNull
  public final TextView txtPostsCountUsr;

  @NonNull
  public final TextView txtProfile;

  @NonNull
  public final TextView txtTimerUsr;

  @NonNull
  public final TextView txtUsername;

  @NonNull
  public final AudioWaveView wavePro;

  protected FragmentUserFrofileBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, SeekBar SeekBarUssr, RelativeLayout bioLayUsr, CardView cardView,
      LinearLayout editProLay, ImageView imgPlayPauseUsr, RoundedImageView imgUserPro,
      LinearLayout linearBox, LinearLayout linearFollowerUsr, LinearLayout linearFollowingUsr,
      LinearLayout linearPostUsr, LinearLayout linearProfile, LinearLayout linearViewOther,
      TextView nameTxtUsr, RecyclerView recyVewUserPro, DrawView siriDViewUsrPro,
      TextView tvNoDataUsr, TextView txtBio, TextView txtCountFollowingU, TextView txtCountFolowerU,
      TextView txtFollowerU, TextView txtFollowingU, TextView txtGenreusr, TextView txtMailUsr,
      TextView txtPhone, TextView txtPostUsr, TextView txtPostsCountUsr, TextView txtProfile,
      TextView txtTimerUsr, TextView txtUsername, AudioWaveView wavePro) {
    super(_bindingComponent, _root, _localFieldCount);
    this.SeekBarUssr = SeekBarUssr;
    this.bioLayUsr = bioLayUsr;
    this.cardView = cardView;
    this.editProLay = editProLay;
    this.imgPlayPauseUsr = imgPlayPauseUsr;
    this.imgUserPro = imgUserPro;
    this.linearBox = linearBox;
    this.linearFollowerUsr = linearFollowerUsr;
    this.linearFollowingUsr = linearFollowingUsr;
    this.linearPostUsr = linearPostUsr;
    this.linearProfile = linearProfile;
    this.linearViewOther = linearViewOther;
    this.nameTxtUsr = nameTxtUsr;
    this.recyVewUserPro = recyVewUserPro;
    this.siriDViewUsrPro = siriDViewUsrPro;
    this.tvNoDataUsr = tvNoDataUsr;
    this.txtBio = txtBio;
    this.txtCountFollowingU = txtCountFollowingU;
    this.txtCountFolowerU = txtCountFolowerU;
    this.txtFollowerU = txtFollowerU;
    this.txtFollowingU = txtFollowingU;
    this.txtGenreusr = txtGenreusr;
    this.txtMailUsr = txtMailUsr;
    this.txtPhone = txtPhone;
    this.txtPostUsr = txtPostUsr;
    this.txtPostsCountUsr = txtPostsCountUsr;
    this.txtProfile = txtProfile;
    this.txtTimerUsr = txtTimerUsr;
    this.txtUsername = txtUsername;
    this.wavePro = wavePro;
  }

  @NonNull
  public static FragmentUserFrofileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentUserFrofileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentUserFrofileBinding>inflate(inflater, com.escalate.R.layout.fragment_user_frofile, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentUserFrofileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentUserFrofileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentUserFrofileBinding>inflate(inflater, com.escalate.R.layout.fragment_user_frofile, null, false, component);
  }

  public static FragmentUserFrofileBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static FragmentUserFrofileBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (FragmentUserFrofileBinding)bind(component, view, com.escalate.R.layout.fragment_user_frofile);
  }
}
