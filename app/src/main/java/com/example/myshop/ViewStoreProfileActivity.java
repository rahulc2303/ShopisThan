package com.example.myshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myshop.Adpater.ProductGenderAdapter;
import com.example.myshop.Model.Products;
import com.example.myshop.Model.Stores;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewStoreProfileActivity extends AppCompatActivity
{

    private CircleImageView imageProfile;
    private TextView products,followers,following,fullname,bio,username;

    private ImageView myProducts, ordersProducts;

    private FirebaseAuth fUsers;

    String storeId;
    private Button editProfile;
    private String CurrentStoreId;
    private String ViewStoreId ;
    DatabaseReference firebaseDatabase;
    private DatabaseReference StoreRef;

    private RecyclerView recyclerViewProducts;
    private ProductGenderAdapter postAdapter;
    private List<Products> postList;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_store_profile);


        fUsers = FirebaseAuth.getInstance();
        ViewStoreId = getIntent().getStringExtra("ViewSellerId");


        imageProfile = findViewById(R.id.image_profile121);
        followers = findViewById(R.id.followers121);
        following = findViewById(R.id.following121);
        products = findViewById(R.id.products121);
        fullname = findViewById(R.id.storeName121);
        bio = findViewById(R.id.storeBio121);
        username = findViewById(R.id.username121);
        myProducts = findViewById(R.id.my_pictures121);
//        ordersProducts = findViewById(R.id.saved_pictures121);
        editProfile = findViewById(R.id.edit_profile121);


        userInfo();
        getFollwersAndFollowinfCount();
        getPostCount();
        checkFollowingStatus();

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
                                .child("following").child(ViewStoreId)
                                .setValue(true);

                        FirebaseDatabase.getInstance().getReference().child("Follow")
                                .child(ViewStoreId).child("followers").child(fUsers.getUid()).setValue(true);


                    }

                    else {


                        FirebaseDatabase.getInstance().getReference().child("Follow").child(fUsers.getUid())
                                .child("following").child(ViewStoreId)
                                .removeValue();
                        FirebaseDatabase.getInstance().getReference().child("Follow")
                                .child(ViewStoreId).child("followers").child(fUsers.getUid()).removeValue();
                    }
                }

            }
        });


        recyclerViewProducts= findViewById(R.id.recucler_view_produtcs121);
        recyclerViewProducts.setHasFixedSize(true);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));

        postList = new ArrayList<>();
        postAdapter = new ProductGenderAdapter(this, postList);
        recyclerViewProducts.setAdapter(postAdapter);


        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        Query query = FirebaseDatabase.getInstance().getReference("Products")
                .orderByChild("sid")
                .equalTo(ViewStoreId);


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
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    private void checkFollowingStatus()
    {
        FirebaseDatabase.getInstance().getReference().child("Follow").child(fUsers.getUid()).child("following")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.child(ViewStoreId).exists())
                        {
                            editProfile.setText("following");
                        }
                        else
                        {
                            editProfile.setText("follow");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    private void getPostCount()
    {
        FirebaseDatabase.getInstance().getReference().child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                int i = 0;
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
//                    Post post = snapshot.getValue(Post.class);

                    Stores vendors = snapshot.getValue(Stores.class);

                    String a = vendors.getSid();

//                    String a = ViewStoreId;

                    if(a!=null && a.equals(ViewStoreId)) i ++;
                }

                products.setText(String.valueOf(i));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getFollwersAndFollowinfCount()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Follow").child(ViewStoreId);

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

        StoreRef = FirebaseDatabase.getInstance().getReference().child("Store").child(ViewStoreId);
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