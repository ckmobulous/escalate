// Generated code from Butter Knife. Do not modify!
package com.escalate.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.am.siriview.DrawView;
import com.escalate.R;
import com.makeramen.roundedimageview.RoundedImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RecentSearchAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private RecentSearchAdapter.MyViewHolder target;

  @UiThread
  public RecentSearchAdapter$MyViewHolder_ViewBinding(RecentSearchAdapter.MyViewHolder target,
      View source) {
    this.target = target;

    target.userNameTxt = Utils.findRequiredViewAsType(source, R.id.nameTxtRecent, "field 'userNameTxt'", TextView.class);
    target.txtLike = Utils.findRequiredViewAsType(source, R.id.txtLike, "field 'txtLike'", TextView.class);
    target.txtCatHash = Utils.findRequiredViewAsType(source, R.id.tv_category_hash, "field 'txtCatHash'", TextView.class);
    target.txtDescption = Utils.findRequiredViewAsType(source, R.id.txt_description, "field 'txtDescption'", TextView.class);
    target.txtViewRply = Utils.findRequiredViewAsType(source, R.id.txt_replies, "field 'txtViewRply'", TextView.class);
    target.txtTimer = Utils.findRequiredViewAsType(source, R.id.txt_timer, "field 'txtTimer'", TextView.class);
    target.imgHeart = Utils.findRequiredViewAsType(source, R.id.img_heart, "field 'imgHeart'", ImageView.class);
    target.commentIv = Utils.findRequiredViewAsType(source, R.id.commentIv, "field 'commentIv'", ImageView.class);
    target.imgShare = Utils.findRequiredViewAsType(source, R.id.imgShare, "field 'imgShare'", ImageView.class);
    target.iViewRply = Utils.findRequiredViewAsType(source, R.id.img_reply, "field 'iViewRply'", ImageView.class);
    target.roundedImageView = Utils.findRequiredViewAsType(source, R.id.imgUserRecent, "field 'roundedImageView'", RoundedImageView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progressBar, "field 'progressBar'", ProgressBar.class);
    target.imgPlayPause = Utils.findRequiredViewAsType(source, R.id.img_play_pause, "field 'imgPlayPause'", ImageView.class);
    target.siriDView = Utils.findRequiredViewAsType(source, R.id.siriDView, "field 'siriDView'", DrawView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RecentSearchAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.userNameTxt = null;
    target.txtLike = null;
    target.txtCatHash = null;
    target.txtDescption = null;
    target.txtViewRply = null;
    target.txtTimer = null;
    target.imgHeart = null;
    target.commentIv = null;
    target.imgShare = null;
    target.iViewRply = null;
    target.roundedImageView = null;
    target.progressBar = null;
    target.imgPlayPause = null;
    target.siriDView = null;
  }
}
