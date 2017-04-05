package com.altran.clientrtm.fragments;

/**
 * Created by gcarv on 02/04/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.altran.clientrtm.R;
import com.altran.clientrtm.activities.Address;
import com.altran.clientrtm.activities.MainActivity;

import org.json.JSONException;

import java.net.URISyntaxException;

public class SelectParametersFragment extends Fragment {

    private ImageView imgViewBackToSelectAddress;

    private static Address addressDepart;
    private static Address addressArrivee;

    private CheckBox checkBoxPlusRapide = null;
    private CheckBox checkBoxPlusCourt = null;
    private CheckBox checkBoxPlusSafe = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parameters_select, null);


        addressDepart = MainActivity.getAddressDepart();
        addressArrivee = MainActivity.getAddressArrivee();


        checkBoxPlusRapide = (CheckBox) view.findViewById(R.id.checkBoxPlusRapide);
        checkBoxPlusCourt = (CheckBox) view.findViewById(R.id.checkBoxPlusCourt);
        checkBoxPlusSafe = (CheckBox) view.findViewById(R.id.checkBoxPlusSafe);

        checkBoxPlusRapide.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked){
                runAlgo(0);
            }else {

                ((MainActivity)getActivity()).removerLayer(0);
            }

        });

        checkBoxPlusCourt.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked){
                runAlgo(1);
            }else {

                ((MainActivity)getActivity()).removerLayer(1);

            }

        });

        checkBoxPlusSafe.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked){
                runAlgo(2);
            }else {

                ((MainActivity)getActivity()).removerLayer(2);

            }

        });




        imgViewBackToSelectAddress = (ImageView) view.findViewById(R.id.imgViewBackToSelectAddress);
        imgViewBackToSelectAddress.setOnClickListener(v -> {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }



    public void runAlgo(int type_path){

        try {
            ((MainActivity)getActivity()).getPath(
                    addressDepart.getLatLng().latitude,
                    addressDepart.getLatLng().longitude,
                    addressArrivee.getLatLng().latitude,
                    addressArrivee.getLatLng().longitude,
                    type_path);
        } catch (URISyntaxException | JSONException e) {
            e.printStackTrace();
        }

    }
}