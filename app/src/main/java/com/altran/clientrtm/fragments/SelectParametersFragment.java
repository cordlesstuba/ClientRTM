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

public class SelectParametersFragment extends Fragment {

    private ImageView imgViewBackToSelectAddress;
    private ImageView imgViewGoToValidateParameters;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parameters_select, null);


        imgViewBackToSelectAddress = (ImageView) view.findViewById(R.id.imgViewBackToSelectAddress);
        imgViewBackToSelectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        return view;
    }
}