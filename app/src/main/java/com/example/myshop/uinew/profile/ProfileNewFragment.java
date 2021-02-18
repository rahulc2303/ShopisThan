package com.example.myshop.uinew.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.myshop.AddProductActivity;
import com.example.myshop.CreateProfileActivity;
import com.example.myshop.CreateShopActivity;
import com.example.myshop.LoginPageActivity;
import com.example.myshop.R;
import com.example.myshop.SettingsActivity;
import com.example.myshop.StoreActivity;
import com.example.myshop.StoreProfileActivity;
import com.example.myshop.ViewOrders;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileNewFragment extends Fragment
{


    public ProfileNewFragment() {
        // Required empty public constructor
    }

    private Button createProfile;
    private ImageButton editProfile;
    private CardView createShop,ViewShop,AddProducts;
    private Button create_shop_btn, view_shop_btn;
    private DatabaseReference CheckStore;
    private DatabaseReference CheckProfile;
    private FirebaseAuth firebaseAuth;
    private String sID;
    private Button add_product_btn;
    private RelativeLayout relativeLayout;
    private TextView logout,settingBtn;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profilenew, container, false);


        firebaseAuth = FirebaseAuth.getInstance();
        sID = firebaseAuth.getCurrentUser().getUid();

        view_shop_btn = (Button) view.findViewById(R.id.ViewShop_btn);
        create_shop_btn= (Button) view.findViewById(R.id.CreateShop_btn);
        createShop = (CardView) view.findViewById(R.id.CreateShop);
        ViewShop = (CardView) view.findViewById(R.id.ViewShop);
        createProfile = (Button) view.findViewById(R.id.CreateProfile);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relative);
        AddProducts = (CardView) view.findViewById(R.id.AddProducts);
        add_product_btn = (Button) view.findViewById(R.id.addProducts);
        logout = view.findViewById(R.id.logout);
        settingBtn = view.findViewById(R.id.settingsBtn);


        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getActivity(), SettingsActivity.class));

            }
        });




        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();

                Intent intentMain = new Intent(getContext(), LoginPageActivity.class);
                intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentMain);


            }
        });


        add_product_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), AddProductActivity.class);
                startActivity(intent);

            }
        });




        create_shop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(getContext(), CreateShopActivity.class);
                startActivity(intent);


            }
        });


        view_shop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(getContext(), StoreProfileActivity.class);
                startActivity(intent);


            }
        });


        createProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateProfileActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        CheckStore = FirebaseDatabase.getInstance().getReference().child("Store")
                .child(sID);

        CheckStore.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    createShop.setVisibility(View.INVISIBLE);
                    ViewShop.setVisibility(View.VISIBLE);
                    AddProducts.setVisibility(View.VISIBLE);

                }
                else
                {
                    createShop.setVisibility(View.VISIBLE);
                    ViewShop.setVisibility(View.INVISIBLE);
                    AddProducts.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {


            }
        });


        CheckProfile = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(sID).child("Profile");
        CheckProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    createProfile.setVisibility(View.INVISIBLE);
                    relativeLayout.setVisibility(View.VISIBLE);


                }
                else
                {
                    createProfile.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.INVISIBLE);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }






}