package com.example.myshop.Adpater;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.Model.CatProducts;
import com.example.myshop.Model.Products;
import com.example.myshop.Model.Stores;
import com.example.myshop.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProdctsCat extends FirebaseRecyclerAdapter<CatProducts,ProdctsCat.myviewholder>
{

    public ProdctsCat(@NonNull FirebaseRecyclerOptions<CatProducts> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int i, @NonNull CatProducts products)
    {
//        Products products = productsList.get(position);


        boolean discountAvailable = products.isDiscountAvailable();

        if (discountAvailable== true)
        {
            holder.Pdis.setVisibility(View.VISIBLE);
            holder.Pprice.setPaintFlags(holder.Pprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            holder.Pdis.setVisibility(View.GONE);
        }


        holder.Pdesc.setText(products.getDescription());
                holder.Pprice.setText(products.getPrice());
                holder.Pname.setText(products.getProductName());
                Picasso.get().load(products.getImage()).placeholder(R.mipmap.ic_launcher).into(holder.productImg);


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productscat,parent,false);
       return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView productImg;
        TextView Pname,Pprice,Pdesc,Pdis;


        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            productImg = itemView.findViewById(R.id.product_image121);
            Pname = itemView.findViewById(R.id.product_name121);
            Pprice = itemView.findViewById(R.id.product_price121);
            Pdesc = itemView.findViewById(R.id.product_description121);
            Pdis = itemView.findViewById(R.id.dis_price121);

        }
    }

}



//



//
//
