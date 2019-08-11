package com.example.samsungproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.samsungproject.adapter.TimeTableAdapter;
import com.example.samsungproject.models.TimeTable;

import java.util.Objects;

/*
* Активность расписания.
* Показывает доступные расписания.
* Расписания отображены в RecycleView.
* Реализованы: создание и редактирование расписания через диалоговое окно (AlertDialog), удаление расписания.
*
*  Переход дальше происходит по клику на элемент.
* При longclick показывает кнопки для удаления и редактирования.
 * */
public class TimeTableActivity extends AppCompatActivity implements DialogInterface.OnShowListener{
    final Context context = this;
    AlertDialog alertDialog;
    RecyclerView timetableView;
    TimeTableAdapter adapter;
    Button new_timetable;
    EditText title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        timetableView=findViewById(R.id.timetablesView);
        timetableView=findViewById(R.id.timetablesView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        timetableView.setLayoutManager(llm);
        adapter=new TimeTableAdapter(this);
        timetableView.setAdapter(adapter);
        new_timetable=findViewById(R.id.new_timetable);
    }

    public void newTimTableClicked(View view) {
        showAlert();
    }

    void showAlert(){
        LayoutInflater li = LayoutInflater.from(context);
        View add_timetable_view = li.inflate(R.layout.new_timetable_alert, new LinearLayout(this),false);
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
                    title.setError("Пожалуйста, введите название расписания!");
                }else {
                    ((TimeTableAdapter) Objects.requireNonNull(timetableView.getAdapter())).addItem(new TimeTable(text));
                    alertDialog.dismiss();
                }
            }
        });
    }
}
