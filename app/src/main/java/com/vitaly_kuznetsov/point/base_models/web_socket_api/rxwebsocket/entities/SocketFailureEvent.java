package com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket.entities;

import com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket.SocketEventTypeEnum;

import okhttp3.Response;

public class SocketFailureEvent extends SocketEvent {

    private final Throwable throwable;
    private final Response response;

    public SocketFailureEvent(Throwable throwable, Response response) {
        super(SocketEventTypeEnum.FAILURE);
        this.throwable = throwable;
        this.response = response;
    }

    public Throwable getException() {
        return throwable;
    }

    public Response getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "SocketFailureEvent{" +
                "exception=" + throwable.getMessage() +
                '}';
    }
}
