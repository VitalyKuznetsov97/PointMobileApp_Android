package com.vitaly_kuznetsov.point.main_activity.view_layer;

import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BaseContract;

public interface MainView extends BaseContract.View {

    void goToLogInActivity();
    void goToSignUpActivity();

}
