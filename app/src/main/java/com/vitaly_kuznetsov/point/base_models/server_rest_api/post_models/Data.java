package com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models;

import java.util.ArrayList;

public class Data {

    private UserData userData;
    private String token;
    private ArrayList<Chat> chats;

    // Getter Methods

    public UserData getUserData() {
        return userData;
    }

    public String getToken() {
        return token;
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }

    // Setter Methods

    public void setUserData(UserData userDataObject) {
        this.userData = userDataObject;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setChats(ArrayList<Chat> chats) {
        this.chats = chats;
    }
}
