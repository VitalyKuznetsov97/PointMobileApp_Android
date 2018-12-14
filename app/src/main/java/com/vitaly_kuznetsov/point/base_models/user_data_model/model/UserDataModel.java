package com.vitaly_kuznetsov.point.base_models.user_data_model.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class UserDataModel {

    public final static String PREFERENCES_FILE = "preferences_file";
    public final static String PREFERENCES_DATA_STRING = "preferences_data_string";

    private String nickname;
    private int myGender;
    private Date myAge;
    private String myBio;
    private int yourGender;
    private ArrayList<String> yourAge;
    private String phone;
    private String sms;
    private String token;

    public UserDataModel(){
        this.nickname = "";
        this.myGender = 2;
        Date date = new Date();
        date.setTime(-1577934000000L);
        this.myAge = date;
        myBio = "";
        this.yourGender = 3;
        this.yourAge = new ArrayList<>();
        this.phone = "";
        this.sms = "";
        this.token = "";
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getMyGender() {
        return myGender;
    }

    public void setMyGender(int myGender) {
        this.myGender = myGender;
    }

    public Date getMyAge() {
        return myAge;
    }

    public void setMyAge(Date myAge) { this.myAge = myAge; }

    public String getMyBio() {
        return myBio;
    }

    public void setMyBio(String myBio) {
        this.myBio = myBio;
    }

    public int getYourGender() {
        return yourGender;
    }

    public void setYourGender(int yourGender) {
        this.yourGender = yourGender;
    }

    public String getYourAgeString() {
        StringBuilder age = new StringBuilder();
        for (int i = 0; i < yourAge.size(); i++){
            if (i != 0) age.append(",");
            age.append(yourAge.get(i));
        }
        return age.toString();
    }

    public ArrayList<String> getYourAge() { return yourAge; }

    public void setYourAge(ArrayList<String> yourAge) { this.yourAge = yourAge; }

    public void setYourAgeString(String yourAgeString) {
        String[] ages = yourAgeString.split(",");
        this.yourAge = new ArrayList<>(Arrays.asList(ages));
    }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
