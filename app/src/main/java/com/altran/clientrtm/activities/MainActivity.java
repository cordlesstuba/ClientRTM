package com.altran.clientrtm.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.altran.clientrtm.R;
import com.altran.clientrtm.fragments.SelectModeFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static GoogleMap _googleMap = null;
    private static Marker start_marker;
    private static Marker end_marker;
    private static List<Marker> markers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                _googleMap = googleMap;
            }
        });



        SelectModeFragment selectModeFragment = new SelectModeFragment();
        replaceFragmentWithAnimation(selectModeFragment,"");

    }

    public void replaceFragmentWithAnimation(android.support.v4.app.Fragment fragment, String tag){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }


    public static void AddMarker(LatLng latLng, int placeNumber) {


        switch (placeNumber){

            case 0:
                start_marker = _googleMap.addMarker(new MarkerOptions().position(latLng));
                markers.add(start_marker);
                pointToPosition(latLng);
                break;

            case 1:
                end_marker = _googleMap.addMarker(new MarkerOptions().position(latLng));
                markers.add(end_marker);
                pointTwoMakers(markers);
                break;


        }

    }

    private static void pointTwoMakers(List<Marker> markers){

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        int padding = 100; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        _googleMap.moveCamera(cu);
        _googleMap.animateCamera(cu);

    }

    private static void pointToPosition(LatLng position) {
        //Build camera position
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(position)
                .zoom(15).build();
        //Zoom in and animate the camera.
        _googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
