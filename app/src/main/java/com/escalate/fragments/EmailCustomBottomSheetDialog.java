package com.escalate.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.escalate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

import static android.content.Context.INPUT_METHOD_SERVICE;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class EmailCustomBottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    private EditText edtOtp;
    private ImageView imgCancelDialog;
    CallbackEmail callbackSignup;
    String email, otp;
    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private String verificationCode;

    public static EmailCustomBottomSheetDialog newInstance(String phoneNumber) {

        EmailCustomBottomSheetDialog myFragment = new EmailCustomBottomSheetDialog();

        Bundle args = new Bundle();
        args.putString("email", phoneNumber);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.content_dialog_bottom_sheet, container, false);

        edtOtp = v.findViewById(R.id.edt_otp);
        imgCancelDialog = v.findViewById(R.id.img_cancel_dialog);
        imgCancelDialog.setOnClickListener(this);
        addTextChangeListner();

        if (getArguments() != null) {

            email = getArguments().getString("email", "");
            Log.e("onCreateView", ": " + email);
        }

        return v;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void addTextChangeListner() {

        edtOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (edtOtp.getText().toString().length() == 6) {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    edtOtp.setCursorVisible(false);
                    edtOtp.requestFocus();
                    edtOtp.setCursorVisible(true);
                    dismiss();
                    callbackSignup.sendEmailOtpDetails(edtOtp.getText().toString());

                } else {

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_cancel_dialog:
                dismiss();
                break;
        }
    }

    public void callBackListener(CallbackEmail callbackSignup) {

        this.callbackSignup = callbackSignup;
    }

    public interface CallbackEmail {

        void sendEmailOtpDetails(String otp);
    }
}