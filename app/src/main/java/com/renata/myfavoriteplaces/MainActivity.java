package com.renata.myfavoriteplaces;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
// Based from tutorial on: https://www.youtube.com/watch?v=J-CP7g_GwpI
    Button btnPickPlace;
    Button btnShowPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        btnPickPlace = findViewById(R.id.btnPickPlace);
        btnShowPlaces = findViewById(R.id.btnShowPlaces);

        btnPickPlace.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        });

        btnShowPlaces.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListPlaces.class);
            startActivity(intent);
        });
    }

}