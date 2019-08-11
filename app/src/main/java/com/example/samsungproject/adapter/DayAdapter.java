package com.example.samsungproject.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungproject.LessonActivity;
import com.example.samsungproject.R;
import com.example.samsungproject.models.Day;

import java.util.List;

/*
* Адаптер для работы recycleview дней.
* Здесь реализовано изменение списка в клиенте, передача команд на изменение данных в БД, а также клики по элементу.
* */
public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder> {


    private LayoutInflater inflater;

    private List<Day> days;

    public DayAdapter(Context context, String weekId) {
        this.inflater = LayoutInflater.from(context);
        this.days = DataTask.getDays(weekId);
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
        DataTask.insertDay(day);
        days.add(day);
        notifyDataSetChanged();
    }
    private void deleteFrom(int pos) {
        DataTask.deleteDay(days.get(pos).getId());
        days.remove(pos);
        notifyDataSetChanged();
    }
    private void updateFrom(int pos) {
        DataTask.updateDay(days.get(pos));
        notifyItemChanged(pos);
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
                    View add_timetable_view = li.inflate(R.layout.new_day_alert, new LinearLayout(context),false);
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
                                        int num=alert_title.getSelectedItemPosition();
                                        days.get(getAdapterPosition()).setNum(num);
                                        updateFrom(getAdapterPosition());
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
                    deleteFrom(getAdapterPosition());
                }
            });
            view.setOnLongClickListener(this);
            view.setOnClickListener(this);
            context=view.getContext();
        }
        @Override
        public void onClick(View view) {
            int pos=getAdapterPosition();
            Intent i=new Intent(view.getContext(), LessonActivity.class);
            i.putExtra("id",days.get(pos).getId());
            i.putExtra("weekid",days.get(pos).getWeekId());
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
