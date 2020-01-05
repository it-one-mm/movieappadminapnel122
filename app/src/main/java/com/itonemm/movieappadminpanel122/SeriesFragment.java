package com.itonemm.movieappadminpanel122;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class SeriesFragment extends Fragment {


    public SeriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_series, container, false);
        FloatingActionButton add=view.findViewById(R.id.add_series);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeriesPopUp popUp=new SeriesPopUp();
                popUp.show(getFragmentManager(),"Series PopUp");
            }
        });
        return view;
    }

}
