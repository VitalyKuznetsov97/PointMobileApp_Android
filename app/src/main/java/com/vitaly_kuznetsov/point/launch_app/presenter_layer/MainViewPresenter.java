package com.vitaly_kuznetsov.point.launch_app.presenter_layer;

import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BaseContract;
import com.vitaly_kuznetsov.point.launch_app.view_layer.MainView;

public class MainViewPresenter implements MainPresenterInterface {

    private MainView mainView;

    @Override
    public void onSignUpClicked() {
        mainView.goToSignUpActivity();
    }

    @Override
    public void onLogInClicked() {
        mainView.goToLogInActivity();
    }

    @Override
    public void attachView(BaseContract.View view) {
        if (view instanceof MainView)
            this.mainView = (MainView) view;
    }

    @Override
    public void detachView() {
        this.mainView = null;
    }

}
