package com.vitaly_kuznetsov.point.chat.model_layer.recycler_view;

public class MessageViewHolderModel {

    private String message;
    private long time;
    private boolean fromMe;

    public MessageViewHolderModel(String message, long time, boolean fromMe) {
        this.message = message;
        this.time = time;
        this.fromMe = fromMe;
    }

    String getMessage() {
        return message;
    }

    long getTime() {
        return time;
    }

    boolean isFromMe() {
        return fromMe;
    }
}
