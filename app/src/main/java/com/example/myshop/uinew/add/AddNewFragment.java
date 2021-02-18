package com.example.myshop.uinew.add;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.Adpater.StoreAdapter;
import com.example.myshop.Model.Stores;
import com.example.myshop.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hendraanggrian.appcompat.widget.SocialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;


public class AddNewFragment extends Fragment
{


    private RecyclerView recyclerView;
    private StoreAdapter storeAdapter;
    private List<Stores> mStores;

    private SocialAutoCompleteTextView search_bar;


    public AddNewFragment()
 {

 }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_addnew, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_store);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        search_bar = view.findViewById(R.id.search_bar);

        mStores = new ArrayList<>();
        storeAdapter = new StoreAdapter(getContext(), mStores,true);
        recyclerView.setAdapter(storeAdapter);


        readUsers();

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                searchUsers(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return view;
    }

    private void searchUsers(String s)
    {
        Query query = FirebaseDatabase.getInstance().getReference("Store").orderByChild("storeName")
                .startAt(s)
                .endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                mStores.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Stores user = snapshot.getValue(Stores.class);
                    mStores.add(user);
                }
                storeAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void readUsers()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Store");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (TextUtils.isEmpty(search_bar.getText().toString()))
                {
                    mStores.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        Stores user = snapshot.getValue(Stores.class);
                        mStores.add(user);
                    }
                    storeAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}