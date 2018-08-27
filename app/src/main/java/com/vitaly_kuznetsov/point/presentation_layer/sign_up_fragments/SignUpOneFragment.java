package com.vitaly_kuznetsov.point.presentation_layer.sign_up_fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.vitaly_kuznetsov.point.R;

public class SignUpOneFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_one, container, false);
        initializeViews(view);
        return view;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " has to implement OnFragmentInteractionListener");
        }
    }

    private static void initializeViews(final View view){
        final ToggleButton maleButton = view.findViewById(R.id.gender_male_button);
        ToggleButton femaleButton = view.findViewById(R.id.gender_female_button);

        maleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                setCompoundButton(view, compoundButton, isChecked);
            }
        });

        femaleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                setCompoundButton(view, compoundButton, isChecked);
            }
        });
    }

    private static void setCompoundButton(View view, CompoundButton compoundButton, boolean isChecked){
        if (isChecked){
            compoundButton.setTextColor(view.getResources().getColor(R.color.colorBlackField));
        }
        else {
            compoundButton.setTextColor(view.getResources().getColor(R.color.colorDescriptionGrey));
        }
    }
}