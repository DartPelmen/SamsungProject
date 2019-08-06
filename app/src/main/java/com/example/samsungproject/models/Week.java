package com.example.samsungproject.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "Week",foreignKeys = @ForeignKey(entity = TimeTable.class,parentColumns = "idTimeTable",childColumns = "TimeTableId",onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE))
public class Week {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "idWeek")
    private String id;
    @NonNull
    @ColumnInfo(name = "Title")
    private String title;
    @ColumnInfo(name = "TimeTableId")
    private String timetableId;

    public Week(@NonNull String title, String timetableId) {
        this.id=UUID.randomUUID().toString().replace("-","").toUpperCase();
        this.title = title;
        this.timetableId = timetableId;
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
