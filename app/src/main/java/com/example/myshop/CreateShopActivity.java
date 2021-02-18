package com.example.myshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.HashMap;

public class CreateShopActivity extends AppCompatActivity
{

    private EditText shopName,shopPhone,shopEmail,shopGST,shopAddress;
    private Button StoreBtn;
    private FirebaseAuth firebaseAuth;
    private String sID;
    private Uri ImageUri;
    private DatabaseReference StoreRef,CheckStore;
    private String name,address,email,GST,phone,category;
    private ImageView shopImg;
    ProgressBar progressBar;
    private TextView categoryShop;
    private static final int GalleryPick =1;
    private StorageReference StoreImageRef;
    private String downloadImageUrl;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_shop);


        firebaseAuth = FirebaseAuth.getInstance();

        sID =firebaseAuth.getCurrentUser().getUid();

        StoreRef = FirebaseDatabase.getInstance().getReference().child("Store").child(sID);
        StoreImageRef = FirebaseStorage.getInstance().getReference().child("Stores Images");

        shopName = (EditText) findViewById(R.id.shop_name);
        shopPhone = (EditText) findViewById(R.id.shop_phone);
        shopEmail = (EditText) findViewById(R.id.shop_email);
        shopGST = (EditText) findViewById(R.id.shopGST);
        shopAddress = (EditText) findViewById(R.id.shop_address);
        StoreBtn = (Button) findViewById(R.id.store_btn);
        categoryShop = (TextView) findViewById(R.id.shop_category);
        shopImg = (ImageView) findViewById(R.id.img_shop);

        shopImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });


        categoryShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pick Category
                categoryDialog();
            }
        });


        StoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                checkDeatils();

            }
        });


    }

    private void OpenGallery() {

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GalleryPick && resultCode== RESULT_OK && data!=null)
        {
            ImageUri = data.getData();
            shopImg.setImageURI(ImageUri);
        }
    }


    private void categoryDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Products Category")
                .setItems(ShopCat.shopCategories, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //get picked category
                        String category = ShopCat.shopCategories[which];

                        //set picked category
                        categoryShop.setText(category);

                    }
                })
                .show();
    }


    private void checkDeatils()
    {

        progressBar = findViewById(R.id.progressBar121);

         name = shopName.getText().toString();
         phone = shopPhone.getText().toString();
         email = shopEmail.getText().toString();
         GST = shopGST.getText().toString();
         address = shopAddress.getText().toString();
         category = categoryShop.getText().toString();

        if(TextUtils.isEmpty(name))
        {
            shopName.setError("Enter the Name");
        }
        else if(TextUtils.isEmpty(phone))
        {
            shopPhone.setError("Enter  the Phone.No");
        }

        else if(TextUtils.isEmpty(email))
        {
            shopEmail.setError("Enter the Email");
        }

        else if(TextUtils.isEmpty(GST))
        {
            shopGST.setError("Enter the GST");
        }

        else if(TextUtils.isEmpty(address))
        {
            shopAddress.setError("Enter the address");
        }
        else if(TextUtils.isEmpty(category))
        {
            categoryShop.setError("Enter the Category");
        }

        else if (ImageUri== null)
        {
            Toast.makeText(this, "Shop image is mandatory.....", Toast.LENGTH_SHORT).show();

        }


        else
        {
            progressBar.setVisibility(View.VISIBLE);
            StoreBtn.setVisibility(View.INVISIBLE);
            StorePhotoInfo();
        }

    }


    private void StorePhotoInfo()
    {


        StorageReference filePath = StoreImageRef.child(ImageUri.getLastPathSegment() + sID + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(CreateShopActivity.this, "Error:" + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
//                Toast.makeText(CreateShopActivity.this, " Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();


                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if(task.isSuccessful())
                        {

                            downloadImageUrl = task.getResult().toString();
//                            Toast.makeText(SellerAddNewProductActivity.this, "got Product image Url Successfully", Toast.LENGTH_SHORT).show();
                            SaveProductInfoToDatabase();

                        }
                    }
                });
            }
        });

    }

    private void SaveProductInfoToDatabase()
    {


        HashMap<String,Object> productMap = new HashMap<>();
        productMap.put("sid",sID);
        productMap.put("storeName",name);
        productMap.put("storeAddress",address);
        productMap.put("storePhone",phone);
        productMap.put("storeEmail",email);
        productMap.put("storeGST",GST);
        productMap.put("image",downloadImageUrl);
        productMap.put("category",category);






        StoreRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {


                    progressBar.setVisibility(View.INVISIBLE);
                    StoreBtn.setVisibility(View.VISIBLE);
                    finish();
                }

                else
                {

                    progressBar.setVisibility(View.INVISIBLE);
                    StoreBtn.setVisibility(View.VISIBLE);
                    String message = task.getException().toString();
                    Toast.makeText(CreateShopActivity.this, "Error:" + message, Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
}