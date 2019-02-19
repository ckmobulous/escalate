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
import android.widget.TextView;

public abstract class RecyclerChatListAdapterBinding extends ViewDataBinding {
  @NonNull
  public final CardView cardView;

  @NonNull
  public final LinearLayout detailLay;

  @NonNull
  public final ImageView imgUser;

  @NonNull
  public final LinearLayout linearViewOther;

  @NonNull
  public final TextView nameTxtChat;

  @NonNull
  public final TextView tvTime;

  protected RecyclerChatListAdapterBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, CardView cardView, LinearLayout detailLay, ImageView imgUser,
      LinearLayout linearViewOther, TextView nameTxtChat, TextView tvTime) {
    super(_bindingComponent, _root, _localFieldCount);
    this.cardView = cardView;
    this.detailLay = detailLay;
    this.imgUser = imgUser;
    this.linearViewOther = linearViewOther;
    this.nameTxtChat = nameTxtChat;
    this.tvTime = tvTime;
  }

  @NonNull
  public static RecyclerChatListAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static RecyclerChatListAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<RecyclerChatListAdapterBinding>inflate(inflater, com.escalate.R.layout.recycler_chat_list_adapter, root, attachToRoot, component);
  }

  @NonNull
  public static RecyclerChatListAdapterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static RecyclerChatListAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<RecyclerChatListAdapterBinding>inflate(inflater, com.escalate.R.layout.recycler_chat_list_adapter, null, false, component);
  }

  public static RecyclerChatListAdapterBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static RecyclerChatListAdapterBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (RecyclerChatListAdapterBinding)bind(component, view, com.escalate.R.layout.recycler_chat_list_adapter);
  }
}
