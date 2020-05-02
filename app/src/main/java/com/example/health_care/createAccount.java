package com.example.health_care;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.health_care.models.Medecin;
import com.example.health_care.models.Patient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class createAccount extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    EditText nom;
    EditText prenom;
    TextView date;
    EditText tel;
    EditText email;
    EditText adress;
    EditText password1;
    EditText password2;
    Button btn;
    boolean married;
    boolean doctor;
    EditText spec;
    EditText code;
    RadioGroup marr;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        date = findViewById(R.id.date);
        tel = findViewById(R.id.tel);
        email = findViewById(R.id.email);
        adress = findViewById(R.id.adresse);
        password1 = findViewById(R.id.password1);
        password2 =findViewById(R.id.password2);
        code = findViewById(R.id.code);
        spec = findViewById(R.id.spec);
        btn = findViewById(R.id.creat);
        marr = findViewById(R.id.marr);
        married = false;
        doctor = false;
        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
       date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int jour = cal.get(Calendar.DAY_OF_MONTH);
                int mois = cal.get(Calendar.MONTH);
                int annee = cal.get(Calendar.YEAR);
                DatePickerDialog dialog = new DatePickerDialog(
                        createAccount.this,android.R.style.Theme_Holo_Light,onDateSetListener,annee,mois,jour);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String birth = dayOfMonth+"-"+month+"-"+year;
                date.setText(birth);
            }
        };
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password1.getText().toString().equals(password2.getText().toString())){
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password1.getText().toString())
                            .addOnCompleteListener(createAccount.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(createAccount.this,"Authentication failed.",Toast.LENGTH_SHORT).show();


                                    } else {
                                        if (doctor == true){
                                            Medecin medecin = new Medecin(nom.getText().toString(), prenom.getText().toString(),spec.getText().toString(),adress.getText().toString(),tel.getText().toString(),email.getText().toString(),code.getText().toString());
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            ref.child("Medecins").child(user.getUid().toString()).setValue(medecin);
                                            // If sign in fails, display a message to the user.
                                            startActivity(new Intent(createAccount.this, LoginActivity.class));

                                        }
                                        else {
                                            Patient patient = new Patient(nom.getText().toString(), prenom.getText().toString(), date.getText().toString(), married, tel.getText().toString(), email.getText().toString(), adress.getText().toString());
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            ref.child("Patients").child(user.getUid().toString()).setValue(patient);
                                            // If sign in fails, display a message to the user.
                                            startActivity(new Intent(createAccount.this, LoginActivity.class));

                                        }
                                    }

                                    // ...
                                }
                            });

                }
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
    public void onRadioButtonClicked2(View view){
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rad3:
                if (checked)
                    doctor = false;
                spec.setVisibility(View.GONE);
                code.setVisibility(View.GONE);
                date.setVisibility(View.VISIBLE);
                marr.setVisibility(View.VISIBLE);
                break;
            case R.id.rad4:
                if (checked)
                    doctor = true;
                date.setVisibility(View.GONE);
                marr.setVisibility(View.GONE);
                spec.setVisibility(View.VISIBLE);
                code.setVisibility(View.VISIBLE);


                break;
        }
    }

    }


