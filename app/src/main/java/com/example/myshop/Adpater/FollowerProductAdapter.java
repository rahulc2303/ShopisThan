package com.example.myshop.Adpater;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.Model.Products;
import com.example.myshop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class FollowerProductAdapter extends RecyclerView.Adapter<FollowerProductAdapter.Viewholder>
{

    private Context mContext;
    private List<Products> mPosts;

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    public FollowerProductAdapter(Context mContext, List<Products> mPosts)
    {
        this.mContext = mContext;
        this.mPosts = mPosts;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    }




    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.productslist,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position)
    {


        Products post = mPosts.get(position);

       boolean discountAvailable = post.getDiscountAvailable();
       boolean stockAvailable = post.getOFS();



        holder.username.setText(post.getSellerName());
        holder.orgPrice.setText(post.getPrice());
        holder.disPrice.setText(post.getDiscountPrice());
        Picasso.get().load(post.getImage()).into(holder.postImage);

        String checkProductsSeller = post.getSid();
        firebaseAuth = FirebaseAuth.getInstance();
        String onlineUser = firebaseAuth.getUid();

        if (stockAvailable == true)
        {
            holder.outoffStock.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.outoffStock.setVisibility(View.INVISIBLE);
        }


        if (discountAvailable == true)
        {
            holder.disPrice.setVisibility(View.VISIBLE);
            holder.orgPrice.setPaintFlags(holder.orgPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.disPrice.setTextSize(20);
            holder.orgPrice.setTextSize(15);
        }
        else {
            holder.disPrice.setVisibility(View.GONE);
            holder.orgPrice.setTextSize(20);


        }

        isLiked(post.getPid(), holder.like);
        noOfLikes(post.getPid(),holder.noOfLikes);


        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               if (checkProductsSeller != null && checkProductsSeller.equals(onlineUser))
                {
                    Toast.makeText(mContext, "You can't buy your own product", Toast.LENGTH_LONG).show();
                }
                else {

                    if (stockAvailable==true)
                    {
                        Toast.makeText(mContext, "This product is currently Out of Stock", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        showQuantityDialog(post);
                    }

                }


            }
        });



        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(holder.like.getTag().equals("like"))
                {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Likes").child(post.getPid()).child(firebaseUser.getUid())
                            .setValue(true);
                }

                else
                {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Likes").child(post.getPid()).child(firebaseUser.getUid())
                            .removeValue();
                }

            }
        });

    }

    private double cost = 0;
    private double finalCoast = 0;
    private int quantity = 0;


    private void showQuantityDialog(Products post)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_quantity,null);

        ImageView productIv = view.findViewById(R.id.productIv);
        TextView titleTv = view.findViewById(R.id.titleTv);
        TextView descriptionTv = view.findViewById(R.id.descriptionTv);
        TextView  originalPriceTv= view.findViewById(R.id.originalPriceTv);
        TextView priceDiscountedTv = view.findViewById(R.id.priceDiscountedTv);
        TextView finalPirceTv = view.findViewById(R.id.finalPirceTv);
        TextView quantityTv = view.findViewById(R.id.quantityTv);
        ImageButton decrementBtn = view.findViewById(R.id.decrementBtn);
        ImageButton incrementBtn= view.findViewById(R.id.incrementBtn);
        Button continueBtn = view.findViewById(R.id.continueBtn);

        String productId = post.getPid();
        String title = post.getProductName();
        String description = post.getDescription();
        String image = post.getImage();

        String price;
        if (post.getDiscountAvailable() == true)
        {
            price = post.getDiscountPrice();
            originalPriceTv.setPaintFlags(originalPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }
        else
        {
            priceDiscountedTv.setVisibility(View.GONE);
            price = post.getPrice();
        }

        cost = Double.parseDouble(price.replace("₹",""));
        finalCoast = Double.parseDouble(price.replaceAll("₹",""));
        quantity = 1;

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(view);

        try {
            Picasso.get().load(image).placeholder(R.drawable.ic_baseline_shopping_cart_24).into(productIv);

        }
        catch (Exception e)
        {
            productIv.setImageResource(R.drawable.ic_baseline_shopping_cart_24);
        }

        titleTv.setText(""+title);
        descriptionTv.setText(""+description);
        quantityTv.setText(""+quantity);
        originalPriceTv.setText("₹"+post.getPrice());
        priceDiscountedTv.setText("₹"+post.getDiscountPrice());
        finalPirceTv.setText("₹"+finalCoast);

        AlertDialog dialog = builder.create();
        dialog.show();

        incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalCoast = finalCoast + cost;
                quantity++;

                finalPirceTv.setText("₹" + finalCoast);
                quantityTv.setText(""+quantity);
            }
        });

        decrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity>1)
                {
                    finalCoast = finalCoast - cost;
                    quantity--;

                    finalPirceTv.setText("₹"+finalCoast);
                    quantityTv.setText(""+quantity);
                }
            }
        });
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String title = titleTv.getText().toString().trim();
                String priceEach = originalPriceTv.getText().toString().trim().replace("₹","");
                String price = finalPirceTv.getText().toString().trim().replace("","");
                String quantity = quantityTv.getText().toString().trim();

                addToCart(productId,title,priceEach,price,quantity);

                dialog.dismiss();


            }
        });







    }

    private void addToCart(String productId, String title, String priceEach, String price, String quantity)
    {
        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        final HashMap<String , Object> cartMap = new HashMap<>();
        cartMap.put("pid",productId);
        cartMap.put("pname",title);
        cartMap.put("price",price);
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",quantity);
        cartMap.put("discount","");
        cartMap.put("priceEach",priceEach);
        cartMap.put("orderstate","no");

        cartListRef.child("Users View").child(firebaseUser.getUid())
                .child("Products").child(productId)
                .updateChildren(cartMap);
        Toast.makeText(mContext, "Added to Cart List", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder
    {
        public ImageView imageProfile;
        public ImageView postImage,like,save;

        public TextView username,noOfLikes,disPrice,orgPrice,outoffStock;



        public Viewholder(@NonNull View itemView)
        {
            super(itemView);


            imageProfile = itemView.findViewById(R.id.image_profile);
            postImage = itemView.findViewById(R.id.post_image);
            like = itemView.findViewById(R.id.like);
            save = itemView.findViewById(R.id.addToCart);
            username = itemView.findViewById(R.id.username);
            noOfLikes = itemView.findViewById(R.id.no_of_likes);
            disPrice = itemView.findViewById(R.id.discountedPriceTv);
            orgPrice = itemView.findViewById(R.id.originalPriceTv);
            outoffStock = itemView.findViewById(R.id.outOffStock);



        }
    }

    private void isLiked (String postId, ImageView imageView)
    {
        FirebaseDatabase.getInstance().getReference().child("Likes").child(postId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.child(firebaseUser.getUid()).exists())
                        {
                              imageView.setImageResource(R.drawable.ic_liked);
                          imageView.setTag("liked");
                        }
                        else
                        {
                            imageView.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                            imageView.setTag("like");

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    private void noOfLikes (String postId, TextView text)
    {
        FirebaseDatabase.getInstance().getReference().child("Likes").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                text.setText(dataSnapshot.getChildrenCount() + "likes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    }


//    public static String generateDeepLink(String uid)
//    {
//        return "https://myshoprahul.page.link?link=https://myshoprahul.page.link"+ uid+"&apn=com.example.shopapp.";
//    }
//
//    public static DatabaseReference getSharedRef(String postId)
//    {
//
//    }

