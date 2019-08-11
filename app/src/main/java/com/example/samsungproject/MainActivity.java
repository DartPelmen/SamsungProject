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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungproject.adapter.WeekAdapter;
import com.example.samsungproject.models.Week;

import java.util.Objects;

/*
 * Активность недель расписания.
 * Показывает доступные недели.
 * Недели отображены в RecycleView.
 * Реализованы: создание и редактирование недель через диалоговое окно (AlertDialog), удаление расписания.
 *
 *  * При создании активности (кроме timetable делается выборка данных по id.
 *
 * *  Переход дальше происходит по клику на элемент.
 * При longclick показывает кнопки для удаления и редактирования.
 *
 * Для перехода назад в слушателе (OnBackPressed) реализовано получение id сущности-родителя (lesson->day->week->timetable)
 * и переход на активность с передачей этого id.
 * */
public class MainActivity extends AppCompatActivity implements DialogInterface.OnShowListener {
    RecyclerView weekView;
    WeekAdapter adapter;
    Button new_week;
    EditText title;
    AlertDialog alertDialog;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weekView=findViewById(R.id.weekView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        weekView.setLayoutManager(llm);
        adapter=new WeekAdapter(MainActivity.this, Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("id")).toString());
        weekView.setAdapter(adapter);
        new_week=findViewById(R.id.new_week);
    }

    public void newWeekClicked(View view) {
        showAlert();
    }
    void showAlert(){
        LayoutInflater li = LayoutInflater.from(context);
        View add_timetable_view = li.inflate(R.layout.new_week_alert, new LinearLayout(this),false);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
        mDialogBuilder.setView(add_timetable_view);
        title=add_timetable_view.findViewById(R.id.title);
        mDialogBuilder.setCancelable(true).setPositiveButton("Создать", null);
        mDialogBuilder.setNegativeButton("Отмена",null);
        alertDialog = mDialogBuilder.create();
        alertDialog.setOnShowListener(this);
        alertDialog.show();
    }
    @Override
    public void onShow(DialogInterface dialogInterface) {
        Button button = (alertDialog).getButton(AlertDialog.BUTTON_POSITIVE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=title.getText().toString().trim();
                if (TextUtils.isEmpty(text)){
                    title.setError("Пожалуйста, введите название недели!");
                }else {
                    Week week=new Week(text, Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("id")).toString());
                    ((WeekAdapter) Objects.requireNonNull(weekView.getAdapter())).addItem(week);
                    alertDialog.dismiss();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(MainActivity.this, TimeTableActivity.class);
        startActivity(i);
    }
}
