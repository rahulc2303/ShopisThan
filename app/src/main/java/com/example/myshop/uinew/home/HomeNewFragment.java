package com.example.myshop.uinew.home;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.myshop.Adpater.AdapterCartItem;
import com.example.myshop.Adpater.MainAdapter;
import com.example.myshop.CartActivity;
import com.example.myshop.GenderActivity;
import com.example.myshop.Model.MainModel;
import com.example.myshop.Model.ModelCartItem;
import com.example.myshop.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;
import java.util.List;


public class HomeNewFragment extends Fragment {


    public HomeNewFragment() {

    }
    private RecyclerView recyclerView;
    RecyclerView newrecyclerView;

    ArrayList<MainModel> mainModels;
    MainAdapter mainAdapter;
    ImageView cartBtn;

    RecyclerView phoneRecycler,categories;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Button manProBtn,womenProBtn,kidsProBtn,trendProBtn,disProBtn,offerProBtn,ltohProBtn,newProBtn;

    private ArrayList<ModelCartItem> cartItemList;
    private AdapterCartItem adapterCartItem;
    private  TextView cartNo;
    private String currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homenew, container, false);

        ImageSlider imageSlider = view.findViewById(R.id.slider);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();



        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.homebanner));
        slideModels.add(new SlideModel(R.drawable.homebanner));
        slideModels.add(new SlideModel(R.drawable.homebanner));
        slideModels.add(new SlideModel(R.drawable.homebanner));
        slideModels.add(new SlideModel(R.drawable.homebanner));
        imageSlider.setImageList(slideModels,true);

        manProBtn = view.findViewById(R.id.manProducts_btn);
        womenProBtn = view.findViewById(R.id.womenProBtn);
        kidsProBtn = view.findViewById(R.id.kidsProBtn);
        trendProBtn = view.findViewById(R.id.trendingProBtn);
        disProBtn = view.findViewById(R.id.discountProBtn);
        offerProBtn = view.findViewById(R.id.offersProbtn);
        ltohProBtn = view.findViewById(R.id.ltohProBtn);
        newProBtn = view.findViewById(R.id.newProBtn);
        cartNo = view.findViewById(R.id.cart_number);

         DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                 .child("Cart List").child("Users View");

         reference.child(currentUser).child("Products").addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot)
             {
                 long noofcart;

                 noofcart = dataSnapshot.getChildrenCount();

                  if (noofcart>0)
                  {
                      cartNo.setVisibility(View.VISIBLE);
                     cartNo.setText(Integer.toString((int) dataSnapshot.getChildrenCount()));

                  }
                  else
                  {
                      cartNo.setVisibility(View.INVISIBLE);
                  }

//                 cartNo.setText(Integer.toString((int) dataSnapshot.getChildrenCount()));



             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });

        cartBtn = view.findViewById(R.id.cartBtn);
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               Intent intent = new Intent(getActivity(), CartActivity.class);
               startActivity(intent);
            }
        });


        manProBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent manIntent = new Intent(getActivity(), GenderActivity.class);
                manIntent.putExtra("gender","Male");
                manIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(manIntent);




            }
        });
        womenProBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent womenIntent = new Intent(getActivity(), GenderActivity.class);
                womenIntent.putExtra("gender","Female");
//                womenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(womenIntent);

            }
        });
        kidsProBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent kidsIntent = new Intent(getActivity(), GenderActivity.class);
                kidsIntent.putExtra("gender","Kids");
                startActivity(kidsIntent);

            }
        });
//
//        disProBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                Intent disProIntent = new Intent(getActivity(), GenderActivity.class);
//                disProIntent.putExtra("gender","discountAvailable");
//                startActivity(disProIntent);
//
//            }
//        });



        newrecyclerView = view.findViewById(R.id.recycler_view);

        Integer[] langLogo = {R.drawable.ic_baseline_home_24,R.drawable.ic_baseline_favorite_border_24,R.drawable.abcdef,R.drawable.abcd,
                R.drawable.abcdef,R.drawable.abcdef,R.drawable.abcd,R.drawable.abcdef};


        String[] langName = {"Appliances","Books","Fashion","Furniture","Handcraft","Shoes","Sports","Toys"};

        mainModels  = new ArrayList<>();
        for (int i =0;i<langLogo.length;i++){
            MainModel model = new MainModel(langLogo[i],langName[i]);
            mainModels.add(model);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getContext(),LinearLayoutManager.HORIZONTAL,false
        );
        newrecyclerView.setLayoutManager(layoutManager);
        newrecyclerView.setItemAnimator(new DefaultItemAnimator());

        mainAdapter = new MainAdapter(getActivity(),mainModels);
        newrecyclerView.setAdapter(mainAdapter);



        return view;
    }


}
