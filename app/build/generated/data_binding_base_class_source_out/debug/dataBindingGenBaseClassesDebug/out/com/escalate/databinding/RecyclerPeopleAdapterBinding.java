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

public abstract class RecyclerPeopleAdapterBinding extends ViewDataBinding {
  @NonNull
  public final CardView cardView;

  @NonNull
  public final LinearLayout detailLay;

  @NonNull
  public final LinearLayout followPeopleLay;

  @NonNull
  public final RoundedImageView imgViewPeople;

  @NonNull
  public final LinearLayout linearViewOther;

  @NonNull
  public final TextView tvNameTopic;

  @NonNull
  public final TextView tvTopic;

  @NonNull
  public final TextView txtFollowPeople;

  protected RecyclerPeopleAdapterBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, CardView cardView, LinearLayout detailLay, LinearLayout followPeopleLay,
      RoundedImageView imgViewPeople, LinearLayout linearViewOther, TextView tvNameTopic,
      TextView tvTopic, TextView txtFollowPeople) {
    super(_bindingComponent, _root, _localFieldCount);
    this.cardView = cardView;
    this.detailLay = detailLay;
    this.followPeopleLay = followPeopleLay;
    this.imgViewPeople = imgViewPeople;
    this.linearViewOther = linearViewOther;
    this.tvNameTopic = tvNameTopic;
    this.tvTopic = tvTopic;
    this.txtFollowPeople = txtFollowPeople;
  }

  @NonNull
  public static RecyclerPeopleAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static RecyclerPeopleAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<RecyclerPeopleAdapterBinding>inflate(inflater, com.escalate.R.layout.recycler_people_adapter, root, attachToRoot, component);
  }

  @NonNull
  public static RecyclerPeopleAdapterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static RecyclerPeopleAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<RecyclerPeopleAdapterBinding>inflate(inflater, com.escalate.R.layout.recycler_people_adapter, null, false, component);
  }

  public static RecyclerPeopleAdapterBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static RecyclerPeopleAdapterBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (RecyclerPeopleAdapterBinding)bind(component, view, com.escalate.R.layout.recycler_people_adapter);
  }
}
