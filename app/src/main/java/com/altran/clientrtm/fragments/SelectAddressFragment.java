package com.altran.clientrtm.fragments;

/**
 * Created by gcarv on 02/04/2017.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.altran.clientrtm.R;
import com.altran.clientrtm.activities.MainActivity;
import com.altran.clientrtm.googlePlace.PlacesDetails;
import com.google.android.gms.maps.model.LatLng;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import com.seatgeek.placesautocomplete.model.Place;

import java.net.URISyntaxException;

public class SelectAddressFragment extends Fragment {

    private ImageView imgViewBackToSelectMode;
    private ImageView imgViewGoToSelectParameters;

    PlacesAutocompleteTextView autocompleteTxtViewDepart;
    PlacesAutocompleteTextView autocompleteTxtViewArrivee;

    int placeNumber = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_select, null);

        imgViewBackToSelectMode = (ImageView) view.findViewById(R.id.imgViewBackToSelectMode);
        imgViewGoToSelectParameters = (ImageView) view.findViewById(R.id.imgViewGoToSelectParameters);

        imgViewBackToSelectMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        imgViewGoToSelectParameters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectParametersFragment selectParametersFragment = new SelectParametersFragment();
                ((MainActivity)getActivity()).replaceFragmentWithAnimation(selectParametersFragment,"");
            }
        });

        autocompleteTxtViewDepart = (PlacesAutocompleteTextView) view.findViewById(R.id.autocompleteTxtViewDepart);
        autocompleteTxtViewDepart.setOnPlaceSelectedListener(
                new OnPlaceSelectedListener() {
                    @Override
                    public void onPlaceSelected(final Place place) {

                        System.out.println(place);
                        System.out.println(place.description + " " + place.place_id + " " + place.terms + " " + place.types + " " + place.matched_substrings + " " + place.toString());
                        placeNumber = 0;
                        PlacesDetails placesDetails = new PlacesDetails(place.place_id);
                        try {
                            placesDetails.loadDetails(placeNumber);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }


                    }
                }
        );

        autocompleteTxtViewArrivee = (PlacesAutocompleteTextView) view.findViewById(R.id.autocompleteTxtViewArrivee);
        autocompleteTxtViewArrivee.setOnPlaceSelectedListener(
                new OnPlaceSelectedListener() {
                    @Override
                    public void onPlaceSelected(final Place place) {

                        System.out.println(place);
                        System.out.println(place.description + " " + place.place_id + " " + place.terms + " " + place.types + " " + place.matched_substrings + " " + place.toString());
                        placeNumber = 1;
                        PlacesDetails placesDetails = new PlacesDetails(place.place_id);
                        try {
                            placesDetails.loadDetails(placeNumber);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }


                    }
                }
        );


        return view;
    }
}