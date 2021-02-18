package com.example.myshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

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

public class StoreActivity extends AppCompatActivity
{

    private ImageView InputProductImage;
    private EditText InputProductName,InputProductDescription,InputProductPrice;
    private static final int GalleryPick =1;
    private Uri ImageUri;
    private String Name, Description,Price, saveCurrentDate, saveCurrentTime,Category;
    private String productRandomKey;
    private StorageReference ProductImageRef;
    private FirebaseAuth firebaseAuth;
    private String downloadImageUrl;
    private DatabaseReference ProductRef,sellerRef;
    private ProgressDialog loadingBar;
    private TextView categoryTv;
    private String sName, sAddress , sPhone, sEmail,sID;

    private Button AddNewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);



        AddNewProduct = (Button) findViewById(R.id.addpro);
        InputProductName = (EditText) findViewById(R.id.product_name);
        InputProductPrice = (EditText) findViewById(R.id.product_price);
        InputProductDescription = (EditText) findViewById(R.id.product_description);
        InputProductImage = (ImageView) findViewById(R.id.select_product_image);
        categoryTv = findViewById(R.id.product_category);

        firebaseAuth = FirebaseAuth.getInstance();


        //init permission arrays
//        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
//        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        loadingBar =new ProgressDialog(this);




        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");

        sellerRef = FirebaseDatabase.getInstance().getReference().child("Users");


        sellerRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Store").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.exists())
                        {
                            sName = dataSnapshot.child("storeName").getValue().toString();
                            sAddress = dataSnapshot.child("storeAddress").getValue().toString();
                            sPhone = dataSnapshot.child("storePhone").getValue().toString();
                            sID = dataSnapshot.child("sid").getValue().toString();
                            sEmail = dataSnapshot.child("storeEmail").getValue().toString();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });

        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        categoryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pick Category
                categoryDialog();
            }
        });




        AddNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProduct();
            }
        });


    }

    private void categoryDialog()
    {
        // dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Products Category")
                .setItems(Constants.productCategories, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //get picked category
                        String category = Constants.productCategories[which];

                        //set picked category
                        categoryTv.setText(category);

                    }
                })
                .show();
    }


    private void ValidateProduct()
    {


        Description = InputProductDescription.getText().toString();
        Price = InputProductPrice.getText().toString();
        Name = InputProductName.getText().toString();
        Category = categoryTv.getText().toString();

        if (ImageUri== null)
        {
            Toast.makeText(this, "Product image is mandatory.....", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "Please write Product description...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Category))
        {
            Toast.makeText(this, "Please write Product Category..", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Name))
        {
            Toast.makeText(this, "Please write Product Name..", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Price))
        {
            Toast.makeText(this, "Please write Product Price..", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInfo();
        }



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
            InputProductImage.setImageURI(ImageUri);
        }
    }



    private void StoreProductInfo()
    {


        loadingBar.setTitle("Adding New Product ");
        loadingBar.setMessage("Please wait, while we are adding the new product....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        sID =firebaseAuth.getCurrentUser().getUid();

        productRandomKey= sID + "-" +saveCurrentDate + saveCurrentTime;


        StorageReference filePath = ProductImageRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(StoreActivity.this, "Error:" + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(StoreActivity.this, " Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(StoreActivity.this, "got Product image Url Successfully", Toast.LENGTH_SHORT).show();
                            SaveProductInfoToDatabase();

                        }
                    }
                });
            }
        });

    }

    private void SaveProductInfoToDatabase() {

        HashMap<String,Object> productMap = new HashMap<>();
        productMap.put("pid",productRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",Description);
        productMap.put("image",downloadImageUrl);
        productMap.put("price",Price);
        productMap.put("pname",Name);
        productMap.put("category",Category);
        productMap.put("sellerName",sName);
        productMap.put("sellerAddress",sAddress);
        productMap.put("sellerPhone",sPhone);
        productMap.put("sellerEmail",sEmail);
        productMap.put("sid",sID);


        productMap.put("productState","Not Approved");



        ProductRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {

                            loadingBar.dismiss();
                            Toast.makeText(StoreActivity.this, "Product is added successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(StoreActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }

                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(StoreActivity.this, "Error:" + message, Toast.LENGTH_SHORT).show();

                        }

                    }
                });


    }

}