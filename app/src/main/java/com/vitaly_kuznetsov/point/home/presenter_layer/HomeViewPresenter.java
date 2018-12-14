package com.vitaly_kuznetsov.point.home.presenter_layer;

import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BaseContract;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicModelActionsInterface;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.base_models.user_data_model.model.UserDataModel;
import com.vitaly_kuznetsov.point.home.view_layer.interfaces.BasicUiActionsHome;

public class HomeViewPresenter
        implements BaseContract.Presenter, BasicModelActionsInterface, HomeViewPresenterInterface {

    private UserDataModel userDataModel;
    private BasicUiActionsHome currentView;

    private boolean alreadyOpened;

    @Override
    public void attachView(BaseContract.View view) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void onMessagePreviewOpened() {

    }

    @Override
    public UserDataModel getUserDataModel() {
        return null;
    }

    @Override
    public boolean mayStartHttpRequest() {
        return false;
    }

    @Override
    public void onResponse(PostModel postModel) {

    }

    @Override
    public void onFailure(String errorMessage) {

    }


}
