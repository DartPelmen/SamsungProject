package com.example.samsungproject;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.samsungproject.adapter.LessonAdapter;
import com.example.samsungproject.database.AppDatabase;
import com.example.samsungproject.models.Lesson;
import com.example.samsungproject.models.Week;

import java.util.List;


public class LessonActivity extends AppCompatActivity {
    RecyclerView lessonView;
    LessonAdapter adapter;
    Button new_note;
    EditText title, description;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        new DataAsynkTask().execute();
        new_note=findViewById(R.id.new_week);
        new_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(context);
                View add_lesson_view = li.inflate(R.layout.new_lesson_alert, null);
                final AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
                mDialogBuilder.setView(add_lesson_view);
                title=add_lesson_view.findViewById(R.id.title);
                description=add_lesson_view.findViewById(R.id.description);


                mDialogBuilder.setCancelable(true).setPositiveButton("Создать", null);
                mDialogBuilder.setNegativeButton("Отмена",null);

                final AlertDialog alertDialog = mDialogBuilder.create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        Button button = (alertDialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String text=title.getText().toString().trim();
                                String desc=description.getText().toString().trim();

                                if (TextUtils.isEmpty(text)){
                                    title.setError("Пожалуйста, введите заголовок!");
                                }else {
                                    new LessonActivity().InsertAsynkTask().execute(,title.getText().toString(), desc, getIntent().getExtras().get("id").toString());
                                    alertDialog.dismiss();
                                }
                            }
                        });
                    }

                });
                alertDialog.show();
            }
        });
    }


    class DataAsynkTask extends AsyncTask<Void, Void, Void> {

        List<Lesson>d;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            lessonView=findViewById(R.id.lesson_view);
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

    class InsertAsynkTask extends AsyncTask<String, Void, Week> {
        @Override
        protected Week doInBackground(String... strings) {
            AppDatabase db =  Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database").build();
            Log.i("INSERT TITLE AND ID", strings[0]+" "+strings[1]);
            Lesson lesson=new Lesson()
            db.weekDao().insert(lesson);
            return lesson;
        }

        @Override
        protected void onPostExecute(Week week) {
            super.onPostExecute(week);
            ((LessonAdapter)lessonView.getAdapter()).addItem(lesson);
        }


    }
}
