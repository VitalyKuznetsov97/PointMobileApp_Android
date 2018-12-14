package com.vitaly_kuznetsov.point.settings.presenter_layer;

interface SettingsPresenterInterface {

    //---------------View LifeCycle-----------------
    void onStopView();
    void onBackPressed();

    //---------------UI------------------
    void onAlertAccepted(int alertType);
    void onSaveIconClicked();

}