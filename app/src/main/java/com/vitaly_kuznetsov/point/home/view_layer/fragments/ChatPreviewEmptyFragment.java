package com.vitaly_kuznetsov.point.home.view_layer.fragments;

import android.app.ProgressDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BaseContract;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicFragmentInterface;
import com.vitaly_kuznetsov.point.home.presenter_layer.HomeViewPresenter;
import com.vitaly_kuznetsov.point.home.view_layer.activities.HomeActivity;
import com.vitaly_kuznetsov.point.home.view_layer.interfaces.ChatPreviewFragmentInterface;

import java.util.Objects;

public class ChatPreviewEmptyFragment extends Fragment implements BasicFragmentInterface, ChatPreviewFragmentInterface {

    private HomeViewPresenter currentPresenter;
    private ProgressDialog progressDialog;

    private ConstraintLayout mainLayout;
    private TextView textView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_empty, container, false);
        init(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        currentPresenter.onMessagePreviewOpened();
    }

    @Override
    public void init(View view) {
        HomeActivity homeActivity = (HomeActivity) getActivity();
        currentPresenter = Objects.requireNonNull(homeActivity).getCurrentPresenter();
        currentPresenter.attachView((BaseContract.View) getActivity());

        textView = view.findViewById(R.id.text_description_2);
        mainLayout = view.findViewById(R.id.constraint_layout_main);
    }

    @Override
    public void showLayout() {
        mainLayout.setAlpha(1);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPresenter.onMessagePreviewOpened();
            }
        });
    }

    @Override
    public void hideLayout() {
        mainLayout.setAlpha(0);
        textView.setOnClickListener(null);
    }

    @Override
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.cancel();
        progressDialog = null;
    }

    @Override
    public boolean isReadyToProgress() {
        return true;
    }
}
