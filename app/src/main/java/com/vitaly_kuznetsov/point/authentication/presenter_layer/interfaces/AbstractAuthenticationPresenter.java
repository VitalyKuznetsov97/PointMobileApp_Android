package com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces;

import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.AbstractAuthenticationView;
import com.vitaly_kuznetsov.point.base_models.BaseContract;

//Abstract class, to be extended by other AuthenticationPresenters
public abstract class AbstractAuthenticationPresenter implements
        BaseContract.Presenter, BasicAuthenticationStateActions, BasicAuthenticationUi {

    private AbstractAuthenticationView currentView;
    private int currentStep;

    //-----------Presenter Functions------------

    @Override
    public void attachView(BaseContract.View view) {
        this.currentView = (AbstractAuthenticationView) view;
        this.currentStep = -1;
    }

    @Override
    public void detachView() {
        this.currentView = null;
    }

    //-----------UI Activity Functions------------

    @Override
    public void onGoBackClicked() {
        currentView.goToMainActivity();
    }

    @Override
    public void onChangeActivityClicked() {
        currentView.changeAuthenticationActivity();
    }

    @Override
    public void onTryAgainTextViewClicked() {}

    //-----------UI Activity Functions------------

    @Override
    public void onAfterError() {
        currentView.hideError();
    }

    @Override
    public void onBackPressed() {
        if (currentStep != 0) {
            currentStep--;
        }
    }
}
