// Generated code from Butter Knife. Do not modify!
package com.escalate.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.escalate.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RecentAdapter$RTopicsVH_ViewBinding implements Unbinder {
  private RecentAdapter.RTopicsVH target;

  @UiThread
  public RecentAdapter$RTopicsVH_ViewBinding(RecentAdapter.RTopicsVH target, View source) {
    this.target = target;

    target.userNameTxt = Utils.findRequiredViewAsType(source, R.id.topic_nameTxt, "field 'userNameTxt'", TextView.class);
    target.imgViewTopic = Utils.findRequiredViewAsType(source, R.id.imgViewTopic, "field 'imgViewTopic'", ImageView.class);
    target.num_of_postTxt = Utils.findRequiredViewAsType(source, R.id.num_of_postTxt, "field 'num_of_postTxt'", TextView.class);
    target.topicDetailLay = Utils.findRequiredViewAsType(source, R.id.topicDetailLay, "field 'topicDetailLay'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RecentAdapter.RTopicsVH target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.userNameTxt = null;
    target.imgViewTopic = null;
    target.num_of_postTxt = null;
    target.topicDetailLay = null;
  }
}
