package com.example.samsungproject.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "TimeTable")
public class TimeTable{

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "idTimeTable")
    private String id;
    @NonNull
    @ColumnInfo(name = "Title")
    private String title;

    public TimeTable(@NonNull String title) {
        this.title = title;
        this.id=UUID.randomUUID().toString().replace("-","").toUpperCase();;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
