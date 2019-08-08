package com.example.samsungproject;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.samsungproject.adapter.TimeTableAdapter;
import com.example.samsungproject.database.AppDatabase;
import com.example.samsungproject.models.TimeTable;

import java.util.List;


public class TimeTableActivity extends AppCompatActivity {

    RecyclerView timetableView;
    TimeTableAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        new DataAsynkTask().execute();
    }
    class DataAsynkTask extends AsyncTask<Void, Void, Void> {

        List<TimeTable> d;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            timetableView=findViewById(R.id.timetablesView);
            LinearLayoutManager llm = new LinearLayoutManager(TimeTableActivity.this);
            timetableView.setLayoutManager(llm);
            adapter=new TimeTableAdapter(TimeTableActivity.this,d);
            timetableView.setAdapter(adapter);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db =  Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database").build();

            d=db.timeTableDao().getAll();
            return null;
        }
    }
}
