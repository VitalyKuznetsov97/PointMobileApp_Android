package com.vitaly_kuznetsov.point.authentication.presenter_layer.presenters;

import com.vitaly_kuznetsov.point.authentication.model_layer.server.login.LoginCheckPhoneRequestController;
import com.vitaly_kuznetsov.point.authentication.model_layer.server.login.LoginSubmitSmsRequestController;
import com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces.AbstractAuthenticationPresenter;
import com.vitaly_kuznetsov.point.authentication.view_layer.fragments.GetCodeFragment;
import com.vitaly_kuznetsov.point.authentication.view_layer.fragments.SendCodeFragment;

public class LogInViewPresenter extends AbstractAuthenticationPresenter {

    @Override
    public boolean onNextFragmentClicked() {
        if (super.onNextFragmentClicked()){
            if (currentStep == 0)
                this.currentView.showAuthenticationFragment(new GetCodeFragment());
            else if (currentStep == 1)
                this.currentView.showAuthenticationFragment(new SendCodeFragment());
            else if (currentStep == 2)
                currentView.goToHomeActivity(this.postModel);
        }
        return false;
    }

    @Override
    public boolean onGetVerificationCodeButtonClicked() {
        if (mayStartHttpRequest())
            LoginCheckPhoneRequestController.getInstance().start(this);

        return false;
    }

    @Override
    public boolean onGoButtonClicked() {
        if (mayStartHttpRequest())
            LoginSubmitSmsRequestController.getInstance().start(this);

        return false;
    }
}
