package com.example.samsungproject.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.samsungproject.models.Day;

import java.util.List;

/*
 * Интерфейс для операций с таблицей БД.
 * Реалзиован с помощью аннотаций в рамках библиотеки Room.
 * Прицип работы аналогичен Hibernate или другим ORM
 * */
@Dao
public interface DayDao {

    @Query("SELECT * FROM Day")
    List<Day> getAll();
    @Query("SELECT * FROM Day WHERE id = :id")
    Day getById(String id);
    @Query("SELECT * FROM Day WHERE WeekId = :weekId")
    List<Day>getAllByWeekId(String weekId);
    @Insert
    void insert(Day day);
    @Update
    void update(Day day);
    @Delete
    void delete(Day day);
    @Query("DELETE FROM Day WHERE id = :idDay")
    void delete(String idDay);
}
