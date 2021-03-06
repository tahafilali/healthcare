package com.example.health_care;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.view.View;
import android.widget.LinearLayout;

public class medecinHome extends AppCompatActivity {
LinearLayout patients;
CardView invit;
CardView medProf;
    private CardView logout;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_medecin_home);
        patients = findViewById(R.id.list_pats);
        invit = findViewById(R.id.invit);
        patients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(medecinHome.this, listePatients.class));
            }
        });
        invit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(medecinHome.this,ListeInvitations.class));
            }
        });
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getInstance().signOut();
                startActivity(new Intent(medecinHome.this,LoginActivity.class));
            }
        });
        medProf = findViewById(R.id.profilMed);
        medProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(medecinHome.this,MedecinProfil.class));
            }
        });

    }

}
