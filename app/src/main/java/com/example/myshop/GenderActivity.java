package com.example.myshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myshop.Adpater.FollowerProductAdapter;
import com.example.myshop.Adpater.ProductCatViewHolder;
import com.example.myshop.Adpater.ProductGenderAdapter;
import com.example.myshop.Model.CatProducts;
import com.example.myshop.Model.Products;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GenderActivity extends AppCompatActivity {

    private TextView genPro;
    private ImageButton Back1;

    private RecyclerView recyclerViewPosts;
    DatabaseReference databaseReference;
    private FollowerProductAdapter postAdapter;
    private List<Products> postList;


    private String ProductGen;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);


//        recyclerView = findViewById(R.id.recycler_Pro_Gen);
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
////        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(layoutManager);

        recyclerViewPosts = findViewById(R.id.recycler_Pro_Gen);
        recyclerViewPosts.setHasFixedSize(true);
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(this));

        postList = new ArrayList<>();
        postAdapter = new FollowerProductAdapter(this, postList);
        recyclerViewPosts.setAdapter(postAdapter);


        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("gender");

        genPro = findViewById(R.id.ProGender);

            genPro.setText(name);
            ProductGen = name;


//        if (name.equals("discountAvailable"))
//        {
//
//            genPro.setText(name);
//            ProductDis = name;
//        }
//        else
//        {
//
//            ProductDis = "NoDiscountAvailable";
//        }
//


        Back1 = findViewById(R.id.backBtn1);

        Back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        Query query = FirebaseDatabase.getInstance().getReference("Products")
                .orderByChild("gender")
                .equalTo(ProductGen);


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


}


//    @Override
//    protected void onStart()
//    {
//        super.onStart();
//
//        DatabaseReference reference =  FirebaseDatabase.getInstance().getReference().child("Products");
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                postList.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Products post = snapshot.getValue(Products.class);
//
//
//                }
//                postAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
////    FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
////            .setQuery(databaseReference.orderByChild("gender").equalTo("Kids"), Products.class)
////            .build();
////
////    FirebaseRecyclerAdapter<Products, ProductCatViewHolder> adapter =
////            new FirebaseRecyclerAdapter<Products, ProductCatViewHolder>(options) {
////                @Override
////                protected void onBindViewHolder(@NonNull ProductCatViewHolder holder, int i, @NonNull Products model)
////                {
////                    holder.txtProductName.setText(model.getProductName());
////                    holder.txtProductDescription.setText(model.getDescription());
////                    holder.txtProductPrice.setText("Price =" + model.getPrice() + "$");
////                    Picasso.get().load(model.getImage()).into(holder.imageView);
////
////
////
////                }
////
////                @NonNull
////                @Override
////                public ProductCatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
////                {
////                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productscat, parent,false);
////                    ProductCatViewHolder holder = new ProductCatViewHolder(view);
////                    return holder;
////                }
////            };
////        recyclerView.setAdapter(adapter);
////                adapter.startListening();
//
//
//
//    }









