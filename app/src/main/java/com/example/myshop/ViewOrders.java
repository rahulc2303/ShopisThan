package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ViewOrders extends AppCompatActivity
{

    private TextView userid;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        userId = getIntent().getStringExtra("ViewSellerId");
        userid = findViewById(R.id.userid);

        userid.setText(userId);

    }
}