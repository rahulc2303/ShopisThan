package com.example.myshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshop.Adpater.CartViewHolder;
import com.example.myshop.Model.ModelCartItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn;
    private TextView txtTotalAmount, txtMsg1, txtMsg2;
    FirebaseAuth firebaseAuth;
    private String onlineUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        firebaseAuth = FirebaseAuth.getInstance();
        onlineUser = firebaseAuth.getUid();


        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        NextProcessBtn = (Button) findViewById(R.id.next_btn);

//        txtTotalAmount = (TextView) findViewById(R.id.total_price);
//        txtMsg1 = (TextView) findViewById(R.id.msg1);
//        txtMsg2 = (TextView) findViewById(R.id.msg2);


    }

    @Override
    protected void onStart()
    {
        super.onStart();


//        txtTotalAmount.setText(" Total Price = $ " + String.valueOf(overTotalPrice));

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        FirebaseRecyclerOptions<ModelCartItem> options =
                new FirebaseRecyclerOptions.Builder<ModelCartItem>()
                        .setQuery(cartListRef.child("Users View")
                                .child(onlineUser)
                                .child("Products"),ModelCartItem.class)
                        .build();

        FirebaseRecyclerAdapter<ModelCartItem, CartViewHolder> adapter =
                new FirebaseRecyclerAdapter<ModelCartItem, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder holder, int i, @NonNull ModelCartItem model)
                    {



                        holder.txtProductQuantity.setText( model.getQuantity());
                        holder.txtProductPrice.setText(model.getPrice());
                        holder.txtProductName.setText(model.getPname());
                        holder.txtOneProductPrice.setText(model.getPriceEach());





//                        int oneTypeProductTPrice = ((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());
//                        overTotalPrice = overTotalPrice + oneTypeProductTPrice;
//
//                      txtTotalAmount.setText(" Total Price = $ " + String.valueOf(overTotalPrice));



//                        holder.itemView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v)
//                            {
//                                CharSequence options[] = new CharSequence[]
//                                        {
//                                                "Edit",
//                                                "Remove"
//
//                                        };
//                                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
//                                builder.setTitle("Cart Options");
//                                builder.setItems(options, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i)
//                                    {
//                                        if(i==0)
//                                        {
//                                            Intent intent = new Intent(CartActivity.this, ProductDetailsActivity.class);
//                                            intent.putExtra("pid", model.getPid());
//                                            startActivity(intent);
//                                        }
//                                        if(i==1)
//                                        {
//                                            cartListRef.child("Users View")
//                                                    .child(Prevalent.currentOnlineUser.getPhone())
//                                                    .child("Products")
//                                                    .child(model.getPid())
//                                                    .removeValue()
//                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                        @Override
//                                                        public void onComplete(@NonNull Task<Void> task)
//                                                        {
//                                                            if (task.isSuccessful())
//                                                            {
//                                                                cartListRef.child("Admin View")
//                                                                        .child(model.getSid())
//                                                                        .child("Products")
//                                                                        .child(model.getPid())
//                                                                        .removeValue();
//
//                                                                Toast.makeText(CartActivity.this, "Item removed successfully.", Toast.LENGTH_SHORT).show();
//                                                                Intent intent = new Intent(CartActivity.this, CartActivity.class);
//                                                                startActivity(intent);
//                                                            }
//                                                        }
//                                                    });
//                                        }
//
//                                    }
//                                });
//                                builder.show();
//                            }
//                        });


                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cartitem,parent,false);
                        CartViewHolder holder = new CartViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}