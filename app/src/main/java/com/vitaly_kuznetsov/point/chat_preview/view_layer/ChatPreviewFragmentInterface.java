package com.vitaly_kuznetsov.point.chat_preview.view_layer;

import com.vitaly_kuznetsov.point.chat_preview.presenter_layer.ChatPreviewViewPresenter;
import com.vitaly_kuznetsov.point.home.view_layer.activities.HomeActivity;

public interface ChatPreviewFragmentInterface {

    void attachPresenter(HomeActivity homeActivity, ChatPreviewViewPresenter presenter);
    HomeActivity getHomeActivity();
}