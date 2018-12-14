package com.vitaly_kuznetsov.point.authentication.presenter_layer.presenters;

import com.vitaly_kuznetsov.point.authentication.model_layer.server.login.LoginCheckPhoneRequestController;
import com.vitaly_kuznetsov.point.authentication.model_layer.server.login.LoginSubmitSmsRequestController;
import com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces.AbstractAuthenticationPresenter;
import com.vitaly_kuznetsov.point.authentication.view_layer.fragments.GetCodeAuthenticationFragment;
import com.vitaly_kuznetsov.point.authentication.view_layer.fragments.SendCodeAuthenticationFragment;

public class LogInViewPresenter extends AbstractAuthenticationPresenter {

    @Override
    public boolean onNextFragmentClicked() {
        if (super.onNextFragmentClicked()){
            if (currentStep == 0)
                this.currentView.showAuthenticationFragment(new GetCodeAuthenticationFragment());
            else if (currentStep == 1)
                this.currentView.showAuthenticationFragment(new SendCodeAuthenticationFragment());
            else if (currentStep == 2) {
                this.userDataModel.setToken(this.postModel.getPayload().getData().getToken());
                currentView.goToHomeActivity(this.postModel);
            }
        }
        return false;
    }

    @Override
    public void onGetVerificationCodeButtonClicked() {
        if (mayStartHttpRequest())
            LoginCheckPhoneRequestController.getInstance().start(this);
    }

    @Override
    public void onGoButtonClicked() {
        if (mayStartHttpRequest())
            LoginSubmitSmsRequestController.getInstance().start(this);
    }
}
