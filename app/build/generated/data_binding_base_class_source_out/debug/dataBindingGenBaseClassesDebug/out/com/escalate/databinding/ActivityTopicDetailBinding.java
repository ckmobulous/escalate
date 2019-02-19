package com.escalate.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public abstract class ActivityTopicDetailBinding extends ViewDataBinding {
  @NonNull
  public final AppBarLayout appbarlayout;

  @NonNull
  public final ImageButton backImgTopic;

  @NonNull
  public final TextView img;

  @NonNull
  public final Toolbar toolbar;

  @NonNull
  public final RecyclerView topicRecView;

  @NonNull
  public final TextView tvNoDataTopic;

  protected ActivityTopicDetailBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, AppBarLayout appbarlayout, ImageButton backImgTopic, TextView img,
      Toolbar toolbar, RecyclerView topicRecView, TextView tvNoDataTopic) {
    super(_bindingComponent, _root, _localFieldCount);
    this.appbarlayout = appbarlayout;
    this.backImgTopic = backImgTopic;
    this.img = img;
    this.toolbar = toolbar;
    this.topicRecView = topicRecView;
    this.tvNoDataTopic = tvNoDataTopic;
  }

  @NonNull
  public static ActivityTopicDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityTopicDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityTopicDetailBinding>inflate(inflater, com.escalate.R.layout.activity_topic_detail, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityTopicDetailBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityTopicDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityTopicDetailBinding>inflate(inflater, com.escalate.R.layout.activity_topic_detail, null, false, component);
  }

  public static ActivityTopicDetailBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityTopicDetailBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityTopicDetailBinding)bind(component, view, com.escalate.R.layout.activity_topic_detail);
  }
}
