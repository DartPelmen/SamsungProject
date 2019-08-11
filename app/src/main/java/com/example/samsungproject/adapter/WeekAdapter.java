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

import com.example.samsungproject.DayActivity;
import com.example.samsungproject.R;
import com.example.samsungproject.database.AppDatabase;
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
    public void deleteFrom(int pos) {
        weeks.remove(pos);
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final Context context;
        Button edit,delete;
        TextView title, alert_title;
        ViewHolder(View view){
            super(view);
            title =view.findViewById(R.id.title);
            edit = view.findViewById(R.id.edit);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater li = LayoutInflater.from(context);
                    View add_timetable_view = li.inflate(R.layout.new_week_alert, null);
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
                                        title.setError("Нзавание недели не длолжно быть пустым!");
                                    }else {
                                        weeks.get(getAdapterPosition()).setTitle(text);
                                        Log.i("TITLE DSADSADASDSADAD",weeks.get(getAdapterPosition()).getTitle());
                                        weeks.get(getAdapterPosition()).setTitle(text);
                                        new UpdateAsynkTask().execute(weeks.get(getAdapterPosition()));
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
                    new DeleteAsynkTask().execute(weeks.get(getAdapterPosition()).getId());
                }
            });
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
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

                db.weekDao().delete(strings[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                deleteFrom(getAdapterPosition());
            }


        }
        class UpdateAsynkTask extends AsyncTask<Week, Void, Week> {
            @Override
            protected Week doInBackground(Week... weeks) {
                AppDatabase db =  Room.databaseBuilder(context,
                        AppDatabase.class, "database").build();
                Log.i("ID+TITLE",weeks[0].getId()+" "+weeks[0].getTitle());
                db.weekDao().update(weeks[0]);
                return weeks[0];
            }

            @Override
            protected void onPostExecute(Week w) {
                super.onPostExecute(w);
                notifyItemChanged(getAdapterPosition());
                // setAt(getAdapterPosition(),t);
            }


        }
    }
}
