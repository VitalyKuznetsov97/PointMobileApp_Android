package com.vitaly_kuznetsov.point.main_activity.presenter_layer;

import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BaseContract;

public interface MainPresenterInterface extends BaseContract.Presenter {

    void onSignUpClicked();
    void onLogInClicked();

}
