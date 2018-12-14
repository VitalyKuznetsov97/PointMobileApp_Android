package com.vitaly_kuznetsov.point.chat.model_layer;

import java.util.Date;

public class Message {

    private String message;
    private Date time;
    private boolean fromMe;

    public Message(String message, Date time, boolean fromMe) {
        this.message = message;
        this.time = time;
        this.fromMe = fromMe;
    }

    public String getMessage() {
        return message;
    }

    public Date getTime() {
        return time;
    }

    public boolean isFromMe() {
        return fromMe;
    }
}
