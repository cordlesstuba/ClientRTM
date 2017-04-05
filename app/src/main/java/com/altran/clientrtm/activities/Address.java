package com.altran.clientrtm.activities;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by gcarves on 03/04/2017.
 */

public class Address {

    public LatLng latLng;
    public String id;

    public Address(String id, LatLng latLng) {
        this.id = id;
        this.latLng = latLng;
    }


    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
