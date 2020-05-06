package com.example.health_care.models;

import android.view.View;
import android.widget.TextView;

import com.example.health_care.R;

import androidx.recyclerview.widget.RecyclerView;

public class RequestViewHolder extends RecyclerView.ViewHolder{
    public TextView name;
    public RequestViewHolder(View itemView){
        super(itemView);
        name = itemView.findViewById(R.id.name);

    }
}
