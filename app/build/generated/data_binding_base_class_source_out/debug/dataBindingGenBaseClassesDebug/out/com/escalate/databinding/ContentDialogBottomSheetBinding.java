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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class ContentDialogBottomSheetBinding extends ViewDataBinding {
  @NonNull
  public final TextView bottomSheetHeading;

  @NonNull
  public final RelativeLayout bottomSheetLayout;

  @NonNull
  public final CardView cv1;

  @NonNull
  public final EditText edtOtp;

  @NonNull
  public final ImageView imgCancelDialog;

  @NonNull
  public final RelativeLayout relative;

  @NonNull
  public final TextView textview;

  @NonNull
  public final TextView txtResendOtp;

  protected ContentDialogBottomSheetBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, TextView bottomSheetHeading, RelativeLayout bottomSheetLayout,
      CardView cv1, EditText edtOtp, ImageView imgCancelDialog, RelativeLayout relative,
      TextView textview, TextView txtResendOtp) {
    super(_bindingComponent, _root, _localFieldCount);
    this.bottomSheetHeading = bottomSheetHeading;
    this.bottomSheetLayout = bottomSheetLayout;
    this.cv1 = cv1;
    this.edtOtp = edtOtp;
    this.imgCancelDialog = imgCancelDialog;
    this.relative = relative;
    this.textview = textview;
    this.txtResendOtp = txtResendOtp;
  }

  @NonNull
  public static ContentDialogBottomSheetBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ContentDialogBottomSheetBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ContentDialogBottomSheetBinding>inflate(inflater, com.escalate.R.layout.content_dialog_bottom_sheet, root, attachToRoot, component);
  }

  @NonNull
  public static ContentDialogBottomSheetBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ContentDialogBottomSheetBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ContentDialogBottomSheetBinding>inflate(inflater, com.escalate.R.layout.content_dialog_bottom_sheet, null, false, component);
  }

  public static ContentDialogBottomSheetBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ContentDialogBottomSheetBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ContentDialogBottomSheetBinding)bind(component, view, com.escalate.R.layout.content_dialog_bottom_sheet);
  }
}
