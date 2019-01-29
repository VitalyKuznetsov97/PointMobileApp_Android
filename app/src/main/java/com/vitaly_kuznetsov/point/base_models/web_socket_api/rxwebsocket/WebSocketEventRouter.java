package com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket;

import com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket.entities.SocketClosedEvent;
import com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket.entities.SocketClosingEvent;
import com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket.entities.SocketEvent;
import com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket.entities.SocketFailureEvent;
import com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket.entities.SocketMessageEvent;
import com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket.entities.SocketOpenEvent;

import io.reactivex.FlowableEmitter;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;


public class WebSocketEventRouter extends WebSocketListener {

    private final FlowableEmitter<SocketEvent> emitter;

    public WebSocketEventRouter(FlowableEmitter<SocketEvent> emitter) {
        this.emitter = emitter;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        if (!emitter.isCancelled()) {
            emitter.onNext(new SocketOpenEvent(webSocket, response));
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        if (!emitter.isCancelled()) {
            emitter.onNext(new SocketMessageEvent(text));
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        if (!emitter.isCancelled()) {
            emitter.onNext(new SocketMessageEvent(bytes));
        }
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        if (!emitter.isCancelled()) {
            emitter.onNext(new SocketClosingEvent(code, reason));
        }
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        if (!emitter.isCancelled()) {
            emitter.onNext(new SocketClosedEvent(code, reason));
        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        if (!emitter.isCancelled()) {
            emitter.onNext(new SocketFailureEvent(t, response));
        }
    }
}
