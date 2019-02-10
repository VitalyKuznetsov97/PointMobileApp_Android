package com.vitaly_kuznetsov.point.chat.view_layer;

import android.support.v7.widget.RecyclerView;

import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.Chatmade;

public interface ChatViewInterface {

    void goToHomeActivity();
    void goToHomeActivity(String errorMessage);

    RecyclerView getRecyclerView();
    void setChatInterior(Chatmade chatmade);

}
