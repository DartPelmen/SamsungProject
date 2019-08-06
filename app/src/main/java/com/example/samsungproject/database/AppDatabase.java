package com.example.samsungproject.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.samsungproject.dao.DayDao;
import com.example.samsungproject.dao.HourDao;
import com.example.samsungproject.dao.LessonDao;
import com.example.samsungproject.dao.TimeTableDao;
import com.example.samsungproject.dao.WeekDao;
import com.example.samsungproject.models.Day;
import com.example.samsungproject.models.Hour;
import com.example.samsungproject.models.Lesson;
import com.example.samsungproject.models.TimeTable;
import com.example.samsungproject.models.Week;

@Database(entities = {TimeTable.class, Week.class, Day.class, Hour.class, Lesson.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TimeTableDao timeTableDao();
    public abstract WeekDao weekDao();
    public abstract DayDao dayDao();
    public abstract LessonDao lessonDao();
    public abstract HourDao hourDao();
}
