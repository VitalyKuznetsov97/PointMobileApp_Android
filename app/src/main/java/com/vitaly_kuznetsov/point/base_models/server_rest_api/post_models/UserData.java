package com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models;

class UserData {

    private String id;
    private String telephoneHash;
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

    // Getter Methods

    public String getId() {
        return id;
    }

    public String getTelephoneHash() {
        return telephoneHash;
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

    public void setTelephoneHash(String telephoneHash) {
        this.telephoneHash = telephoneHash;
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
