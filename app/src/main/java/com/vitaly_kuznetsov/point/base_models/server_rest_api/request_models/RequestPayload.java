package com.vitaly_kuznetsov.point.base_models.server_rest_api.request_models;

import com.vitaly_kuznetsov.point.base_models.user_data_model.model.UserDataModel;

public class RequestPayload {

    private String token;
    private String phone;
    private String sms;
    private String nickname;
    private String myBio;
    private String myAge;
    private String myGender;
    private String yourAge;
    private String yourGender;

    // Constructors

    private RequestPayload() {
    }

    public static RequestPayload createRegisterPayload(UserDataModel userDataModel){
        RequestPayload payload = new RequestPayload();

        payload.setPhone(userDataModel.getPhone());
        payload.setSms(userDataModel.getSms());
        payload.setNickname(userDataModel.getNickname());
        payload.setMyAge(String.valueOf(userDataModel.getMyAgeTimeStamp()));
        payload.setMyGender(String.valueOf(userDataModel.getMyGender()));
        payload.setYourAge(userDataModel.getYourAgeString());
        payload.setYourGender(String.valueOf(userDataModel.getYourGender()));

        return payload;
    }

    public static RequestPayload createCheckPhonePayload(UserDataModel userDataModel){
        RequestPayload payload = new RequestPayload();

        payload.setPhone(userDataModel.getPhone());

        return payload;
    }

    public static RequestPayload createSubmitSmsPayload(UserDataModel userDataModel){
        RequestPayload payload = new RequestPayload();

        payload.setPhone(userDataModel.getPhone());
        payload.setSms(userDataModel.getSms());

        return payload;
    }

    public static RequestPayload createLoginCheckToken(UserDataModel userDataModel){
        RequestPayload payload = new RequestPayload();

        payload.setToken(userDataModel.getToken());

        return payload;
    }

    public static RequestPayload createProfileEditProfile(UserDataModel userDataModel){
        RequestPayload payload = new RequestPayload();

        payload.setToken(userDataModel.getToken());
        payload.setNickname(userDataModel.getNickname());
        payload.setMyBio(userDataModel.getMyBio());
        payload.setMyAge(String.valueOf(userDataModel.getMyAgeTimeStamp()));
        payload.setMyGender(String.valueOf(userDataModel.getMyGender()));
        payload.setYourAge(userDataModel.getYourAgeString());
        payload.setYourGender(String.valueOf(userDataModel.getYourGender()));

        return payload;
    }

    public static RequestPayload createHistoryGet(UserDataModel userDataModel){
        RequestPayload payload = new RequestPayload();

        payload.setToken(userDataModel.getToken());

        return payload;
    }

    // Getter Methods

    public String getPhone() {
        return phone;
    }

    public String getSms() {
        return sms;
    }

    public String getNickname() {
        return nickname;
    }

    public String getMyBio() {
        return myBio;
    }

    public String getMyAge() {
        return myAge;
    }

    public String getMyGender() {
        return myGender;
    }

    public String getYourAge() {
        return yourAge;
    }

    public String getToken() {
        return token;
    }

    public String getYourGender() {
        return yourGender;
    }

    // Setter Methods

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setMyBio(String myBio) {
        this.myBio = myBio;
    }

    public void setMyAge(String myAge) {
        this.myAge = myAge;
    }

    public void setMyGender(String myGender) {
        this.myGender = myGender;
    }

    public void setYourAge(String yourAge) {
        this.yourAge = yourAge;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setYourGender(String yourGender) {
        this.yourGender = yourGender;
    }
}