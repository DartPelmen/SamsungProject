package com.example.samsungproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungproject.adapter.DataTask;
import com.example.samsungproject.adapter.Divider;
import com.example.samsungproject.adapter.LessonAdapter;
import com.example.samsungproject.models.Lesson;

import java.util.Objects;

/*
 * Активность уроков дня.
 * Показывает доступные уроки.
 * Уроки отображены в RecycleView.
 *
 * При создании активности (кроме timetable делается выборка данных по id.
 *
 * Для перехода назад в слушателе (OnBackPressed) реализовано получение id сущности-родителя (lesson->day->week->timetable)
  *и переход на активность с передачей этого id.
 * */
public class LessonActivity extends AppCompatActivity {
    RecyclerView lessonView;
    LessonAdapter adapter;
    Button new_note;
    EditText title, description;
    TimePicker timePicker;
    final Context context = this;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        lessonView=findViewById(R.id.lesson_view);
        lessonView.addItemDecoration(new Divider(this));
        LinearLayoutManager llm = new LinearLayoutManager(LessonActivity.this);
        lessonView.setLayoutManager(llm);
        adapter=new LessonAdapter(LessonActivity.this, Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("id")).toString());
        lessonView.setAdapter(adapter);
        new_note=findViewById(R.id.new_note);
    }

    public void newNoteClicked(View view) {
        showAlert();
    }
    void showAlert(){
        LayoutInflater li = LayoutInflater.from(context);
        View add_lesson_view = li.inflate(R.layout.new_lesson_alert, new LinearLayout(this),false);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
        mDialogBuilder.setView(add_lesson_view);
        title=add_lesson_view.findViewById(R.id.title);
        description=add_lesson_view.findViewById(R.id.description);
        timePicker=add_lesson_view.findViewById(R.id.timefield);
        timePicker.setIs24HourView(true);
        mDialogBuilder.setCancelable(true).setPositiveButton("Создать", null);
        mDialogBuilder.setNegativeButton("Отмена",null);

        alertDialog = mDialogBuilder.create();
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
                            Lesson lesson=new Lesson(timePicker.getCurrentHour(),
                                    timePicker.getCurrentMinute(),
                                    title.getText().toString(),
                                    desc,
                                    Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("id")).toString());
                            ((LessonAdapter) Objects.requireNonNull(lessonView.getAdapter())).addItem(lesson);
                            alertDialog.dismiss();
                        }
                    }
                });
            }

        });
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(LessonActivity.this,DayActivity.class);
        String weekid=DataTask.getDay(Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("id")).toString()).getWeekId();
        i.putExtra("id",weekid);
        startActivity(i);
    }
}
