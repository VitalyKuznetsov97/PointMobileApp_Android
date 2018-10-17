package com.vitaly_kuznetsov.point.view_layer.authentication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.view_layer.authentication.pickers.DatePickerFragment;

public class SignUpOneFragment extends Fragment {

    private DialogFragment datePickerFragment;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_one, container, false);
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
        ToggleButton maleButton = view.findViewById(R.id.gender_male_button);
        ToggleButton femaleButton = view.findViewById(R.id.gender_female_button);
        ConstraintLayout calendarConstraintLayout = view.findViewById(R.id.calendar_constraint_layout);

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

        calendarConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }

    private void setCompoundButton(View view, CompoundButton compoundButton, boolean isChecked){
        if (isChecked){
            compoundButton.setTextColor(view.getResources().getColor(R.color.colorBlackField));
        }
        else {
            compoundButton.setTextColor(view.getResources().getColor(R.color.colorDescriptionGrey));
        }
    }

    private void showDatePickerDialog() {
        datePickerFragment = new DatePickerFragment();
        if (getFragmentManager() != null) {
            datePickerFragment.show(getFragmentManager(), "datePicker");
        }
        else {
            Log.i("DatePickerInfo:", "Failed to get a FragmentManager.");
        }
    }
}