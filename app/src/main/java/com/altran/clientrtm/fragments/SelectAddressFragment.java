package com.altran.clientrtm.fragments;

/**
 * Created by gcarv on 02/04/2017.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.altran.clientrtm.R;
import com.altran.clientrtm.activities.Address;
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

        imgViewBackToSelectMode.setOnClickListener(v -> {

            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            }
        });

        imgViewGoToSelectParameters.setOnClickListener(v -> {

            if (autocompleteTxtViewDepart.getText().toString().equals("") || autocompleteTxtViewArrivee.getText().toString().equals(""))
                ((MainActivity)getActivity()).AlertUser("Veuillez selectionner une adresse de départ et d'arrivée");
            else{

                SelectParametersFragment selectParametersFragment = new SelectParametersFragment();
                ((MainActivity)getActivity()).replaceFragmentWithAnimation(selectParametersFragment,"");
            }

        });

        autocompleteTxtViewDepart = (PlacesAutocompleteTextView) view.findViewById(R.id.autocompleteTxtViewDepart);
        autocompleteTxtViewDepart.setOnPlaceSelectedListener(
                place -> {

                    placeNumber = 0;



                    PlacesDetails placesDetails = new PlacesDetails(place.place_id);
                    try {
                        placesDetails.loadDetails(placeNumber);

                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }




                }
        );

        autocompleteTxtViewArrivee = (PlacesAutocompleteTextView) view.findViewById(R.id.autocompleteTxtViewArrivee);
        autocompleteTxtViewArrivee.setOnPlaceSelectedListener(
                place -> {

                    placeNumber = 1;


                    ((MainActivity)getActivity()).HIDE_KEYBOARD(getActivity());

                    PlacesDetails placesDetails = new PlacesDetails(place.place_id);
                    try {
                        placesDetails.loadDetails(placeNumber);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }


                }
        );


        return view;
    }

}