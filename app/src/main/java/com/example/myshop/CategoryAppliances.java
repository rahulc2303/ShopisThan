package com.example.myshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshop.Adpater.FollowerProductAdapter;
import com.example.myshop.Adpater.ProdctsCat;
import com.example.myshop.Adpater.ProductCatViewHolder;
import com.example.myshop.Adpater.ProductGenderAdapter;
import com.example.myshop.Model.CatProducts;
import com.example.myshop.Model.Products;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CategoryAppliances extends AppCompatActivity
{


    private TextView productCat;
    private ImageButton Back;


//    private RecyclerView recyclerView;
//    private ProductGenderAdapter postAdapter;
//    private List<Products> postList;
    DatabaseReference databaseReference;

    private RecyclerView recyclerView;
    private FollowerProductAdapter postAdapter;
    private List<Products> postList;


    private String ProductCat;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_appliances);



        recyclerView = findViewById(R.id.recycler_Pro_Cat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        postList = new ArrayList<>();
        postAdapter = new FollowerProductAdapter(this, postList);
        recyclerView.setAdapter(postAdapter);

        Back = findViewById(R.id.backBtn);


        productCat = findViewById(R.id.productCat);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        productCat.setText(name);
        ProductCat = name;


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        Query query = FirebaseDatabase.getInstance().getReference("Products")
                .orderByChild("category")
                .equalTo(ProductCat);


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