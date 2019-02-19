package com.escalate.databinding;
import com.escalate.R;
import com.escalate.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class SingleRowRepliesPostBindingImpl extends SingleRowRepliesPostBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.card_view, 1);
        sViewsWithIds.put(R.id.linear_view_other, 2);
        sViewsWithIds.put(R.id.rl_user, 3);
        sViewsWithIds.put(R.id.img_user, 4);
        sViewsWithIds.put(R.id.progressBar, 5);
        sViewsWithIds.put(R.id.rl_in_btw, 6);
        sViewsWithIds.put(R.id.ll_user_in_btw, 7);
        sViewsWithIds.put(R.id.tv_name, 8);
        sViewsWithIds.put(R.id.tv_category_hash, 9);
        sViewsWithIds.put(R.id.img_options, 10);
        sViewsWithIds.put(R.id.event_ll, 11);
        sViewsWithIds.put(R.id.rl_player, 12);
        sViewsWithIds.put(R.id.img_play_pause, 13);
        sViewsWithIds.put(R.id.SeekBar01, 14);
        sViewsWithIds.put(R.id.siriDView, 15);
        sViewsWithIds.put(R.id.txt_timer, 16);
        sViewsWithIds.put(R.id.txt_description, 17);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public SingleRowRepliesPostBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 18, sIncludes, sViewsWithIds));
    }
    private SingleRowRepliesPostBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.SeekBar) bindings[14]
            , (android.support.v7.widget.CardView) bindings[1]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.ImageView) bindings[10]
            , (android.widget.ImageView) bindings[13]
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[4]
            , (android.widget.RelativeLayout) bindings[2]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.ProgressBar) bindings[5]
            , (android.widget.RelativeLayout) bindings[6]
            , (android.widget.RelativeLayout) bindings[12]
            , (android.widget.RelativeLayout) bindings[3]
            , (com.am.siriview.DrawView) bindings[15]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[16]
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
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