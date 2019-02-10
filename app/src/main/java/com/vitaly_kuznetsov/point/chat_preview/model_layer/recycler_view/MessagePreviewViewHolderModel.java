package com.vitaly_kuznetsov.point.chat_preview.model_layer.recycler_view;

public class MessagePreviewViewHolderModel {

    private String message;
    private long time;
    private String nick;

    private boolean last;

    public MessagePreviewViewHolderModel(String message, long time, String nick) {
        this.message = message;
        this.time = time;
        this.nick = nick;
        this.last = false;
    }

    public MessagePreviewViewHolderModel(){}

    public void setLast(boolean last) {
        this.last = last;
    }

    public boolean isLast() {
        return last;
    }

    String getMessage() {
        return message;
    }

    long getTime() {
        return time;
    }

    String getNick() {
        return nick;
    }
}
