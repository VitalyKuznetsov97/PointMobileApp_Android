package com.vitaly_kuznetsov.point.base_models.reusable_fragments;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.AbstractAuthenticationFragment;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.BasicUIActionsFragment;

import java.util.ArrayList;

public class SignUpTwoFragment extends AbstractAuthenticationFragment implements BasicUIActionsFragment {

    private ToggleButton allAgeToggleButton;
    private ArrayList<ToggleButton> ageToggleButtonArrayList;
    private ToggleButton maleToggleButton;
    private ToggleButton femaleToggleButton;
    private ToggleButton allGenderButton;

    //--------------Lifecycle Actions----------------

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_two, container, false);
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

    @Override
    public void setViewsFromUserDataModel(){

        if (this.userDataModel.getYourGender() == 1)
            maleToggleButton.setChecked(true);
        else if (this.userDataModel.getYourGender() == 0)
            femaleToggleButton.setChecked(true);
        else if (this.userDataModel.getYourGender() == -1)
            allGenderButton.setChecked(true);

        ArrayList<String> agesArrayList = this.userDataModel.getYourAge();
        if (agesArrayList.contains(getStringById(R.string.age_group_five)))
            allAgeToggleButton.setChecked(true);
        else {
            for (String age : agesArrayList) {
                if (age.equals(getStringById(R.string.age_group_zero)))
                    ageToggleButtonArrayList.get(0).setChecked(true);
                else if (age.equals(getStringById(R.string.age_group_one)))
                    ageToggleButtonArrayList.get(1).setChecked(true);
                else if (age.equals(getStringById(R.string.age_group_two)))
                    ageToggleButtonArrayList.get(2).setChecked(true);
                else if (age.equals(getStringById(R.string.age_group_three)))
                    ageToggleButtonArrayList.get(3).setChecked(true);
                else if (age.equals(getStringById(R.string.age_group_four)))
                    ageToggleButtonArrayList.get(4).setChecked(true);
            }
        }
    }

    private String getStringById(int id){
        return getResources().getString(id);
    }

    //--------------Save Fragment data, if Fragment is being--------------
    // -------------in onStop State or starts http request----------------

    @Override
    public void saveFragmentState() {
        if (maleToggleButton.isChecked())
            this.userDataModel.setYourGender(1);
        else if (femaleToggleButton.isChecked())
            this.userDataModel.setYourGender(0);
        else if (allGenderButton.isChecked())
            this.userDataModel.setYourGender(-1);
        else
            this.userDataModel.setYourGender(2);

        ArrayList<String> agesArrayList = new ArrayList<>();
        if (allAgeToggleButton.isChecked())
            agesArrayList.add(String.valueOf(allAgeToggleButton.getText()));
        else {
            for (ToggleButton button : ageToggleButtonArrayList)
                if(button.isChecked()) agesArrayList.add(String.valueOf(button.getText()));
        }

        this.userDataModel.setYourAge(agesArrayList);
    }

    //--------------Checks if all info in fragment is filled correctly----------------

    @Override
    public boolean isReadyToProgress() {
        boolean ageArrayFlag = false;
        for (ToggleButton button : ageToggleButtonArrayList)
            if (button.isChecked()) ageArrayFlag = true;

        return (maleToggleButton.isChecked() || femaleToggleButton.isChecked() || allGenderButton.isChecked())
                && (allAgeToggleButton.isChecked() || ageArrayFlag);
    }
}