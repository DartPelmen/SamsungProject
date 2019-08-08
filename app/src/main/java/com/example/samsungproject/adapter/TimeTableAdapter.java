package com.example.samsungproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungproject.MainActivity;
import com.example.samsungproject.R;
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
        TimeTable timeTable=timeTables.get(position);
        holder.title.setText(timeTable.getTitle());
    }

    @Override
    public int getItemCount() {
        return timeTables.size();
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
            String s=timeTables.get(pos).getId();
            Intent i=new Intent(view.getContext(), MainActivity.class);
            i.putExtra("id",s);
            context.startActivity(i);
        }
    }
}
