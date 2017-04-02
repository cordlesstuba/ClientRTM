package com.altran.clientrtm.fragments;

/**
 * Created by gcarv on 02/04/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.altran.clientrtm.R;
import com.altran.clientrtm.activities.MainActivity;

public class SelectModeFragment extends Fragment {

    private TextView txtViewModeRoute;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mode_select, null);

        txtViewModeRoute = (TextView) view.findViewById(R.id.txtViewModeRoute);
        txtViewModeRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectAddressFragment selectAddressFragment = new SelectAddressFragment();
                ((MainActivity)getActivity()).replaceFragmentWithAnimation(selectAddressFragment,"");

            }
        });

        return view;
    }
}