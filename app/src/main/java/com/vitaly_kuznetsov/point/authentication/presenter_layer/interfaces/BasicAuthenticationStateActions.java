package com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces;

interface BasicAuthenticationStateActions {

    void onAfterError();
    boolean onBackPressed();
    void onStopView();

    boolean mayStartHttpRequest();
}
