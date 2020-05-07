package com.example.health_care;

import android.os.Bundle;

import com.example.health_care.models.Medecin;
import com.example.health_care.models.Patient;
import com.example.health_care.models.PatientViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    DatabaseReference ref2;
    private RecyclerView list;
    FirebaseAuth mAuth;

    FirebaseUser user;

    private FirebaseRecyclerOptions<Patient> options;
    private FirebaseRecyclerAdapter<Patient, PatientViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_liste_patients);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        list = findViewById(R.id.recycle);
        ref = FirebaseDatabase.getInstance().getReference().child("Invitations accepter").child(user.getUid().toString());
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(listePatients.this));



        options = new FirebaseRecyclerOptions.Builder<Patient>().setQuery(ref,Patient.class).build();

        adapter = new FirebaseRecyclerAdapter<Patient, PatientViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PatientViewHolder holder, int position, @NonNull Patient model) {
                holder.name.setText(model.getNom().toString()+" "+model.getPrenom().toString());

            }

            @NonNull
            @Override
            public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new PatientViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_patient,parent,false));
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
