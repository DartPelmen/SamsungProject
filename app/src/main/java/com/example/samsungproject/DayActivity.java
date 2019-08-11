package com.example.samsungproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungproject.adapter.DayAdapter;
import com.example.samsungproject.adapter.Divider;
import com.example.samsungproject.models.Day;

import java.util.Objects;

/*
 * Активность дней недели.
 * Показывает доступные дни.
 * Дни отображены в RecycleView.
 * Реализованы: создание и редактирование дней через диалоговое окно (AlertDialog), удаление дня. *
 *
 * Переход дальше происходит по клику на элемент.
 * При longclick показывает кнопки для удаления и редактирования.
 *
 * При создании активности (кроме timetable делается выборка данных по id.
 *
 * Для перехода назад в слушателе (OnBackPressed) реализовано получение id сущности-родителя (lesson->day->week->timetable)
 *и переход на активность с передачей этого id.
 * */
public class DayActivity extends AppCompatActivity implements DialogInterface.OnShowListener {
    final Context context = this;
    DayAdapter adapter;
    RecyclerView dayView;
    Button new_day;
    Spinner spinner;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        LinearLayoutManager llm = new LinearLayoutManager(DayActivity.this);
        dayView=findViewById(R.id.dayView);
        dayView.addItemDecoration(new Divider(this));
        dayView.setLayoutManager(llm);
        adapter=new DayAdapter(DayActivity.this, Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("id")).toString());
        dayView.setAdapter(adapter);
        new_day=findViewById(R.id.new_day);
    }


    public void newDayClicked(View view) {
        showAlert();
    }

    private void showAlert() {
        LayoutInflater li = LayoutInflater.from(context);
        View add_timetable_view = li.inflate(R.layout.new_day_alert, new LinearLayout(this),false);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
        mDialogBuilder.setView(add_timetable_view);
        spinner=add_timetable_view.findViewById(R.id.spinner);
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
                Day day=new Day(spinner.getSelectedItemPosition(), Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("id")).toString());
                ((DayAdapter) Objects.requireNonNull(dayView.getAdapter())).addItem(day);
                alertDialog.dismiss();
            }
        });
    }
}
