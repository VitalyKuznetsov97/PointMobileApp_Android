package com.vitaly_kuznetsov.point.search.view_layer;

import com.vitaly_kuznetsov.point.home.view_layer.activities.HomeActivity;
import com.vitaly_kuznetsov.point.search.presenter_layer.SearchFragmentPresenter;

public interface SearchFragmentInterface {

    void startAnimation();
    void stopAnimation();

    void attachPresenter(SearchFragmentPresenter searchFragmentPresenter);
    void setPointMainButtonChecked(boolean flag);

    HomeActivity getHomeActivity();
    SearchFragmentPresenter getPresenter();
}
