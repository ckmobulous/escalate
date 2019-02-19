// Generated code from Butter Knife. Do not modify!
package com.escalate.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.escalate.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AllSearchFrag_ViewBinding implements Unbinder {
  private AllSearchFrag target;

  @UiThread
  public AllSearchFrag_ViewBinding(AllSearchFrag target, View source) {
    this.target = target;

    target.recViewPeople = Utils.findRequiredViewAsType(source, R.id.recViewPeople, "field 'recViewPeople'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AllSearchFrag target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recViewPeople = null;
  }
}
