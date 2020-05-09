package com.example.health_care;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.health_care.models.Patient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    DatabaseReference ref;
    Patient patient;
    FirebaseAuth mAuth;
    boolean married=true;

    FrameLayout back;

    FirebaseUser user;
    LinearLayout profile_email_block,profile_tel_block,profile_addresse_block,profile_sf_block;
    TextView profile_nom,profile_tel,profile_addresse,profile_email,profile_sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        ref = FirebaseDatabase.getInstance().getReference().child("Patients").child(user.getUid().toString());

        profile_email = findViewById(R.id.profile_email);
        profile_nom = findViewById(R.id.profile_nom);
        profile_tel = findViewById(R.id.profile_tel);
        profile_addresse = findViewById(R.id.profile_addresse);
        profile_sf = findViewById(R.id.profile_sf);


        profile_email_block= findViewById(R.id.profile_emailblock);
        profile_tel_block= findViewById(R.id.profile_telblock);
        profile_addresse_block= findViewById(R.id.profile_addresseblock);
        profile_sf_block= findViewById(R.id.profile_sfblock);

        back=findViewById(R.id.back);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                patient = dataSnapshot.getValue(Patient.class);
                profile_email.setText(patient.getEmail());
                profile_nom.setText(patient.getNom()+" "+patient.getPrenom());
                profile_addresse.setText(patient.getAdresse());
                profile_tel.setText(patient.getTel());
                if(patient.getSituationFamiliale())
                    profile_sf.setText("Marié");
                else
                    profile_sf.setText("Single");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        profile_email_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView titre,ancien;
                EditText nom,prenom;
                boolean married;
                RadioGroup marr;
                marr = findViewById(R.id.marr);
                married = false;

                    final AlertDialog.Builder alert = new AlertDialog.Builder(ProfileActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.request_dialog_profile,null);

                    final EditText diagnom = (EditText)mView.findViewById(R.id.diag_nom);

                    titre=(TextView) mView.findViewById(R.id.titre);
                    ancien=(TextView) mView.findViewById(R.id.ancien);

                    titre.setText("Modifier email");
                    ancien.setText("email: "+patient.getEmail());

                diagnom.setVisibility(view.VISIBLE);
                diagnom.setHint("nouveau email... ");
                    Button btn_cancel = (Button)mView.findViewById(R.id.cancel);
                    Button btn_okay = (Button)mView.findViewById(R.id.send);
                    alert.setView(mView);
                    final AlertDialog alertDialog = alert.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    btn_okay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ref.child("email").setValue(diagnom.getText().toString());
                            Toast.makeText(ProfileActivity.this,diagnom.toString(), Toast.LENGTH_SHORT).show();

                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();



            }
        });
        profile_tel_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView titre,ancien;

                final AlertDialog.Builder alert = new AlertDialog.Builder(ProfileActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.request_dialog_profile,null);
                final EditText diagnom = (EditText)mView.findViewById(R.id.diag_nom);

                titre=(TextView) mView.findViewById(R.id.titre);
                ancien=(TextView) mView.findViewById(R.id.ancien);

                titre.setText("Modifier tel");
                ancien.setText("tel: "+patient.getTel());


                diagnom.setHint("Nouveau tel... ");

                diagnom.setVisibility(view.VISIBLE);
                diagnom.setHint("modifier tel...");
                Button btn_cancel = (Button)mView.findViewById(R.id.cancel);
                Button btn_okay = (Button)mView.findViewById(R.id.send);
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                btn_okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ref.child("tel").setValue(diagnom.getText().toString());
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();



            }
        });
        profile_addresse_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView titre,ancien;

                final AlertDialog.Builder alert = new AlertDialog.Builder(ProfileActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.request_dialog_profile,null);
                final EditText diagnom = (EditText)mView.findViewById(R.id.diag_nom);

                titre=(TextView) mView.findViewById(R.id.titre);
                ancien=(TextView) mView.findViewById(R.id.ancien);

                titre.setText("Modifier adresse");
                ancien.setText("adresse: "+patient.getAdresse());


                diagnom.setHint("Nouvelle adresse... ");
                diagnom.setVisibility(view.VISIBLE);

                Button btn_cancel = (Button)mView.findViewById(R.id.cancel);
                Button btn_okay = (Button)mView.findViewById(R.id.send);
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                btn_okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ref.child("adresse").setValue(diagnom.getText().toString());
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();



            }
        });
        profile_sf_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView titre,ancien;


                final AlertDialog.Builder alert = new AlertDialog.Builder(ProfileActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.request_dialog_profile,null);
                final RadioGroup diagnom = (RadioGroup) mView.findViewById(R.id.marr);

                titre=(TextView) mView.findViewById(R.id.titre);
                ancien=(TextView) mView.findViewById(R.id.ancien);

                titre.setText("Modifier Situation familiale");
                if(patient.getSituationFamiliale()==true)
                ancien.setText("SF: Marié");
                else
                    ancien.setText("SF: Single");

                diagnom.getCheckedRadioButtonId();
                diagnom.setVisibility(view.VISIBLE);
                Button btn_cancel = (Button)mView.findViewById(R.id.cancel);
                Button btn_okay = (Button)mView.findViewById(R.id.send);
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                btn_okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ref.child("situationFamiliale").setValue(married);

                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();



            }
        });
        profile_nom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView titre,ancien;

                final AlertDialog.Builder alert = new AlertDialog.Builder(ProfileActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.request_dialog_profile,null);
                titre=(TextView) mView.findViewById(R.id.titre);
                ancien=(TextView) mView.findViewById(R.id.ancien);

                titre.setText("Modifier Nom");
                ancien.setText(patient.getNom()+" "+patient.getPrenom());
                final EditText diagnom = (EditText)mView.findViewById(R.id.diag_nom);
                final EditText diagprenom = (EditText)mView.findViewById(R.id.diag_prenom);

                diagnom.setHint("Nouveau nom... ");
                diagnom.setVisibility(view.VISIBLE);

                diagprenom.setHint("Nouveau prenom... ");
                diagprenom.setVisibility(view.VISIBLE);


                Button btn_cancel = (Button)mView.findViewById(R.id.cancel);
                Button btn_okay = (Button)mView.findViewById(R.id.send);
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                btn_okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ref.child("nom").setValue(diagnom.getText().toString());
                        ref.child("prenom").setValue(diagprenom.getText().toString());

                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();



            }
        });

    back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
        }
    });
    }
    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rad1:
                if (checked)
                    married = true;
                break;
            case R.id.rad2:
                if (checked)
                    married = false;
                break;
        }
    }
}
