package com.zeyadabdohosiny.r2.RequestModel;

public class Requestmodel {
    String userId;
    String NameOfTheUser;
    String Imageuri;
    int RateOftheUser;
    String Time;
    String NameofThepc;
    int rate;

    public Requestmodel(String userId, String nameOfTheUser, String imageuri, int rateOftheUser, String time, String nameofThepc) {
        this.userId = userId;
        NameOfTheUser = nameOfTheUser;
        Imageuri = imageuri;
        RateOftheUser = rateOftheUser;
        Time = time;
        NameofThepc = nameofThepc;
        this.rate = rate;
    }

    public Requestmodel(String userId, String nameOfTheUser, String imageuri, String time, String nameofThepc) {
        this.userId = userId;
        NameOfTheUser = nameOfTheUser;
        Imageuri = imageuri;
        Time = time;
        NameofThepc = nameofThepc;
    }


    public Requestmodel() {
    }

    public Requestmodel(String nameofThepc, String nameOfTheUser, String userId) {
        NameofThepc = nameofThepc;
        NameOfTheUser = nameOfTheUser;
        this.userId = userId;
    }

    public String getNameOfTheUser() {
        return NameOfTheUser;
    }

    public void setNameOfTheUser(String nameOfTheUser) {
        NameOfTheUser = nameOfTheUser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNameofThepc() {
        return NameofThepc;
    }

    public void setNameofThepc(String nameofThepc) {
        NameofThepc = nameofThepc;
    }

    public Requestmodel(String nameofThepc) {
        NameofThepc = nameofThepc;
    }

    public String getImageuri() {
        return Imageuri;
    }

    public void setImageuri(String imageuri) {
        Imageuri = imageuri;
    }

    public int getRateOftheUser() {
        return RateOftheUser;
    }

    public void setRateOftheUser(int rateOftheUser) {
        RateOftheUser = rateOftheUser;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }


}
