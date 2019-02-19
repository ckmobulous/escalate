package com.escalate.databinding;
import com.escalate.R;
import com.escalate.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ChatListBindingImpl extends ChatListBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.ll_leftChat, 1);
        sViewsWithIds.put(R.id.imgVGreenA, 2);
        sViewsWithIds.put(R.id.myChatLay, 3);
        sViewsWithIds.put(R.id.reciveLay, 4);
        sViewsWithIds.put(R.id.imgPlayPauseUser, 5);
        sViewsWithIds.put(R.id.timerTxt, 6);
        sViewsWithIds.put(R.id.siriDView, 7);
        sViewsWithIds.put(R.id.wavePro, 8);
        sViewsWithIds.put(R.id.durationTxt, 9);
        sViewsWithIds.put(R.id.ll_rightChat, 10);
        sViewsWithIds.put(R.id.rightLay, 11);
        sViewsWithIds.put(R.id.myPostLay, 12);
        sViewsWithIds.put(R.id.imgPlayPauseMy, 13);
        sViewsWithIds.put(R.id.timeMyTxt, 14);
        sViewsWithIds.put(R.id.siriDViewMy, 15);
        sViewsWithIds.put(R.id.waveProMy, 16);
        sViewsWithIds.put(R.id.txtEditMY, 17);
        sViewsWithIds.put(R.id.imgVGreyA, 18);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ChatListBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 19, sIncludes, sViewsWithIds));
    }
    private ChatListBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[9]
            , (android.widget.ImageView) bindings[13]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.ImageView) bindings[2]
            , (android.widget.ImageView) bindings[18]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.RelativeLayout) bindings[10]
            , (android.widget.RelativeLayout) bindings[3]
            , (android.widget.RelativeLayout) bindings[12]
            , (android.widget.RelativeLayout) bindings[4]
            , (android.widget.RelativeLayout) bindings[11]
            , (com.am.siriview.DrawView) bindings[7]
            , (com.am.siriview.DrawView) bindings[15]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[17]
            , (rm.com.audiowave.AudioWaveView) bindings[8]
            , (rm.com.audiowave.AudioWaveView) bindings[16]
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