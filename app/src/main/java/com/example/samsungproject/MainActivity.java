package com.example.samsungproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.samsungproject.adapter.WeekAdapter;
import com.example.samsungproject.database.AppDatabase;
import com.example.samsungproject.models.Week;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    RecyclerView weekView;
    WeekAdapter adapter;
    Button new_week;
    EditText title;

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DataAsynkTask().execute();
        new_week=findViewById(R.id.new_week);
        new_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(context);
                View add_timetable_view = li.inflate(R.layout.new_week_alert, null);
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
                                    title.setError("Пожалуйста, введите название недели!");
                                }else {
                                    new MainActivity.InsertAsynkTask().execute(title.getText().toString(),getIntent().getExtras().get("id").toString());
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

        List<Week>d;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            weekView=findViewById(R.id.weekView);

            LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
            weekView.setLayoutManager(llm);
            adapter=new WeekAdapter(MainActivity.this,d);
            weekView.setAdapter(adapter);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db =  Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database").build();
            d=db.weekDao().getAllByTimeTableId(getIntent().getExtras().get("id").toString());
            return null;
        }
    }

    class InsertAsynkTask extends AsyncTask<String, Void, Week> {
        @Override
        protected Week doInBackground(String... strings) {
            AppDatabase db =  Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database").build();
            Log.i("INSERT TITLE AND ID", strings[0]+" "+strings[1]);
            Week week=new Week(strings[0],strings[1]);
            db.weekDao().insert(week);
            return week;
        }

        @Override
        protected void onPostExecute(Week week) {
            super.onPostExecute(week);
            ((WeekAdapter)weekView.getAdapter()).addItem(week);
        }


    }
}
