package com.zeyadabdohosiny.r2.RequestModel;

public class HistoryModle {
    String NameOfTheShop;
    String  imageView;
    String Time;
    String shop_id;
    String date;
    String nameofPs;

    public String getNameofPs() {
        return nameofPs;
    }

    public void setNameofPs(String nameofPs) {
        this.nameofPs = nameofPs;
    }

    public String getComeOrNot() {
        return comeOrNot;
    }

    public void setComeOrNot(String comeOrNot) {
        this.comeOrNot = comeOrNot;
    }

    String comeOrNot;

    public HistoryModle(String nameOfTheShop, String imageView, String time, String shop_id, String comeOrNot) {
        NameOfTheShop = nameOfTheShop;
        this.imageView = imageView;
        Time = time;
        this.shop_id = shop_id;
        this.comeOrNot = comeOrNot;
    }

    public HistoryModle(String nameOfTheShop, String imageView, String time, String shop_id, String date, String comeOrNot) {
        NameOfTheShop = nameOfTheShop;
        this.imageView = imageView;
        Time = time;
        this.shop_id = shop_id;
        this.date = date;
        this.comeOrNot = comeOrNot;
    }

    public HistoryModle() {
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public HistoryModle(String nameOfTheShop, String imageView, String dateOfRegestration, String shop_id) {
        NameOfTheShop = nameOfTheShop;
        this.imageView = imageView;
        this.Time = dateOfRegestration;
        this.shop_id = shop_id;
    }

    public HistoryModle(String nameOfTheShop, String imageView, String dateOfRegestration) {
        NameOfTheShop = nameOfTheShop;
        this.imageView = imageView;
        this.Time = dateOfRegestration;
    }

    public String getNameOfTheShop() {
        return NameOfTheShop;
    }

    public void setNameOfTheShop(String nameOfTheShop) {
        NameOfTheShop = nameOfTheShop;
    }



    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public String getDateOfRegestration() {
        return Time;
    }

    public void setDateOfRegestration(String dateOfRegestration) {
        this.Time = dateOfRegestration;
    }
}
