package com.example.samsungproject.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

/*
 * Модели для таблиц БД.
 * Созданы на базе аннотаций в рамках библиотеки Room.
 * Принцип работы аналогичен другим ORM.
 * */
@Entity(tableName = "Week",foreignKeys = @ForeignKey(entity = TimeTable.class,parentColumns = "id",childColumns = "TimeTableId",onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE))
public class Week extends Model{
    @NonNull
    @ColumnInfo(name = "Title")
    private String title;
    @ColumnInfo(name = "TimeTableId")
    private String timetableId;

    public Week(@NonNull String title, String timetableId) {
        super();
        this.title = title;
        this.timetableId = timetableId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimeTableId() {
        return timetableId;
    }

    public void setTimeTableId(String timeTableId) {
        this.timetableId = timeTableId;
    }
    public String getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(String timetableId) {
        this.timetableId = timetableId;
    }

}
