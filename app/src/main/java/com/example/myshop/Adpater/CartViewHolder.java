package com.example.myshop.Adpater;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.ItemClickListner.ItemClickListner;
import com.example.myshop.R;


public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView txtProductName , txtProductPrice, txtProductQuantity,txtOneProductPrice;
    private ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView)
    {
        super(itemView);
        txtProductName = itemView.findViewById(R.id.itemTitleTv);
        txtProductPrice = itemView.findViewById(R.id.itemPriceTv);
        txtProductQuantity = itemView.findViewById(R.id.itemQuantityTv);
        txtOneProductPrice = itemView.findViewById(R.id.itemPriceEachTv);



    }

    @Override
    public void onClick(View view)
    {
        itemClickListner.onClick(view, getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner)
    {
        this.itemClickListner = itemClickListner;
    }
}
