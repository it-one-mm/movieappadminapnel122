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
public class MovieFragment extends Fragment {


    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_movie, container, false);
        FloatingActionButton add=view.findViewById(R.id.add_movies);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoviePopup popup=new MoviePopup();
                popup.show(getFragmentManager(),"Add Moive");
            }
        });
        return view;
    }

}
