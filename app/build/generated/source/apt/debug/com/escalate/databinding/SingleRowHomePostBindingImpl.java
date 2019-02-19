package com.escalate.databinding;
import com.escalate.R;
import com.escalate.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class SingleRowHomePostBindingImpl extends SingleRowHomePostBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.card_view, 1);
        sViewsWithIds.put(R.id.lLay, 2);
        sViewsWithIds.put(R.id.linear_view_other, 3);
        sViewsWithIds.put(R.id.rl_user, 4);
        sViewsWithIds.put(R.id.img_user, 5);
        sViewsWithIds.put(R.id.progressBar, 6);
        sViewsWithIds.put(R.id.rl_in_btw, 7);
        sViewsWithIds.put(R.id.ll_user_in_btw, 8);
        sViewsWithIds.put(R.id.tv_name, 9);
        sViewsWithIds.put(R.id.hideIv, 10);
        sViewsWithIds.put(R.id.tv_category_hash, 11);
        sViewsWithIds.put(R.id.img_options, 12);
        sViewsWithIds.put(R.id.event_ll, 13);
        sViewsWithIds.put(R.id.txt_description, 14);
        sViewsWithIds.put(R.id.rl_player, 15);
        sViewsWithIds.put(R.id.img_play_pause, 16);
        sViewsWithIds.put(R.id.SeekBar01, 17);
        sViewsWithIds.put(R.id.siriDView, 18);
        sViewsWithIds.put(R.id.txt_timer, 19);
        sViewsWithIds.put(R.id.img_heart, 20);
        sViewsWithIds.put(R.id.img_comment, 21);
        sViewsWithIds.put(R.id.imgShare, 22);
        sViewsWithIds.put(R.id.img_reply, 23);
        sViewsWithIds.put(R.id.txt_like, 24);
        sViewsWithIds.put(R.id.txt_replies, 25);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public SingleRowHomePostBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 26, sIncludes, sViewsWithIds));
    }
    private SingleRowHomePostBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.SeekBar) bindings[17]
            , (android.support.v7.widget.CardView) bindings[1]
            , (android.widget.LinearLayout) bindings[13]
            , (android.widget.ImageView) bindings[10]
            , (android.widget.ImageView) bindings[21]
            , (android.widget.ImageView) bindings[20]
            , (android.widget.ImageView) bindings[12]
            , (android.widget.ImageView) bindings[16]
            , (android.widget.ImageView) bindings[23]
            , (android.widget.ImageView) bindings[22]
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[5]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.RelativeLayout) bindings[3]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.ProgressBar) bindings[6]
            , (android.widget.RelativeLayout) bindings[7]
            , (android.widget.RelativeLayout) bindings[15]
            , (android.widget.RelativeLayout) bindings[4]
            , (com.am.siriview.DrawView) bindings[18]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[24]
            , (android.widget.TextView) bindings[25]
            , (android.widget.TextView) bindings[19]
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