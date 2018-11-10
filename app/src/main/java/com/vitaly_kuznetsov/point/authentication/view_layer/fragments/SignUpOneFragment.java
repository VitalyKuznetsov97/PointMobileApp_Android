package com.vitaly_kuznetsov.point.authentication.view_layer.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.AuthenticationFragment;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.SignUpView;
import com.vitaly_kuznetsov.point.base_models.UserDataModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class SignUpOneFragment extends Fragment implements AuthenticationFragment {

    private UserDataModel userDataModel;

    private EditText nameEditText;
    private ToggleButton maleButton;
    private ToggleButton femaleButton;
    private TextView calendarTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_one, container, false);
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

    @SuppressLint("ClickableViewAccessibility")
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

    private void setViewsFromUserDataModel(){

        nameEditText.setText(this.userDataModel.getUserName());

        if (this.userDataModel.getUserGender() == 0)
            maleButton.setChecked(true);
        else if (this.userDataModel.getUserGender() == 1)
            femaleButton.setChecked(true);

        if (this.userDataModel.getUserBirthDate() != null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            calendarTextView.setText(dateFormat.format(this.userDataModel.getUserBirthDate()));
        }
    }

    @Override
    public void saveFragmentState() {

        Log.i("Save: ", String.valueOf(1));
        this.userDataModel.setUserName(String.valueOf(this.nameEditText.getText()));

        if (maleButton.isChecked())
            this.userDataModel.setUserGender(0);
        else if (femaleButton.isChecked())
            this.userDataModel.setUserGender(1);
        else
            this.userDataModel.setUserGender(2);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        try {
            Date date = dateFormat.parse(String.valueOf(calendarTextView.getText()));
            this.userDataModel.setUserBirthDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isReadyToProgress() {
        return !String.valueOf(nameEditText.getText()).equals("")
                && (maleButton.isChecked() || femaleButton.isChecked());
    }
}