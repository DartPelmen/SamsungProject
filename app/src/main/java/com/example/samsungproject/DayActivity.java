package com.example.samsungproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.samsungproject.adapter.DayAdapter;
import com.example.samsungproject.adapter.WeekAdapter;
import com.example.samsungproject.database.AppDatabase;
import com.example.samsungproject.models.Day;
import com.example.samsungproject.models.Lesson;

import java.util.List;

public class DayActivity extends AppCompatActivity {
    DayAdapter adapter;
    RecyclerView dayView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        new DataAsynkTask().execute();
    }

    class DataAsynkTask extends AsyncTask<Void, Void, Void> {

        List<Lesson> d;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            LinearLayoutManager llm = new LinearLayoutManager(DayActivity.this);
            dayView=findViewById(R.id.dayView);
            dayView.setLayoutManager(llm);
            adapter=new DayAdapter(DayActivity.this,d);
            dayView.setAdapter(adapter);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db =  Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database").build();
            d=db.lessonDao().getAll();

            return null;
        }



    }
}
