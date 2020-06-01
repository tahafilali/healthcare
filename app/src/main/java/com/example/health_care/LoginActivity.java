package com.example.health_care;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
EditText login;
EditText password;
Button btn1;
ProgressBar progressBar ;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference ref;
Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        btn1 = findViewById(R.id.connect);
        ref = FirebaseDatabase.getInstance().getReference();
        btn2 = findViewById(R.id.sign);
        progressBar = findViewById(R.id.progressBar);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                btn1.setVisibility(View.GONE);
                btn2.setVisibility(View.GONE);
                login.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                mAuth.signInWithEmailAndPassword(login.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {


                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    btn1.setVisibility(View.VISIBLE);
                                    btn2.setVisibility(View.VISIBLE);
                                    login.setVisibility(View.VISIBLE);
                                    password.setVisibility(View.VISIBLE);


                                } else {
                                    // If sign in fails, display a message to the user.
                                    final  FirebaseUser user = mAuth.getCurrentUser();
                                   ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                       @Override
                                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                           if (dataSnapshot.child("Medecins").hasChild(user.getUid())){
                                               startActivity(new Intent(LoginActivity.this, medecinHome.class));
                                           }
                                           if (dataSnapshot.child("Patients").hasChild(user.getUid())){
                                               startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                                           }

                                       }

                                       @Override
                                       public void onCancelled(@NonNull DatabaseError databaseError) {

                                       }
                                   });


                                    // ...
                                }

                                // ...
                            }
                        });
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,createAccount.class);
                startActivity(intent);
            }
        });
    }


    protected void onStart(){
        super.onStart();
        progressBar.setVisibility(View.GONE);
        btn1.setVisibility(View.VISIBLE);
        btn2.setVisibility(View.VISIBLE);
        login.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);

        FirebaseUser user=mAuth.getCurrentUser();

        if (user!=null){
            progressBar.setVisibility(View.VISIBLE);
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            login.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
            final String id=user.getUid().toString();
            ref=FirebaseDatabase.getInstance().getReference();
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child("Patients").hasChild(id)){
                        Intent t1=new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(t1);
                    }
                    if (dataSnapshot.child("Medecins").hasChild(id)){
                        Intent t2=new Intent(LoginActivity.this,medecinHome.class);
                        startActivity(t2);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }


}
