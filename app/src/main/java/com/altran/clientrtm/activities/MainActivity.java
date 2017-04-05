package com.altran.clientrtm.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.altran.clientrtm.R;
import com.altran.clientrtm.fragments.SelectModeFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonLineStringStyle;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.utils.URIBuilder;

public class MainActivity extends AppCompatActivity {

    private static GoogleMap _googleMap = null;
    private static List<Marker> markers = new ArrayList<>();

    private static Address addressDepart;
    private static Address addressArrivee;


    GeoJsonLayer layerFaster = null;
    GeoJsonLayer layerShortest = null;
    GeoJsonLayer layerSafer = null;

    ProgressBar progressBarMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBarMain = (ProgressBar) findViewById(R.id.progressBarMain);
        progressBarMain.setVisibility(View.INVISIBLE);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(googleMap -> _googleMap = googleMap);

        SelectModeFragment selectModeFragment = new SelectModeFragment();
        replaceFragmentWithAnimation(selectModeFragment, "");

    }

    public void replaceFragmentWithAnimation(android.support.v4.app.Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
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


        switch (placeNumber) {

            case 0:
                Marker start_marker = _googleMap.addMarker(new MarkerOptions().position(latLng));
                markers.add(start_marker);
                pointToPosition(latLng);
                break;

            case 1:
                Marker end_marker = _googleMap.addMarker(new MarkerOptions().position(latLng));
                markers.add(end_marker);
                pointTwoMakers(markers);
                break;
        }
    }

    private static void pointTwoMakers(List<Marker> markers) {

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
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(position)
                .zoom(15).build();
        _googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void AlertUser(String s) {
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
    }


    public static Address getAddressDepart() {
        System.out.println(addressDepart.latLng);
        return addressDepart;
    }

    public static void setAddressDepart(String _id, LatLng _latLng) {
        MainActivity.addressDepart = new Address(_id, _latLng);
    }

    public static Address getAddressArrivee() {
        System.out.println(addressArrivee.latLng);
        return addressArrivee;
    }

    public static void setAddressArrivee(String _id, LatLng _latLng) {
        MainActivity.addressArrivee = new Address(_id, _latLng);
    }


    public void getPath(Double lat_dep, Double lng_dep, Double lat_arr, Double lng_arr, int type_path) throws URISyntaxException, JSONException {


        progressBarMain.setVisibility(View.VISIBLE);


        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("10.245.1.22")
                .setPort(8000)
                .setPath("/api/0.1/path")
                .setParameter("lat_dep", String.valueOf(lat_dep))
                .setParameter("lon_dep", String.valueOf(lng_dep))
                .setParameter("lat_arr", String.valueOf(lat_arr))
                .setParameter("lon_arr", String.valueOf(lng_arr))
                .setParameter("type_path", String.valueOf(type_path))
                .build();

        System.out.println("URI BUILDER NEW: " + uri);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.valueOf(uri), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println("STATUS CODE : " + statusCode);
                try {
                    String str = new String(responseBody, "UTF-8");
                    System.out.println("responseBody" + str);

                    JSONObject jsonObject = new JSONObject(str);
                    displayPath(jsonObject,type_path);


                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("ERROR HTTP : ", String.valueOf(statusCode));
            }

        });

    }


    public void displayPath(JSONObject geoJSON, int type_path) {


        switch (type_path){

            case 0:
                layerFaster = new GeoJsonLayer(_googleMap, geoJSON);
                AddStylePath(layerFaster,type_path);
                layerFaster.addLayerToMap();
                break;


            case 1:
                layerShortest = new GeoJsonLayer(_googleMap, geoJSON);
                AddStylePath(layerShortest,type_path);
                layerShortest.addLayerToMap();
                break;



            case 2:
                layerSafer = new GeoJsonLayer(_googleMap, geoJSON);
                AddStylePath(layerSafer,type_path);
                layerSafer.addLayerToMap();
                break;
        }

        progressBarMain.setVisibility(View.INVISIBLE);
    }

    public void HIDE_KEYBOARD(Activity activity){
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void AddStylePath(GeoJsonLayer layer, Integer type_path){

        for (GeoJsonFeature feature : layer.getFeatures()) {

            GeoJsonLineStringStyle geoJsonLineStringStyle = new GeoJsonLineStringStyle();

            switch (type_path){

                case 0:
                    geoJsonLineStringStyle.setColor(Color.RED);
                    break;
                case 1:
                    geoJsonLineStringStyle.setColor(Color.GREEN);
                    break;
                case 2:
                    geoJsonLineStringStyle.setColor(Color.BLUE);
                    break;

            }

            geoJsonLineStringStyle.setWidth(15);
            feature.setLineStringStyle(geoJsonLineStringStyle);

        }
    }


    public void removerLayer(int type){

        switch (type){
            case 0:
                if (layerFaster.isLayerOnMap()){
                    layerFaster.removeLayerFromMap();
                }
                break;

            case 1:
                if (layerShortest.isLayerOnMap()){
                    layerShortest.removeLayerFromMap();
                }
                break;
            case 2:
                if (layerSafer.isLayerOnMap()){
                    layerSafer.removeLayerFromMap();
                }
                break;
        }

    }

    public void setProgressBarVisible(ProgressBar progressBar, boolean visible){

        if (visible)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.INVISIBLE);

    }
}