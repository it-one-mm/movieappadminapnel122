package com.itonemm.movieappadminpanel122;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import es.dmoral.toasty.Toasty;

public class CategoryPopup extends DialogFragment {

    CategoryModel cModel;
    String id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.categorypopup,container,false);
        Button btnsave=view.findViewById(R.id.btn_save_category);
        final EditText edt_name=view.findViewById(R.id.edt_category_name);


        if(cModel!=null)
        {
            edt_name.setText(cModel.categoryName);
        }


        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!edt_name.getText().toString().equals(""))
                {
                    FirebaseFirestore db=FirebaseFirestore.getInstance();
                    final CollectionReference Ref=db.collection("categories");


                    final CategoryModel categoryModel=new CategoryModel();
                    categoryModel.categoryName=edt_name.getText().toString();

                    if(cModel!=null){
                        Ref.document(id).set(categoryModel);
                        Toasty.success(getContext(),"Category Update Successfully",Toasty.LENGTH_LONG).show();
                        cModel=null;
                        id="";
                    }
                    else {

                        Ref.whereEqualTo("categoryName",categoryModel.categoryName).addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                if(queryDocumentSnapshots.size()<=0)
                                {
                                    Ref.add(categoryModel);
                                    Toasty.success(getContext(),"Category Update Successfully",Toasty.LENGTH_LONG).show();

                                }
                                else
                                {
                                    Toasty.error(getContext(),"Category Name Already Exits",Toasty.LENGTH_LONG).show();
                                }
                            }
                        });


                    }
                    edt_name.setText("");
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
