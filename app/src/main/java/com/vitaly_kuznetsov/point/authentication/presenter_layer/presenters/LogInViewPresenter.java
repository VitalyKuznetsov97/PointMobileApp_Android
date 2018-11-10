package com.vitaly_kuznetsov.point.authentication.presenter_layer.presenters;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.vitaly_kuznetsov.point.authentication.model_layer.user_model_preferences.ModelHandler;
import com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces.AuthenticationPresenterInterface;
import com.vitaly_kuznetsov.point.authentication.view_layer.fragments.GetCodeFragment;
import com.vitaly_kuznetsov.point.authentication.view_layer.fragments.SendCodeFragment;
import com.vitaly_kuznetsov.point.base_models.BaseContract;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.LogInView;
import com.vitaly_kuznetsov.point.base_models.UserDataModel;

public class LogInViewPresenter implements AuthenticationPresenterInterface {

    private LogInView logInView;
    private UserDataModel userDataModel;
    private int currentStep;

    @Override
    public void attachView(BaseContract.View view) {
        if (view instanceof LogInView)
            this.logInView = (LogInView) view;

        if (this.userDataModel == null)
            this.userDataModel = ModelHandler.getInstance((Context) view);

        this.currentStep = -1;
    }

    private Fragment showCurrentFragment(){
        if (currentStep == 0)
            return new GetCodeFragment();
        else
            return new SendCodeFragment();
    }

    @Override
    public void detachView() {
        this.logInView = null;
    }

    @Override
    public void onNextFragmentClicked() {
        if (logInView.getFragment() == null || logInView.getFragment().isReadyToProgress()) {
            currentStep++;
            this.logInView.showAuthenticationFragment(showCurrentFragment());
        }
        else
        {
            logInView.showError();
        }
    }

    @Override
    public void onGoBackClicked() {
        logInView.goToMainActivity();
    }

    @Override
    public void onChangeActivityClicked() {
        logInView.changeAuthenticationActivity();
    }

    @Override
    public void onTryAgainTextViewClicked() { }

    @Override
    public UserDataModel getUserDataModel() {
        return userDataModel;
    }

    @Override
    public void applyUserDataModelChanges() {
        ModelHandler.changeUserDataModel(this.userDataModel);
    }

    @Override
    public void onAfterError() {
        logInView.hideError();
    }

    @Override
    public void onBackPressed() {
        if (currentStep != 0) {
            currentStep--;
        }
    }

}
