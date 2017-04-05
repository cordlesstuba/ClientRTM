package com.altran.clientrtm.googlePlace;

import android.util.Log;

import com.altran.clientrtm.activities.MainActivity;
import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.utils.URIBuilder;

/**
 * Created by gcarves on 03/04/2017.
 */

public class PlacesDetails {


    private static String PLACE_ID = null;
    static LatLng latLng = null;

    public PlacesDetails(String PLACE_ID) {
        PlacesDetails.PLACE_ID = PLACE_ID;
    }

    public LatLng loadDetails(final int placeNumber) throws URISyntaxException {

            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("maps.googleapis.com")
                    .setPath("/maps/api/place/details/json")
                    .setParameter("placeid",PLACE_ID)
                    .setParameter("key","AIzaSyAzFKnN_R1Sn2GLegJFcCO9QWIgBFFeUJ8")
                    .build();

            System.out.println("URI BUILDER NEW: " + uri);

            AsyncHttpClient client = new AsyncHttpClient();
            client.get(String.valueOf(uri), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    System.out.println("STATUS CODE : " + statusCode);
                    try {
                        String str = new String(responseBody, "UTF-8");


                        JSONObject reponse = new JSONObject(str);
                        Double lat = reponse.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                        Double lng = reponse.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getDouble("lng");

                        latLng = new LatLng(lat,lng);


                        switch (placeNumber){
                            case 0:
                                MainActivity.setAddressDepart(PLACE_ID,latLng);
                                break;
                            case 1:
                                MainActivity.setAddressArrivee(PLACE_ID,latLng);
                                break;
                        }



                        MainActivity.AddMarker(latLng,placeNumber);


                    } catch (UnsupportedEncodingException | JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e("ERROR HTTP : ", String.valueOf(statusCode));
                }




            });

        return latLng;

    }


}
