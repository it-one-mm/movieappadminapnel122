package com.itonemm.movieappadminpanel122;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import es.dmoral.toasty.Toasty;

public class SeriesPopUp extends DialogFragment {
    ArrayList<String>categorynames=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.seriespopup,container,false);
        final Spinner spinner=view.findViewById(R.id.series_category);
        final FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference category=db.collection("categories");
        category.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
              categorynames.clear();
                for(DocumentSnapshot s:queryDocumentSnapshots)
                {
                   categorynames.add( s.toObject(CategoryModel.class).categoryName);
                }
                ArrayAdapter<String>adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,categorynames);
                spinner.setAdapter(adapter);
            }
        });
       final EditText edtseriesName=view.findViewById(R.id.series_name);
        final EditText edtseriesImage=view.findViewById(R.id.series_image_link);
        final EditText edtseriesViedo=view.findViewById(R.id.series_video);

        Button btnsave=view.findViewById(R.id.save_series);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtseriesName.getText().toString().equals("") && edtseriesImage.getText().toString().equals("")&& edtseriesViedo.getText().toString().equals(""))
                {
                    Toasty.error(getContext(),"Please Fill Data",Toasty.LENGTH_LONG).show();
                }
                else
                {
                    SeriesModel seriesModel=new SeriesModel();
                    seriesModel.Name=edtseriesName.getText().toString().trim();
                    seriesModel.Image=edtseriesImage.getText().toString().trim();
                    seriesModel.Video=edtseriesViedo.getText().toString().trim();
                    seriesModel.Category=categorynames.get(spinner.getSelectedItemPosition());
                    CollectionReference seriesRef=db.collection("series");
                    seriesRef.add(seriesModel);
                    Toasty.success(getContext(),"Save Successfully",Toasty.LENGTH_LONG).show();;
                    edtseriesImage.setText("");
                    edtseriesName.setText("");
                    edtseriesViedo.setText("");
                }
            }
        });

        Button btncancel=view.findViewById(R.id.cancel_series);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtseriesImage.setText("");
                edtseriesName.setText("");
                edtseriesViedo.setText("");
                dismiss();
            }
        });



        return view;
    }
}
