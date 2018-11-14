package com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models;

public class PostModel {

    private boolean status;
    private String message;
    private PostPayload payload;


    // Getter Methods

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public PostPayload getPayload() {
        return payload;
    }

    // Setter Methods

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPayload(PostPayload payloadObject) {
        this.payload = payloadObject;
    }
}