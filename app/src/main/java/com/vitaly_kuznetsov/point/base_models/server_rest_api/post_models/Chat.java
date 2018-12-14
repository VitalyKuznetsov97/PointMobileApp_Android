package com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models;

import java.util.ArrayList;

public class Chat {

    private String chatId;
    private Chatmade chatmade;
    private String initDate;
    private ArrayList<Message> messages = new ArrayList <> ();


    // Getter Methods

    public String getChatId() {
        return chatId;
    }

    public Chatmade getChatmade() {
        return chatmade;
    }

    public String getInitDate() {
        return initDate;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    // Setter Methods

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void setChatmade(Chatmade chatmade) {
        this.chatmade = chatmade;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}