package com.renata.myfavoriteplaces;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    /*declaro os atributos dessa activity, que são dois butões, um de salvar e outro de remover, quatro TextView,
    que representam a latitude, longitude, o nome do lugar e a descrição do lugar, um fragmento GoogleMap e um Marker
    (que é o alfinete, o qual marca a localização deseja)*/
    private Button mBtnSave, mBtnRemove;
    private TextView mTvLat, mTvLng, mTvName, mTvDescription;
    private GoogleMap mMap;
    private Marker mMarker;
    //Declaro a variável place e uma String chamada rowId
    private Place mPlace;
    private String mRowId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ll_activity_maps);
        //mapFragment carrega o mapa dentro do fragmento
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.idMap);

        mapFragment.getMapAsync(this);

        init();
    }

    private void init() {
        //faz as associações dos atributos com os componentes da activiy.
        mTvLat = findViewById(R.id.idTvLat);
        mTvLng = findViewById(R.id.idTvLng);
        mTvName = findViewById(R.id.idTvName);
        mTvDescription = findViewById(R.id.idTvDescription);

        mBtnSave = findViewById(R.id.idBtnSave);
        /*ao clicar no botão save, é instanciada a classe Place, a qual recebe os seguintes
        argumentos: o nome que foi escrito na mTvName, a descrição que foi escrita no mTvDescription,
        recebe o latlng(latitude e longitude do local), através da posição do marcador
        e recebe o zoom(o qual ampliará o mapa na posição onde está o marcador).*/
        mBtnSave.setOnClickListener(v -> {
            if (mMarker != null) {
                Place place = new Place(mTvName.getText().toString(), mTvDescription.getText().toString(), mMarker.getPosition(), String.valueOf(mMap.getCameraPosition().zoom));
                //instancia a base de dados
                MyDatabaseHelper db = new MyDatabaseHelper(this);
                //adiciona o objeto place nessa base de dados
                db.addPlace(place);
                //fecha a activity
                finish();
            } else {
                Toast.makeText(this, "Insert a marker on Map", Toast.LENGTH_LONG).show();
            }
        });

        mBtnRemove = findViewById(R.id.idBtnRemove);
        //ao clicar no botão remove
        mBtnRemove.setOnClickListener(v -> {
            //instancio a base de dados
            MyDatabaseHelper db = new MyDatabaseHelper(this);
            //deleto os dados do lugar correspondente ao id do registo que eu quero apagar
            db.deleteOneRow(mRowId);
            //fecha a activity
            finish();
        });

        changeVieMode();
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
    //é invocado quando o mapa estiver pronto
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(this);
        //se for trazido, através de intent, a informação id de outra activity
        if (getIntent().hasExtra("id")) {
            //é adicionado um alfinete no mapa no local em que o objeto place traz como latitude e longitude
            addMarker(mPlace.getLatLng());
            //googleMAp posiciona a câmera dando um zoom no local onde está o marcador.
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mPlace.getLatLng(), Float.valueOf(getIntent().getStringExtra("zoom"))));
        }
    }
    //verifica se há ou não informações vindas de outra acitivity
    private void changeVieMode() {
        //se vier informações de outra activity ele expõe essas informações
        if (getIntent().hasExtra("id")) {

            Intent intent = getIntent();
            //instancia um objeto da classe Latlong e passa como argumento a latitude e a longitude trazidas pela classe CustomAdapter
            LatLng position = new LatLng(
                    Double.parseDouble(intent.getStringExtra("latitude")),
                    Double.parseDouble(intent.getStringExtra("longitude")));
            /*instancia um objeto da classe Place e passa como argumento o nome,a descrição trazida pela classe CustomAdapter
            e a posição declarada acima.*/
            mPlace = new Place(
                    intent.getStringExtra("name"),
                    intent.getStringExtra("description"),
                    position,
                    intent.getStringExtra("zoom"));
            //a rowId recebe o id pela classe CustomAdapter
            mRowId = intent.getStringExtra("id");

            //seta o texto de todas as informações acima nos Text View dessa Activity
            mTvName.setText(mPlace.getmPlaceName());
            mTvDescription.setText(mPlace.getmDescription());
            mTvLat.setText(String.valueOf(mPlace.getLatitude()));
            mTvLng.setText(String.valueOf(mPlace.getLongitude()));
            //o TextView do nome passa a ser não editável
            mTvName.setEnabled(false);
            mTvDescription.setEnabled(false);
            //O botão remove é visível e o botão save fica invisível
            mBtnRemove.setVisibility(View.VISIBLE);
            mBtnSave.setVisibility(View.GONE);
            //se não ele remove o botão delete e deixa apenas o botão save.*/
        } else {
            //caso não venha nenhum conteúdo da activity anterior
            //o botão remove fica invisível e o botão save passa a ser visível
            mBtnRemove.setVisibility(View.GONE);
            mBtnSave.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onMapClick(LatLng position) {
        //quando se clica no mapa, é invocado o método  passando como argumento a posição latlng do local clicado.
        if (!getIntent().hasExtra("id")) {
            addMarker(position);
        }
    }

    //adiciona um marcador no mapa
    public void addMarker(LatLng position) {
        //se o marcador for diferente de nulo
        if (mMarker != null) {
            //limpa os marcadodores do mapa
            mMap.clear();
        }
        //é adicionado um marcador na posição recebida como argumento e é setado um título para o marcador
        mMarker = mMap.addMarker(new MarkerOptions().position(position).title(mTvName.getText().toString()));
        //move a camera do mapa para onde está o marcador
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        //seta no TextView da activity a latitude e a longitude
        mTvLat.setText("Latitude: \n" + String.valueOf(position.latitude));
        mTvLng.setText("Longitude: \n" + String.valueOf(position.longitude));
    }
}