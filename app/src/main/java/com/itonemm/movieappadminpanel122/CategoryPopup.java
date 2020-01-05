package com.itonemm.movieappadminpanel122;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import es.dmoral.toasty.Toasty;

public class CategoryPopup extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.categorypopup,container,false);
        Button btnsave=view.findViewById(R.id.btn_save_category);
        final EditText edt_name=view.findViewById(R.id.edt_category_name);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!edt_name.getText().toString().equals(""))
                {
                    FirebaseFirestore db=FirebaseFirestore.getInstance();
                    CollectionReference Ref=db.collection("categories");
                    CategoryModel categoryModel=new CategoryModel();
                    categoryModel.categoryName=edt_name.getText().toString();
                    Ref.add(categoryModel);
                    edt_name.setText("");
                    Toasty.success(getContext(),"Category Save Successfully",Toasty.LENGTH_LONG).show();
                }
                else
                {
                    Toasty.error(getContext(),"Please Fill Data",Toasty.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
}
