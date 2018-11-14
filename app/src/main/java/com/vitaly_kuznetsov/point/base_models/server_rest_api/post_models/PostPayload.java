package com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models;

public class PostPayload {

    private boolean status;
    private String message;
    private Data data;

    // Getter Methods

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }

    // Setter Methods

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Data dataObject) {
        this.data = dataObject;
    }
}
