package com.renata.myfavoriteplaces;

import com.google.android.gms.maps.model.LatLng;

public class Place {

    private String mPlaceName;
    private String mDescription;
    private LatLng mLatlng;
    private String mZoom;

    public Place(String place_name, String description, LatLng latlng, String zoom) {
        this.mPlaceName = place_name;
        this.mDescription = description;
        this.mLatlng = latlng;
        this.mZoom = zoom;
    }

    public String getmPlaceName() {
        return mPlaceName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public Double getLatitude() {
        return mLatlng.latitude;
    }

    public Double getLongitude() {
        return mLatlng.longitude;
    }

    public LatLng getLatLng() {
        return mLatlng;
    }

    public String getmZoom() { return mZoom; }

}
