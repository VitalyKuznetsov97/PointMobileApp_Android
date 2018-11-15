package com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models;

public class Data {

    private UserData userData;
    private String token;

    // Getter Methods

    public UserData getUserData() {
        return userData;
    }

    public String getToken() {
        return token;
    }

    // Setter Methods

    public void setUserData(UserData userDataObject) {
        this.userData = userDataObject;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
