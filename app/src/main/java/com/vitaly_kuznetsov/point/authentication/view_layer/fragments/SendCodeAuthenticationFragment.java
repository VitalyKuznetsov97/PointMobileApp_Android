package com.vitaly_kuznetsov.point.authentication.view_layer.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces.AbstractAuthenticationPresenter;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.AbstractAuthenticationFragment;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.AbstractAuthenticationView;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.BasicCodeActionsFragment;

import java.util.Objects;

public class SendCodeAuthenticationFragment extends AbstractAuthenticationFragment implements BasicCodeActionsFragment {

    private ToggleButton toggleButton;
    private EditText editText;
    private TextView textView;
    private Button goButton;

    //--------------Lifecycle Actions----------------

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_code, container, false);
        init(view);
        return view;
    }

    //--------------Initialize Fragment upon creation----------------

    @Override
    public void init(View view) {
        final LinearLayout layout = view.findViewById(R.id.privacy_policy_linear_layout);
        toggleButton = view.findViewById(R.id.accept_privacy_policy_checkbox);
        goButton = view.findViewById(R.id.go_button);
        editText = view.findViewById(R.id.edit_text_0);
        textView = view.findViewById(R.id.error_text_view_0);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleButton.setChecked(!toggleButton.isChecked());
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AbstractAuthenticationPresenter presenter =
                        ((AbstractAuthenticationView) Objects.requireNonNull(getActivity())).getPresenter();
                presenter.onGoButtonClicked();
            }
        });
    }

    //--------------Save Fragment data, if Fragment is being--------------
    // -------------in onStop State or starts http request----------------

    @Override
    public void setViewsFromUserDataModel() {
        editText.setText(userDataModel.getSms());
    }

    @Override
    public void saveFragmentState() {
        this.userDataModel.setSms(String.valueOf(editText.getText()));
    }

    //--------------Checks if all info in fragment is filled correctly----------------

    @Override
    public boolean isReadyToProgress() {
        return toggleButton.isChecked() && editText.getText().length() == 6;
    }

    //--------------Error text manipulations----------------

    @Override
    public void setErrorText(String errorText) {
        this.textView.setText(errorText);
    }

    @Override
    public void showError() {
        this.textView.setAlpha(1);
        this.goButton.setActivated(true);
    }

    @Override
    public void hideError() {
        this.textView.setAlpha(0);
        this.goButton.setActivated(false);
        this.textView.setText(R.string.error_string_1);
    }
}
