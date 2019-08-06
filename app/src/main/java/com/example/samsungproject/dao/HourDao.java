package com.example.samsungproject.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.samsungproject.models.Day;
import com.example.samsungproject.models.Hour;
import com.example.samsungproject.models.TimeTable;

import java.util.List;
import java.util.UUID;
@Dao
public interface HourDao {

    @Query("SELECT * FROM Hour")
    List<Hour> getAll();
    @Query("SELECT * FROM Hour WHERE idHour = :id")
    Hour getById(String id);
    @Query("SELECT * FROM Hour WHERE TimeTableId = :timeTableId")
    List<Hour>getAllByTimeTableId(String timeTableId);
    @Insert
    void insert(Hour hour);
    @Update
    void update(Hour hour);
    @Delete
    void delete(Hour hour);

}
