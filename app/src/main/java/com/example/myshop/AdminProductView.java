package com.example.myshop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.Adpater.ProductGenderAdapter;
import com.example.myshop.Model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminProductView extends RecyclerView.Adapter<AdminProductView.Viewholder>
{
    private Context context;
    private List<Products> productsList;

    public AdminProductView(Context context, List<Products> productsList)
    {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.adminproductview,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position)
    {

        Products post = productsList.get(position);

        boolean discountAvailable = post.getDiscountAvailable();
        boolean checkOutOfStock = post.getOFS();

        holder.txtDiscountPrice.setText(post.getDiscountPrice());


        if (checkOutOfStock == true)
        {
            holder.switchCompat.setChecked(false);
            holder.TextOn.setText("Out of Stock");
            holder.TextOn.setTextColor(ContextCompat.getColor(context, R.color.red));

        }
         else
        {
            holder.switchCompat.setChecked(true);
            holder.TextOn.setText("In Stock");
            holder.TextOn.setTextColor(ContextCompat.getColor(context, R.color.black));
        }

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

//        isOutStock(post.getPid(),holder.switchCompat);


        holder.switchCompat.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v)
            {
                if (holder.switchCompat.isChecked())
                {

                    DatabaseReference productRef;
                    productRef =  FirebaseDatabase.getInstance().getReference().child("Products").child(post.getPid());
                    productRef.child("OFS").setValue(false).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            Toast.makeText(context, "In Stock", Toast.LENGTH_SHORT).show();
                            Toast.makeText(context, "In Stock", Toast.LENGTH_SHORT).show();
                            holder.TextOn.setText("In Stock");
                            holder.TextOn.setTextColor(ContextCompat.getColor(context, R.color.black));
                        }
                    });
                }
                else
                {
                    DatabaseReference productRef;
                    productRef =  FirebaseDatabase.getInstance().getReference().child("Products").child(post.getPid());
                    productRef.child("OFS").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            Toast.makeText(context, "Out of Stock", Toast.LENGTH_SHORT).show();
                              holder.TextOn.setText("Out of Stock");
                            holder.TextOn.setTextColor(ContextCompat.getColor(context, R.color.red));
                        }
                    });


                }

            }
        });
    }

    @Override
    public int getItemCount()
    {

        return productsList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder
    {

        public TextView txtProductName, txtProductDescription, txtProductPrice, txtDiscountPrice,TextOn;
        public ImageView imageView;
        SwitchCompat switchCompat;

        public Viewholder(@NonNull View itemView)
        {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.product_image121);
            txtProductName = (TextView) itemView.findViewById(R.id.product_name121);
            txtProductDescription = (TextView) itemView.findViewById(R.id.product_description121);
            txtProductPrice = (TextView) itemView.findViewById(R.id.product_price121);
            txtDiscountPrice = (TextView) itemView.findViewById(R.id.dis_price121);
            switchCompat = itemView.findViewById(R.id.switchButton);
            TextOn = itemView.findViewById(R.id.textOn);


        }
    }
}
