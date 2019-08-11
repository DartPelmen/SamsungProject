package com.example.samsungproject.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.samsungproject.models.TimeTable;
import com.example.samsungproject.models.Week;

import java.util.List;
import java.util.UUID;

/*
 * Интерфейс для операций с таблицей БД.
 * Реалзиован с помощью аннотаций в рамках библиотеки Room.
 * Прицип работы аналогичен Hibernate или другим ORM
 * */
@Dao
public interface WeekDao {

    @Query("SELECT * FROM Week")
    List<Week> getAll();
    @Query("SELECT * FROM Week WHERE id = :id")
    Week getById(String id);
    @Query("SELECT * FROM Week WHERE TimeTableId = :timeTableId")
    List<Week>getAllByTimeTableId(String timeTableId);
    @Insert
    void insert(Week week);
    @Update
    void update(Week week);
    @Delete
    void delete(Week week);
    @Query("DELETE FROM Week WHERE id= :idWeek")
    void delete(String idWeek);

}
