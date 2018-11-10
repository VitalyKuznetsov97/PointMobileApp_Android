package com.vitaly_kuznetsov.point.authentication.view_layer.fragments;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.AuthenticationFragment;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.SignUpView;
import com.vitaly_kuznetsov.point.base_models.UserDataModel;

import java.util.ArrayList;
import java.util.Objects;

public class SignUpTwoFragment extends Fragment implements AuthenticationFragment {

    private UserDataModel userDataModel;

    private ToggleButton allAgeToggleButton;
    private ArrayList<ToggleButton> ageToggleButtonArrayList;
    private ToggleButton maleToggleButton;
    private ToggleButton femaleToggleButton;
    private ToggleButton allGenderButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_two, container, false);
        init(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (this.userDataModel == null){
                SignUpView signUpView = (SignUpView) Objects.requireNonNull(getActivity());
                this.userDataModel = signUpView.getPresenter().getUserDataModel();
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

    public void init(View view) {
        //View and array initialization
        GridLayout gridLayout = view.findViewById(R.id.grid_layout_0);
        ConstraintLayout constraintLayout = view.findViewById(R.id.constraint_layout_1);

        allAgeToggleButton = view.findViewById(R.id.age_group_button_5);
        ageToggleButtonArrayList = new ArrayList<>();
        for (int i = 0; i < gridLayout.getChildCount() - 2; i += 2) {
            ageToggleButtonArrayList.add((ToggleButton) gridLayout.getChildAt(i));
        }

        //Checks if all age groups are checked at once and sets all ages button checked if so
        for (ToggleButton toggleButton : ageToggleButtonArrayList){
            toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (allAgeToggleButton.isChecked())
                        allAgeToggleButton.setChecked(false);
                    else {
                        boolean flag = true;
                        int i = 0;
                        while (flag && i < 5) {
                            if (!ageToggleButtonArrayList.get(i).isChecked()) flag = false;
                            i++;
                        }
                        if (flag) {
                            allAgeToggleButton.setChecked(true);
                        }
                    }
                }
            });
        }

        //makes the layout of genders being clickable
        for (int i = 0; i < constraintLayout.getChildCount(); i += 2) {
            LinearLayout linearLayout = (LinearLayout) constraintLayout.getChildAt(i);
            final ToggleButton button = (ToggleButton) linearLayout.getChildAt(2);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    button.setChecked(!button.isChecked());
                }
            });
        }

        allAgeToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    for (ToggleButton button : ageToggleButtonArrayList) {
                        button.setChecked(false);
                    }
                }
            }
        });

        //Ensures only one gender is checked at once
        maleToggleButton = view.findViewById(R.id.gender_male_button);
        femaleToggleButton = view.findViewById(R.id.gender_female_button);
        allGenderButton = view.findViewById(R.id.gender_all_gender_button);

        maleToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    if (femaleToggleButton.isChecked()) femaleToggleButton.setChecked(false);
                    if (allGenderButton.isChecked()) allGenderButton.setChecked(false);
                }
            }
        });

        femaleToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    if (maleToggleButton.isChecked()) maleToggleButton.setChecked(false);
                    if (allGenderButton.isChecked()) allGenderButton.setChecked(false);
                }
            }
        });

        allGenderButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    if (femaleToggleButton.isChecked()) femaleToggleButton.setChecked(false);
                    if (maleToggleButton.isChecked()) maleToggleButton.setChecked(false);
                }
            }
        });

        setViewsFromUserDataModel();
    }

    private void setViewsFromUserDataModel(){

        if (this.userDataModel.getPreferredGender() == 0)
            maleToggleButton.setChecked(true);
        else if (this.userDataModel.getPreferredGender() == 1)
            femaleToggleButton.setChecked(true);
        else if (this.userDataModel.getPreferredGender() == 2)
            allGenderButton.setChecked(true);

        ArrayList<Integer> agesArrayList = this.userDataModel.getPreferredAge();
        if (agesArrayList.contains(5))
            allAgeToggleButton.setChecked(true);
        else {
            for (int i : agesArrayList) {
                ageToggleButtonArrayList.get(i).setChecked(true);
            }
        }
    }

    @Override
    public void saveFragmentState() {
        Log.i("Save: ", String.valueOf(2));
        if (maleToggleButton.isChecked())
            this.userDataModel.setPreferredGender(0);
        else if (femaleToggleButton.isChecked())
            this.userDataModel.setPreferredGender(1);
        else if (allGenderButton.isChecked())
            this.userDataModel.setPreferredGender(2);
        else
            this.userDataModel.setPreferredGender(3);

        int i = 0;
        ArrayList<Integer> agesArrayList = new ArrayList<>();
        for (ToggleButton button : ageToggleButtonArrayList){
            if(button.isChecked()) agesArrayList.add(i);
            i++;
        }
        if (allAgeToggleButton.isChecked()) agesArrayList.add(5);
        this.userDataModel.setPreferredAge(agesArrayList);
    }

    @Override
    public boolean isReadyToProgress() {
        boolean ageArrayFlag = false;
        for (ToggleButton button : ageToggleButtonArrayList)
            if (button.isChecked()) ageArrayFlag = true;

        return (maleToggleButton.isChecked() || femaleToggleButton.isChecked() || allGenderButton.isChecked())
                && (allAgeToggleButton.isChecked() || ageArrayFlag);
    }
}