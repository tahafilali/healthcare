package com.example.health_care;

import android.os.Bundle;

import com.example.health_care.models.Patient;
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

public class listePatients extends AppCompatActivity {

    DatabaseReference ref;
    private RecyclerView list;
    private ArrayList<Patient> arrayList;
    private FirebaseRecyclerOptions<Patient> options;
    private FirebaseRecyclerAdapter<Patient, PatientViewHolder> adapter;
    EditText search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_patients);
        search = findViewById(R.id.search);
        list = findViewById(R.id.recycle);
        ref = FirebaseDatabase.getInstance().getReference().child("Patients");
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(listePatients.this));
        arrayList = new ArrayList<Patient>();
        options = new FirebaseRecyclerOptions.Builder<Patient>().setQuery(ref,Patient.class).build();

        adapter = new FirebaseRecyclerAdapter<Patient, PatientViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PatientViewHolder holder, int position, @NonNull Patient model) {
                holder.name.setText(model.getNom().toString()+" "+model.getPrenom().toString());
            }

            @NonNull
            @Override
            public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new PatientViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false));
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
