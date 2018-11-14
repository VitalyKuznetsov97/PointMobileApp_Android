package com.vitaly_kuznetsov.point.base_models.server_rest_api.request_models;

public class RequestModel {

    private RequestPayload payload;

    public RequestModel(RequestPayload payload) {
        this.payload = payload;
    }
    // Getter Methods

    public RequestPayload getPayload() {
        return payload;
    }

    // Setter Methods

    public void setPayload(RequestPayload payloadObject) {
        this.payload = payloadObject;
    }
}