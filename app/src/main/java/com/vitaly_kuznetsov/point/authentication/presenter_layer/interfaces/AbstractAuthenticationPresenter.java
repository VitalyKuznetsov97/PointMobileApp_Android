package com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces;

import android.content.Context;

import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.AbstractAuthenticationFragment;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicModelActionsInterface;
import com.vitaly_kuznetsov.point.base_models.user_data_model.user_model_preferences.ModelHandler;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.AbstractAuthenticationView;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.BasicCodeActionsFragment;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BaseContract;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostPayload;
import com.vitaly_kuznetsov.point.base_models.user_data_model.model.UserDataModel;

//Abstract class, to be extended by other AuthenticationPresenters
public abstract class  AbstractAuthenticationPresenter implements
        BaseContract.Presenter, BasicAuthenticationStateActions, BasicAuthenticationUi,
        BasicModelActionsInterface {

    protected AbstractAuthenticationView currentView;
    protected int currentStep;
    protected UserDataModel userDataModel;
    protected PostModel postModel;

    //-----------Presenter Functions------------

    @Override
    public void attachView(BaseContract.View view) {
        this.currentView = (AbstractAuthenticationView) view;
        this.currentStep = -1;
        this.userDataModel = ModelHandler.getInstance((Context) view);
    }

    @Override
    public void detachView() {
        this.currentView = null;
    }

    //-----------UI Activity Functions------------

    @Override
    public void onGoBackClicked() {
        currentView.goToMainActivity();
    }

    @Override
    public void onChangeActivityClicked() {
        currentView.changeAuthenticationActivity();
    }

    @Override
    public void onTryAgainTextViewClicked() {}

    //-----------UI Change State Functions------------

    @Override
    public void onAfterError() {
        currentView.hideError();
    }

    @Override
    public boolean onBackPressed() {
        if (currentStep != 0) {
            currentStep--;
            currentView.hideError();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNextFragmentClicked() {
        if (currentView.getFragment() == null || currentView.getFragment().isReadyToProgress()) {
            this.currentStep++;
            return true;
        }
        else
            this.currentView.showError();
        return false;
    }

    @Override
    public void onStopView() {
        if (this.userDataModel != null)
            ModelHandler.changeUserDataModel(this.userDataModel);
    }


    @Override
    public boolean mayStartHttpRequest() {
        AbstractAuthenticationFragment fragment = currentView.getFragment();
        if (fragment.isReadyToProgress()){
            fragment.saveFragmentState();
            return true;
        }
        else {
            this.currentView.showError();
            return false;
        }
    }

    //---------Model Actions----------

    @Override
    public UserDataModel getUserDataModel() {
        return userDataModel;
    }

    @Override
    public void onResponse(PostModel postModel) {
        BasicCodeActionsFragment fragment = (BasicCodeActionsFragment) currentView.getFragment();
        if (postModel.getStatus()){
            PostPayload payload = postModel.getPayload();
            if (payload.getStatus()) {
                this.postModel = postModel;
                onNextFragmentClicked();
                return;
            }
            else
                fragment.setErrorText(payload.getMessage());
        }
        else
            fragment.setErrorText(postModel.getMessage());
        this.currentView.showError();
    }

    @Override
    public void onFailure(String errorMessage) {
        BasicCodeActionsFragment fragment = (BasicCodeActionsFragment) currentView.getFragment();
        fragment.setErrorText(errorMessage);
        this.currentView.showError();
    }
}