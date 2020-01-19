package com.itonemm.movieappadminpanel122;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {


    public CategoryFragment() {
        // Required empty public constructor
    }

    FirebaseFirestore db;
    CollectionReference reference;
    ArrayList<String> documentIds;
    ListView list;
    ProgressBar progressBar;
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
        list=view.findViewById(R.id.category_list);
        documentIds=new ArrayList<String>();
        progressBar=view.findViewById(R.id.progressbar);
        db=FirebaseFirestore.getInstance();
        reference=db.collection("categories");
       loadData();

        final EditText edtsearch=view.findViewById(R.id.search_category);
        edtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!edtsearch.getText().toString().equals("")){
                searchData(s.toString());}
                else
                {
                    loadData();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    public void searchData(String s)
    {
        reference.whereEqualTo("categoryName",s).addSnapshotListener(
                new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        ArrayList<CategoryModel> categoryModelArrayList=new ArrayList<CategoryModel>();

                        documentIds.clear();;

                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){

                            documentIds.add(documentSnapshot.getId());
                            CategoryModel c=documentSnapshot.toObject(CategoryModel.class);
                            categoryModelArrayList.add(c);
                        }
                        CategoryAdapter adapter=new CategoryAdapter(categoryModelArrayList);
                        list.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                    }
                }
        );
    }

    public void loadData()
    {
        reference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                ArrayList<CategoryModel> categoryModelArrayList=new ArrayList<CategoryModel>();

                documentIds.clear();;

                for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){

                    documentIds.add(documentSnapshot.getId());
                    CategoryModel c=documentSnapshot.toObject(CategoryModel.class);
                    categoryModelArrayList.add(c);
                }
                CategoryAdapter adapter=new CategoryAdapter(categoryModelArrayList);
                list.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);


            }
        });
    }

    private class CategoryAdapter extends BaseAdapter{

        ArrayList<CategoryModel> categoryModels=new ArrayList<CategoryModel>();

        public CategoryAdapter(ArrayList<CategoryModel> categoryModels) {
            this.categoryModels = categoryModels;
        }

        @Override
        public int getCount() {
            return categoryModels.size();
        }

        @Override
        public Object getItem(int position) {
            return categoryModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater myinflater=getLayoutInflater();
            View v=myinflater.inflate(R.layout.categorylist,null);
            TextView txt_sr=v.findViewById(R.id.category_sr);
            final TextView txt_name=v.findViewById(R.id.category_name);
            txt_sr.setText((position+1)+"");
            final CategoryModel temp=categoryModels.get(position);
            txt_name.setText(temp.categoryName);

            LinearLayout categoryitem=v.findViewById(R.id.categoryitem);
            categoryitem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    PopupMenu popup=new PopupMenu(getContext(),txt_name);

                    MenuInflater inflater=popup.getMenuInflater();
                    inflater.inflate(R.menu.popmenu,popup.getMenu());
                    popup.show();

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if(item.getItemId()==R.id.deletemenu)
                            {
                                reference.document(documentIds.get(position)).delete();

                                loadData();
                            }
                            if(item.getItemId()==R.id.editmenu)
                            {
                                CategoryPopup cPopUp=new CategoryPopup();
                                cPopUp.cModel=temp;
                                cPopUp.id=documentIds.get(position);
                                cPopUp.show(getFragmentManager(),"Edit Category");
                            }
                            return true;
                        }
                    });

                    return true;
                }
            });
            return v;
        }
    }
}
