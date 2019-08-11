package com.example.samsungproject.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.joda.time.LocalDate;

import java.util.UUID;

/*
* Модели для таблиц БД.
* Созданы на базе аннотаций в рамках библиотеки Room.
* Принцип работы аналогичен другим ORM.
* */
@Entity(tableName = "Day",foreignKeys = @ForeignKey(entity = Week.class,parentColumns = "id",childColumns = "WeekId",onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE))
public class Day extends Model{

    @NonNull
    @ColumnInfo(name = "num")
    private int num;
    @ColumnInfo(name="WeekId")
    private String weekId;
    public Day(int num, String weekId) {
        super();
        this.num = num;
        this.weekId = weekId;
        this.id=UUID.randomUUID().toString().replace("-","").toUpperCase();;
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
