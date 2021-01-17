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

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> place_id, place_name, place_description, place_latitude, place_longitude;

    public CustomAdapter(Context context,
                         ArrayList<String> place_id,
                         ArrayList<String> place_name,
                         ArrayList<String> place_description,
                         ArrayList<String> place_latitude,
                         ArrayList<String> place_longitude) {
        this.context = context;

        this.place_id = place_id;
        this.place_name = place_name;
        this.place_description = place_description;
        this.place_latitude = place_latitude;
        this.place_longitude = place_longitude;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.place_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.place_name_txt.setText(String.valueOf(place_name.get(position)));
        holder.place_description_txt.setText(String.valueOf(place_description.get(position)));
        holder.placeRowLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, MapsActivity.class);
            intent.putExtra("id", String.valueOf(place_id.get(position)));
            intent.putExtra("name", String.valueOf(place_name.get(position)));
            intent.putExtra("description", String.valueOf(place_description.get(position)));
            intent.putExtra("latitude", String.valueOf(place_latitude.get(position)));
            intent.putExtra("longitude", String.valueOf(place_longitude.get(position)));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return place_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView place_name_txt, place_description_txt;
        LinearLayout placeRowLayout;
        View view;
//        public Item currentItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            view = itemView;
            view.setOnClickListener(v -> {

            });

            place_name_txt = itemView.findViewById(R.id.place_name);
            place_description_txt = itemView.findViewById(R.id.place_description);
            placeRowLayout = itemView.findViewById(R.id.placeRowLayout);
        }
    }
}
