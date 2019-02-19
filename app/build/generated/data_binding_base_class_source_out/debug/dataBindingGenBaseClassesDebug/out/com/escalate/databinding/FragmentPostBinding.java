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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.am.siriview.DrawView;
import rm.com.audiowave.AudioWaveView;

public abstract class FragmentPostBinding extends ViewDataBinding {
  @NonNull
  public final SeekBar SeekBarPost;

  @NonNull
  public final Button btnPost;

  @NonNull
  public final EditText edtDesc;

  @NonNull
  public final EditText edtOldPass;

  @NonNull
  public final EditText edtTxtSearchPost;

  @NonNull
  public final LinearLayout eventLl;

  @NonNull
  public final ImageView imgPlayPausePost;

  @NonNull
  public final ImageView imgRePlay;

  @NonNull
  public final LinearLayout linear;

  @NonNull
  public final RecyclerView recViewCateg;

  @NonNull
  public final RelativeLayout recoedRelLay;

  @NonNull
  public final ImageView recordButton;

  @NonNull
  public final RelativeLayout relativePostMic;

  @NonNull
  public final DrawView siriDViewPost;

  @NonNull
  public final TextView tvNoDataCategories;

  @NonNull
  public final TextView txtTimerP;

  @NonNull
  public final TextView txtTimerPost;

  @NonNull
  public final AudioWaveView waveProDialog;

  protected FragmentPostBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, SeekBar SeekBarPost, Button btnPost, EditText edtDesc,
      EditText edtOldPass, EditText edtTxtSearchPost, LinearLayout eventLl,
      ImageView imgPlayPausePost, ImageView imgRePlay, LinearLayout linear,
      RecyclerView recViewCateg, RelativeLayout recoedRelLay, ImageView recordButton,
      RelativeLayout relativePostMic, DrawView siriDViewPost, TextView tvNoDataCategories,
      TextView txtTimerP, TextView txtTimerPost, AudioWaveView waveProDialog) {
    super(_bindingComponent, _root, _localFieldCount);
    this.SeekBarPost = SeekBarPost;
    this.btnPost = btnPost;
    this.edtDesc = edtDesc;
    this.edtOldPass = edtOldPass;
    this.edtTxtSearchPost = edtTxtSearchPost;
    this.eventLl = eventLl;
    this.imgPlayPausePost = imgPlayPausePost;
    this.imgRePlay = imgRePlay;
    this.linear = linear;
    this.recViewCateg = recViewCateg;
    this.recoedRelLay = recoedRelLay;
    this.recordButton = recordButton;
    this.relativePostMic = relativePostMic;
    this.siriDViewPost = siriDViewPost;
    this.tvNoDataCategories = tvNoDataCategories;
    this.txtTimerP = txtTimerP;
    this.txtTimerPost = txtTimerPost;
    this.waveProDialog = waveProDialog;
  }

  @NonNull
  public static FragmentPostBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentPostBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentPostBinding>inflate(inflater, com.escalate.R.layout.fragment_post, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentPostBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentPostBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentPostBinding>inflate(inflater, com.escalate.R.layout.fragment_post, null, false, component);
  }

  public static FragmentPostBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static FragmentPostBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (FragmentPostBinding)bind(component, view, com.escalate.R.layout.fragment_post);
  }
}
