// Generated code from Butter Knife. Do not modify!
package com.escalate.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.escalate.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RecentAdapter$RTagsVH_ViewBinding implements Unbinder {
  private RecentAdapter.RTagsVH target;

  @UiThread
  public RecentAdapter$RTagsVH_ViewBinding(RecentAdapter.RTagsVH target, View source) {
    this.target = target;

    target.tagNameTxt = Utils.findRequiredViewAsType(source, R.id.tagNameTxt, "field 'tagNameTxt'", TextView.class);
    target.noPostTag = Utils.findRequiredViewAsType(source, R.id.noPostTag, "field 'noPostTag'", TextView.class);
    target.tagLay = Utils.findRequiredViewAsType(source, R.id.tagLay, "field 'tagLay'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RecentAdapter.RTagsVH target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tagNameTxt = null;
    target.noPostTag = null;
    target.tagLay = null;
  }
}
