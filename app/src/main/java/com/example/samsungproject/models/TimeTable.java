package com.example.samsungproject.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.UUID;
/*
 * Модели для таблиц БД.
 * Созданы на базе аннотаций в рамках библиотеки Room.
 * Принцип работы аналогичен другим ORM.
 * */
@Entity(tableName = "TimeTable")
public class TimeTable extends Model{


    @NonNull
    @ColumnInfo(name = "Title")
    private String title;

    public TimeTable(@NonNull String id, @NonNull String title) {
        this.id = id;
        this.title = title;
    }
    @Ignore
    public TimeTable(@NonNull String title) {
        this.title = title;
        this.id=UUID.randomUUID().toString().replace("-","").toUpperCase();;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
