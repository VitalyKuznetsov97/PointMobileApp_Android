package com.vitaly_kuznetsov.point.authentication.view_layer.interfaces;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces.AbstractAuthenticationPresenter;
import com.vitaly_kuznetsov.point.authentication.presenter_layer.presenters.SignUpViewPresenter;
import com.vitaly_kuznetsov.point.base_models.user_data_model.model.UserDataModel;

import java.util.Objects;

public abstract class AbstractAuthenticationFragment extends Fragment
        implements AuthenticationFragment {

    protected UserDataModel userDataModel;

    //--------------Lifecycle Actions----------------

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.userDataModel = ((AbstractAuthenticationView)
                    Objects.requireNonNull(getActivity())).
                    getPresenter().getUserDataModel();

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " AuthenticationView Interface.");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        AbstractAuthenticationView authenticationView = (AbstractAuthenticationView) getActivity();
        if (authenticationView != null) {
            authenticationView.setFragment(this);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AbstractAuthenticationPresenter presenter =
                ((AbstractAuthenticationView) Objects.requireNonNull(getActivity())).getPresenter();
        if (presenter instanceof SignUpViewPresenter){
            ((SignUpViewPresenter) presenter).adaptUI();
        }
    }
}
