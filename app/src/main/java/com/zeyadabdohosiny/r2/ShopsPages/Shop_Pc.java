package com.zeyadabdohosiny.r2.ShopsPages;

public class Shop_Pc {
   String shop_Id;
   String nameOfShop;
   String numberofpc;
   private boolean  Open;
   String  ImageUrl;
   String  menu_Image;

    public String getMenu_Image() {
        return menu_Image;
    }

    public void setMenu_Image(String menu_Image) {
        this.menu_Image = menu_Image;
    }

    public Shop_Pc(String menu_Image) {
        this.menu_Image = menu_Image;
    }

    public Shop_Pc() {
    }

    public Shop_Pc(String name, boolean open, String imageUrl) {
        this.numberofpc = name;
        Open = open;
        ImageUrl = imageUrl;
    }

    public Shop_Pc(String shop_Id, String nameOfShop, String numberofpc, boolean open, String imageUrl) {
        this.shop_Id = shop_Id;
        this.nameOfShop = nameOfShop;
        this.numberofpc = numberofpc;
        Open = open;
        ImageUrl = imageUrl;
    }

    public String getShop_Id() {
        return shop_Id;
    }

    public void setShop_Id(String shop_Id) {
        this.shop_Id = shop_Id;
    }

    public String getNameOfShop() {
        return nameOfShop;
    }

    public void setNameOfShop(String nameOfShop) {
        this.nameOfShop = nameOfShop;
    }

    public String getNumberofpc() {
        return numberofpc;
    }

    public void setNumberofpc(String numberofpc) {
        this.numberofpc = numberofpc;
    }

    public Shop_Pc(boolean open, String imageUrl) {
        Open = open;
        ImageUrl = imageUrl;
    }

    public boolean isOpen() {
        return Open;
    }

    public void setOpen(boolean open) {
        Open = open;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
