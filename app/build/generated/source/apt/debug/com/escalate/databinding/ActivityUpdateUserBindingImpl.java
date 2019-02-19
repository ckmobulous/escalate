package com.escalate.databinding;
import com.escalate.R;
import com.escalate.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityUpdateUserBindingImpl extends ActivityUpdateUserBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.appbarlayout, 1);
        sViewsWithIds.put(R.id.toolbar, 2);
        sViewsWithIds.put(R.id.backIvew, 3);
        sViewsWithIds.put(R.id.img, 4);
        sViewsWithIds.put(R.id.frameProfilePick, 5);
        sViewsWithIds.put(R.id.cirImgUpdt, 6);
        sViewsWithIds.put(R.id.urlLay, 7);
        sViewsWithIds.put(R.id.txt_bio, 8);
        sViewsWithIds.put(R.id.txtTimerUpdt, 9);
        sViewsWithIds.put(R.id.imgPlayPsUpdt, 10);
        sViewsWithIds.put(R.id.siriDViewUpdt, 11);
        sViewsWithIds.put(R.id.wavePro, 12);
        sViewsWithIds.put(R.id.editBioIv, 13);
        sViewsWithIds.put(R.id.linear, 14);
        sViewsWithIds.put(R.id.edtName, 15);
        sViewsWithIds.put(R.id.imgNameEdit, 16);
        sViewsWithIds.put(R.id.edtUsrName, 17);
        sViewsWithIds.put(R.id.imgUsrNameEdit, 18);
        sViewsWithIds.put(R.id.edtEmailU, 19);
        sViewsWithIds.put(R.id.imgEmailUpdt, 20);
        sViewsWithIds.put(R.id.callIview, 21);
        sViewsWithIds.put(R.id.ccpProfile, 22);
        sViewsWithIds.put(R.id.edtPhoneUpdt, 23);
        sViewsWithIds.put(R.id.imgPhnupdt, 24);
        sViewsWithIds.put(R.id.relGenreLay, 25);
        sViewsWithIds.put(R.id.txtGenreU, 26);
        sViewsWithIds.put(R.id.rightIview, 27);
        sViewsWithIds.put(R.id.btnSave, 28);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityUpdateUserBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 29, sIncludes, sViewsWithIds));
    }
    private ActivityUpdateUserBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.support.design.widget.AppBarLayout) bindings[1]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.Button) bindings[28]
            , (android.widget.ImageView) bindings[21]
            , (com.hbb20.CountryCodePicker) bindings[22]
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[6]
            , (android.widget.ImageView) bindings[13]
            , (android.widget.EditText) bindings[19]
            , (android.widget.EditText) bindings[15]
            , (android.widget.EditText) bindings[23]
            , (android.widget.EditText) bindings[17]
            , (android.widget.FrameLayout) bindings[5]
            , (android.widget.ImageView) bindings[4]
            , (android.widget.ImageView) bindings[20]
            , (android.widget.ImageView) bindings[16]
            , (android.widget.ImageView) bindings[24]
            , (android.widget.ImageView) bindings[10]
            , (android.widget.ImageView) bindings[18]
            , (android.widget.LinearLayout) bindings[14]
            , (android.widget.RelativeLayout) bindings[25]
            , (android.widget.ImageView) bindings[27]
            , (com.am.siriview.DrawView) bindings[11]
            , (android.support.v7.widget.Toolbar) bindings[2]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[26]
            , (android.widget.TextView) bindings[9]
            , (android.widget.LinearLayout) bindings[7]
            , (rm.com.audiowave.AudioWaveView) bindings[12]
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