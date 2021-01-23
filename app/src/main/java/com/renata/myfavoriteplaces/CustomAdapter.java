package com.renata.myfavoriteplaces;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// O RecyclerView.ViewHolder ajuda a criar ViewHolders que preenchem os dados para as views presentes no recyclerView.
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    //declaração dos atributos da classe
    private Context mContext;
    private ArrayList<String> mPlace_id, mPlace_name, mPlace_description, mPlace_latitude, mPlace_longitude, mPlace_zoom;

    //criu o construtor do CustomAdapter
    public CustomAdapter(Context pContext,
                         ArrayList<String> pPlace_id,
                         ArrayList<String> pPlace_name,
                         ArrayList<String> pPlace_description,
                         ArrayList<String> pPlace_latitude,
                         ArrayList<String> pPlace_longitude,
                         ArrayList<String> pPlace_zoom
    ) {
        this.mContext = pContext;

        this.mPlace_id = pPlace_id;
        this.mPlace_name = pPlace_name;
        this.mPlace_description = pPlace_description;
        this.mPlace_latitude = pPlace_latitude;
        this.mPlace_longitude = pPlace_longitude;
        this.mPlace_zoom = pPlace_zoom;


    }

    // cria visualizações para o RecyclerView, aumentando o layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        // Preenche cada linha do ViewHolder com o layout place_row
        View view = inflater.inflate(R.layout.ll_place_row, parent, false);
        return new ViewHolder(view);
    }

    //é chamado ao vincular os dados às visualizações que estão sendo criadas no RecyclerView
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Define os dados para as visualizações
        holder.mTvplaceName.setText(String.valueOf(mPlace_name.get(position)));
        holder.mTvPlaceDescription.setText(String.valueOf(mPlace_description.get(position)));
        //define listas de cliques para itens individuais no visualizador
        holder.mLlPlaceRow.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, MapsActivity.class);
            intent.putExtra("id", String.valueOf(mPlace_id.get(position)));
            intent.putExtra("name", String.valueOf(mPlace_name.get(position)));
            intent.putExtra("description", String.valueOf(mPlace_description.get(position)));
            intent.putExtra("latitude", String.valueOf(mPlace_latitude.get(position)));
            intent.putExtra("longitude", String.valueOf(mPlace_longitude.get(position)));
            intent.putExtra("zoom", String.valueOf(mPlace_zoom.get(position)));
            mContext.startActivity(intent);
        });
    }

    //mostra a quantidade de elementos da lista
    @Override
    public int getItemCount() {
        return mPlace_name.size();
    }

    //a classe ViewHolder ajuda a preencher os dados  de cada item do recyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTvplaceName, mTvPlaceDescription;
        LinearLayout mLlPlaceRow;

        public ViewHolder(@NonNull View itemView) {
            //invoca o construtor da classe pai
            super(itemView);

            mTvplaceName = itemView.findViewById(R.id.idTvPlaceName);
            mTvPlaceDescription = itemView.findViewById(R.id.idTvPlaceDescription);
            mLlPlaceRow = itemView.findViewById(R.id.idLlPlaceRow);
        }
    }
}
