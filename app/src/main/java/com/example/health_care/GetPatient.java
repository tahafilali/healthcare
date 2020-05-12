package com.example.health_care;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.health_care.models.Patient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GetPatient#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GetPatient extends Fragment {
    DatabaseReference ref;
    DatabaseReference reff;
    private ArrayList<Patient> arrayList;
    private ArrayList<String> arrayKeys;
    String key;
    Patient pat;
    FrameLayout back;
    Patient patient;
    FirebaseAuth mAuth;
    boolean married=true;


    FirebaseUser user;
    LinearLayout profile_email_block,profile_tel_block,profile_addresse_block,profile_sf_block;
    TextView profile_nom,profile_tel,profile_addresse,profile_email,profile_sf;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GetPatient() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GetPatient.
     */
    // TODO: Rename and change types and number of parameters
    public static GetPatient newInstance(String param1, String param2) {
        GetPatient fragment = new GetPatient();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v;
        v = inflater.inflate(R.layout.fragment_get_patient, container, false);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();




        pat = null;

        DossierMedicalMain activity = (DossierMedicalMain) getActivity();
        final String data= activity.getIntent().getExtras().getString("message");
        reff =  FirebaseDatabase.getInstance().getReference().child("Patients");
        profile_email = v.findViewById(R.id.profile_email);
        back = v.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),listePatients.class));
            }
        });
        profile_tel =  v.findViewById(R.id.profile_tel);
        profile_sf =  v.findViewById(R.id.profile_sf);
        profile_addresse =  v.findViewById(R.id.profile_addresse);
        profile_nom =  v.findViewById(R.id.profile_nom);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    if (ds.getKey().equals(data)){
                        pat = ds.getValue(Patient.class);
                        profile_nom.setText(pat.getNom()+" "+pat.getPrenom());

                        profile_tel.setText(pat.getTel());
                        profile_email.setText(pat.getEmail());

                        profile_addresse.setText(pat.getAdresse());
                        if (pat.isSituationFamiliale()==true){
                            profile_sf.setText("Married");
                        }
                        else {
                            profile_sf.setText("Single");
                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        return v;

    }
}
