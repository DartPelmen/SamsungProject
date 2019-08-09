package com.example.samsungproject.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "Lesson",foreignKeys = @ForeignKey(entity = Day.class,parentColumns = "idDay",childColumns = "DayId",onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE))
public class Lesson{
    @NonNull
    @PrimaryKey
    @ColumnInfo(name="idLesson")
    private String id;
    @NonNull
    @ColumnInfo(name="number")
    private String number;
    @NonNull
    @ColumnInfo(name="title")
    private String title;
    @ColumnInfo(name="description")
    private String description;
    @ColumnInfo(name="DayId")
    private String dayId;

    public Lesson( String number, @NonNull String title, String description, String dayId) {
        this.id =UUID.randomUUID().toString().replace("-","").toUpperCase();
        this.number = number;
        this.title = title;
        this.description = description;
        this.dayId = dayId;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDayId() {
        return dayId;
    }

    public void setDayId(String dayId) {
        this.dayId = dayId;
    }
}
