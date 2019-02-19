package com.escalate.databinding;
import com.escalate.R;
import com.escalate.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class RecyclerHomeAdapterBindingImpl extends RecyclerHomeAdapterBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.card_view, 1);
        sViewsWithIds.put(R.id.linear_view_other, 2);
        sViewsWithIds.put(R.id.img_user, 3);
        sViewsWithIds.put(R.id.tv_name, 4);
        sViewsWithIds.put(R.id.tv_, 5);
        sViewsWithIds.put(R.id.event_ll, 6);
        sViewsWithIds.put(R.id.txt_discription, 7);
        sViewsWithIds.put(R.id.rl_player, 8);
        sViewsWithIds.put(R.id.img_play_pause, 9);
        sViewsWithIds.put(R.id.SeekBar01, 10);
        sViewsWithIds.put(R.id.wave, 11);
        sViewsWithIds.put(R.id.siriDView, 12);
        sViewsWithIds.put(R.id.txt_timer, 13);
        sViewsWithIds.put(R.id.jcPlayer, 14);
        sViewsWithIds.put(R.id.img_heart, 15);
        sViewsWithIds.put(R.id.commentIv, 16);
        sViewsWithIds.put(R.id.imgShare, 17);
        sViewsWithIds.put(R.id.img_reply, 18);
        sViewsWithIds.put(R.id.txt_like, 19);
        sViewsWithIds.put(R.id.txt_replies, 20);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public RecyclerHomeAdapterBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 21, sIncludes, sViewsWithIds));
    }
    private RecyclerHomeAdapterBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.SeekBar) bindings[10]
            , (android.support.v7.widget.CardView) bindings[1]
            , (android.widget.ImageView) bindings[16]
            , (android.widget.LinearLayout) bindings[6]
            , (android.widget.ImageView) bindings[15]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.ImageView) bindings[18]
            , (android.widget.ImageView) bindings[17]
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[3]
            , (com.example.jean.jcplayer.view.JcPlayerView) bindings[14]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.RelativeLayout) bindings[8]
            , (com.am.siriview.DrawView) bindings[12]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[19]
            , (android.widget.TextView) bindings[20]
            , (android.widget.TextView) bindings[13]
            , (rm.com.audiowave.AudioWaveView) bindings[11]
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