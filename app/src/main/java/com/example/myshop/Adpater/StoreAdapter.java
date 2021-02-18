package com.example.myshop.Adpater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.Model.Stores;
import com.example.myshop.R;
import com.example.myshop.StoreProfileActivity;
import com.example.myshop.ViewOrders;
import com.example.myshop.ViewStoreProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.Viewholder>
{

    private Context mContext;
    private List<Stores> mStores;

    private FirebaseUser firebaseUser;


    public StoreAdapter(Context mContext, List<Stores> mStores, boolean b)
    {
        this.mContext = mContext;
        this.mStores = mStores;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.storelist,viewGroup,false);
        return new StoreAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position)
    {
        firebaseUser  = FirebaseAuth.getInstance().getCurrentUser();

        final  Stores stores = mStores.get(position);

        holder.Follow_btn.setVisibility(View.VISIBLE);
        holder.ShopName.setText(stores.getStoreName());
        holder.ShopAddress.setText(stores.getStoreAddress());

        Picasso.get().load(stores.getImage()).placeholder(R.mipmap.ic_launcher).into(holder.ShopImage);
        isFollowing(stores.getSid(), holder.Follow_btn);

        if (stores.getSid().equals(firebaseUser.getUid() ))
        {
            holder.Follow_btn.setVisibility(View.GONE);

        }


        holder.Follow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.Follow_btn.getText().toString().equals("follow"))
                {
                    FirebaseDatabase.getInstance().getReference().child("Follow")
                            .child(firebaseUser.getUid()).child("following").child(stores.getSid())
                            .setValue(true);

                    FirebaseDatabase.getInstance().getReference().child("Follow")
                            .child(stores.getSid()).child("followers").child(firebaseUser.getUid())
                            .setValue(true);
                }
                else {

                    FirebaseDatabase.getInstance().getReference().child("Follow")
                            .child(firebaseUser.getUid()).child("following").child(stores.getSid())
                            .removeValue();

                    FirebaseDatabase.getInstance().getReference().child("Follow")
                            .child(stores.getSid()).child("followers").child(firebaseUser.getUid())
                            .removeValue();
                }
            }
        });

        holder.GoToShopProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String currentUser = firebaseUser.getUid();
                if (currentUser.equals(stores.getSid()))

                {
                    Intent intent= new Intent(mContext, StoreProfileActivity.class);
                    mContext.startActivity(intent);

                }
                else
                {
                    Intent intent= new Intent(mContext, ViewStoreProfileActivity.class);
                    intent.putExtra("ViewSellerId", stores.getSid());
                    mContext.startActivity(intent);
                }


            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mStores.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder // 1st step
    {
        public ImageView ShopImage;  //2nd step
        public TextView ShopName,ShopAddress,GoToShopProfile;
        public Button Follow_btn;





        public Viewholder(@NonNull View itemView) // 3rd steo
        {
            super(itemView);

            ShopImage = itemView.findViewById(R.id.store_image);
            ShopName = itemView.findViewById(R.id.ShopName);
            ShopAddress = itemView.findViewById(R.id.ShopAddress);
            GoToShopProfile = itemView.findViewById(R.id.gotoshop);
            Follow_btn = itemView.findViewById(R.id.follow_btn);


        }
    }



    private void isFollowing(final String storeId, Button button)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseUser.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child(storeId).exists())
                {
                    button.setText("following");

                }
                else {
                    button.setText("follow");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
