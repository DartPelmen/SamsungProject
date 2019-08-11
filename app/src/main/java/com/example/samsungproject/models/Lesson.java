package com.example.samsungproject.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;


@Entity(tableName = "Lesson",foreignKeys = @ForeignKey(entity = Day.class,parentColumns = "idDay",childColumns = "DayId",onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE))
public class Lesson{
    @NonNull
    @PrimaryKey
    @ColumnInfo(name="idLesson")
    private String id;
    @NonNull
    @ColumnInfo(name="hour")
    private Integer hour;
    @NonNull
    @ColumnInfo(name="minute")
    private Integer minute;
    @NonNull
    @ColumnInfo(name="title")
    private String title;
    @ColumnInfo(name="description")
    private String description;
    @ColumnInfo(name="DayId")
    private String dayId;

    public Lesson(@NonNull Integer hour, @NonNull Integer minute, @NonNull String title, String description, String dayId) {
        this.id = UUID.randomUUID().toString().replace("-","").toUpperCase();
        this.hour = hour;
        this.minute = minute;
        this.title = title;
        this.description = description;
        this.dayId = dayId;
    }
    @Ignore
    public Lesson(@NonNull String id, @NonNull Integer hour, @NonNull Integer minute, @NonNull String title, String description, String dayId) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
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


    @NonNull
    public Integer getHour() {
        return hour;
    }

    public void setHour(@NonNull Integer hour) {
        this.hour = hour;
    }

    @NonNull
    public Integer getMinute() {
        return minute;
    }

    public void setMinute(@NonNull Integer minute) {
        this.minute = minute;
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
