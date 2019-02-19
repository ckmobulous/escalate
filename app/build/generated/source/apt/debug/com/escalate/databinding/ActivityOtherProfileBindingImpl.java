package com.escalate.databinding;
import com.escalate.R;
import com.escalate.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityOtherProfileBindingImpl extends ActivityOtherProfileBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new android.databinding.ViewDataBinding.IncludedLayouts(40);
        sIncludes.setIncludes(1, 
            new String[] {"inlcude_filter_layout"},
            new int[] {2},
            new int[] {com.escalate.R.layout.inlcude_filter_layout});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.appbarlayout, 3);
        sViewsWithIds.put(R.id.toolbarOProfile, 4);
        sViewsWithIds.put(R.id.backIv, 5);
        sViewsWithIds.put(R.id.img_i, 6);
        sViewsWithIds.put(R.id.img, 7);
        sViewsWithIds.put(R.id.linear_profile, 8);
        sViewsWithIds.put(R.id.card_view, 9);
        sViewsWithIds.put(R.id.linear_view_other, 10);
        sViewsWithIds.put(R.id.imgUserOprofile, 11);
        sViewsWithIds.put(R.id.tvUserName, 12);
        sViewsWithIds.put(R.id.tvAtUser, 13);
        sViewsWithIds.put(R.id.tv_generic, 14);
        sViewsWithIds.put(R.id.followLay, 15);
        sViewsWithIds.put(R.id.followLayOprofile, 16);
        sViewsWithIds.put(R.id.txtFollowOprofile, 17);
        sViewsWithIds.put(R.id.reportLay, 18);
        sViewsWithIds.put(R.id.linearReport, 19);
        sViewsWithIds.put(R.id.txtReport, 20);
        sViewsWithIds.put(R.id.bioLay, 21);
        sViewsWithIds.put(R.id.txt_bio, 22);
        sViewsWithIds.put(R.id.playPauseIvOpro, 23);
        sViewsWithIds.put(R.id.siriDViewOprofile, 24);
        sViewsWithIds.put(R.id.wavePro, 25);
        sViewsWithIds.put(R.id.SeekBar01, 26);
        sViewsWithIds.put(R.id.durationTxt, 27);
        sViewsWithIds.put(R.id.linear_box, 28);
        sViewsWithIds.put(R.id.layPostOprofile, 29);
        sViewsWithIds.put(R.id.txtPostsCount, 30);
        sViewsWithIds.put(R.id.txtPostOprofile, 31);
        sViewsWithIds.put(R.id.layFollowers, 32);
        sViewsWithIds.put(R.id.txtCountFolowers, 33);
        sViewsWithIds.put(R.id.txtFollower, 34);
        sViewsWithIds.put(R.id.layFollowing, 35);
        sViewsWithIds.put(R.id.txtCountFollowing, 36);
        sViewsWithIds.put(R.id.txtFollowing, 37);
        sViewsWithIds.put(R.id.recyclerViewProfileList, 38);
        sViewsWithIds.put(R.id.tvNoDataOPro, 39);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    @Nullable
    private final com.escalate.databinding.InlcudeFilterLayoutBinding mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityOtherProfileBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 40, sIncludes, sViewsWithIds));
    }
    private ActivityOtherProfileBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.SeekBar) bindings[26]
            , (android.support.design.widget.AppBarLayout) bindings[3]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.RelativeLayout) bindings[21]
            , (android.support.v7.widget.CardView) bindings[9]
            , (android.widget.TextView) bindings[27]
            , (android.widget.LinearLayout) bindings[15]
            , (android.widget.LinearLayout) bindings[16]
            , (android.widget.ImageView) bindings[7]
            , (android.widget.ImageView) bindings[6]
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[11]
            , (android.widget.LinearLayout) bindings[32]
            , (android.widget.LinearLayout) bindings[35]
            , (android.widget.LinearLayout) bindings[29]
            , (android.widget.LinearLayout) bindings[28]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.LinearLayout) bindings[19]
            , (android.widget.LinearLayout) bindings[10]
            , (android.widget.ImageView) bindings[23]
            , (android.support.v7.widget.RecyclerView) bindings[38]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.LinearLayout) bindings[18]
            , (com.am.siriview.DrawView) bindings[24]
            , (android.support.v7.widget.Toolbar) bindings[4]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[39]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[22]
            , (android.widget.TextView) bindings[36]
            , (android.widget.TextView) bindings[33]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[34]
            , (android.widget.TextView) bindings[37]
            , (android.widget.TextView) bindings[31]
            , (android.widget.TextView) bindings[30]
            , (android.widget.TextView) bindings[20]
            , (rm.com.audiowave.AudioWaveView) bindings[25]
            );
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (com.escalate.databinding.InlcudeFilterLayoutBinding) bindings[2];
        setContainedBinding(this.mboundView1);
        this.relativeIncludeOP.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        mboundView1.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (mboundView1.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    public void setLifecycleOwner(@Nullable android.arch.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        mboundView1.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
        executeBindingsOn(mboundView1);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}