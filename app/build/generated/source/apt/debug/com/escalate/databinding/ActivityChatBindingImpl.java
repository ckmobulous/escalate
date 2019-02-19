package com.escalate.databinding;
import com.escalate.R;
import com.escalate.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityChatBindingImpl extends ActivityChatBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.appbarlayout, 1);
        sViewsWithIds.put(R.id.toolbarChat, 2);
        sViewsWithIds.put(R.id.iVbackChat, 3);
        sViewsWithIds.put(R.id.circleImageView, 4);
        sViewsWithIds.put(R.id.toolbarTitleName, 5);
        sViewsWithIds.put(R.id.swipeRefreshChat, 6);
        sViewsWithIds.put(R.id.recyclerChat, 7);
        sViewsWithIds.put(R.id.noDataTxt, 8);
        sViewsWithIds.put(R.id.recordLay, 9);
        sViewsWithIds.put(R.id.micIView, 10);
        sViewsWithIds.put(R.id.btnReplyChat, 11);
        sViewsWithIds.put(R.id.sendLay, 12);
        sViewsWithIds.put(R.id.playPauseChat, 13);
        sViewsWithIds.put(R.id.siriDViewChat, 14);
        sViewsWithIds.put(R.id.txtTimerSend, 15);
        sViewsWithIds.put(R.id.sentIview, 16);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityChatBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }
    private ActivityChatBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.support.design.widget.AppBarLayout) bindings[1]
            , (android.widget.TextView) bindings[11]
            , (com.escalate.utils.CircleImageView) bindings[4]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.ImageView) bindings[10]
            , (android.widget.TextView) bindings[8]
            , (android.widget.ImageView) bindings[13]
            , (android.widget.RelativeLayout) bindings[9]
            , (android.support.v7.widget.RecyclerView) bindings[7]
            , (android.widget.RelativeLayout) bindings[12]
            , (android.widget.ImageView) bindings[16]
            , (com.am.siriview.DrawView) bindings[14]
            , (android.support.v4.widget.SwipeRefreshLayout) bindings[6]
            , (android.support.v7.widget.Toolbar) bindings[2]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[15]
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