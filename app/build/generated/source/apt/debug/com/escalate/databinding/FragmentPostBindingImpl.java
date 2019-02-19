package com.escalate.databinding;
import com.escalate.R;
import com.escalate.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentPostBindingImpl extends FragmentPostBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.linear, 1);
        sViewsWithIds.put(R.id.edtTxtSearchPost, 2);
        sViewsWithIds.put(R.id.recViewCateg, 3);
        sViewsWithIds.put(R.id.tvNoDataCategories, 4);
        sViewsWithIds.put(R.id.edt_old_pass, 5);
        sViewsWithIds.put(R.id.edtDesc, 6);
        sViewsWithIds.put(R.id.recoedRelLay, 7);
        sViewsWithIds.put(R.id.txtTimerP, 8);
        sViewsWithIds.put(R.id.recordButton, 9);
        sViewsWithIds.put(R.id.relativePostMic, 10);
        sViewsWithIds.put(R.id.event_ll, 11);
        sViewsWithIds.put(R.id.txtTimerPost, 12);
        sViewsWithIds.put(R.id.SeekBarPost, 13);
        sViewsWithIds.put(R.id.siriDViewPost, 14);
        sViewsWithIds.put(R.id.waveProDialog, 15);
        sViewsWithIds.put(R.id.imgPlayPausePost, 16);
        sViewsWithIds.put(R.id.imgRePlay, 17);
        sViewsWithIds.put(R.id.btnPost, 18);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentPostBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 19, sIncludes, sViewsWithIds));
    }
    private FragmentPostBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.SeekBar) bindings[13]
            , (android.widget.Button) bindings[18]
            , (android.widget.EditText) bindings[6]
            , (android.widget.EditText) bindings[5]
            , (android.widget.EditText) bindings[2]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.ImageView) bindings[16]
            , (android.widget.ImageView) bindings[17]
            , (android.widget.LinearLayout) bindings[1]
            , (android.support.v7.widget.RecyclerView) bindings[3]
            , (android.widget.RelativeLayout) bindings[7]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.RelativeLayout) bindings[10]
            , (com.am.siriview.DrawView) bindings[14]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[12]
            , (rm.com.audiowave.AudioWaveView) bindings[15]
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