package com.vitaly_kuznetsov.point.home.view_layer.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.home.view_layer.interfaces.BasicHomeFragment;

public class SettingsFragment extends Fragment implements BasicHomeFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        init(view);
        return view;
    }

    @Override
    public void init(View view) {

    }
}
