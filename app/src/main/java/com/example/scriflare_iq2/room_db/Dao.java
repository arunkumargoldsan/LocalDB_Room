package com.example.scriflare_iq2.room_db;
import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface Dao {
    @Insert
    void insert(UserModal modal);

    @Update
    void update(UserModal modal);

    @Delete
    void delete(UserModal modal);

    @Query("DELETE FROM user")
    void deleteAllUser();

    @Query("SELECT * FROM user")
    LiveData<List<UserModal>> getAllUser();
}
