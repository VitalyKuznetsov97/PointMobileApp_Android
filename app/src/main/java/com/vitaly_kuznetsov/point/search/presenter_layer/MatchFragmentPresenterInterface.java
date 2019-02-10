package com.vitaly_kuznetsov.point.search.presenter_layer;

import com.vitaly_kuznetsov.point.search.view_layer.MatchFragment;

public interface MatchFragmentPresenterInterface {

    void onAcceptButtonClicked();
    void onDeclineButtonClicked();
    void onCancelButtonClicked();
    void onButtonAfterAnimationClicked();

    void attachView(MatchFragment fragment);
    void detachView();
}
