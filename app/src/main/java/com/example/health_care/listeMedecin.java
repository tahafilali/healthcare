package com.example.health_care;

import android.os.Bundle;

import com.example.health_care.models.Medecin;
import com.example.health_care.models.MedecinViewHolder;
import com.example.health_care.models.PatientViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

public class listeMedecin extends AppCompatActivity {
    DatabaseReference ref;
    private RecyclerView list;
    private ArrayList<Medecin> arrayList;
    private FirebaseRecyclerOptions<Medecin> options;
    private FirebaseRecyclerAdapter<Medecin, MedecinViewHolder> adapter;
    EditText search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_medecin);
        search = findViewById(R.id.search);
        list = findViewById(R.id.recycle);
        ref = FirebaseDatabase.getInstance().getReference().child("Medecins");
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(listeMedecin.this));
        arrayList = new ArrayList<Medecin>();
        options = new FirebaseRecyclerOptions.Builder<Medecin>().setQuery(ref, Medecin.class).build();
        adapter = new FirebaseRecyclerAdapter<Medecin, MedecinViewHolder>(options) {

            @NonNull
            @Override
            public MedecinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new MedecinViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false));
            }

            @Override
            protected void onBindViewHolder(@NonNull MedecinViewHolder holder, int position, @NonNull Medecin model) {
                holder.name.setText(model.getNom().toString()+" "+model.getPrenom().toString());
            }
        };
        list.setAdapter(adapter);

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
