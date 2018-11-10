package com.vitaly_kuznetsov.point.authentication.presenter_layer.presenters;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.authentication.model_layer.user_model_preferences.ModelHandler;
import com.vitaly_kuznetsov.point.authentication.view_layer.fragments.GetCodeFragment;
import com.vitaly_kuznetsov.point.authentication.view_layer.fragments.SendCodeFragment;
import com.vitaly_kuznetsov.point.authentication.view_layer.fragments.SignUpOneFragment;
import com.vitaly_kuznetsov.point.authentication.view_layer.fragments.SignUpTwoFragment;
import com.vitaly_kuznetsov.point.base_models.BaseContract;
import com.vitaly_kuznetsov.point.base_models.UserDataModel;
import com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces.SignUpPresenterInterface;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.SignUpView;

public class SignUpViewPresenter implements SignUpPresenterInterface {

    private UserDataModel userDataModel;
    private SignUpView signUpView;
    private int currentStep;

    @Override
    public void attachView(BaseContract.View view) {
        if (view instanceof SignUpView)
            this.signUpView = (SignUpView) view;

        if (this.userDataModel == null)
            this.userDataModel = ModelHandler.getInstance((Context) view);

        currentStep = -1;
    }

    @Override
    public void detachView() {
        this.signUpView = null;
    }

    @Override
    public void onGoBackClicked() {
        signUpView.goToMainActivity();
    }

    @Override
    public void onChangeActivityClicked() {
        signUpView.changeAuthenticationActivity();
    }

    @Override
    public void onTryAgainTextViewClicked() {}

    @Override
    public void onNextFragmentClicked() {

        if (signUpView.getFragment() == null || signUpView.getFragment().isReadyToProgress()) {
            currentStep++;
            signUpView.setSteps(currentStep);
            this.signUpView.showAuthenticationFragment(showCurrentFragment());
        }
        else
        {
            signUpView.showError();
        }
    }

    private Fragment showCurrentFragment(){
        if (currentStep == 0) {
            this.signUpView.setDescriptionText(R.string.description_1);
            this.signUpView.showNextButton();
            this.signUpView.hideSkipButton();
            return new SignUpOneFragment();
        }
        else if (currentStep == 1) {
            this.signUpView.setDescriptionText(R.string.description_3);
            this.signUpView.showNextButton();
            this.signUpView.showSkipButton();
            return new SignUpTwoFragment();
        }
        else  if (currentStep == 2) {
            this.signUpView.setDescriptionText(R.string.description_4);
            this.signUpView.hideSkipButton();
            this.signUpView.hideNextButton();
            return new GetCodeFragment();
        }
        else {
            this.signUpView.setDescriptionText(R.string.description_4);
            this.signUpView.hideSkipButton();
            this.signUpView.hideNextButton();
            return new SendCodeFragment();
        }
    }

    @Override
    public void onSkipTextViewClicked() {
        currentStep++;

        signUpView.setSteps(currentStep);

        this.signUpView.showAuthenticationFragment(showCurrentFragment());
    }

    @Override
    public void onBackPressed() {
        if (currentStep != 0) {
            currentStep--;

            signUpView.setSteps(currentStep);

            showCurrentFragment();
        }
    }

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
        signUpView.hideError();
    }
}
