package com.example.samsungproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.samsungproject.adapter.DayAdapter;
import com.example.samsungproject.database.AppDatabase;
import com.example.samsungproject.models.Lesson;

import java.util.List;

public class LessonInfoActivity extends AppCompatActivity {

    TextView title,description;
    TimePicker timePicker;
    Button delete, edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_info);
        title=findViewById(R.id.lessonTitle);
        edit=findViewById(R.id.edit);
        timePicker=findViewById(R.id.timefield);
        timePicker.setIs24HourView(true);
        description=findViewById(R.id.description);
        title.setText(getIntent().getExtras().get("title").toString());
        timePicker.setCurrentHour(Integer.parseInt(getIntent().getExtras().get("hour").toString()));
        timePicker.setCurrentMinute(Integer.parseInt(getIntent().getExtras().get("minute").toString()));
        description.setText(getIntent().getExtras().get("description").toString());
        delete=findViewById(R.id.deleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DataAsynkTask().execute();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t=title.getText().toString();
                if (TextUtils.isEmpty(t.trim()))
                {
                    title.setError("Заголовок не должен быть пуст!");
                }
                else {
                    String id= getIntent().getExtras().get("id").toString();
                    String hour= timePicker.getCurrentHour().toString();
                    String minute=timePicker.getCurrentMinute().toString();
                    String desc = description.getText().toString();
                    String di = getIntent().getExtras().get("dayid").toString();
                    new UpdateAsynkTask().execute(id,hour,minute,t,desc,di);
                }

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(LessonInfoActivity.this,LessonActivity.class);
        i.putExtra("id",getIntent().getExtras().get("dayid").toString());
        startActivity(i);
    }

    class DataAsynkTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent i=new Intent(LessonInfoActivity.this,LessonActivity.class);
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
    class UpdateAsynkTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String aString) {
            super.onPostExecute(aString);
            Intent i=new Intent(LessonInfoActivity.this,LessonActivity.class);
            i.putExtra("id",aString);
            startActivity(i);
        }

        @Override
        protected String doInBackground(String... strings) {
            Lesson l=new Lesson(strings[0],Integer.parseInt(strings[1]),Integer.parseInt(strings[2]),strings[3],strings[4],strings[5]);
            AppDatabase db =  Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database").build();
            db.lessonDao().update(l);
            return strings[5];
        }
    }
}
