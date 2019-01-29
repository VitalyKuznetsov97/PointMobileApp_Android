package com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket.entities;

import com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket.SocketEventTypeEnum;

public class SocketEvent {

    private final SocketEventTypeEnum type;

    public SocketEvent(SocketEventTypeEnum type) {
        this.type = type;
    }

    public SocketEventTypeEnum getType() {
        return type;
    }

    @Override
    public String toString() {
        return "SocketEvent{" +
                "type=" + type +
                '}';
    }
}
