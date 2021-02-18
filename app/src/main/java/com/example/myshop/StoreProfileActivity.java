package com.example.myshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myshop.Adpater.ProductGenderAdapter;
import com.example.myshop.Model.Products;
import com.example.myshop.Model.Stores;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoreProfileActivity extends AppCompatActivity
{
    private CircleImageView imageProfile;
    private TextView products,followers,following,fullname,bio,username;

    private ImageView myProducts, ordersProducts;

    private FirebaseAuth fUsers;
    String storeId;
    private Button editProfile;
    private String CurrentStoreId;
    private String ViewStoreId = "";
    DatabaseReference firebaseDatabase;
    private DatabaseReference StoreRef;


    private RecyclerView recyclerViewProducts;
    private AdminProductView postAdapter;
    private List<Products> postList;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_profile);


        fUsers = FirebaseAuth.getInstance();
        storeId = fUsers.getCurrentUser().getUid();

        imageProfile = findViewById(R.id.image_profile);
        followers = findViewById(R.id.followers);
        following = findViewById(R.id.following);
        products = findViewById(R.id.products);
        fullname = findViewById(R.id.storeName);
        bio = findViewById(R.id.storeBio);
        username = findViewById(R.id.username);
        myProducts = findViewById(R.id.my_pictures);
        ordersProducts = findViewById(R.id.saved_pictures);
        editProfile = findViewById(R.id.edit_profile);


        userInfo();
        getFollwersAndFollowinfCount();
        getPostCount();


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String btnText = editProfile.getText().toString();
                if (btnText.equals("Edit profile"))
                {

                }
                else
                {
                    if (btnText.equals("follow"))
                    {
                        FirebaseDatabase.getInstance().getReference().child("Follow").child(fUsers.getUid())
                                .child("following").child(storeId)
                                .setValue(true);
                        FirebaseDatabase.getInstance().getReference().child("Follow")
                                .child(storeId).child("following").child(fUsers.getUid()).setValue(true);
                    }

                    else {

                        FirebaseDatabase.getInstance().getReference().child("Follow").child(fUsers.getUid())
                                .child("following").child(storeId)
                                .removeValue();
                        FirebaseDatabase.getInstance().getReference().child("Follow")
                                .child(storeId).child("following").child(fUsers.getUid()).removeValue();

                    }
                }

            }
        });

        recyclerViewProducts= findViewById(R.id.recucler_view_Own_Produtcs);
        recyclerViewProducts.setHasFixedSize(true);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));

        postList = new ArrayList<>();
        postAdapter = new AdminProductView(this, postList);
        recyclerViewProducts.setAdapter(postAdapter);


        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        Query query = FirebaseDatabase.getInstance().getReference("Products")
                .orderByChild("sid")
                .equalTo(storeId);
        query.addListenerForSingleValueEvent(valueEventListener);


    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {
            postList.clear();
            if (dataSnapshot.exists())
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Products post = snapshot.getValue(Products.class);
                    postList.add(post);

                }
                postAdapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error)
        {

        }
    };


    private void getPostCount()
    {
        FirebaseDatabase.getInstance().getReference().child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                int counter = 0;
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
//                    Post post = snapshot.getValue(Post.class);

                    Stores vendors = snapshot.getValue(Stores.class);

                    String a = vendors.getSid();

                    if(a!=null && a.equals(storeId)) counter ++;
                }

                products.setText(String.valueOf(counter));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getFollwersAndFollowinfCount()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Follow").child(storeId);

        ref.child("followers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                followers.setText("" + dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                following.setText("" + dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void userInfo()
    {

        StoreRef = FirebaseDatabase.getInstance().getReference().child("Store").child(storeId);
//        FirebaseDatabase.getInstance().getReference().child("Store").child(storeId)
//
            StoreRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Stores stores = dataSnapshot.getValue(Stores.class);

                username.setText(stores.getStoreName());
                fullname.setText(stores.getCategory());
                bio.setText(stores.getStoreAddress());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}