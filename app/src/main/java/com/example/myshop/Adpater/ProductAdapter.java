package com.example.myshop.Adpater;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.FilterProducts;
import com.example.myshop.Model.Products;
import com.example.myshop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hendraanggrian.appcompat.widget.SocialTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Viewholder>
{


    private Context context;
    public List<Products> productsList;


    public ProductAdapter(Context context, List<Products> productsList) {
        this.context = context;
        this.productsList = productsList;

    }

    private FirebaseUser firebaseUser;


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.productslist,viewGroup,false);
       return  new ProductAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position)
    {
        Products products = productsList.get(position);
//        Picasso.get().load(products.getImage()).into(holder.postImage);
//        holder.price.setText(products.getPrice());

//        String id = products.getPid();
//        String sid = products.getSid();
        boolean discountAvailable = products.getDiscountAvailable();
//        String discountPrice = products.getDiscountPrice();
//        String orgPrice = products.getPrice();
//        String productCategory = products.getCategory();
//        String productDescription = products.getDescription();
//        String productIcon = products.getImage();
//        String sellerName = products.getSellerName();


        //set data
        holder.username.setText(products.getSellerName());
        holder.price.setText(products.getPrice());
        holder.discountPrice.setText(products.getDiscountPrice());


        if (discountAvailable == true)
        {
            holder.discountPrice.setVisibility(View.VISIBLE);
            holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            holder.discountPrice.setVisibility(View.GONE);


        }
        try {
            Picasso.get().load(products.getImage()).placeholder(R.drawable.ic_baseline_add_a_photo_24).into(holder.postImage);
        }
        catch (Exception e)
        {
            holder.postImage.setImageResource(R.drawable.ic_baseline_add_a_photo_24);
        }




    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }



    public class  Viewholder extends RecyclerView.ViewHolder
    {

        public ImageView imageProfile;
        public ImageView postImage,like,save;

        public TextView username,noOfLikes,price,discountPrice;


        public Viewholder(@NonNull View itemView)
        {
            super(itemView);

            imageProfile = itemView.findViewById(R.id.image_profile);
            postImage = itemView.findViewById(R.id.post_image);
            like = itemView.findViewById(R.id.like);
            save = itemView.findViewById(R.id.addToCart);

            username = itemView.findViewById(R.id.username);
            noOfLikes = itemView.findViewById(R.id.no_of_likes);
            price = itemView.findViewById(R.id.originalPriceTv);
            discountPrice = itemView.findViewById(R.id.discountedPriceTv);



        }
    }
}
