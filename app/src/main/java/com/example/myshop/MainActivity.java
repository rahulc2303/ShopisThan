package com.example.myshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        t = new Thread(){

            public void run(){
                try{
                    t.sleep(3000);
                    Intent i=new Intent(getApplicationContext(),LoginPageActivity.class);
                    startActivity(i);
                    finish();
                }catch (Exception e){

                }
            }
        };
        t.start();

    }
}