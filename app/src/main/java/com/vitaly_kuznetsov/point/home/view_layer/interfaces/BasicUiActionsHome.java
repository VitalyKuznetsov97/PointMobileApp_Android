package com.vitaly_kuznetsov.point.home.view_layer.interfaces;

import android.support.v4.app.Fragment;

import com.vitaly_kuznetsov.point.home.presenter_layer.HomeViewPresenter;

public interface BasicUiActionsHome {

    Fragment getCurrentFragment();

    void showAlertDialog(int alertType, String Message);

}