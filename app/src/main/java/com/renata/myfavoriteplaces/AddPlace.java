package com.renata.myfavoriteplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

public class AddPlace extends AppCompatActivity {

    EditText txtName, txtLatitude, txtLongitude, txtDescription;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        init();
    }

    private void init() {
        txtLongitude = findViewById(R.id.txtLongitude);
        txtLatitude = findViewById(R.id.txtLatitude);
        txtName = findViewById(R.id.txtName);
        txtDescription = findViewById(R.id.txtDescription);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(v -> {
            LatLng latLng = new LatLng(
                    Double.parseDouble(txtLatitude.getText().toString().trim()),
                    Double.parseDouble(txtLongitude.getText().toString().trim()));
            Place place = new Place(txtName.getText().toString().trim(), txtDescription.getText().toString().trim(), latLng);

            MyDatabaseHelper db = new MyDatabaseHelper(this);
            db.addPlace(place);
        });
    }
}