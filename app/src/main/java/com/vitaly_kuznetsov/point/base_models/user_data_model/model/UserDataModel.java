package com.vitaly_kuznetsov.point.base_models.user_data_model.model;

import java.util.ArrayList;
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
        myBio = "Hello!";
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

    public long getMyAgeTimeStamp() {
        return myAge.getTime() / 1000L;
    }

    public void setMyAgeTimeStamp(long timeStamp) {
        myAge = new Date(timeStamp * 1000L);
    }

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
        if (yourAge.contains("All ages"))
            return "18-99";
        else {
            String lowest = yourAge.get(0).substring(0, 2);
            String highest;
            if (yourAge.contains("46+")) highest = "99";
            else highest = yourAge.get(yourAge.size() - 1).substring(3, 5);

            return lowest + "-" + highest;
        }
    }

    public ArrayList<String> getYourAge() { return yourAge; }

    public void setYourAge(ArrayList<String> yourAge) { this.yourAge = yourAge; }

    public void setYourAgeString(String yourAgeString) {
        String[] agesLowHigh;
        if (yourAgeString.contains("-"))
            agesLowHigh = yourAgeString.split("-");
        else
            agesLowHigh = yourAgeString.split("–");

        String lowest = agesLowHigh[0];
        String highest = agesLowHigh[1];
        int low, high;

        switch (lowest) {
            case "23" : low = 1; break;
            case "28" : low = 2; break;
            case "36" : low = 3; break;
            case "46" : low = 4; break;
            default: low = 0; break;
        }

        switch (highest) {
            case "22" : high = 0; break;
            case "27" : high = 1; break;
            case "35" : high = 2; break;
            case "45" : high = 3; break;
            default: high = 4; break;
        }

        this.yourAge = new ArrayList<>();
        if (low == 0 && high == 4) yourAge.add("All ages");
        else {
            ArrayList<String> list = new ArrayList<>();
            list.add("18–22");
            list.add("23–27");
            list.add("28–35");
            list.add("36–45");
            list.add("46+");
            list.add("All ages");

            for (int i = low; i <= high; i++)
                yourAge.add(list.get(i));
        }
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
