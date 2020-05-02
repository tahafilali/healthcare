package com.example.health_care;

import android.content.Intent;
import android.os.Bundle;

import com.example.health_care.models.PatientViewHolder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.view.View;

public class HomeActivity extends AppCompatActivity {
private CardView search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, listeMedecin.class));
            }
        });

    }

}
