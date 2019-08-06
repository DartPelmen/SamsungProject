package com.example.samsungproject.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.samsungproject.models.TimeTable;

import java.util.List;
import java.util.UUID;
@Dao
public interface TimeTableDao {

    @Query("SELECT * FROM TimeTable")
    List<TimeTable> getAll();
    @Query("SELECT * FROM TimeTable WHERE idTimeTable = :id")
    TimeTable getById(String id);

    @Insert
    void insert(TimeTable timeTable);
    @Update
    void update(TimeTable timeTable);
    @Delete
    void delete(TimeTable timeTable);

}
