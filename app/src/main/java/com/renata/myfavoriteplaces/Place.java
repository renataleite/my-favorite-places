package com.renata.myfavoriteplaces;

import com.google.android.gms.maps.model.LatLng;

public class Place {
    private int id;
    private String placeName;
    private String description;
    private LatLng latlng;

    public Place(String place_name, String description, LatLng latlng) {
        this.placeName = place_name;
        this.description = description;
        this.latlng = latlng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

//    public void setPlace_name(String place_name) {
//        this.place_name = place_name;
//    }

    public String getDescription() {
        return description;
    }

//    public void setDescription(String description) {
//        this.description = description;
//    }

    public Double getLatitude() {
        return latlng.latitude;
    }

//    public void setLatitude(Float latitude) {
//        this.latitude = latitude;
//    }

    public Double getLongitude() {
        return latlng.longitude;
    }

    public LatLng getLatLng() {
        return latlng;
    }

//    public void setLongitude(Float longitude) {
//        this.longitude = longitude;
//    }
}
