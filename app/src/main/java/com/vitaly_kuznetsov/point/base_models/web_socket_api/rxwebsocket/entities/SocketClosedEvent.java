package com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket.entities;

import com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket.SocketEventTypeEnum;

public class SocketClosedEvent extends SocketEvent {

    private final int code;
    private final String reason;

    public SocketClosedEvent(int code, String reason) {
        super(SocketEventTypeEnum.CLOSED);
        this.code = code;
        this.reason = reason;
    }

    public int getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "SocketClosedEvent{" +
                "code=" + code +
                ", reason='" + reason + '\'' +
                '}';
    }
}
