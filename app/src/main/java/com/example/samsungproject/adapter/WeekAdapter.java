package com.example.samsungproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungproject.DayActivity;
import com.example.samsungproject.R;
import com.example.samsungproject.models.Day;

import java.util.List;

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.ViewHolder> {


    private LayoutInflater inflater;

    private List<Day> days;

    public WeekAdapter(Context context, List<Day> days) {
        this.inflater = LayoutInflater.from(context);
        this.days = days;
        Log.i("ASDASDDASADSADSDASDAS", String.valueOf(days.size()));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.week_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Day day=days.get(position);
        holder.dayT.setText(String.valueOf(day.getDayT()));
        holder.lessonN.setText(String.valueOf(day.getNum()));
    }

    @Override
    public int getItemCount() {
        return days.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final Context context;

        TextView dayT, lessonN;
        ViewHolder(View view){
            super(view);
            dayT=view.findViewById(R.id.dayTitle);
            lessonN=view.findViewById(R.id.lessonNum);
            view.setOnClickListener(this);
            context=view.getContext();
        }
        @Override
        public void onClick(View view) {
            int pos=getAdapterPosition();
            String s=days.get(pos).getId();
            Log.i("ID ",s);
            Intent i=new Intent(view.getContext(), DayActivity.class);
            i.putExtra("id",s);
            context.startActivity(i);
        }
    }
}
