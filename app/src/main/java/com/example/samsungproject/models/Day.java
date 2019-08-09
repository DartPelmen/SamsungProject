package com.example.samsungproject.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.joda.time.LocalDate;

import java.util.UUID;


@Entity(tableName = "Day",foreignKeys = @ForeignKey(entity = Week.class,parentColumns = "idWeek",childColumns = "WeekId",onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE))
public class Day{
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "idDay")
    private String id;
    @NonNull
    @ColumnInfo(name = "num")
    private int num;
    @ColumnInfo(name="WeekId")
    private String weekId;
    public Day(int num, String weekId) {
        this.num = num;
        this.weekId = weekId;
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

    public String getWeekId() {
        return weekId;
    }

    public void setWeekId(String weekId) {
        this.weekId = weekId;
    }

    public String getDayT(){

        return new LocalDate().withDayOfWeek(num+1).dayOfWeek().getAsText();
    }

}
