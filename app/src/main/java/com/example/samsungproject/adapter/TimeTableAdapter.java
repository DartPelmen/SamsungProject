package com.example.samsungproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.DragEvent;
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

import javax.inject.Inject;

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
        TextView title;

        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            edit = view.findViewById(R.id.edit_t);
            delete=view.findViewById(R.id.delete_t);
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

    }
}