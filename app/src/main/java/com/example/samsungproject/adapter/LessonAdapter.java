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

import com.example.samsungproject.LessonInfoActivity;
import com.example.samsungproject.R;
import com.example.samsungproject.models.Lesson;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.ViewHolder> {

    private LayoutInflater inflater;

    private List<Lesson> lessons;

    public LessonAdapter(Context context, List<Lesson> lessons) {
        this.inflater = LayoutInflater.from(context);
        this.lessons = lessons;
        Log.i("SIZEOF ADAPTER LESSONS", String.valueOf(lessons.size()));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.week_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lesson lesson=lessons.get(position);
        String s=lessons.get(position).getId()+" "+lessons.get(position).getTitle()+" "+lessons.get(position).getNumber()+" "+lessons.get(position).getDescription();
        Log.i("INFO ABOUT LESSON",s);
        holder.title.setText(String.valueOf(lesson.getTitle()));
        holder.time.setText(String.valueOf(lesson.getNumber()));
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final Context context;

        TextView title, time;
        ViewHolder(View view){
            super(view);
            title =view.findViewById(R.id.title);
            time =view.findViewById(R.id.time);
            view.setOnClickListener(this);
            context=view.getContext();
        }
        @Override
        public void onClick(View view) {
            int pos=getAdapterPosition();
            String s=lessons.get(pos).getId();
            Log.i("ID ",s);
            Intent i=new Intent(view.getContext(), LessonInfoActivity.class);
            i.putExtra("id",s);
            i.putExtra("number",lessons.get(pos).getNumber());
            i.putExtra("title",lessons.get(pos).getTitle());
            i.putExtra("description",lessons.get(pos).getDescription());
            context.startActivity(i);
        }
    }
}