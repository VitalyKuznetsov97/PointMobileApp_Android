package com.vitaly_kuznetsov.point.authentication.view_layer.interfaces;

import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;

interface BasicUiActionsAuthentication {

    //Intents
    void changeAuthenticationActivity();
    void goToMainActivity();
    void goToHomeActivity(PostModel postModel);

    //Show or hide buttons
    void showTryAgainTextView();
    void hideTryAgainTextView();
    void showError();
    void hideError();

    //Show or remove Fragment
    void showAuthenticationFragment(AbstractAuthenticationFragment newFragment);
    void removeAuthenticationFragment();

    //Getters and Setters
    AbstractAuthenticationFragment getFragment();
    void setFragment(AbstractAuthenticationFragment fragment);
}