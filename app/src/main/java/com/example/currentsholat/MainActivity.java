package com.example.currentsholat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

        button1 = findViewById(R.id.toms);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ontent = new Intent(MainActivity.this, MuslimShalat.class);
                startActivity(ontent);
            }
        });

    }
}
