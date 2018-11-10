package com.vitaly_kuznetsov.point.base_models;

import java.util.ArrayList;
import java.util.Date;

public class UserDataModel {

    public final static String PREFERENCES_FILE = "preferences_file";
    public final static String PREFERENCES_DATA_STRING = "preferences_data_string";

    private String userName;
    private int userGender;
    private Date userBirthDate;
    private int preferredGender;
    private ArrayList<Integer> preferredAge;
    private String phoneNumber;

    public UserDataModel(){
        this.userName = "";
        this.userGender = 2;
        Date date = new Date();
        date.setTime(-1577934000000L);
        this.userBirthDate = date;
        this.preferredGender = 3;
        this.preferredAge = new ArrayList<>();
        this.phoneNumber = "";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserGender() {
        return userGender;
    }

    public void setUserGender(int userGender) {
        this.userGender = userGender;
    }

    public Date getUserBirthDate() {
        return userBirthDate;
    }

    public void setUserBirthDate(Date userBirthDate) { this.userBirthDate = userBirthDate; }

    public int getPreferredGender() {
        return preferredGender;
    }

    public void setPreferredGender(int preferredGender) {
        this.preferredGender = preferredGender;
    }

    public ArrayList<Integer> getPreferredAge() { return preferredAge; }

    public void setPreferredAge(ArrayList<Integer> preferredAge) { this.preferredAge = preferredAge; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

}
