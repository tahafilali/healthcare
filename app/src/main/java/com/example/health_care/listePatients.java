package com.example.health_care;

import android.content.Intent;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import java.util.ArrayList;

public class listePatients extends AppCompatActivity {

    DatabaseReference ref;
    DatabaseReference ref2;
    private RecyclerView list;
    FirebaseAuth mAuth;
    private ArrayList<Patient> arrayList;
    private ArrayList<String> arrayKeys;
    String key;
    Patient pat;
    String id;
    Patient patient;
    FirebaseUser user;

    private FirebaseRecyclerOptions<Patient> options;
    private FirebaseRecyclerAdapter<Patient, PatientViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_liste_patients);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Patients list");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        list = findViewById(R.id.recycle);
        ref = FirebaseDatabase.getInstance().getReference().child("Invitations accepter").child(user.getUid().toString());
        ref2 = FirebaseDatabase.getInstance().getReference().child("Patients");
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(listePatients.this));

        key = "";
        pat = null;
        arrayList = new ArrayList<Patient>();
        arrayKeys = new ArrayList<String>();
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    pat = ds.getValue(Patient.class);
                    key = ds.getKey().toString();
                    arrayList.add(pat);
                    arrayKeys.add(ds.getKey().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        searching("");
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        MenuItem item = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.stopListening();
                searching(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.stopListening();
                searching(newText);
                adapter.startListening();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void searching(String search){
        Query q = ref.orderByChild("nom").startAt(search).endAt(search+"\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<Patient>().setQuery(q,Patient.class).build();

        adapter = new FirebaseRecyclerAdapter<Patient, PatientViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PatientViewHolder holder, int position, @NonNull Patient model) {
                holder.name.setText(model.getNom().toString()+" "+model.getPrenom().toString());
                holder.patient = model;

            }

            @NonNull
            @Override
            public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v;
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_patient,parent,false);
                final PatientViewHolder pHolder = new PatientViewHolder(v);
                pHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Patient p = pHolder.patient;
                        for (int i = 0;i<arrayList.size();i++){
                            if (arrayList.get(i).getEmail().toString().equals(p.getEmail().toString())){
                                id = arrayKeys.get(i).toString();
                            }
                        }
                        Intent i = new Intent(listePatients.this,DossierMedicalMain.class);

                        i.putExtra("message", id);
                        startActivity(i);
                    }
                });
                return pHolder;
            }
        };
        list.setAdapter(adapter);
    }
}
