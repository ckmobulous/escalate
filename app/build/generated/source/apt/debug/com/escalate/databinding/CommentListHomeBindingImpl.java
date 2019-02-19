package com.escalate.databinding;
import com.escalate.R;
import com.escalate.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class CommentListHomeBindingImpl extends CommentListHomeBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.linear_view_other, 1);
        sViewsWithIds.put(R.id.rl_user, 2);
        sViewsWithIds.put(R.id.imgUserList, 3);
        sViewsWithIds.put(R.id.progressBar, 4);
        sViewsWithIds.put(R.id.rl_in_btw, 5);
        sViewsWithIds.put(R.id.deailLay, 6);
        sViewsWithIds.put(R.id.tv_name, 7);
        sViewsWithIds.put(R.id.tvCategoryComnt, 8);
        sViewsWithIds.put(R.id.muteVolIview, 9);
        sViewsWithIds.put(R.id.img_options, 10);
        sViewsWithIds.put(R.id.event_ll, 11);
        sViewsWithIds.put(R.id.rl_player, 12);
        sViewsWithIds.put(R.id.imgPlayPauseCmtList, 13);
        sViewsWithIds.put(R.id.SeekBar01, 14);
        sViewsWithIds.put(R.id.siriDViewCmt, 15);
        sViewsWithIds.put(R.id.txtTimerList, 16);
        sViewsWithIds.put(R.id.txtDescriptionCmt, 17);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public CommentListHomeBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 18, sIncludes, sViewsWithIds));
    }
    private CommentListHomeBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.SeekBar) bindings[14]
            , (android.widget.LinearLayout) bindings[6]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.ImageView) bindings[10]
            , (android.widget.ImageView) bindings[13]
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[3]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.ProgressBar) bindings[4]
            , (android.widget.RelativeLayout) bindings[5]
            , (android.widget.RelativeLayout) bindings[12]
            , (android.widget.RelativeLayout) bindings[2]
            , (com.am.siriview.DrawView) bindings[15]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[16]
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