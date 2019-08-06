package com.example.samsungproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.samsungproject.adapter.WeekAdapter;
import com.example.samsungproject.database.AppDatabase;
import com.example.samsungproject.models.Day;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    int i=0;
    RecyclerView weekView;
    WeekAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new DataAsynkTask().execute();
    }


    class DataAsynkTask extends AsyncTask<Void, Void, Void> {

        List<Day>d;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            weekView=findViewById(R.id.weekView);
            LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
            weekView.setLayoutManager(llm);
            adapter=new WeekAdapter(MainActivity.this,d);
            weekView.setAdapter(adapter);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db =  Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database").build();

            d=db.dayDao().getAll();


            Log.i("ASDADADAD ", String.valueOf(d.size()));
            return null;
        }
    }
}
