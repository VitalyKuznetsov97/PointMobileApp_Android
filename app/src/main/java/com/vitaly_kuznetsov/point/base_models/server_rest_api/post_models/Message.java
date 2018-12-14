package com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models;

public class Message {
    private String id;
    private String chatId;
    private String senderId;
    private String text;
    private String date;


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getChatId() {
        return chatId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
