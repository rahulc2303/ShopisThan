package com.example.myshop.Adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.Model.ModelCartItem;
import com.example.myshop.R;

import java.util.ArrayList;

public class AdapterCartItem  extends RecyclerView.Adapter<AdapterCartItem.HolderCartItem>
{
    private Context context;
    private ArrayList<ModelCartItem> cartItems;

    public AdapterCartItem(Context context, ArrayList<ModelCartItem> cartItems)
    {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public HolderCartItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.row_cartitem,parent,false);

        return new HolderCartItem(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HolderCartItem holder, int position)
    {
        ModelCartItem modelCartItem = cartItems.get(position);

        String pid = modelCartItem.getPid();
        String title = modelCartItem.getPname();
        String price = modelCartItem.getPriceEach();
        String cost = modelCartItem.getPrice();
        String quantity = modelCartItem.getQuantity();

        holder.itemTitleTv.setText(""+title);
        holder.itemPriceTv.setText(""+cost);
        holder.itemQuantityTv.setText("["+quantity+"]");
        holder.itemTitleTv.setText(""+title);
        holder.itemPriceEachTv.setText(""+price);







    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    class HolderCartItem extends RecyclerView.ViewHolder
 {
     private TextView itemTitleTv,itemPriceTv,itemPriceEachTv,itemQuantityTv,itemRemoveTv;

     public HolderCartItem(@NonNull View itemView)
     {
         super(itemView);

         itemTitleTv = itemView.findViewById(R.id.itemTitleTv);
         itemPriceTv = itemView.findViewById(R.id.itemPriceTv);
         itemPriceEachTv = itemView.findViewById(R.id.itemPriceEachTv);
         itemQuantityTv = itemView.findViewById(R.id.itemQuantityTv);
         itemRemoveTv = itemView.findViewById(R.id.itemRemoveTv);

     }
 }
}
