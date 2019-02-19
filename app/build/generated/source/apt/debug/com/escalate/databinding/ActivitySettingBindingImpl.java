package com.escalate.databinding;
import com.escalate.R;
import com.escalate.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivitySettingBindingImpl extends ActivitySettingBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.appbarlayout, 1);
        sViewsWithIds.put(R.id.toolbar, 2);
        sViewsWithIds.put(R.id.backIvSeting, 3);
        sViewsWithIds.put(R.id.toolbar_title, 4);
        sViewsWithIds.put(R.id.rel_user_profile, 5);
        sViewsWithIds.put(R.id.textTitle, 6);
        sViewsWithIds.put(R.id.swStatusCustom, 7);
        sViewsWithIds.put(R.id.changePassLay, 8);
        sViewsWithIds.put(R.id.lineChange, 9);
        sViewsWithIds.put(R.id.reportedUserLay, 10);
        sViewsWithIds.put(R.id.termsCondLay, 11);
        sViewsWithIds.put(R.id.relPrivacyPpolicy, 12);
        sViewsWithIds.put(R.id.relPostQueries, 13);
        sViewsWithIds.put(R.id.logoutLay, 14);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivitySettingBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }
    private ActivitySettingBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.support.design.widget.AppBarLayout) bindings[1]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.RelativeLayout) bindings[8]
            , (android.view.View) bindings[9]
            , (android.widget.RelativeLayout) bindings[14]
            , (android.widget.RelativeLayout) bindings[13]
            , (android.widget.RelativeLayout) bindings[12]
            , (android.widget.RelativeLayout) bindings[5]
            , (android.widget.RelativeLayout) bindings[10]
            , (android.support.v7.widget.SwitchCompat) bindings[7]
            , (android.widget.RelativeLayout) bindings[11]
            , (android.widget.TextView) bindings[6]
            , (android.support.v7.widget.Toolbar) bindings[2]
            , (android.widget.TextView) bindings[4]
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