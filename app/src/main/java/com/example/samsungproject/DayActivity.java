package com.example.samsungproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.samsungproject.adapter.DayAdapter;
import com.example.samsungproject.database.AppDatabase;
import com.example.samsungproject.models.Day;

import java.util.List;

public class DayActivity extends AppCompatActivity {
    final Context context = this;
    DayAdapter adapter;
    RecyclerView dayView;
    Button new_day;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        new DataAsynkTask().execute();
        new_day=findViewById(R.id.new_day);
        new_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(context);
                View add_timetable_view = li.inflate(R.layout.new_day_alert, null);
                final AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
                mDialogBuilder.setView(add_timetable_view);
                spinner=add_timetable_view.findViewById(R.id.spinner);
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
                                String text=spinner.getSelectedItem().toString();
                                    new DayActivity.InsertAsynkTask().execute(String.valueOf(spinner.getSelectedItemPosition()),getIntent().getExtras().get("id").toString());
                                    alertDialog.dismiss();
                            }
                        });
                    }

                });
                alertDialog.show();
            }
        });
    }

    class DataAsynkTask extends AsyncTask<Void, Void, Void> {

        List<Day> d;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            LinearLayoutManager llm = new LinearLayoutManager(DayActivity.this);
            dayView=findViewById(R.id.dayView);
            dayView.setLayoutManager(llm);
            adapter=new DayAdapter(DayActivity.this,d);
            dayView.setAdapter(adapter);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db =  Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database").build();
            d=db.dayDao().getAll();

            return null;
        }
    }

    class InsertAsynkTask extends AsyncTask<String, Void, Day> {
        @Override
        protected Day doInBackground(String... strings) {
            AppDatabase db =  Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database").build();
            Log.i("INSERT TITLE AND ID", strings[0]+" "+strings[1]);
            Day day=new Day(Integer.parseInt(strings[0]),strings[1]);
            db.dayDao().insert(day);
            return day;
        }

        @Override
        protected void onPostExecute(Day day) {
            super.onPostExecute(day);
            ((DayAdapter)dayView.getAdapter()).addItem(day);
        }

    }

}
