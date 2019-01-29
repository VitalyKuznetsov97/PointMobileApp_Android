package com.vitaly_kuznetsov.point.home.view_layer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicFragmentInterface;
import com.vitaly_kuznetsov.point.base_models.reusable_fragments.UserCardFragment;
import com.vitaly_kuznetsov.point.settings.view_layer.SettingsActivity;

import java.util.Objects;

public class SettingsFragment extends Fragment implements BasicFragmentInterface {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        init(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        fragmentTransaction.replace(R.id.constraint_layout_user_card_horizontal, new UserCardFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void init(View view) {
        ImageView imageView = view.findViewById(R.id.pencil);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean isReadyToProgress() {
        return true;
    }
}
