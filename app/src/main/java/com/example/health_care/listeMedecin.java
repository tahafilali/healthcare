package com.example.health_care;

import android.app.Dialog;
import android.graphics.ColorSpace;
import android.os.Bundle;

import com.example.health_care.models.Medecin;
import com.example.health_care.models.MedecinViewHolder;
import com.example.health_care.models.Patient;
import com.example.health_care.models.PatientViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class listeMedecin extends AppCompatActivity {
    DatabaseReference ref;
    private RecyclerView list;
    Button btn1;
    FirebaseUser user;
    Button btn2;
    private FirebaseAuth mAuth;
    private ArrayList<Medecin> arrayList;
    private ArrayList<String> arrayKeys;

    Medecin med;
    private FirebaseRecyclerOptions<Medecin> options;
    TextView spec;
    TextView names;
    private FirebaseRecyclerAdapter<Medecin, MedecinViewHolder> adapter;
    Dialog dialog;
    String key;
    Patient p ;
    String medId="";

    DatabaseReference ref2;
    DatabaseReference ref3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_liste_medecin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Donctors list");
        list = findViewById(R.id.recycle);
        ref = FirebaseDatabase.getInstance().getReference().child("Medecins");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        ref2 = FirebaseDatabase.getInstance().getReference();
        ref3 = FirebaseDatabase.getInstance().getReference("Patients").child(user.getUid().toString());
        list.setHasFixedSize(true);
        p = null;


        list.setLayoutManager(new LinearLayoutManager(listeMedecin.this));

        list.setAdapter(adapter);
        arrayList = new ArrayList<Medecin>();
        arrayKeys = new ArrayList<String>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){

                    med = ds.getValue(Medecin.class);
                    key = ds.getKey().toString();
                    arrayList.add(med);
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

    private void searching(String searchText){
        String query = searchText;
        Query reff = ref.orderByChild("nom").startAt(query).endAt(query+"\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<Medecin>().setQuery(reff,Medecin.class).build();
        adapter = new FirebaseRecyclerAdapter<Medecin, MedecinViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MedecinViewHolder holder, int position, @NonNull Medecin model) {
                holder.nom.setText(model.getNom().toString());
                holder.prenom.setText(model.getPrenom().toString());
                holder.specialite.setText(model.getSpecialite().toString());
                holder.tel.setText(model.getTel().toString());
                holder.adress.setText(model.getAdresse().toString());
                holder.names.setText(model.getNom().toString()+" "+model.getPrenom().toString());
                holder.spec.setText(model.getSpecialite().toString());
                holder.med = model;
            }

            @NonNull
            @Override
            public MedecinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v;
                View v1;
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
                v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_dialog,parent,false);

                final MedecinViewHolder mHolder = new MedecinViewHolder(v,v1);
                dialog = new Dialog(parent.getContext());
                dialog.setContentView(R.layout.request_dialog);

                spec = dialog.findViewById(R.id.spec);
                btn1 = dialog.findViewById(R.id.send);
                btn2 = dialog.findViewById(R.id.cancel);
                names = dialog.findViewById(R.id.names);
                mHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String medId="";
                        for(int i = 0;i<arrayList.size();i++){
                            if (mHolder.med.getEmail().equals(arrayList.get(i).getEmail())){
                                medId = arrayKeys.get(i);
                            }
                        }
                        ref2.child("Invitations").child(medId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(user.getUid().toString())){
                                    btn1.setVisibility(View.GONE);
                                }
                                else {
                                    btn1.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        names.setText(mHolder.names.getText().toString());
                        spec.setText(mHolder.spec.getText().toString());
                        dialog.show();
                        btn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });
                        final String[] finalMedId = {medId};
                        btn1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                for(int i = 0;i<arrayList.size();i++){
                                    if (mHolder.med.getEmail().equals(arrayList.get(i).getEmail())){
                                        finalMedId[0] = arrayKeys.get(i);
                                    }
                                }

                                ref3.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        p = dataSnapshot.getValue(Patient.class);
                                        ref2.child("Invitations").child(finalMedId[0]).child(user.getUid().toString()).setValue(p);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });




                                Toast.makeText(listeMedecin.this,"The request has been sent",Toast.LENGTH_SHORT).show();

                                dialog.cancel();

                            }
                        });
                    }
                });

                return mHolder;
            }
        };list.setAdapter(adapter);

    }


}