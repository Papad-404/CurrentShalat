package com.example.currentsholat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button button, button1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.city);
        Intent intent = getIntent();
        textView.setText(intent.getStringExtra("mycity"));

        button = findViewById(R.id.btncity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mintent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(mintent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        break;
                    case R.id.sholat:
                        startActivity(new Intent(MainActivity.this, MuslimShalat.class));
                        break;
                    case R.id.kiblat:
                        startActivity(new Intent(MainActivity.this, Compass.class));
                        break;
                }
                return false;
            }
        });
    }

}
