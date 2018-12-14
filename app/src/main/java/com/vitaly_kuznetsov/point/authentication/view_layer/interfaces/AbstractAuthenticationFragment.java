package com.vitaly_kuznetsov.point.authentication.view_layer.interfaces;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.vitaly_kuznetsov.point.authentication.presenter_layer.presenters.SignUpViewPresenter;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BaseContract;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicFragmentInterface;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicModelActionsInterface;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicStateActionsFragment;
import com.vitaly_kuznetsov.point.base_models.user_data_model.model.UserDataModel;

import java.util.Objects;

public abstract class AbstractAuthenticationFragment extends Fragment
        implements BasicFragmentInterface, BasicStateActionsFragment {

    protected UserDataModel userDataModel;
    private BasicModelActionsInterface presenter;

    //--------------Lifecycle Actions----------------

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            BaseContract.View view = (BaseContract.View) getActivity();
            presenter = (BasicModelActionsInterface) Objects.requireNonNull(view).getPresenter();
            this.userDataModel = presenter.getUserDataModel();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " AuthenticationView Interface.");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Activity currentActivity = getActivity();
        if (currentActivity instanceof AbstractAuthenticationView) {
            AbstractAuthenticationView authenticationView = (AbstractAuthenticationView) currentActivity;
            authenticationView.setFragment(this);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (presenter instanceof SignUpViewPresenter){
            ((SignUpViewPresenter) presenter).adaptUI();
        }
    }
}