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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.am.siriview.DrawView;
import rm.com.audiowave.AudioWaveView;

public abstract class ChatListBinding extends ViewDataBinding {
  @NonNull
  public final TextView durationTxt;

  @NonNull
  public final ImageView imgPlayPauseMy;

  @NonNull
  public final ImageView imgPlayPauseUser;

  @NonNull
  public final ImageView imgVGreenA;

  @NonNull
  public final ImageView imgVGreyA;

  @NonNull
  public final RelativeLayout llLeftChat;

  @NonNull
  public final RelativeLayout llRightChat;

  @NonNull
  public final RelativeLayout myChatLay;

  @NonNull
  public final RelativeLayout myPostLay;

  @NonNull
  public final RelativeLayout reciveLay;

  @NonNull
  public final RelativeLayout rightLay;

  @NonNull
  public final DrawView siriDView;

  @NonNull
  public final DrawView siriDViewMy;

  @NonNull
  public final TextView timeMyTxt;

  @NonNull
  public final TextView timerTxt;

  @NonNull
  public final TextView txtEditMY;

  @NonNull
  public final AudioWaveView wavePro;

  @NonNull
  public final AudioWaveView waveProMy;

  protected ChatListBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, TextView durationTxt, ImageView imgPlayPauseMy,
      ImageView imgPlayPauseUser, ImageView imgVGreenA, ImageView imgVGreyA,
      RelativeLayout llLeftChat, RelativeLayout llRightChat, RelativeLayout myChatLay,
      RelativeLayout myPostLay, RelativeLayout reciveLay, RelativeLayout rightLay,
      DrawView siriDView, DrawView siriDViewMy, TextView timeMyTxt, TextView timerTxt,
      TextView txtEditMY, AudioWaveView wavePro, AudioWaveView waveProMy) {
    super(_bindingComponent, _root, _localFieldCount);
    this.durationTxt = durationTxt;
    this.imgPlayPauseMy = imgPlayPauseMy;
    this.imgPlayPauseUser = imgPlayPauseUser;
    this.imgVGreenA = imgVGreenA;
    this.imgVGreyA = imgVGreyA;
    this.llLeftChat = llLeftChat;
    this.llRightChat = llRightChat;
    this.myChatLay = myChatLay;
    this.myPostLay = myPostLay;
    this.reciveLay = reciveLay;
    this.rightLay = rightLay;
    this.siriDView = siriDView;
    this.siriDViewMy = siriDViewMy;
    this.timeMyTxt = timeMyTxt;
    this.timerTxt = timerTxt;
    this.txtEditMY = txtEditMY;
    this.wavePro = wavePro;
    this.waveProMy = waveProMy;
  }

  @NonNull
  public static ChatListBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ChatListBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ChatListBinding>inflate(inflater, com.escalate.R.layout.chat_list, root, attachToRoot, component);
  }

  @NonNull
  public static ChatListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ChatListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ChatListBinding>inflate(inflater, com.escalate.R.layout.chat_list, null, false, component);
  }

  public static ChatListBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ChatListBinding bind(@NonNull View view, @Nullable DataBindingComponent component) {
    return (ChatListBinding)bind(component, view, com.escalate.R.layout.chat_list);
  }
}
