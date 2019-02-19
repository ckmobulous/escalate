package com.escalate.databinding;
import com.escalate.R;
import com.escalate.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentSignUpBindingImpl extends FragmentSignUpBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.linear, 1);
        sViewsWithIds.put(R.id.cv1, 2);
        sViewsWithIds.put(R.id.edtFullName, 3);
        sViewsWithIds.put(R.id.edtUsrNamSinUp, 4);
        sViewsWithIds.put(R.id.edtEmailSinUp, 5);
        sViewsWithIds.put(R.id.ccpSinUp, 6);
        sViewsWithIds.put(R.id.phnEtdTxt, 7);
        sViewsWithIds.put(R.id.edtPaswrdSinUp, 8);
        sViewsWithIds.put(R.id.imgEyePasWd, 9);
        sViewsWithIds.put(R.id.edtCnfPaswrd, 10);
        sViewsWithIds.put(R.id.imgCnfEvePasWd, 11);
        sViewsWithIds.put(R.id.termsLay, 12);
        sViewsWithIds.put(R.id.imgboxSup, 13);
        sViewsWithIds.put(R.id.tvRemember, 14);
        sViewsWithIds.put(R.id.notifiLay, 15);
        sViewsWithIds.put(R.id.imgVnotificatin, 16);
        sViewsWithIds.put(R.id.tvNotification, 17);
        sViewsWithIds.put(R.id.btnSinUp, 18);
    }
    // views
    @NonNull
    private final android.support.v4.widget.NestedScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentSignUpBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 19, sIncludes, sViewsWithIds));
    }
    private FragmentSignUpBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[18]
            , (com.hbb20.CountryCodePicker) bindings[6]
            , (android.support.v7.widget.CardView) bindings[2]
            , (android.widget.EditText) bindings[10]
            , (android.widget.EditText) bindings[5]
            , (android.widget.EditText) bindings[3]
            , (android.widget.EditText) bindings[8]
            , (android.widget.EditText) bindings[4]
            , (android.widget.ImageView) bindings[11]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.ImageView) bindings[16]
            , (android.widget.ImageView) bindings[13]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.RelativeLayout) bindings[15]
            , (android.widget.EditText) bindings[7]
            , (android.widget.RelativeLayout) bindings[12]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[14]
            );
        this.mboundView0 = (android.support.v4.widget.NestedScrollView) bindings[0];
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