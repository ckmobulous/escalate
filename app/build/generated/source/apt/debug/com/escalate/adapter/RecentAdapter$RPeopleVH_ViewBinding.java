// Generated code from Butter Knife. Do not modify!
package com.escalate.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.escalate.R;
import com.makeramen.roundedimageview.RoundedImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RecentAdapter$RPeopleVH_ViewBinding implements Unbinder {
  private RecentAdapter.RPeopleVH target;

  @UiThread
  public RecentAdapter$RPeopleVH_ViewBinding(RecentAdapter.RPeopleVH target, View source) {
    this.target = target;

    target.userNameTxt = Utils.findRequiredViewAsType(source, R.id.tv_nameTopic, "field 'userNameTxt'", TextView.class);
    target.txtFollowPeople = Utils.findRequiredViewAsType(source, R.id.txtFollowPeople, "field 'txtFollowPeople'", TextView.class);
    target.imgViewPeople = Utils.findRequiredViewAsType(source, R.id.imgViewPeople, "field 'imgViewPeople'", RoundedImageView.class);
    target.detailLay = Utils.findRequiredViewAsType(source, R.id.detailLay, "field 'detailLay'", LinearLayout.class);
    target.tvTopic = Utils.findRequiredViewAsType(source, R.id.tvTopic, "field 'tvTopic'", TextView.class);
    target.followPeopleLay = Utils.findRequiredViewAsType(source, R.id.followPeopleLay, "field 'followPeopleLay'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RecentAdapter.RPeopleVH target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.userNameTxt = null;
    target.txtFollowPeople = null;
    target.imgViewPeople = null;
    target.detailLay = null;
    target.tvTopic = null;
    target.followPeopleLay = null;
  }
}
