package com.example.samsungproject;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.samsungproject.adapter.LessonAdapter;
import com.example.samsungproject.database.AppDatabase;
import com.example.samsungproject.models.Lesson;

import java.util.List;


public class LessonActivity extends AppCompatActivity {
    RecyclerView lessonView;
    LessonAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DataAsynkTask().execute();
    }


    class DataAsynkTask extends AsyncTask<Void, Void, Void> {

        List<Lesson>d;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            lessonView=findViewById(R.id.lesson_list_layout);
            LinearLayoutManager llm = new LinearLayoutManager(LessonActivity.this);
            lessonView.setLayoutManager(llm);
            adapter=new LessonAdapter(LessonActivity.this,d);
            lessonView.setAdapter(adapter);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db =  Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database").build();
            d=db.lessonDao().getAllByDayId(getIntent().getExtras().get("id").toString());
            return null;
        }
    }
}
