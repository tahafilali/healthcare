package com.example.health_care;

import android.app.Dialog;
import android.app.VoiceInteractor;
import android.os.Bundle;

import com.example.health_care.models.MedecinViewHolder;
import com.example.health_care.models.MedicalFolder;
import com.example.health_care.models.Patient;
import com.example.health_care.models.PatientViewHolder;
import com.example.health_care.models.RequestViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListeInvitations extends AppCompatActivity {
    DatabaseReference ref;
    DatabaseReference reff;
    FirebaseAuth mAuth;
    Dialog dialog;
    Button btn1;
    Button btn2;
    TextView names;
    Patient patient;
    TextView spec;
    private ArrayList<String> arrayKeys;
    String key;
    FirebaseUser user;
    DatabaseReference ref2;
    DatabaseReference ref3;
    Patient pat;
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
        patient = null;
        ref = FirebaseDatabase.getInstance().getReference().child("Invitations").child(user.getUid().toString());
        ref2 =  FirebaseDatabase.getInstance().getReference();
        key = "";
        arrayList = new ArrayList<Patient>();
        arrayKeys = new ArrayList<String>();
        reff.addValueEventListener(new ValueEventListener() {
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
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(ListeInvitations.this));





        options = new FirebaseRecyclerOptions.Builder<Patient>().setQuery(ref,Patient.class).build();

        adapter = new FirebaseRecyclerAdapter<Patient, RequestViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RequestViewHolder holder, int position, @NonNull Patient model) {
                holder.name.setText(model.getNom().toString()+" "+model.getPrenom().toString());
                holder.names.setText(model.getNom().toString()+" "+model.getPrenom().toString());
                holder.patient = model;
            }

            @NonNull
            @Override
            public RequestViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
                View v;
                View v1;
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_patient,parent,false);
                v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_dialog,parent,false);
                final RequestViewHolder mHolder = new RequestViewHolder(v,v1);
                dialog = new Dialog(parent.getContext());
                dialog.setContentView(R.layout.request_dialog);

                spec = dialog.findViewById(R.id.spec);
                btn1 = dialog.findViewById(R.id.send);
                btn2 = dialog.findViewById(R.id.cancel);
                btn1.setText("Accept request");
                btn2.setText("Refuse request");
                names = dialog.findViewById(R.id.names);
                spec.setVisibility(View.GONE);
                mHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    String medId="";
                    @Override
                    public void onClick(View v) {
                        dialog.show();
                        final String[] finalMedId = {medId};
                       btn1.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {


                               Patient p = mHolder.patient;
                               for(int i = 0;i<arrayList.size();i++){
                                   if (p.getEmail().equals(arrayList.get(i).getEmail())){
                                       finalMedId[0] = arrayKeys.get(i);
                                   }
                               }
                               MedicalFolder m = new MedicalFolder("0","0","0","0","0","0","nothing","nothing");
                               ref2.child("Dossier Medical").child(finalMedId[0]).setValue(m);
                               ref2.child("Invitations accepter").child(user.getUid().toString()).child(finalMedId[0]).setValue(p).addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                  ref.removeValue();
                                   }
                               });
                               dialog.cancel();
                           }
                       });
                       btn2.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               ref.removeValue();
                               dialog.cancel();
                           }
                       });
                    }
                });
                return mHolder;
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
