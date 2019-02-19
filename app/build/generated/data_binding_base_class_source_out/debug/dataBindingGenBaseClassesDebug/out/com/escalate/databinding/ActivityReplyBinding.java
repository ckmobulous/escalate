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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class ActivityReplyBinding extends ViewDataBinding {
  @NonNull
  public final AppBarLayout appbarlayout;

  @NonNull
  public final ImageButton backIvArrow;

  @NonNull
  public final TextView btnReply;

  @NonNull
  public final ImageView micIviewRply;

  @NonNull
  public final RecyclerView recyclerViewRply;

  @NonNull
  public final RelativeLayout replyLay;

  @NonNull
  public final SwipeRefreshLayout swipeRefreshReply;

  @NonNull
  public final Toolbar toolbar;

  @NonNull
  public final TextView toolbarTitle;

  @NonNull
  public final TextView tvNoDataRply;

  protected ActivityReplyBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, AppBarLayout appbarlayout, ImageButton backIvArrow, TextView btnReply,
      ImageView micIviewRply, RecyclerView recyclerViewRply, RelativeLayout replyLay,
      SwipeRefreshLayout swipeRefreshReply, Toolbar toolbar, TextView toolbarTitle,
      TextView tvNoDataRply) {
    super(_bindingComponent, _root, _localFieldCount);
    this.appbarlayout = appbarlayout;
    this.backIvArrow = backIvArrow;
    this.btnReply = btnReply;
    this.micIviewRply = micIviewRply;
    this.recyclerViewRply = recyclerViewRply;
    this.replyLay = replyLay;
    this.swipeRefreshReply = swipeRefreshReply;
    this.toolbar = toolbar;
    this.toolbarTitle = toolbarTitle;
    this.tvNoDataRply = tvNoDataRply;
  }

  @NonNull
  public static ActivityReplyBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityReplyBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityReplyBinding>inflate(inflater, com.escalate.R.layout.activity_reply, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityReplyBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityReplyBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityReplyBinding>inflate(inflater, com.escalate.R.layout.activity_reply, null, false, component);
  }

  public static ActivityReplyBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityReplyBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityReplyBinding)bind(component, view, com.escalate.R.layout.activity_reply);
  }
}
