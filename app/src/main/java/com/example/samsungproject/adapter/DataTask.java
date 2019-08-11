package com.example.samsungproject.adapter;

import android.os.AsyncTask;
import android.util.Log;

import com.example.samsungproject.database.AppDatabase;
import com.example.samsungproject.models.Day;
import com.example.samsungproject.models.Lesson;
import com.example.samsungproject.models.TimeTable;
import com.example.samsungproject.models.Week;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
/*
* Класс, через который проводятся операции по работе с БД.
* На вход методов подаются необходимые для работы с БД данные - ID записей или полностью объекты записей,
* в то время как с помощью расположенных здесь классов AsynkTask выполняются операции.
*
* Следует помнить, что Room (используемое здесь средство для работы с БД, такое как SQLiteOnlineHelper)
* не позволяет работать с БД не с отдельного потока.
* */
public class DataTask {

    public static TimeTable getTimeTable(String id){
        TimeTable timeTable=null;
        try {
            timeTable= new OneTimeTableTask().execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return timeTable;
    }
    public static Week getWeek(String id){
        Week week=null;
        try {
            week= new OneWeekTask().execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return week;
    }
    public static Day getDay(String id){
        Day day=null;
        try {
            day= new OneDayTask().execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return day;
    }
    public static Lesson getLesson(String id){
        Lesson lesson=null;
        try {
            lesson= new OneLessonTask().execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return lesson;
    }

    public static List<TimeTable>getTimeTables(){
        List<TimeTable> timeTables=new ArrayList<>();
        try {
            timeTables= new TimeTableTask().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return timeTables;
    }
    public static List<Week>getWeeks(String timetableId){
        List<Week> weeks=new ArrayList<>();
        try {
            weeks= new WeekTask().execute(timetableId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return weeks;
    }
    public static List<Day>getDays(String weekId){
        List<Day> days=new ArrayList<>();
        try {
            days= new DayTask().execute(weekId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return days;
    }
    public static List<Lesson>getLessons(String dayId){
        List<Lesson> lessons=new ArrayList<>();
        try {
            lessons= new LessonTask().execute(dayId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return lessons;
    }

    public static void insertTimeTable(TimeTable timeTable){
        new InsertTimeTableTask().execute(timeTable);
    }
    public static void insertWeek(Week week){
        new InsertWeekTask().execute(week);
    }
    public static void insertDay(Day day){
        new InsertDayTask().execute(day);
    }
    public static void insertLesson(Lesson lesson){
        new InsertLessonTask().execute(lesson);
    }

    public static void deleteTimeTable(String id){
        new DeleteTimeTableTask().execute(id);
    }
    public static void deleteWeek(String id){
        new DeleteWeekTask().execute(id);
    }
    public static void deleteDay(String id){
        new DeleteDayTask().execute(id);
    }
    public static void deleteLesson(String id){
        new DeleteLessonTask().execute(id);
    }

    public static void updateTimeTable(TimeTable timeTable) {
        new UpdateTimeTableTask().execute(timeTable);
    }
    public static void updateWeek(Week week) {
        new UpdateWeekTask().execute(week);
    }
    public static void updateDay(Day day) {
        new UpdateDayTask().execute(day);
    }
    public static void updateLesson(Lesson lesson) {
        new UpdateLessonTask().execute(lesson);
    }

    static class TimeTableTask extends AsyncTask<Void,Void,List<TimeTable>>{
        @Override
        protected List<TimeTable> doInBackground(Void... voids) {
            return AppDatabase.getInstance().timeTableDao().getAll();
        }
    }
    static class WeekTask extends AsyncTask<String,Void,List<Week>>{
        @Override
        protected List<Week> doInBackground(String... strings) {
            return AppDatabase.getInstance().weekDao().getAllByTimeTableId(strings[0]);
        }
    }
    static class DayTask extends AsyncTask<String,Void,List<Day>>{
        @Override
        protected List<Day> doInBackground(String... strings) {
            return AppDatabase.getInstance().dayDao().getAllByWeekId(strings[0]);
        }
    }
    static class LessonTask extends AsyncTask<String,Void,List<Lesson>>{
        @Override
        protected List<Lesson> doInBackground(String... strings) {
            return AppDatabase.getInstance().lessonDao().getAllByDayId(strings[0]);
        }
    }

    static class InsertTimeTableTask extends AsyncTask<TimeTable, Void, Void> {
        @Override
        protected Void doInBackground(TimeTable... timeTables) {
            AppDatabase.getInstance().timeTableDao().insert(timeTables[0]);
            return null;
        }
    }
    static class InsertWeekTask extends AsyncTask<Week, Void, Void> {
        @Override
        protected Void doInBackground(Week... weeks) {
            AppDatabase.getInstance().weekDao().insert(weeks[0]);
            return null;
        }
    }
    static class InsertDayTask extends AsyncTask<Day, Void, Void> {
        @Override
        protected Void doInBackground(Day... days) {
            AppDatabase.getInstance().dayDao().insert(days[0]);
            return null;
        }
    }
    static class InsertLessonTask extends AsyncTask<Lesson, Void, Void> {
        @Override
        protected Void doInBackground(Lesson... lessons) {
            AppDatabase.getInstance().lessonDao().insert(lessons[0]);
            return null;
        }
    }

    static class DeleteTimeTableTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {

            AppDatabase.getInstance().timeTableDao().delete(strings[0]);
            return null;
        }
    }
    static class DeleteWeekTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            AppDatabase.getInstance().weekDao().delete(strings[0]);
            return null;
        }
    }
    static class DeleteDayTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            AppDatabase.getInstance().dayDao().delete(strings[0]);
            return null;
        }
    }
    static class DeleteLessonTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            AppDatabase.getInstance().lessonDao().delete(strings[0]);
            return null;
        }
    }

    static class UpdateTimeTableTask extends AsyncTask<TimeTable, Void, Void> {
        @Override
        protected Void doInBackground(TimeTable... timeTables) {

            AppDatabase.getInstance().timeTableDao().update(timeTables[0]);
            return null;
        }
    }
    static class UpdateWeekTask extends AsyncTask<Week, Void, Void> {
        @Override
        protected Void doInBackground(Week... weeks) {
            AppDatabase.getInstance().weekDao().update(weeks[0]);
            return null;
        }
    }
    static class UpdateDayTask extends AsyncTask<Day, Void, Void> {
        @Override
        protected Void doInBackground(Day... days) {
            AppDatabase.getInstance().dayDao().update(days[0]);
            return null;
        }
    }
    static class UpdateLessonTask extends AsyncTask<Lesson, Void, Void> {
        @Override
        protected Void doInBackground(Lesson... lessons) {
            AppDatabase.getInstance().lessonDao().update(lessons[0]);
            return null;
        }
    }

    static class OneTimeTableTask extends AsyncTask<String,Void,TimeTable>{
        @Override
        protected TimeTable doInBackground(String... strings) {
            return AppDatabase.getInstance().timeTableDao().getById(strings[0]);
        }
    }
    static class OneWeekTask extends AsyncTask<String,Void,Week>{
        @Override
        protected Week doInBackground(String... strings) {
            return AppDatabase.getInstance().weekDao().getById(strings[0]);
        }
    }
    static class OneDayTask extends AsyncTask<String,Void,Day>{
        @Override
        protected Day doInBackground(String... strings) {
            return AppDatabase.getInstance().dayDao().getById(strings[0]);
        }
    }
    static class OneLessonTask extends AsyncTask<String,Void,Lesson>{
        @Override
        protected Lesson doInBackground(String... strings) {
            return AppDatabase.getInstance().lessonDao().getById(strings[0]);
        }
    }


}


