package com.renata.myfavoriteplaces;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private Button btnSave, btnRemove;
    private TextView txtLat, txtLng, txtName, txtDescription;
    private GoogleMap mMap;
    private Marker marker;
    private Place place;
    private String rowId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        init();
    }

    private void init() {
        txtLat = findViewById(R.id.txtLat);
        txtLng = findViewById(R.id.txtLng);
        txtName = findViewById(R.id.txtName);
        txtDescription = findViewById(R.id.txtDescription);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            Place place = new Place(txtName.getText().toString(), txtDescription.getText().toString(), marker.getPosition());
            MyDatabaseHelper db = new MyDatabaseHelper(this);
            db.addPlace(place);
            finish();
        });

        btnRemove = findViewById(R.id.btnRemove);
        btnRemove.setOnClickListener(v -> {
            MyDatabaseHelper db = new MyDatabaseHelper(this);
            db.deleteOneRow(rowId);
            finish();
        });

        makeViewChanges();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.setOnMapClickListener(this);
        if (getIntent().hasExtra("id")) {
            addMarker(place.getLatLng());
        }
    }

    private void makeViewChanges() {
        if (getIntent().hasExtra("id")) {
            Intent intent = getIntent();
            LatLng position = new LatLng(
                    Double.parseDouble(intent.getStringExtra("latitude")),
                    Double.parseDouble(intent.getStringExtra("longitude")));

            place = new Place(
                    intent.getStringExtra("name"),
                    intent.getStringExtra("description"),
                    position
            );

            rowId = intent.getStringExtra("id");

            txtName.setText(place.getPlaceName());
            txtDescription.setText(place.getDescription());
            txtLat.setText(String.valueOf(place.getLatitude()));
            txtLng.setText(String.valueOf(place.getLongitude()));

            txtName.setEnabled(false);
//            txtName.setInputType(InputType.TYPE_NULL);

            txtDescription.setEnabled(false);
//            txtDescription.setInputType(InputType.TYPE_NULL);

            btnRemove.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.GONE);
        } else {
            btnRemove.setVisibility(View.GONE);
            btnSave.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onMapClick(LatLng position) {
        addMarker(position);
    }

    public void addMarker(LatLng position) {
        if (marker != null) {
            mMap.clear();
        }
        marker = mMap.addMarker(new MarkerOptions().position(position).title("Marcador"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));

        txtLat.setText("Latitude: \n" + String.valueOf(position.latitude));
        txtLng.setText("Longitude: \n" + String.valueOf(position.longitude));
    }
}