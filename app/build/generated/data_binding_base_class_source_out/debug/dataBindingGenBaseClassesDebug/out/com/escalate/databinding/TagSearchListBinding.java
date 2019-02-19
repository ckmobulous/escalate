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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.makeramen.roundedimageview.RoundedImageView;

public abstract class TagSearchListBinding extends ViewDataBinding {
  @NonNull
  public final CardView cardView;

  @NonNull
  public final RoundedImageView imgViewTag;

  @NonNull
  public final LinearLayout linearViewOther;

  @NonNull
  public final TextView noPostTag;

  @NonNull
  public final RelativeLayout tagLay;

  @NonNull
  public final TextView tagNameTxt;

  protected TagSearchListBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, CardView cardView, RoundedImageView imgViewTag,
      LinearLayout linearViewOther, TextView noPostTag, RelativeLayout tagLay,
      TextView tagNameTxt) {
    super(_bindingComponent, _root, _localFieldCount);
    this.cardView = cardView;
    this.imgViewTag = imgViewTag;
    this.linearViewOther = linearViewOther;
    this.noPostTag = noPostTag;
    this.tagLay = tagLay;
    this.tagNameTxt = tagNameTxt;
  }

  @NonNull
  public static TagSearchListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static TagSearchListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<TagSearchListBinding>inflate(inflater, com.escalate.R.layout.tag_search_list, root, attachToRoot, component);
  }

  @NonNull
  public static TagSearchListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static TagSearchListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<TagSearchListBinding>inflate(inflater, com.escalate.R.layout.tag_search_list, null, false, component);
  }

  public static TagSearchListBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static TagSearchListBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (TagSearchListBinding)bind(component, view, com.escalate.R.layout.tag_search_list);
  }
}
