package com.example.health_care;

import android.os.Bundle;

import com.example.health_care.models.MedicalFolder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DossierMedical extends AppCompatActivity {
    DatabaseReference ref;
    FirebaseUser user;
    FirebaseAuth mAuth;
LinearLayout bloodSect;
    LinearLayout pulseSect;
    LinearLayout oxymetySect;
    LinearLayout heightSect;
    LinearLayout weightsec;
    LinearLayout tempSect;
    LinearLayout orderSect;
    LinearLayout situationSect;
    TextView blood;
    TextView pulse;
    TextView oxymetry;
    MedicalFolder md;
    TextView height;
    TextView weight;
    TextView temp;
    TextView order;
    TextView situation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossier_medical);
        bloodSect = findViewById(R.id.bloodSect);
        oxymetySect = findViewById(R.id.oxymetrySect);
        pulseSect = findViewById(R.id.pulseSect);
        heightSect = findViewById(R.id.heightSect);
        weightsec = findViewById(R.id.weightsec);
        tempSect = findViewById(R.id.tempSect);
        orderSect = findViewById(R.id.orderSect);
        situationSect = findViewById(R.id.situationSect);
        blood = findViewById(R.id.blood);
        pulse = findViewById(R.id.pulse);
        oxymetry = findViewById(R.id.oxymetry);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        temp = findViewById(R.id.temp);
        order = findViewById(R.id.order);
        situation = findViewById(R.id.situation);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        md = null;
        ref = FirebaseDatabase.getInstance().getReference().child("Dossier Medical").child(user.getUid().toString());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    md = dataSnapshot.getValue(MedicalFolder.class);
                    blood.setText(String.valueOf(md.getBloodPressure()));
                    pulse.setText(String.valueOf(md.getPULSE()));
                    oxymetry.setText(String.valueOf(md.getOxymety()));
                    height.setText(String.valueOf(md.getHeight()));
                    weight.setText(String.valueOf(md.getWeight()));
                    temp.setText(String.valueOf(md.getTemperature()));
                    order.setText(md.getOrder());
                    situation.setText(md.getSituation());
                }
                else {
                    blood.setText("0");
                    pulse.setText("0");
                    oxymetry.setText("0");
                    height.setText("0");
                    weight.setText("0");
                    temp.setText("0");
                    order.setText("0");
                    situation.setText("0");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }

}
