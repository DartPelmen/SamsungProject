package com.example.samsungproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungproject.DayActivity;
import com.example.samsungproject.R;
import com.example.samsungproject.models.Week;

import java.util.List;

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.ViewHolder> {
    private LayoutInflater inflater;

    private List<Week> weeks;

    public WeekAdapter(Context context, List<Week> weeks) {
        this.inflater = LayoutInflater.from(context);
        this.weeks = weeks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.week_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Week week=weeks.get(position);

        holder.title.setText(String.valueOf(week.getTitle()));
    }

    @Override
    public int getItemCount() {
        return weeks.size();
    }

    public void addItem(Week week){
        weeks.add(week);
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final Context context;

        TextView title;
        ViewHolder(View view){
            super(view);
            title =view.findViewById(R.id.title);
            view.setOnClickListener(this);
            context=view.getContext();
        }
        @Override
        public void onClick(View view) {
            int pos=getAdapterPosition();
            String s=weeks.get(pos).getId();
            Intent i=new Intent(view.getContext(), DayActivity.class);
            i.putExtra("id",s);
            i.putExtra("title",weeks.get(pos).getTitle());
            context.startActivity(i);
        }
    }
}
