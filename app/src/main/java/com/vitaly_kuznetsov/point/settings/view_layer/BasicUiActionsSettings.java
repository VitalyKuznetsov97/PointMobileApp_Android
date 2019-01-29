package com.vitaly_kuznetsov.point.settings.view_layer;

import android.support.v4.app.Fragment;

import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;

import java.util.ArrayList;

public interface BasicUiActionsSettings {

    //Intents
    void goToHomeActivity();
    void goToHomeActivity(PostModel postModel);
    void goToHomeActivity(String errorText);

    //Show or hide buttons
    void showAlertDialog(int alertType);
    void hideKeyboard();
    void showProgressBar();

    //Show, save or remove Fragment
    void showFragments();
    void saveFragmentsState();

    //Getters and Setters
    ArrayList<Fragment> getFragments();

}