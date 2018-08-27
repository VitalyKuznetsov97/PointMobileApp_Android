package com.vitaly_kuznetsov.point.presentation_layer.sign_up_fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vitaly_kuznetsov.point.R;

public class SignUpTwoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up_two, container, false);
    }

    interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            OnFragmentInteractionListener mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " has to implement OnFragmentInteractionListener");
        }
    }
}