package com.example.samsungproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.samsungproject.adapter.DayAdapter;
import com.example.samsungproject.database.AppDatabase;
import com.example.samsungproject.models.Lesson;

import java.util.List;

public class LessonInfoActivity extends AppCompatActivity {

    TextView title,time,description;
    Button delete, edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_info);
        title=findViewById(R.id.lessonTitle);
        time=findViewById(R.id.timeField);
        description=findViewById(R.id.description);
        title.setText(getIntent().getExtras().get("title").toString());
        time.setText(getIntent().getExtras().get("number").toString());
        description.setText(getIntent().getExtras().get("description").toString());
        delete=findViewById(R.id.deleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DataAsynkTask().execute();
            }
        });
    }

    class DataAsynkTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent i=new Intent(LessonInfoActivity.this,DayActivity.class);
            startActivity(i);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db =  Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database").build();
            db.lessonDao().delete(getIntent().getExtras().get("id").toString());
            return null;
        }
}
}
