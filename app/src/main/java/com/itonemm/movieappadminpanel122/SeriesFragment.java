package com.itonemm.movieappadminpanel122;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


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


        final RecyclerView list=view.findViewById(R.id.series_list);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference reference=db.collection("series");
        reference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<SeriesModel> seriesModels=new ArrayList<>();
                for(DocumentSnapshot s:queryDocumentSnapshots)
                {
                    seriesModels.add(s.toObject(SeriesModel.class));

                }
                SeriesAdapter adapter=new SeriesAdapter(seriesModels);
                LinearLayoutManager lm=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                list.setAdapter(adapter);
                list.setLayoutManager(lm);
            }
        })        ;
        return view;
    }


    public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesHolder>


    {
        ArrayList<SeriesModel>seriesModels=new ArrayList<>();

        public SeriesAdapter(ArrayList<SeriesModel> seriesModels) {
            this.seriesModels = seriesModels;
        }

        @NonNull
        @Override
        public SeriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(parent.getContext());
            View myView=inflater.inflate(R.layout.seriesitem,parent,false);
           SeriesHolder h=new SeriesHolder(myView);
            return h;
        }

        @Override
        public void onBindViewHolder(@NonNull SeriesHolder holder, int position) {

            holder.sr.setText(position+1+"");
            holder.name.setText(seriesModels.get(position).Name);
            Glide.with(getContext())
                    .load(seriesModels.get(position).Image)
                    .into(holder.imageView);


        }

        @Override
        public int getItemCount() {
            return seriesModels.size();
        }

        public class SeriesHolder extends RecyclerView.ViewHolder{

            TextView sr,name;
            ImageView imageView;
            public SeriesHolder(@NonNull View itemView) {
                super(itemView);
                sr=itemView.findViewById(R.id.sr);
                name=itemView.findViewById(R.id.name);
                imageView=itemView.findViewById(R.id.imageView);
            }
        }
    }
}
