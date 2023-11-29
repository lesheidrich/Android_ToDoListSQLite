package com.heidrich.beadando_todolist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    Activity activity;
    private ArrayList task_id, task_name, task_importance, task_status;
    Animation translate_anim;

    CustomAdapter(Activity activity, Context c, ArrayList tid, ArrayList tn, ArrayList ti, ArrayList ts){
        this.activity = activity;
        this.context = c;
        this.task_id = tid;
        this.task_name = tn;
        this.task_importance = ti;
        this.task_status = ts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.my_row, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.task_id_txt.setText(String.valueOf(task_id.get(position)));
        holder.task_name_txt.setText(String.valueOf(task_name.get(position)));
        holder.task_importance_txt.setText(String.valueOf(task_importance.get(position)));
        holder.task_status_txt.setText(String.valueOf(task_status.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(task_id.get(position)));
                intent.putExtra("task_name", String.valueOf(task_name.get(position)));
                intent.putExtra("task_importance", String.valueOf(task_importance.get(position)));
                intent.putExtra("task_status", String.valueOf(task_status.get(position)));

                activity.startActivityForResult(intent, 1);
//                if (activity instanceof MainActivity) {
//                    ((MainActivity) activity).startActivityForResult(intent, 1);
//                } else {
//                    activity.startActivityForResult(intent, 1);
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return task_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView task_id_txt, task_name_txt, task_importance_txt, task_status_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            task_id_txt = itemView.findViewById(R.id.task_id_txt);
            task_name_txt = itemView.findViewById(R.id.task_name_txt);
            task_importance_txt = itemView.findViewById(R.id.task_importance_txt);
            task_status_txt = itemView.findViewById(R.id.task_status_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }

}
