package com.example.samsungproject.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.samsungproject.dao.DayDao;
import com.example.samsungproject.dao.LessonDao;
import com.example.samsungproject.dao.TimeTableDao;
import com.example.samsungproject.dao.WeekDao;
import com.example.samsungproject.models.Day;
import com.example.samsungproject.models.Lesson;
import com.example.samsungproject.models.TimeTable;
import com.example.samsungproject.models.Week;

@Database(entities = {TimeTable.class, Week.class, Day.class, Lesson.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TimeTableDao timeTableDao();
    public abstract WeekDao weekDao();
    public abstract DayDao dayDao();
    public abstract LessonDao lessonDao();

    private static AppDatabase instance;
    private static final String DB_NAME = "database";

    public static AppDatabase getInstance() {
        return instance;
    }

    public static AppDatabase setInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DB_NAME).build();
                }
            }
        }
        return instance;
    }
}
