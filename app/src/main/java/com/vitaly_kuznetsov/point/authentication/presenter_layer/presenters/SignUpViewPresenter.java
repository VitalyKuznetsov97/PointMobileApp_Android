package com.vitaly_kuznetsov.point.authentication.presenter_layer.presenters;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.authentication.model_layer.server.registration.RegistrationCheckPhoneRequestController;
import com.vitaly_kuznetsov.point.authentication.model_layer.server.registration.RegistrationRegisterUserRequestController;
import com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces.AbstractAuthenticationPresenter;
import com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces.SignUpPresenterInterface;
import com.vitaly_kuznetsov.point.authentication.view_layer.fragments.GetCodeAuthenticationFragment;
import com.vitaly_kuznetsov.point.authentication.view_layer.fragments.SendCodeAuthenticationFragment;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.BasicUiActionsSignUp;
import com.vitaly_kuznetsov.point.base_models.reusable_fragments.SignUpOneAuthenticationFragment;
import com.vitaly_kuznetsov.point.base_models.reusable_fragments.SignUpTwoAuthenticationFragment;

public class SignUpViewPresenter extends AbstractAuthenticationPresenter
        implements SignUpPresenterInterface {

    //-----------------Change Fragment or go to main screen-------------------

    @Override
    public boolean onNextFragmentClicked() {
        if (super.onNextFragmentClicked()){
            ((BasicUiActionsSignUp) this.currentView).setSteps(this.currentStep);

            if (currentStep == 0)
                this.currentView.showAuthenticationFragment(new SignUpOneAuthenticationFragment());
            else if (currentStep == 1)
                this.currentView.showAuthenticationFragment(new SignUpTwoAuthenticationFragment());
            else if (currentStep == 2)
                this.currentView.showAuthenticationFragment(new GetCodeAuthenticationFragment());
            else if (currentStep == 3)
                this.currentView.showAuthenticationFragment(new SendCodeAuthenticationFragment());
            else if (currentStep == 4) {
                this.userDataModel.setToken(this.postModel.getPayload().getData().getToken());
                currentView.goToHomeActivity(this.postModel);
            }
        }
        return false;
    }

    //-----------------On buttons being clicked-------------------

    @Override
    public void onSkipTextViewClicked() {
        currentStep++;
        this.currentView.showAuthenticationFragment(new GetCodeAuthenticationFragment());
        ((BasicUiActionsSignUp) this.currentView).setSteps(this.currentStep);
    }

    @Override
    public boolean onBackPressed() {
        if (super.onBackPressed()){
            adaptUI();
        }
        return false;
    }

    //-----------------Also start a http request---------------------

    @Override
    public void onGetVerificationCodeButtonClicked() {
        if (mayStartHttpRequest())
            RegistrationCheckPhoneRequestController.getInstance().start(this);
    }

    @Override
    public void onGoButtonClicked() {
        if (mayStartHttpRequest())
            RegistrationRegisterUserRequestController.getInstance().start(this);
    }

    //---------------Set UI----------------

    @Override
    public void adaptUI(){
        BasicUiActionsSignUp signUpView = (BasicUiActionsSignUp) currentView;
        signUpView.setSteps(currentStep);

        if (currentStep == 0) {
            signUpView.setDescriptionText(R.string.description_1);
            signUpView.showNextButton();
            signUpView.hideSkipButton();
        }
        else if (currentStep == 1) {
            signUpView.setDescriptionText(R.string.description_3);
            signUpView.showNextButton();
            signUpView.showSkipButton();
        }
        else  if (currentStep == 2) {
            signUpView.setDescriptionText(R.string.description_4);
            signUpView.hideSkipButton();
            signUpView.hideNextButton();
        }
        else {
            signUpView.setDescriptionText(R.string.description_4);
            signUpView.hideSkipButton();
            signUpView.hideNextButton();
        }
    }

}