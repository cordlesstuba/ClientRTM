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

public class SelectAddressFragment extends Fragment {

    private ImageView imgViewBackToSelectMode;
    private ImageView imgViewGoToSelectParameters;

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


        return view;
    }
}