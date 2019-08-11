package com.example.samsungproject.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.util.UUID;

/*
 * Модели для таблиц БД.
 * Созданы на базе аннотаций в рамках библиотеки Room.
 * Принцип работы аналогичен другим ORM.
 *
 * Это не модель для БД - от нее наследуются другие можели.
 * Позволеет задумать об обощениях этих моделей на более качетсвенный уровень.
 * */
public class Model {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    protected String id;

    public Model(){
        this.id= UUID.randomUUID().toString().replaceAll("-","");
    }
    public Model(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }
}
