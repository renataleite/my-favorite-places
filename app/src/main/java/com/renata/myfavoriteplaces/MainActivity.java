package com.renata.myfavoriteplaces;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //declaração dos atributos da classe da primeira view que são os dois botões
    Button mBtnPickPlace;
    Button mBtnShowPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ll_activity_main);

        init();
    }

    /*O método init faz a associação dos atributos acima declarados com os componentes da view e
    cria uma intent para mudança de activity para cada botão. Ao clicar no btnPickPlace abrirá a activity MapsActivity, a qual
    mostrará o GoogleMaps e ao clicar no btnShowPlaces abrirá a activity ListPlaces, a qual lista os lugares favoritos*/
    private void init() {
        //associação das variáveis com os componentes do layout
        mBtnPickPlace = findViewById(R.id.idBtnPickPlace);
        mBtnShowPlaces = findViewById(R.id.idbtnShowPlaces);

        mBtnPickPlace.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        });

        mBtnShowPlaces.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListPlaces.class);
            startActivity(intent);
        });
    }

}