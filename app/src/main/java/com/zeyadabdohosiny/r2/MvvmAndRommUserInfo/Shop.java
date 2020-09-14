package com.zeyadabdohosiny.r2.MvvmAndRommUserInfo;

public class Shop {
    String UserIdToken;
    String Name;
    String Image_Uri;
    int lang;
    int latd;
    String MenuImage;
    double langtude;
    double latude;
    int rate;

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Shop(String userIdToken, String name, String image_Uri, double langtude, double latude, int rate) {
        UserIdToken = userIdToken;
        Name = name;
        Image_Uri = image_Uri;
        this.langtude = langtude;
        this.latude = latude;
        rate=rate;
    }

    public double getLangtude() {
        return langtude;
    }

    public void setLangtude(double langtude) {
        this.langtude = langtude;
    }

    public double getLatude() {
        return latude;
    }

    public void setLatude(double latude) {
        this.latude = latude;
    }

    public String getMenuImage() {
        return MenuImage;
    }

    public void setMenuImage(String menuImage) {
        MenuImage = menuImage;
    }

    public Shop(String userIdToken, String name, String image_Uri, int lang, int latd, String menuImage,int rate) {
        UserIdToken = userIdToken;
        Name = name;
        Image_Uri = image_Uri;
        this.lang = lang;
        this.latd = latd;
        MenuImage = menuImage;
        rate=rate;
    }

    public String getUserIdToken() {
        return UserIdToken;
    }

    public void setUserIdToken(String userIdToken) {
        UserIdToken = userIdToken;
    }

    public Shop() {
    }

    public Shop(String userIdToken, String name, String image_Uri, int lang, int latd) {
        UserIdToken = userIdToken;
        Name = name;
        Image_Uri = image_Uri;
        this.lang = lang;
        this.latd = latd;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage_Uri() {
        return Image_Uri;
    }

    public void setImage_Uri(String image_Uri) {
        Image_Uri = image_Uri;
    }

    public int getLang() {
        return lang;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    public int getLatd() {
        return latd;
    }

    public void setLatd(int latd) {
        this.latd = latd;
    }
}
