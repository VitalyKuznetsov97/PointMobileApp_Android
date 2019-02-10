package com.vitaly_kuznetsov.point.base_models.web_socket_api.response_models;

import com.vitaly_kuznetsov.point.base_models.user_data_model.model.UserDataModel;

public class User {
    private String id;
    private String phone;
    private String nickname;
    private String myBio;
    private String myAge;
    private String myGender;
    private String yourGender;
    private String yourAge;
    private String eat;
    private String drink;
    private String film;
    private String sport;
    private String date;
    private String walk;

    //Builders
    public UserDataModel createUserDataModel(){
        UserDataModel userDataModel = new UserDataModel();

        userDataModel.setNickname(this.getNickname());
        userDataModel.setMyGender(Integer.parseInt(this.getMyGender()));
        userDataModel.setMyAgeTimeStamp(Long.parseLong(this.getMyAge()));
        userDataModel.setMyBio(this.getMyBio());
        userDataModel.setYourGender(Integer.parseInt(this.getYourGender()));
        userDataModel.setYourAgeString(this.getYourAge());
        userDataModel.setPhone(this.getPhone());

        return userDataModel;
    }

    // Getter Methods

    public String getId() {
        return id;
    }

    public String getPhone() {
        return phone;
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

    public String getYourGender() {
        return yourGender;
    }

    public String getYourAge() {
        return yourAge;
    }

    public String getEat() {
        return eat;
    }

    public String getDrink() {
        return drink;
    }

    public String getFilm() {
        return film;
    }

    public String getSport() {
        return sport;
    }

    public String getDate() {
        return date;
    }

    public String getWalk() {
        return walk;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public void setYourGender(String yourGender) {
        this.yourGender = yourGender;
    }

    public void setYourAge(String yourAge) {
        this.yourAge = yourAge;
    }

    public void setEat(String eat) {
        this.eat = eat;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWalk(String walk) {
        this.walk = walk;
    }
}