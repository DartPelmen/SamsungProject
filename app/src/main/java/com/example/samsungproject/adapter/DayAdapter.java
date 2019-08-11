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
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.samsungproject.LessonActivity;
import com.example.samsungproject.R;
import com.example.samsungproject.database.AppDatabase;
import com.example.samsungproject.models.Day;

import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder> {


    private LayoutInflater inflater;

    private List<Day> days;

    public DayAdapter(Context context, List<Day> days) {
        this.inflater = LayoutInflater.from(context);
        this.days = days;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.day_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Day day=days.get(position);
        holder.title.setText(String.valueOf(day.getDayT()));
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public void addItem(Day day){
        days.add(day);
        notifyDataSetChanged();
    }
    public void deleteFrom(int pos) {
        days.remove(pos);
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final Context context;

        TextView title;
        Spinner alert_title;
        Button edit, delete;
        ViewHolder(View view){
            super(view);
            title=view.findViewById(R.id.title);
            edit=view.findViewById(R.id.edit);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater li = LayoutInflater.from(context);
                    View add_timetable_view = li.inflate(R.layout.new_day_alert, null);
                    final AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
                    mDialogBuilder.setView(add_timetable_view);
                    alert_title=add_timetable_view.findViewById(R.id.spinner);
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
                                        Integer num=alert_title.getSelectedItemPosition();
                                        days.get(getAdapterPosition()).setNum(num);
                                        new UpdateAsynkTask().execute(days.get(getAdapterPosition()));
                                        alertDialog.dismiss();

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
                    new DeleteAsynkTask().execute(days.get(getAdapterPosition()).getId());
                }
            });
            view.setOnLongClickListener(this);
            view.setOnClickListener(this);
            context=view.getContext();
        }
        @Override
        public void onClick(View view) {
            int pos=getAdapterPosition();
            String s=days.get(pos).getId();
            Intent i=new Intent(view.getContext(), LessonActivity.class);
            i.putExtra("id",s);
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
        class DeleteAsynkTask extends AsyncTask<String, Void, Void> {
            @Override
            protected Void doInBackground(String... strings) {
                AppDatabase db =  Room.databaseBuilder(context,
                        AppDatabase.class, "database").build();

                db.dayDao().delete(strings[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                deleteFrom(getAdapterPosition());
            }
        }

        class UpdateAsynkTask extends AsyncTask<Day, Void, Day> {
            @Override
            protected Day doInBackground(Day... days) {
                AppDatabase db =  Room.databaseBuilder(context,
                        AppDatabase.class, "database").build();
                db.dayDao().update(days[0]);
                return days[0];
            }

            @Override
            protected void onPostExecute(Day d) {
                super.onPostExecute(d);
                notifyItemChanged(getAdapterPosition());
                // setAt(getAdapterPosition(),t);
            }


        }
    }


}
