package com.vitaly_kuznetsov.point.authentication.view_layer.fragments;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.authentication.presenter_layer.presenters.SignUpViewPresenter;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.AuthenticationFragment;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.AuthenticationView;
import com.vitaly_kuznetsov.point.base_models.UserDataModel;

import java.util.Objects;

public class GetCodeFragment extends Fragment implements AuthenticationFragment {

    private UserDataModel userDataModel;

    private EditText phoneNumberEditText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_code, container, false);
        init(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (this.userDataModel == null){
                AuthenticationView view = (AuthenticationView) Objects.requireNonNull(getActivity());
                this.userDataModel = view.getPresenter().getUserDataModel();
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " AuthenticationView Interface.");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        saveFragmentState();
    }

    private void init(View view) {
        phoneNumberEditText = view.findViewById(R.id.edit_text_0);
        Button getVerificationCode = view.findViewById(R.id.get_a_verificatiom_code_button);

        getVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AuthenticationView)
                        Objects.requireNonNull(getActivity()))
                        .getPresenter().onNextFragmentClicked();
            }
        });
        setViewsFromUserDataModel();
    }

    private void setViewsFromUserDataModel(){
        phoneNumberEditText.setText(this.userDataModel.getPhoneNumber());
    }

    @Override
    public void saveFragmentState() {
        Log.i("Save: ", String.valueOf(3));
        this.userDataModel.setPhoneNumber(String.valueOf(this.phoneNumberEditText.getText()));
    }

    @Override
    public boolean isReadyToProgress() {
        return !String.valueOf(phoneNumberEditText.getText()).equals("");
    }
}