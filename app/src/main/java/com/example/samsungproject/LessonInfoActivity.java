package com.example.samsungproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.samsungproject.adapter.DataTask;
import com.example.samsungproject.models.Lesson;

import java.util.Objects;
/*
 * Активность информации об уроке.
 * Показывает информацию об уроке.
 * Получает данные через намерение.
 * Реализовано: редактирование урока, удаление урока.
 *
 * При создании активности (кроме timetable делается выборка данных по id.
 *
 * Для перехода назад в слушателе (OnBackPressed) реализовано получение id сущности-родителя (lesson->day->week->timetable)
 *и переход на активность с передачей этого id.
 * */
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
        title.setText(Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("title")).toString());
        timePicker.setCurrentHour(Integer.parseInt(Objects.requireNonNull(getIntent().getExtras().get("hour")).toString()));
        timePicker.setCurrentMinute(Integer.parseInt(Objects.requireNonNull(getIntent().getExtras().get("minute")).toString()));
        description.setText(Objects.requireNonNull(getIntent().getExtras().get("description")).toString());
        delete=findViewById(R.id.deleteButton);
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(LessonInfoActivity.this,LessonActivity.class);
        i.putExtra("id", Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("dayid")).toString());
        startActivity(i);
    }

    public void editClicked(View view) {
        String t=title.getText().toString();
        if (TextUtils.isEmpty(t.trim()))
        {
            title.setError("Заголовок не должен быть пуст!");
        }
        else {
            String id= Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("id")).toString();
            Integer hour= timePicker.getCurrentHour();
            Integer minute=timePicker.getCurrentMinute();
            String desc = description.getText().toString();
            String di = Objects.requireNonNull(getIntent().getExtras().get("dayid")).toString();
            Lesson l=new Lesson(id,hour,minute,t,desc,di);
            DataTask.updateLesson(l);
            Intent i=new Intent(LessonInfoActivity.this,LessonActivity.class);
            i.putExtra("id", Objects.requireNonNull(getIntent().getExtras().get("dayid")).toString());
            startActivity(i);
        }

    }
    public void deleteClicked(View view) {
            DataTask.deleteLesson(Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("id")).toString());
            Intent i=new Intent(LessonInfoActivity.this,LessonActivity.class);
            i.putExtra("id", Objects.requireNonNull(getIntent().getExtras().get("dayid")).toString());
            startActivity(i);
        }

 }