package com.vitaly_kuznetsov.point.authentication.view_layer.fragments;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.AbstractAuthenticationFragment;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.AbstractAuthenticationView;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.BasicCodeActionsFragment;

import java.util.Objects;

public class GetCodeAuthenticationFragment extends AbstractAuthenticationFragment
        implements BasicCodeActionsFragment {

    private EditText phoneNumberEditText;
    private TextView textView;
    private Button getVerificationCode;

    //--------------Lifecycle Actions----------------

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_code, container, false);
        init(view);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        saveFragmentState();
    }

    //--------------Initialize Fragment upon creation----------------

    @Override
    public void init(View view) {
        phoneNumberEditText = view.findViewById(R.id.edit_text_0);
        textView = view.findViewById(R.id.error_text_view_0);
        getVerificationCode = view.findViewById(R.id.get_a_verification_code_button);

        getVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AbstractAuthenticationView)
                        Objects.requireNonNull(getActivity()))
                        .getPresenter().onGetVerificationCodeButtonClicked();
            }
        });

        setViewsFromUserDataModel();
    }

    @Override
    public void setViewsFromUserDataModel(){
        String phoneNumber = this.userDataModel.getPhone();
        phoneNumberEditText.setText(phoneNumber);
    }

    //--------------Save Fragment data, if Fragment is being--------------
    // -------------in onStop State or starts http request----------------

    @Override
    public void saveFragmentState() {
        String phoneNumber = String.valueOf(phoneNumberEditText.getText());
        this.userDataModel.setPhone(phoneNumber);
    }

    //--------------Checks if all info in fragment is filled correctly----------------

    @Override
    public boolean isReadyToProgress() {
        String phoneNumber = String.valueOf(phoneNumberEditText.getText());
        return PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber) &&
                !PhoneNumberUtils.isEmergencyNumber(phoneNumber) &&
                !PhoneNumberUtils.isLocalEmergencyNumber(getContext(), phoneNumber) &&
                android.util.Patterns.PHONE.matcher(phoneNumber).matches();
    }

    //--------------Error text manipulations----------------
    @Override
    public void setErrorText(String errorText) {
        this.textView.setText(errorText);
    }

    @Override
    public void showError() {
        this.textView.setAlpha(1);
        this.getVerificationCode.setActivated(true);
    }

    @Override
    public void hideError() {
        this.textView.setAlpha(0);
        this.getVerificationCode.setActivated(false);
        this.textView.setText(R.string.wrong_phone_number_textview);
    }
}