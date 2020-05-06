package com.example.health_care.models;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.health_care.R;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MedecinViewHolder extends RecyclerView.ViewHolder {
    public TextView nom;
    public TextView prenom;
    public TextView specialite;
    public TextView tel;
    public TextView adress;
    private CardView item;
    private Dialog dialog;
    public TextView names;
    public TextView spec;
    public String email;
    private Button btn1;
    public Medecin med;
    private  Button btn2;


    public MedecinViewHolder(View itemView,View itemView1) {
        super(itemView);
        item = (CardView) itemView.findViewById(R.id.item);
        names = itemView1.findViewById(R.id.names);
        spec = itemView1.findViewById(R.id.spec);
        btn1 = itemView1.findViewById(R.id.send);
        btn2 = itemView1.findViewById(R.id.cancel);
        nom = itemView.findViewById(R.id.nomMedecin);
        prenom = itemView.findViewById(R.id.prenomMedecin);
        specialite = itemView.findViewById(R.id.specialite);
        tel = itemView.findViewById(R.id.telMedecin);
        adress = itemView.findViewById(R.id.adresseMedecin);
        med = null;
    }
}