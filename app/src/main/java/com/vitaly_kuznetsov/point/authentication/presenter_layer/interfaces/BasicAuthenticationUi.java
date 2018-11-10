package com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces;

interface BasicAuthenticationUi {

    //Let presenter know about UI interaction
    void onNextFragmentClicked();
    void onGoBackClicked();
    void onChangeActivityClicked();
    void onTryAgainTextViewClicked();

}
