package com.example.samsungproject.models;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.UUID;
@Entity(tableName = "Hour",foreignKeys = @ForeignKey(entity = TimeTable.class,parentColumns = "idTimeTable",childColumns = "TimeTableId",onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE))
public class Hour{
    @NonNull
    @PrimaryKey
    @ColumnInfo(name="idHour")
    private String id;
    @NonNull
    @ColumnInfo(name="number")
    private int num;
    @NonNull
    @ColumnInfo(name="time")
    private String time;
    @ColumnInfo(name="TimeTableId")
    private String timeTableId;
    public Hour(@NonNull int num,@NonNull String time, String timeTableId) {
        this.num = num;
        this.time = time;
        this.timeTableId = timeTableId;
        this.id=UUID.randomUUID().toString().replace("-","").toUpperCase();;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeTableId() {
        return timeTableId;
    }

    public void setTimeTableId(String timeTableId) {
        this.timeTableId = timeTableId;
    }
}
