package com.zeyadabdohosiny.r2.InfoRoomDataBase.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserInfoDao {
    @Insert
    void Insert(UserInfo userInfo);

    @Delete
    void Delete(UserInfo userInfo);

    @Update
    void Update(UserInfo userInfo);
    @Query("SELECT * FROM User_Info ORDER BY id DESC")
    LiveData<List<UserInfo>> GetAllUsers();
    @Query("Delete FROM User_Info")
    void DeleteAllUsers();
}
