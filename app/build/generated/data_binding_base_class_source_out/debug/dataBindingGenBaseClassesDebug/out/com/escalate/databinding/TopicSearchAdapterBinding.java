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
import android.widget.LinearLayout;
import android.widget.TextView;
import com.makeramen.roundedimageview.RoundedImageView;

public abstract class TopicSearchAdapterBinding extends ViewDataBinding {
  @NonNull
  public final CardView cardView;

  @NonNull
  public final RoundedImageView imgViewTopic;

  @NonNull
  public final LinearLayout linearViewOther;

  @NonNull
  public final TextView numOfPostTxt;

  @NonNull
  public final LinearLayout topicDetailLay;

  @NonNull
  public final TextView topicNameTxt;

  protected TopicSearchAdapterBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, CardView cardView, RoundedImageView imgViewTopic,
      LinearLayout linearViewOther, TextView numOfPostTxt, LinearLayout topicDetailLay,
      TextView topicNameTxt) {
    super(_bindingComponent, _root, _localFieldCount);
    this.cardView = cardView;
    this.imgViewTopic = imgViewTopic;
    this.linearViewOther = linearViewOther;
    this.numOfPostTxt = numOfPostTxt;
    this.topicDetailLay = topicDetailLay;
    this.topicNameTxt = topicNameTxt;
  }

  @NonNull
  public static TopicSearchAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static TopicSearchAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<TopicSearchAdapterBinding>inflate(inflater, com.escalate.R.layout.topic_search_adapter, root, attachToRoot, component);
  }

  @NonNull
  public static TopicSearchAdapterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static TopicSearchAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<TopicSearchAdapterBinding>inflate(inflater, com.escalate.R.layout.topic_search_adapter, null, false, component);
  }

  public static TopicSearchAdapterBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static TopicSearchAdapterBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (TopicSearchAdapterBinding)bind(component, view, com.escalate.R.layout.topic_search_adapter);
  }
}
