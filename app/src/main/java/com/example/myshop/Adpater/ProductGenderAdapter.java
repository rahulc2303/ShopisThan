package com.example.myshop.Adpater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.Model.Products;
import com.example.myshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductGenderAdapter extends RecyclerView.Adapter<ProductGenderAdapter.Viewholder>
{

    private Context mContext;
    private List<Products> mPosts;

    public ProductGenderAdapter(Context mContext, List<Products> mPosts)
    {
        this.mContext = mContext;
        this.mPosts = mPosts;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.productscat,parent,false);
        return new Viewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position)
    {


        Products post = mPosts.get(position);

        boolean discountAvailable = post.getDiscountAvailable();

        holder.txtDiscountPrice.setText(post.getDiscountPrice());


        if (discountAvailable == true)
        {
            holder.txtDiscountPrice.setVisibility(View.VISIBLE);
            holder.txtProductPrice.setPaintFlags( holder.txtProductPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.txtDiscountPrice.setTextSize(20);
            holder.txtProductPrice.setTextSize(15);



        }
        else {
            holder.txtDiscountPrice.setVisibility(View.GONE);
            holder.txtProductPrice.setTextSize(20);


        }

        holder.txtProductName.setText(post.getProductName());
        holder.txtProductDescription.setText(post.getDescription());
        holder.txtProductPrice.setText(post.getPrice());
        Picasso.get().load(post.getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount()
    {
        return mPosts.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder
    {

        public TextView txtProductName, txtProductDescription, txtProductPrice, txtDiscountPrice;
        public ImageView imageView;

        public Viewholder(@NonNull View itemView)
        {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.product_image121);
            txtProductName = (TextView) itemView.findViewById(R.id.product_name121);
            txtProductDescription = (TextView) itemView.findViewById(R.id.product_description121);
            txtProductPrice = (TextView) itemView.findViewById(R.id.product_price121);
            txtDiscountPrice = (TextView) itemView.findViewById(R.id.dis_price121);
        }
    }
}
