package com.example.myshop.uinew.order;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myshop.Adpater.FollowerProductAdapter;
import com.example.myshop.Adpater.StoreFollowerAdpater;
import com.example.myshop.Model.Products;
import com.example.myshop.Model.Stores;
import com.example.myshop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hendraanggrian.appcompat.widget.SocialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;


public class OrderNewFragment extends Fragment
{

    private RecyclerView recyclerViewPosts,recyclerViewStores;
    private FollowerProductAdapter postAdapter;
    private List<Products> postList;
    private ImageView imageView;

    private StoreFollowerAdpater storeFollowerAdpater;
    private List<Stores> storesList;

    private List<String> followingList;

    public OrderNewFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ordernew, container, false);

        recyclerViewPosts = view.findViewById(R.id.recycler_view_pro);
        recyclerViewPosts.setHasFixedSize(true);
         recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        postList = new ArrayList<>();
        postAdapter = new FollowerProductAdapter(getContext(), postList);
        recyclerViewPosts.setAdapter(postAdapter);


        recyclerViewStores = view.findViewById(R.id.recycler_view_story);
        recyclerViewStores.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerViewStores.setLayoutManager(linearLayoutManager);

        storesList = new ArrayList<>();
        storeFollowerAdpater = new StoreFollowerAdpater(getContext(), storesList);
        recyclerViewStores.setAdapter(storeFollowerAdpater);

        followingList = new ArrayList<>();
        StoreFollowingList();

        checkFollowingUsers();

        return view;
    }

    private void StoreFollowingList()
    {
        followingList = new ArrayList<>(); //new line added

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Follow").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid()).child("following");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                followingList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    followingList.add(snapshot.getKey());
                }
//                followingList.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                readPosts();
                readStores();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void checkFollowingUsers() {

        followingList = new ArrayList<>(); //new line added

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Follow").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid()).child("following");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                followingList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    followingList.add(snapshot.getKey());
                }
//                followingList.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
                readPosts();
//                readStores();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void readPosts()
    {



        DatabaseReference reference =  FirebaseDatabase.getInstance().getReference().child("Products");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Products post = snapshot.getValue(Products.class);

                    for (String id : followingList) {

                        String a = post.getSid();

                        if (a!=null && a.equals(id))
                        {
                            postList.add(post);
                        }
                    }
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void readStores()
    {
        DatabaseReference reference =  FirebaseDatabase.getInstance().getReference().child("Store");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                storesList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Stores post = snapshot.getValue(Stores.class);

                    for (String id : followingList) {

                        String a = post.getSid();

                        if (a!=null && a.equals(id))
                        {
                            storesList.add(post);
                        }
                    }
                }
                storeFollowerAdpater.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}