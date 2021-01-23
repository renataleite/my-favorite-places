package com.renata.myfavoriteplaces;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListPlaces extends AppCompatActivity {
    //atributos da activity
    ArrayList<String> mPlace_id, mPlace_name, mPlace_longitude, mPlace_latitude, mPlace_description, mPlace_zoom;
    RecyclerView mRvPlaces;
    CustomAdapter mCustomAdapter;
    private MyDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_activity_list_places);

        init();
    }

    private void init() {
        //associa o atributo mRvPlaces ao componente RecyclerView do layout
        mRvPlaces = findViewById(R.id.idRvPlaces);

        storeDataInArrays();
        /*instancia um objeto da classe CustomAdapter, a qual recebe os seguintes argumentos:
        contexto e os arraylists populados pelo método storeDataInArrays*/
        mCustomAdapter = new CustomAdapter(ListPlaces.this, mPlace_id, mPlace_name, mPlace_description, mPlace_latitude, mPlace_longitude, mPlace_zoom);
        //seta o customAdapter no reciclerView
        mRvPlaces.setAdapter(mCustomAdapter);
        //seta o gerenciador de layout, instanciando um linearlayout nessa activity
        mRvPlaces.setLayoutManager(new LinearLayoutManager(ListPlaces.this));
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }
    //faz uma consulta a base de dados e armazena os dados nos respectivos arrayslists declarados nessa activity
    private void storeDataInArrays() {
        //é instanciado um objeto da class MyDatabaseHelper, a qual representa a base de dados
        db = new MyDatabaseHelper(this);
        //é instanciado vários ArrayList
        mPlace_id = new ArrayList<>();
        mPlace_name = new ArrayList<>();
        mPlace_latitude = new ArrayList<>();
        mPlace_longitude = new ArrayList<>();
        mPlace_description = new ArrayList<>();
        mPlace_zoom = new ArrayList<>();

        //o cursor recebe a leitura de toda a base de dados
        Cursor cursor = db.readAllData();
        //se a base de dados estiver vazia
        if (cursor.getCount() == 0) {
            //é mostrada uma curta mensagem dizendo que não há dados
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            //é adicionado cada dado recebido pelo cursor, no respectivo arraylist enquanto houver dados
            while (cursor.moveToNext()) {
                mPlace_id.add(cursor.getString(0));
                mPlace_name.add(cursor.getString(1));
                mPlace_description.add(cursor.getString(2));
                mPlace_latitude.add(cursor.getString(3));
                mPlace_longitude.add(cursor.getString(4));
                mPlace_zoom.add(cursor.getString(5));
            }
        }
    }
}