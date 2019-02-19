package com.escalate.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.am.siriview.DrawView;
import com.hbb20.CountryCodePicker;
import com.makeramen.roundedimageview.RoundedImageView;
import rm.com.audiowave.AudioWaveView;

public abstract class ActivityUpdateUserBinding extends ViewDataBinding {
  @NonNull
  public final AppBarLayout appbarlayout;

  @NonNull
  public final ImageView backIvew;

  @NonNull
  public final Button btnSave;

  @NonNull
  public final ImageView callIview;

  @NonNull
  public final CountryCodePicker ccpProfile;

  @NonNull
  public final RoundedImageView cirImgUpdt;

  @NonNull
  public final ImageView editBioIv;

  @NonNull
  public final EditText edtEmailU;

  @NonNull
  public final EditText edtName;

  @NonNull
  public final EditText edtPhoneUpdt;

  @NonNull
  public final EditText edtUsrName;

  @NonNull
  public final FrameLayout frameProfilePick;

  @NonNull
  public final ImageView img;

  @NonNull
  public final ImageView imgEmailUpdt;

  @NonNull
  public final ImageView imgNameEdit;

  @NonNull
  public final ImageView imgPhnupdt;

  @NonNull
  public final ImageView imgPlayPsUpdt;

  @NonNull
  public final ImageView imgUsrNameEdit;

  @NonNull
  public final LinearLayout linear;

  @NonNull
  public final RelativeLayout relGenreLay;

  @NonNull
  public final ImageView rightIview;

  @NonNull
  public final DrawView siriDViewUpdt;

  @NonNull
  public final Toolbar toolbar;

  @NonNull
  public final TextView txtBio;

  @NonNull
  public final TextView txtGenreU;

  @NonNull
  public final TextView txtTimerUpdt;

  @NonNull
  public final LinearLayout urlLay;

  @NonNull
  public final AudioWaveView wavePro;

  protected ActivityUpdateUserBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, AppBarLayout appbarlayout, ImageView backIvew, Button btnSave,
      ImageView callIview, CountryCodePicker ccpProfile, RoundedImageView cirImgUpdt,
      ImageView editBioIv, EditText edtEmailU, EditText edtName, EditText edtPhoneUpdt,
      EditText edtUsrName, FrameLayout frameProfilePick, ImageView img, ImageView imgEmailUpdt,
      ImageView imgNameEdit, ImageView imgPhnupdt, ImageView imgPlayPsUpdt,
      ImageView imgUsrNameEdit, LinearLayout linear, RelativeLayout relGenreLay,
      ImageView rightIview, DrawView siriDViewUpdt, Toolbar toolbar, TextView txtBio,
      TextView txtGenreU, TextView txtTimerUpdt, LinearLayout urlLay, AudioWaveView wavePro) {
    super(_bindingComponent, _root, _localFieldCount);
    this.appbarlayout = appbarlayout;
    this.backIvew = backIvew;
    this.btnSave = btnSave;
    this.callIview = callIview;
    this.ccpProfile = ccpProfile;
    this.cirImgUpdt = cirImgUpdt;
    this.editBioIv = editBioIv;
    this.edtEmailU = edtEmailU;
    this.edtName = edtName;
    this.edtPhoneUpdt = edtPhoneUpdt;
    this.edtUsrName = edtUsrName;
    this.frameProfilePick = frameProfilePick;
    this.img = img;
    this.imgEmailUpdt = imgEmailUpdt;
    this.imgNameEdit = imgNameEdit;
    this.imgPhnupdt = imgPhnupdt;
    this.imgPlayPsUpdt = imgPlayPsUpdt;
    this.imgUsrNameEdit = imgUsrNameEdit;
    this.linear = linear;
    this.relGenreLay = relGenreLay;
    this.rightIview = rightIview;
    this.siriDViewUpdt = siriDViewUpdt;
    this.toolbar = toolbar;
    this.txtBio = txtBio;
    this.txtGenreU = txtGenreU;
    this.txtTimerUpdt = txtTimerUpdt;
    this.urlLay = urlLay;
    this.wavePro = wavePro;
  }

  @NonNull
  public static ActivityUpdateUserBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityUpdateUserBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityUpdateUserBinding>inflate(inflater, com.escalate.R.layout.activity_update_user, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityUpdateUserBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityUpdateUserBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityUpdateUserBinding>inflate(inflater, com.escalate.R.layout.activity_update_user, null, false, component);
  }

  public static ActivityUpdateUserBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityUpdateUserBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityUpdateUserBinding)bind(component, view, com.escalate.R.layout.activity_update_user);
  }
}
