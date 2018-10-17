package com.vitaly_kuznetsov.point.view_layer.authentication.fragments;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.vitaly_kuznetsov.point.R;

public class SignUpTwoFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_two, container, false);
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
        GridLayout gridLayout = view.findViewById(R.id.grid_layout_0);
        ConstraintLayout constraintLayout = view.findViewById(R.id.constraint_layout_1);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            if (gridLayout.getChildAt(i) instanceof ToggleButton){
                    ToggleButton childButton = (ToggleButton) gridLayout.getChildAt(i);
                    childButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                            setCompoundButton(view, compoundButton, isChecked);
                            }
                    });
            }
        }

        for (int i = 0; i < constraintLayout.getChildCount(); i++) {
            if (constraintLayout.getChildAt(i) instanceof  LinearLayout){
                LinearLayout linearLayout = (LinearLayout) constraintLayout.getChildAt(i);
                final ToggleButton toggleButton = (ToggleButton) linearLayout.getChildAt(2);
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toggleButton.setChecked(!toggleButton.isChecked());
                    }
                });
            }
        }
    }

    private void setCompoundButton(View view, CompoundButton compoundButton, boolean isChecked){
        if (isChecked){
            compoundButton.setTextColor(view.getResources().getColor(R.color.colorBlackField));
        }
        else {
            compoundButton.setTextColor(view.getResources().getColor(R.color.colorDescriptionGrey));
        }
    }
}