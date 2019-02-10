package com.vitaly_kuznetsov.point.search.presenter_layer;

import com.vitaly_kuznetsov.point.base_models.web_socket_api.response_models.User;
import com.vitaly_kuznetsov.point.search.view_layer.SearchFragment;

public interface SearchFragmentPresenterInterface {

    void onMainButtonCheckedChanged(boolean checked);

    void attachView(SearchFragment fragment);
    void detachView();

    void onFragmentResumed();
}
