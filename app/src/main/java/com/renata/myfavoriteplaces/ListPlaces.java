package com.renata.myfavoriteplaces;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListPlaces extends AppCompatActivity {

    public ArrayList<String> place_id, place_name, place_longitude, place_latitude, place_description;
    RecyclerView listPlaces;
    CustomAdapter customAdapter;
    private MyDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_places);

        init();
    }

    private void init() {

        listPlaces = findViewById(R.id.listPlaces);

        storeDataInArrays();

        customAdapter = new CustomAdapter(ListPlaces.this, place_id, place_name, place_description, place_latitude, place_longitude);
        listPlaces.setAdapter(customAdapter);
        listPlaces.setLayoutManager(new LinearLayoutManager(ListPlaces.this));
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
        // do some stuff here
    }

    private void storeDataInArrays() {
        db = new MyDatabaseHelper(this);
        place_id = new ArrayList<>();
        place_name = new ArrayList<>();
        place_latitude = new ArrayList<>();
        place_longitude = new ArrayList<>();
        place_description = new ArrayList<>();

        Cursor cursor = db.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                place_id.add(cursor.getString(0));
                place_name.add(cursor.getString(1));
                place_description.add(cursor.getString(2));
                place_latitude.add(cursor.getString(3));
                place_longitude.add(cursor.getString(4));
            }
        }
    }
}