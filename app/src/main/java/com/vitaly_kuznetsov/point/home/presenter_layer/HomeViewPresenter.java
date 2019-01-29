package com.vitaly_kuznetsov.point.home.presenter_layer;

import android.content.Context;

import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BaseContract;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicModelActionsInterface;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostPayload;
import com.vitaly_kuznetsov.point.base_models.user_data_model.model.UserDataModel;
import com.vitaly_kuznetsov.point.base_models.user_data_model.user_model_preferences.ModelHandler;
import com.vitaly_kuznetsov.point.home.model_layer.HistoryGetRequestController;
import com.vitaly_kuznetsov.point.home.view_layer.activities.HomeActivity;
import com.vitaly_kuznetsov.point.home.view_layer.fragments.ChatPreviewFragment;
import com.vitaly_kuznetsov.point.home.view_layer.interfaces.BasicUiActionsHome;
import com.vitaly_kuznetsov.point.home.view_layer.interfaces.ChatPreviewFragmentInterface;

public class HomeViewPresenter
        implements BaseContract.Presenter, BasicModelActionsInterface, HomeViewPresenterInterface {

    private UserDataModel userDataModel;
    private BasicUiActionsHome currentView;

    private boolean alreadyOpened;

    @Override
    public void attachView(BaseContract.View view) {
        this.userDataModel = ModelHandler.getInstance((Context) view);
        currentView = (BasicUiActionsHome) view;
    }

    @Override
    public void detachView() {
        currentView = null;
    }

    @Override
    public void onMessagePreviewOpened() {
        if (mayStartHttpRequest()){
            HistoryGetRequestController.getInstance().start(this);
            ChatPreviewFragmentInterface fragment = (ChatPreviewFragmentInterface) currentView.getCurrentFragment();
            fragment.showProgressDialog();

            alreadyOpened = true;
        }
    }

    @Override
    public boolean isAlreadyOpened() {
        return alreadyOpened;
    }

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
        if (currentView != null) {
            ChatPreviewFragmentInterface fragment = (ChatPreviewFragmentInterface) currentView.getCurrentFragment();

            fragment.hideProgressDialog();
            if (postModel.getStatus()) {
                PostPayload payload = postModel.getPayload();
                if (payload.getStatus())
                    ((HomeActivity) currentView).showFrag(new ChatPreviewFragment());
                else
                    fragment.showLayout();
            } else
                fragment.showLayout();
        }
    }

    @Override
    public void onFailure(String errorMessage) {
        ChatPreviewFragmentInterface fragment = (ChatPreviewFragmentInterface) currentView.getCurrentFragment();
        fragment.showLayout();
    }


}
