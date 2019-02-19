package com.escalate.databinding;
import com.escalate.R;
import com.escalate.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentUserFrofileBindingImpl extends FragmentUserFrofileBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.linear_profile, 1);
        sViewsWithIds.put(R.id.card_view, 2);
        sViewsWithIds.put(R.id.linear_view_other, 3);
        sViewsWithIds.put(R.id.imgUserPro, 4);
        sViewsWithIds.put(R.id.nameTxtUsr, 5);
        sViewsWithIds.put(R.id.txtUsername, 6);
        sViewsWithIds.put(R.id.txtPhone, 7);
        sViewsWithIds.put(R.id.txtMailUsr, 8);
        sViewsWithIds.put(R.id.editProLay, 9);
        sViewsWithIds.put(R.id.txtProfile, 10);
        sViewsWithIds.put(R.id.bioLayUsr, 11);
        sViewsWithIds.put(R.id.txt_bio, 12);
        sViewsWithIds.put(R.id.imgPlayPauseUsr, 13);
        sViewsWithIds.put(R.id.siriDViewUsrPro, 14);
        sViewsWithIds.put(R.id.wavePro, 15);
        sViewsWithIds.put(R.id.SeekBarUssr, 16);
        sViewsWithIds.put(R.id.txtTimerUsr, 17);
        sViewsWithIds.put(R.id.txtGenreusr, 18);
        sViewsWithIds.put(R.id.linear_box, 19);
        sViewsWithIds.put(R.id.linearPostUsr, 20);
        sViewsWithIds.put(R.id.txtPostsCountUsr, 21);
        sViewsWithIds.put(R.id.txtPostUsr, 22);
        sViewsWithIds.put(R.id.linearFollowerUsr, 23);
        sViewsWithIds.put(R.id.txtCountFolowerU, 24);
        sViewsWithIds.put(R.id.txtFollowerU, 25);
        sViewsWithIds.put(R.id.linearFollowingUsr, 26);
        sViewsWithIds.put(R.id.txtCountFollowingU, 27);
        sViewsWithIds.put(R.id.txtFollowingU, 28);
        sViewsWithIds.put(R.id.recyVewUserPro, 29);
        sViewsWithIds.put(R.id.tvNoDataUsr, 30);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentUserFrofileBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 31, sIncludes, sViewsWithIds));
    }
    private FragmentUserFrofileBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.SeekBar) bindings[16]
            , (android.widget.RelativeLayout) bindings[11]
            , (android.support.v7.widget.CardView) bindings[2]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.ImageView) bindings[13]
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[4]
            , (android.widget.LinearLayout) bindings[19]
            , (android.widget.LinearLayout) bindings[23]
            , (android.widget.LinearLayout) bindings[26]
            , (android.widget.LinearLayout) bindings[20]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.LinearLayout) bindings[3]
            , (android.widget.TextView) bindings[5]
            , (android.support.v7.widget.RecyclerView) bindings[29]
            , (com.am.siriview.DrawView) bindings[14]
            , (android.widget.TextView) bindings[30]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[27]
            , (android.widget.TextView) bindings[24]
            , (android.widget.TextView) bindings[25]
            , (android.widget.TextView) bindings[28]
            , (android.widget.TextView) bindings[18]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[22]
            , (android.widget.TextView) bindings[21]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[6]
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