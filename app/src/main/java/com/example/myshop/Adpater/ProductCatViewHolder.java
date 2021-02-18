package com.example.myshop.Adpater;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.ItemClickListner.ItemClickListner;
import com.example.myshop.R;

public class ProductCatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductDescription, txtProductPrice;
    public ImageView imageView;
    public ItemClickListner listner;

    public ProductCatViewHolder(@NonNull View itemView)
    {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.product_image121);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name121);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description121);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price121);
    }


    public  void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View v) {

    }
}
