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
    void hideProgressBar();

    //Show or remove Fragment
    void showFragments();

    //Getters and Setters
    ArrayList<Fragment> getFragments();

}