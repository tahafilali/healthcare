package com.example.health_care.models;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.health_care.R;
import com.google.firebase.database.FirebaseDatabase;

import androidx.recyclerview.widget.RecyclerView;

public class PatientViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public PatientViewHolder(View itemView){
        super(itemView);
        name = itemView.findViewById(R.id.name);

    }

}
