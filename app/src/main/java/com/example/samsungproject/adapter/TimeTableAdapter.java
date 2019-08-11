package com.example.samsungproject.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.samsungproject.MainActivity;
import com.example.samsungproject.R;
import com.example.samsungproject.database.AppDatabase;
import com.example.samsungproject.models.TimeTable;

import java.util.List;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.ViewHolder> {


    private LayoutInflater inflater;

    private List<TimeTable> timeTables;

    public TimeTableAdapter(Context context, List<TimeTable> timeTables) {
        this.inflater = LayoutInflater.from(context);
        this.timeTables = timeTables;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.timetable_list_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimeTable timeTable = timeTables.get(position);
        holder.title.setText(timeTable.getTitle());
    }

    @Override
    public int getItemCount() {
        return timeTables.size();
    }

    public void addItem(TimeTable tm) {
        timeTables.add(tm);
        notifyDataSetChanged();
    }
    public void deleteFrom(int pos) {
        timeTables.remove(pos);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final Context context;
        Button edit, delete;
        TextView title, alert_title;

        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            edit = view.findViewById(R.id.edit);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater li = LayoutInflater.from(context);
                    View add_timetable_view = li.inflate(R.layout.new_timetable_alert, null);
                    final AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
                    mDialogBuilder.setView(add_timetable_view);
                    alert_title=add_timetable_view.findViewById(R.id.title);
                    mDialogBuilder.setCancelable(true).setPositiveButton("Сохранить", null);
                    mDialogBuilder.setNegativeButton("Отмена",null);

                    final AlertDialog alertDialog = mDialogBuilder.create();
                    alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            Button button = (alertDialog).getButton(AlertDialog.BUTTON_POSITIVE);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String text=alert_title.getText().toString().trim();
                                    if (TextUtils.isEmpty(text)){
                                        title.setError("Заголовок расписания не должен быть пустым!");
                                    }else {
                                        timeTables.get(getAdapterPosition()).setTitle(text);
                                        Log.i("TITLE DSADSADASDSADAD",timeTables.get(getAdapterPosition()).getTitle());
                                        timeTables.get(getAdapterPosition()).setTitle(text);
                                        new UpdateAsynkTask().execute(timeTables.get(getAdapterPosition()));
                                        alertDialog.dismiss();
                                    }
                                }
                            });
                        }

                    });
                    alertDialog.show();
                }
            });
            delete=view.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=getAdapterPosition();
                    new DeleteAsynkTask().execute(timeTables.get(pos).getId(), String.valueOf(pos));
                }
            });
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            context = view.getContext();
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            String s = timeTables.get(pos).getId();
            Intent i = new Intent(view.getContext(), MainActivity.class);
            i.putExtra("id", s);
            context.startActivity(i);
        }

        @Override
        public boolean onLongClick(View view) {
            if (edit.getVisibility()==View.VISIBLE)
            {
                edit.setVisibility(View.INVISIBLE);
                delete.setVisibility(View.INVISIBLE);
            }
            else{
                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
            }
            return true;
        }
        class DeleteAsynkTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... strings) {
                AppDatabase db =  Room.databaseBuilder(context,
                        AppDatabase.class, "database").build();

                db.timeTableDao().delete(strings[0]);
                return strings[1];
            }

            @Override
            protected void onPostExecute(String aString) {
                super.onPostExecute(aString);

                deleteFrom(Integer.parseInt(aString));
            }


        }
        class UpdateAsynkTask extends AsyncTask<TimeTable, Void, TimeTable> {
            @Override
            protected TimeTable doInBackground(TimeTable... timeTables) {
                AppDatabase db =  Room.databaseBuilder(context,
                        AppDatabase.class, "database").build();
                Log.i("ID+TITLE",timeTables[0].getId()+" "+timeTables[0].getTitle());
                db.timeTableDao().update(timeTables[0]);
                return timeTables[0];
            }

            @Override
            protected void onPostExecute(TimeTable t) {
                super.onPostExecute(t);
                notifyItemChanged(getAdapterPosition());
               // setAt(getAdapterPosition(),t);
            }


        }

    }
}