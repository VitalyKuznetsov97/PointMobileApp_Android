package com.vitaly_kuznetsov.point.base_models.web_socket_api;

import com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket.entities.SocketFailureEvent;
import com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket.entities.SocketMessageEvent;
import com.vitaly_kuznetsov.point.search.presenter_layer.WebSocketHandlingInterface;

import java.util.HashMap;

public interface WebSocketInterface {

    void start(HashMap<String, String> socketParams);

    void stop();

    void sendMessage(String message);

    void onMessageReceived(SocketMessageEvent message);

    void onErrorOccurred(SocketFailureEvent failureEvent);

}