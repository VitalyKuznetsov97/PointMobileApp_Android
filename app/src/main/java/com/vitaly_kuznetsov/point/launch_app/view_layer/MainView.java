package com.vitaly_kuznetsov.point.launch_app.view_layer;

import com.vitaly_kuznetsov.point.base_models.BaseContract;

public interface MainView extends BaseContract.View {

    void goToLogInActivity();
    void goToSignUpActivity();

}