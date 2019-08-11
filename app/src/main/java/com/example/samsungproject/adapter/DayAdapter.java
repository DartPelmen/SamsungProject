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

import com.example.samsungproject.LessonActivity;
import com.example.samsungproject.R;
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
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final Context context;

        TextView title;
        ViewHolder(View view){
            super(view);
            title=view.findViewById(R.id.title);
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
    }


}
