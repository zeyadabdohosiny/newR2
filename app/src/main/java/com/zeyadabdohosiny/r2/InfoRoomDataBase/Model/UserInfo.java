package com.zeyadabdohosiny.r2.InfoRoomDataBase.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "User_Info")
public class UserInfo  {
    @PrimaryKey
    int id;
    String UserIdToken;
    String Name;
    String Phone;
    String ImageUrl;

    public UserInfo(String userIdToken, String name, String phone, String imageUrl) {
        UserIdToken = userIdToken;
        Name = name;
        Phone = phone;
        ImageUrl = imageUrl;
    }

    public UserInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserIdToken() {
        return UserIdToken;
    }

    public void setUserIdToken(String userIdToken) {
        UserIdToken = userIdToken;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
