package com.vitaly_kuznetsov.point.base_models;

public interface BaseContract {

    interface View {

        void init();

    }

    interface Presenter {

        void attachView(View view);
        void detachView();

    }

}
