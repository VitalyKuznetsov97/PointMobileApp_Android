package com.vitaly_kuznetsov.point.settings.presenter_layer;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BaseContract;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicFragmentInterface;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicModelActionsInterface;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostPayload;
import com.vitaly_kuznetsov.point.base_models.user_data_model.model.UserDataModel;
import com.vitaly_kuznetsov.point.base_models.user_data_model.user_model_preferences.ModelHandler;
import com.vitaly_kuznetsov.point.settings.model_layer.ProfileEditProfileRequestController;
import com.vitaly_kuznetsov.point.settings.view_layer.BasicUiActionsSettings;

import java.util.ArrayList;

import static com.vitaly_kuznetsov.point.base_models.reusable_fragments.CustomDialog.EMPTY_FIELD;
import static com.vitaly_kuznetsov.point.base_models.reusable_fragments.CustomDialog.EXIT_ALERT;
import static com.vitaly_kuznetsov.point.base_models.reusable_fragments.CustomDialog.SAVE_ALERT;


public class SettingsPresenter
        implements BaseContract.Presenter, BasicModelActionsInterface, SettingsPresenterInterface {

    private UserDataModel userDataModel;
    private BasicUiActionsSettings currentView;

    //--------------View Lifecycle-----------------
    @Override
    public void attachView(BaseContract.View view) {
        this.userDataModel = ModelHandler.getInstance((Context) view);
        currentView = (BasicUiActionsSettings) view;
    }

    @Override
    public void detachView() {
        currentView = null;
    }

    @Override
    public void onStopView(){}

    //---------------UI---------------
    @Override
    public void onBackPressed(){
        currentView.showAlertDialog(EXIT_ALERT);
    }

    @Override
    public void onAlertAccepted(int alertType) {
        currentView.hideKeyboard();

        if (alertType == EXIT_ALERT){
            currentView.goToHomeActivity();
        }
        else if (alertType == SAVE_ALERT){
            if (mayStartHttpRequest()) {
                currentView.saveFragmentsState();
                ModelHandler.changeUserDataModel(userDataModel);
                ProfileEditProfileRequestController.getInstance().start(this);
            }
            else
                currentView.showAlertDialog(EMPTY_FIELD);
        }
    }

    @Override
    public void onSaveIconClicked() {
        currentView.showAlertDialog(SAVE_ALERT);
    }

    //-------------Model Actions--------------

    @Override
    public UserDataModel getUserDataModel() {
        return userDataModel;
    }

    @Override
    public boolean mayStartHttpRequest() {
        ArrayList<Fragment> fragmentArrayList = currentView.getFragments();
        if (fragmentArrayList == null)
            return false;
        else {
            for (Fragment fragment : fragmentArrayList){
                BasicFragmentInterface basicFragmentInterface = (BasicFragmentInterface) fragment;
                if (basicFragmentInterface == null ||
                        !basicFragmentInterface.isReadyToProgress())
                    return false;
            }
            currentView.showProgressBar();
            return true;
        }
    }

    @Override
    public void onResponse(PostModel postModel) {
        if (currentView != null) {
            if (postModel.getStatus()) {
                PostPayload payload = postModel.getPayload();
                if (payload.getStatus()) {
                    currentView.goToHomeActivity();
                }
                else
                    currentView.goToHomeActivity(payload.getMessage());
            } else
                currentView.goToHomeActivity(postModel.getMessage());
        }
    }

    @Override
    public void onFailure(String errorMessage) {
        if (currentView != null) {
            currentView.goToHomeActivity(errorMessage);
        }
    }
}