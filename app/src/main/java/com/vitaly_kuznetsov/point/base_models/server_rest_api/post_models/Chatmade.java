package com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models;

public class Chatmade {
    private String nick;
    private String id;

    // Getter Methods

    public String getNick() {
        return nick;
    }

    public String getId() {
        return id;
    }

    // Setter Methods

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setId(String id) {
        this.id = id;
    }
}