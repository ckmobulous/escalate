package com.escalate.databinding;
import com.escalate.R;
import com.escalate.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityCommentBindingImpl extends ActivityCommentBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.topLay, 1);
        sViewsWithIds.put(R.id.backImgView, 2);
        sViewsWithIds.put(R.id.view, 3);
        sViewsWithIds.put(R.id.linLay, 4);
        sViewsWithIds.put(R.id.linear_view_other, 5);
        sViewsWithIds.put(R.id.rl_user, 6);
        sViewsWithIds.put(R.id.imgUserCmt, 7);
        sViewsWithIds.put(R.id.progressBar, 8);
        sViewsWithIds.put(R.id.rl_in_btw, 9);
        sViewsWithIds.put(R.id.ll_user_in_btw, 10);
        sViewsWithIds.put(R.id.tvNameCmt, 11);
        sViewsWithIds.put(R.id.tv_category_hash, 12);
        sViewsWithIds.put(R.id.img_options, 13);
        sViewsWithIds.put(R.id.event_ll, 14);
        sViewsWithIds.put(R.id.txtDescriptionCmt, 15);
        sViewsWithIds.put(R.id.rl_player, 16);
        sViewsWithIds.put(R.id.playPauseIvCmt, 17);
        sViewsWithIds.put(R.id.SeekBar01, 18);
        sViewsWithIds.put(R.id.siriDViewCmnt, 19);
        sViewsWithIds.put(R.id.txtTimerCmt, 20);
        sViewsWithIds.put(R.id.commentRecView, 21);
        sViewsWithIds.put(R.id.tvNoDataCmt, 22);
        sViewsWithIds.put(R.id.commentLay, 23);
        sViewsWithIds.put(R.id.micIviewCmnt, 24);
        sViewsWithIds.put(R.id.cmtTxt, 25);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityCommentBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 26, sIncludes, sViewsWithIds));
    }
    private ActivityCommentBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.SeekBar) bindings[18]
            , (android.widget.ImageView) bindings[2]
            , (android.widget.TextView) bindings[25]
            , (android.widget.RelativeLayout) bindings[23]
            , (android.support.v7.widget.RecyclerView) bindings[21]
            , (android.widget.LinearLayout) bindings[14]
            , (android.widget.ImageView) bindings[13]
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[7]
            , (android.widget.LinearLayout) bindings[4]
            , (android.widget.RelativeLayout) bindings[5]
            , (android.widget.LinearLayout) bindings[10]
            , (android.widget.ImageView) bindings[24]
            , (android.widget.ImageView) bindings[17]
            , (android.widget.ProgressBar) bindings[8]
            , (android.widget.RelativeLayout) bindings[9]
            , (android.widget.RelativeLayout) bindings[16]
            , (android.widget.RelativeLayout) bindings[6]
            , (com.am.siriview.DrawView) bindings[19]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[22]
            , (android.widget.TextView) bindings[15]
            , (android.widget.TextView) bindings[20]
            , (android.view.View) bindings[3]
            );
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
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