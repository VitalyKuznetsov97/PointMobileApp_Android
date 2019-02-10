package com.vitaly_kuznetsov.point.search.presenter_layer;

public interface WebSocketHandlingInterface {

    void onMessageSent(String message);
    void onWebSocketResponse(String response);
    void onError(String message);

}
