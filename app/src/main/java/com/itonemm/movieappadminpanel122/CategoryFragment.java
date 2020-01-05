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
public class CategoryFragment extends Fragment {


    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_category, container, false);

        FloatingActionButton addcategory=view.findViewById(R.id.add_category);
        addcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryPopup popup=new CategoryPopup();
                popup.show(getFragmentManager(),"Add Category");
            }
        });
        return view;
    }

}
