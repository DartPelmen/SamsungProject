package com.example.samsungproject.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungproject.MainActivity;
import com.example.samsungproject.R;
import com.example.samsungproject.models.TimeTable;

import java.util.List;
/*
 * Адаптер для работы recycleview расписаний.
 * Здесь реализовано изменение списка в клиенте, передача команд на изменение данных в БД, а также клики по элементу.
 * */
public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.ViewHolder> {


    private LayoutInflater inflater;
    private List<TimeTable> timeTables;

    public TimeTableAdapter(Context context) {

        this.inflater = LayoutInflater.from(context);
        this.timeTables = DataTask.getTimeTables();
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
        DataTask.insertTimeTable(tm);
        timeTables.add(tm);
        notifyDataSetChanged();
    }
    private void deleteFrom(int pos) {
        DataTask.deleteTimeTable(timeTables.get(pos).getId());
        timeTables.remove(pos);
        notifyDataSetChanged();
    }
    private void updateFrom(int pos) {
        DataTask.updateTimeTable(timeTables.get(pos));
        notifyItemChanged(pos);
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
                   showAlert();
                }
            });
            delete=view.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteFrom(getAdapterPosition());
                }
            });
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            context = view.getContext();
        }

        private void showAlert(){
            LayoutInflater li = LayoutInflater.from(context);
            View add_timetable_view = li.inflate(R.layout.new_timetable_alert, new LinearLayout(context),false);
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
                                updateFrom(getAdapterPosition());
                                alertDialog.dismiss();
                            }
                        }
                    });
                }

            });
            alertDialog.show();
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

    }
}