package com.vitaly_kuznetsov.point.base_models.mvp_base_contract;

import com.vitaly_kuznetsov.point.base_models.user_data_model.model.UserDataModel;

public interface BaseContract {

    interface View {

        void init();
        Presenter getPresenter();

    }

    interface Presenter {

        void attachView(View view);
        void detachView();

    }

}
