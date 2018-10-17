package com.vitaly_kuznetsov.point.view_layer.authentication.fragments;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.vitaly_kuznetsov.point.R;

public class GetCodeFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_code, container, false);
        initializeViews(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " has to implement OnFragmentInteractionListener");
        }
    }

    private void initializeViews(final View view){
        Button verificationButton = view.findViewById(R.id.verification_button);
        final LinearLayout layout = view.findViewById(R.id.privacy_policy_linear_layout);
        final ToggleButton toggleButton = view.findViewById(R.id.accept_privacy_policy_checkbox);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleButton.setChecked(!toggleButton.isChecked());
            }
        });

        verificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setVisibility(View.GONE);
            }
        });
    }
}