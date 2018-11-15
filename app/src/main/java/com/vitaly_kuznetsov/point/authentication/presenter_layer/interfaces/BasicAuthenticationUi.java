package com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces;

interface BasicAuthenticationUi {

    //Let presenter know about UI interaction
    boolean onNextFragmentClicked();
    void onGoBackClicked();
    void onChangeActivityClicked();
    void onTryAgainTextViewClicked();

    //Also start a http request
    void onGoButtonClicked();
    void onGetVerificationCodeButtonClicked();

}
