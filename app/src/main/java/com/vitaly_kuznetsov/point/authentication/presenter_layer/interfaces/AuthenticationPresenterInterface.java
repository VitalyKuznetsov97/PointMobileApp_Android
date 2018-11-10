package com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces;

import com.vitaly_kuznetsov.point.base_models.BaseContract;
import com.vitaly_kuznetsov.point.base_models.UserDataModel;

public interface AuthenticationPresenterInterface extends BaseContract.Presenter {

    void onNextFragmentClicked();
    void onGoBackClicked();
    void onChangeActivityClicked();
    void onTryAgainTextViewClicked();
    UserDataModel getUserDataModel();
    void applyUserDataModelChanges();
    void onAfterError();
    void onBackPressed();

}
