package com.vitaly_kuznetsov.point.home.view_layer.interfaces;

import android.support.v4.app.Fragment;

import com.vitaly_kuznetsov.point.home.presenter_layer.HomeViewPresenter;

public interface BasicUiActionsHome {

    HomeViewPresenter getCurrentPresenter();
    Fragment getCurrentFragment();
    void setCurrentFragment(Fragment fragment);

    void showAlertDialog(int alertType);

}