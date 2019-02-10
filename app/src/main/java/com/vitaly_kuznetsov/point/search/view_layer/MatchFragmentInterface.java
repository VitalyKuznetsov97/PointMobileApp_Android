package com.vitaly_kuznetsov.point.search.view_layer;

import com.vitaly_kuznetsov.point.home.view_layer.activities.HomeActivity;
import com.vitaly_kuznetsov.point.search.presenter_layer.MatchFragmentPresenter;

public interface MatchFragmentInterface {

    void startButtonAnimation();
    void attachPresenter(MatchFragmentPresenter matchFragmentPresenter);
    HomeActivity getHomeActivity();

}