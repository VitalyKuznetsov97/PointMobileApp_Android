package com.vitaly_kuznetsov.point.authentication.view_layer.interfaces;

import android.support.v4.app.Fragment;

import com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces.AbstractAuthenticationPresenter;

interface BasicUiActions {

    //Intents
    void changeAuthenticationActivity();
    void goToMainActivity();

    //Show or hide buttons
    void showTryAgainTextView();
    void hideTryAgainTextView();
    void showError();
    void hideError();

    //Show Fragment
    void showAuthenticationFragment(int current);

    //Getters
    AbstractAuthenticationPresenter getPresenter();
    Fragment getFragment();
}