package com.vitaly_kuznetsov.point.launch_app.presenter_layer;

import com.vitaly_kuznetsov.point.base_models.BaseContract;

public interface MainPresenterInterface extends BaseContract.Presenter {

    void onSignUpClicked();
    void onLogInClicked();

}
