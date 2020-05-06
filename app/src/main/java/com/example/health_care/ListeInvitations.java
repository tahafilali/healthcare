package com.example.health_care;

import android.app.VoiceInteractor;
import android.os.Bundle;

import com.example.health_care.models.Patient;
import com.example.health_care.models.PatientViewHolder;
import com.example.health_care.models.RequestViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class ListeInvitations extends AppCompatActivity {
    DatabaseReference ref;
    DatabaseReference reff;
    FirebaseAuth mAuth;
    FirebaseUser user;
    Query ref2;
    private RecyclerView list;
    private ArrayList<Patient> arrayList;
    private FirebaseRecyclerOptions<Patient> options;
    private FirebaseRecyclerAdapter<Patient, RequestViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_invitations);
        list = findViewById(R.id.recycle);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        reff = FirebaseDatabase.getInstance().getReference().child("Patients");
        ref = FirebaseDatabase.getInstance().getReference().child("Invitations").child(user.getUid().toString());
       // ref2 =  FirebaseDatabase.getInstance().getReference("Patients").orderByKey().equalTo(ref.child(user.getUid().toString()));

        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(ListeInvitations.this));


        arrayList = new ArrayList<Patient>();


        options = new FirebaseRecyclerOptions.Builder<Patient>().setQuery(ref,Patient.class).build();

        adapter = new FirebaseRecyclerAdapter<Patient, RequestViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RequestViewHolder holder, int position, @NonNull Patient model) {
                holder.name.setText(model.getNom().toString()+" "+model.getPrenom().toString());
            }

            @NonNull
            @Override
            public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new RequestViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_patient,parent,false));
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
