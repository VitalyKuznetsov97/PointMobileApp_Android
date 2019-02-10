package com.vitaly_kuznetsov.point.launch_app.presenter_layer;

import android.content.Context;

import com.vitaly_kuznetsov.point.base_models.server_rest_api.request_controllers.LoginCheckTokenRequestController;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BaseContract;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostPayload;
import com.vitaly_kuznetsov.point.base_models.user_data_model.model.UserDataModel;
import com.vitaly_kuznetsov.point.base_models.user_data_model.user_model_preferences.ModelHandler;
import com.vitaly_kuznetsov.point.launch_app.view_layer.LaunchViewInterface;

public class LaunchViewPresenter implements LaunchViewPresenterInterface {

    private UserDataModel userDataModel;
    private LaunchViewInterface currentView;

    //-----------Presenter Functions------------

    @Override
    public void attachView(BaseContract.View view) {

        currentView = (LaunchViewInterface) view;
        currentView.startTimer();
        userDataModel = ModelHandler.getInstance((Context) view);

        if (!userDataModel.getToken().equals(""))
            LoginCheckTokenRequestController.getInstance().start(this);
        else
            currentView.goToMainActivity();
    }

    @Override
    public void detachView() {
        this.currentView = null;
    }

    //---------Model Actions----------

    @Override
    public UserDataModel getUserDataModel() {
        return userDataModel;
    }

    @Override
    public boolean mayStartHttpRequest() {
        return true;
    }

    @Override
    public void onResponse(PostModel postModel) {
        if (currentView != null && postModel != null) {
            currentView.stopTimer();
            if (postModel.getStatus()) {
                PostPayload payload = postModel.getPayload();
                if (payload.getStatus())
                    currentView.goToHomeActivity(postModel);
                else
                    currentView.goToMainActivity();
            } else
                currentView.goToMainActivity();
        }
    }

    @Override
    public void onFailure(String errorMessage) {
        if (currentView != null) {
            currentView.stopTimer();
            currentView.goToMainActivity();
        }
    }
}
