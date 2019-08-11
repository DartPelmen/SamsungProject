package com.example.samsungproject.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.samsungproject.models.Lesson;

import java.util.List;

/*
 * Интерфейс для операций с таблицей БД.
 * Реалзиован с помощью аннотаций в рамках библиотеки Room.
 * Прицип работы аналогичен Hibernate или другим ORM
 * */
@Dao
public interface LessonDao {

    @Query("SELECT * FROM Lesson")
    List<Lesson> getAll();
    @Query("SELECT * FROM Lesson WHERE id = :id")
    Lesson getById(String id);
    @Query("SELECT * FROM Lesson WHERE dayId = :dayId")
    List<Lesson>getAllByDayId(String dayId);
    @Insert
    void insert(Lesson lesson);
    @Update
    void update(Lesson lesson);
    @Delete
    void delete(Lesson lesson);
    @Query("DELETE FROM LESSON WHERE id = :idLesson")
    void delete(String idLesson);
}
