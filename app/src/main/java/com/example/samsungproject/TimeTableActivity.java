package com.example.samsungproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.samsungproject.adapter.TimeTableAdapter;
import com.example.samsungproject.database.AppDatabase;
import com.example.samsungproject.models.TimeTable;

import java.util.List;


public class TimeTableActivity extends AppCompatActivity {
    final Context context = this;

    RecyclerView timetableView;
    TimeTableAdapter adapter;
    Button new_timetable;
    EditText title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        new DataAsynkTask().execute();
        new_timetable=findViewById(R.id.new_timetable);
        new_timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(context);
                View add_timetable_view = li.inflate(R.layout.new_timetable_alert, null);
                final AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
                mDialogBuilder.setView(add_timetable_view);
                title=add_timetable_view.findViewById(R.id.title);
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
                                if (TextUtils.isEmpty(text)){
                                    title.setError("Пожалуйста, введите название расписания!");
                                }else {
                                    new InsertAsynkTask().execute(title.getText().toString());
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

        List<TimeTable> d;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            timetableView=findViewById(R.id.timetablesView);
            LinearLayoutManager llm = new LinearLayoutManager(TimeTableActivity.this);
            timetableView.setLayoutManager(llm);
            adapter=new TimeTableAdapter(TimeTableActivity.this,d);
            timetableView.setAdapter(adapter);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db =  Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database").build();

            d=db.timeTableDao().getAll();
            return null;
        }
    }
    class InsertAsynkTask extends AsyncTask<String, Void, TimeTable> {


        @Override
        protected TimeTable doInBackground(String... strings) {
            AppDatabase db =  Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database").build();
            TimeTable tm=new TimeTable(strings[0]);
            db.timeTableDao().insert(tm);
            return tm;
        }

        @Override
        protected void onPostExecute(TimeTable timeTable) {
            super.onPostExecute(timeTable);
            ((TimeTableAdapter)timetableView.getAdapter()).addItem(timeTable);
        }


    }
}
