package com.vitaly_kuznetsov.point.authentication.view_layer.interfaces;

import android.support.v4.app.Fragment;

import com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces.AuthenticationPresenterInterface;
import com.vitaly_kuznetsov.point.base_models.BaseContract;

public interface AuthenticationView extends BaseContract.View {

    void changeAuthenticationActivity();
    void goToMainActivity();
    void showTryAgainTextView();
    void hideTryAgainTextView();
    void showError();
    void hideError();
    void showAuthenticationFragment(Fragment currentFragment);
    AuthenticationPresenterInterface getPresenter();
    AuthenticationFragment getFragment();

}
