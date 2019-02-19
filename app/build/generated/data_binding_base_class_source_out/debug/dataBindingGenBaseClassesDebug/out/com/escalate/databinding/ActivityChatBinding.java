package com.escalate.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.am.siriview.DrawView;
import com.escalate.utils.CircleImageView;

public abstract class ActivityChatBinding extends ViewDataBinding {
  @NonNull
  public final AppBarLayout appbarlayout;

  @NonNull
  public final TextView btnReplyChat;

  @NonNull
  public final CircleImageView circleImageView;

  @NonNull
  public final ImageView iVbackChat;

  @NonNull
  public final ImageView micIView;

  @NonNull
  public final TextView noDataTxt;

  @NonNull
  public final ImageView playPauseChat;

  @NonNull
  public final RelativeLayout recordLay;

  @NonNull
  public final RecyclerView recyclerChat;

  @NonNull
  public final RelativeLayout sendLay;

  @NonNull
  public final ImageView sentIview;

  @NonNull
  public final DrawView siriDViewChat;

  @NonNull
  public final SwipeRefreshLayout swipeRefreshChat;

  @NonNull
  public final Toolbar toolbarChat;

  @NonNull
  public final TextView toolbarTitleName;

  @NonNull
  public final TextView txtTimerSend;

  protected ActivityChatBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, AppBarLayout appbarlayout, TextView btnReplyChat,
      CircleImageView circleImageView, ImageView iVbackChat, ImageView micIView, TextView noDataTxt,
      ImageView playPauseChat, RelativeLayout recordLay, RecyclerView recyclerChat,
      RelativeLayout sendLay, ImageView sentIview, DrawView siriDViewChat,
      SwipeRefreshLayout swipeRefreshChat, Toolbar toolbarChat, TextView toolbarTitleName,
      TextView txtTimerSend) {
    super(_bindingComponent, _root, _localFieldCount);
    this.appbarlayout = appbarlayout;
    this.btnReplyChat = btnReplyChat;
    this.circleImageView = circleImageView;
    this.iVbackChat = iVbackChat;
    this.micIView = micIView;
    this.noDataTxt = noDataTxt;
    this.playPauseChat = playPauseChat;
    this.recordLay = recordLay;
    this.recyclerChat = recyclerChat;
    this.sendLay = sendLay;
    this.sentIview = sentIview;
    this.siriDViewChat = siriDViewChat;
    this.swipeRefreshChat = swipeRefreshChat;
    this.toolbarChat = toolbarChat;
    this.toolbarTitleName = toolbarTitleName;
    this.txtTimerSend = txtTimerSend;
  }

  @NonNull
  public static ActivityChatBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityChatBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityChatBinding>inflate(inflater, com.escalate.R.layout.activity_chat, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityChatBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityChatBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityChatBinding>inflate(inflater, com.escalate.R.layout.activity_chat, null, false, component);
  }

  public static ActivityChatBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityChatBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityChatBinding)bind(component, view, com.escalate.R.layout.activity_chat);
  }
}
