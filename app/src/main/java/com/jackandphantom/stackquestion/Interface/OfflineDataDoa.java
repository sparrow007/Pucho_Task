package com.jackandphantom.stackquestion.Interface;

import com.jackandphantom.stackquestion.model.OfflineData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface OfflineDataDoa {

    @Query("SELECT * FROM offlinedata")
    List<OfflineData> getAll();

    @Insert
    void insertAll(OfflineData offlineData);

}
