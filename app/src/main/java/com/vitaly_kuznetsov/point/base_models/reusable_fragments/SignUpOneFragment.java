package com.vitaly_kuznetsov.point.base_models.reusable_fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.authentication.view_layer.fragments.DatePickerFragment;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.AbstractAuthenticationFragment;

import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.BasicUIActionsFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SignUpOneFragment extends AbstractAuthenticationFragment implements BasicUIActionsFragment {

    private EditText nameEditText;
    private ToggleButton maleButton;
    private ToggleButton femaleButton;
    private TextView calendarTextView;

    //--------------Lifecycle Actions----------------

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_one, container, false);
        init(view);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        saveFragmentState();
    }

    //--------------Initialize Fragment upon creation----------------

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void init(View view) {

        maleButton = view.findViewById(R.id.gender_male_button);
        femaleButton = view.findViewById(R.id.gender_female_button);
        nameEditText = view.findViewById(R.id.edit_text_0);
        calendarTextView = view.findViewById(R.id.calendar_text_view);
        ConstraintLayout calendarConstraintLayout = view.findViewById(R.id.calendar_constraint_layout);

        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (maleButton.isChecked() && femaleButton.isChecked())
                    femaleButton.setChecked(false);
            }
        });

        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (femaleButton.isChecked() && maleButton.isChecked())
                    maleButton.setChecked(false);
            }
        });

        calendarConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager() != null) {
                    new DatePickerFragment().show(getFragmentManager(), "datePicker");
                }
            }
        });

        setViewsFromUserDataModel();
    }

    @Override
    public void setViewsFromUserDataModel(){

        nameEditText.setText(this.userDataModel.getNickname());

        if (this.userDataModel.getMyGender() == 1)
            maleButton.setChecked(true);
        else if (this.userDataModel.getMyGender() == 0)
            femaleButton.setChecked(true);

        if (this.userDataModel.getMyAge() != null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            calendarTextView.setText(dateFormat.format(this.userDataModel.getMyAge()));
        }
    }

    //--------------Save Fragment data, if Fragment is being--------------
    // -------------in onStop State or starts http request----------------

    @Override
    public void saveFragmentState() {
        this.userDataModel.setNickname(String.valueOf(this.nameEditText.getText()));

        if (maleButton.isChecked())
            this.userDataModel.setMyGender(1);
        else if (femaleButton.isChecked())
            this.userDataModel.setMyGender(0);
        else
            this.userDataModel.setMyGender(2);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        try {
            Date date = dateFormat.parse(String.valueOf(calendarTextView.getText()));
            this.userDataModel.setMyAge(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //--------------Checks if all info in fragment is filled correctly----------------

    @Override
    public boolean isReadyToProgress() {
        return !String.valueOf(nameEditText.getText()).equals("")
                && (maleButton.isChecked() || femaleButton.isChecked());
    }
}