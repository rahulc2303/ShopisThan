package com.example.myshop.Adpater;

import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.Model.Stores;
import com.example.myshop.R;
import com.example.myshop.ViewStoreProfileActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StoreFollowerAdpater extends RecyclerView.Adapter<StoreFollowerAdpater.Viewholder>
{
    private Context mContext;
    private List<Stores> mStores;

    public StoreFollowerAdpater(Context mContext, List<Stores> mStores)
    {
        this.mContext = mContext;
        this.mStores = mStores;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.follwerlist,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position)
    {
        Stores stores = mStores.get(position);

        holder.storeName.setText(stores.getStoreName());
        Picasso.get().load(stores.getImage()).into(holder.storeImg);

        holder.storeImg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent= new Intent(mContext, ViewStoreProfileActivity.class);
                intent.putExtra("ViewSellerId", stores.getSid());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mStores.size();
    }

    public  static class Viewholder extends RecyclerView.ViewHolder
    {
        private ImageView storeImg;
        private TextView storeName;
        public Viewholder(@NonNull View itemView)
        {
            super(itemView);

            storeImg = itemView.findViewById(R.id.storeProfileImg);
            storeName = itemView.findViewById(R.id.storeName);
            storeName.setSelected(true);

        }
    }
}
