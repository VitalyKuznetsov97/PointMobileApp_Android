package com.vitaly_kuznetsov.point.authentication.view_layer.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.AuthenticationFragment;

public class SendCodeFragment extends Fragment implements AuthenticationFragment{

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_code, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        final LinearLayout layout = view.findViewById(R.id.privacy_policy_linear_layout);
        final ToggleButton toggleButton = view.findViewById(R.id.accept_privacy_policy_checkbox);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleButton.setChecked(!toggleButton.isChecked());
            }
        });
    }

    @Override
    public void saveFragmentState() {}

    @Override
    public boolean isReadyToProgress() {
        return false;
    }
}
