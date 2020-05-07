package com.example.health_care.models;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.health_care.R;

import androidx.recyclerview.widget.RecyclerView;

public class RequestViewHolder extends RecyclerView.ViewHolder{
    public TextView name;
    public Button btn1;
    public Button btn2;
    public TextView names;
    public Patient patient;

    public RequestViewHolder(View itemView,View itemView1){
        super(itemView);
        name = itemView.findViewById(R.id.name);
        names = itemView1.findViewById(R.id.names);
        btn1 = itemView1.findViewById(R.id.send);
        btn2 = itemView1.findViewById(R.id.cancel);
    }
}
