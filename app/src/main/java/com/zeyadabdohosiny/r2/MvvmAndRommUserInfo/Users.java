package com.zeyadabdohosiny.r2.MvvmAndRommUserInfo;

public class Users {
    String Name;
    String Mail;
    String UserIdToken;
    String Image_Uri;
    String Phone;
    int rate;
    boolean setOrNot;
    boolean newUser;

    public Users(String name, String mail, String userIdToken, String image_Uri, String phone, int rate, boolean setOrNot, boolean newUser) {
        Name = name;
        Mail = mail;
        UserIdToken = userIdToken;
        Image_Uri = image_Uri;
        Phone = phone;
        this.rate = rate;
        this.setOrNot = setOrNot;
        this.newUser = newUser;
    }

    public boolean isNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    public Users(String name, String mail, String userIdToken, String image_Uri, String phone, int rate, boolean setOrNot) {
        Name = name;
        Mail = mail;
        UserIdToken = userIdToken;
        Image_Uri = image_Uri;
        Phone = phone;
        this.rate = rate;
        this.setOrNot = setOrNot;
    }

    public Users(int rate) {
        this.rate = rate;
    }

    public Users(String name, String mail, String userIdToken, String image_Uri, String phone, boolean setOrNot) {
        Name = name;
        Mail = mail;
        UserIdToken = userIdToken;
        Image_Uri = image_Uri;
        Phone = phone;
        this.setOrNot = setOrNot;
    }

    public Users(boolean setOrNot) {
        this.setOrNot = setOrNot;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public boolean isSetOrNot() {
        return setOrNot;
    }

    public void setSetOrNot(boolean setOrNot) {
        this.setOrNot = setOrNot;
    }

    public Users(String name, String mail, String userIdToken, String image_Uri, String phone) {
        Name = name;
        Mail = mail;
        UserIdToken = userIdToken;
        Image_Uri = image_Uri;
        Phone = phone;
    }


    public Users() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getUserIdToken() {
        return UserIdToken;
    }

    public void setUserIdToken(String userIdToken) {
        UserIdToken = userIdToken;
    }

    public String getImage_Uri() {
        return Image_Uri;
    }

    public void setImage_Uri(String image_Uri) {
        Image_Uri = image_Uri;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}


