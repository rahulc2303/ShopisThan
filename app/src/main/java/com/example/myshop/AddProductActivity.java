package com.example.myshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class AddProductActivity extends AppCompatActivity
{
    private ImageView InputProductImage;
    private EditText InputProductName,InputProductDescription,InputProductPrice,discountPriceEt,discountNoteEt;
    private static final int GalleryPick =1;
    private Uri ImageUri;
    private String Name, Description,Price, saveCurrentDate, saveCurrentTime,Category,GenderCategory,discountPrice,discountNote;
    private String productRandomKey;
    private StorageReference ProductImageRef;
    private FirebaseAuth firebaseAuth;
    private boolean discountAvailable = false;
    private String downloadImageUrl;
    private DatabaseReference ProductRef,sellerRef;
    private TextView categoryTv,categoryGender;
    private   SwitchCompat discountSwitch;
    ProgressBar progressBar;
    private String sName, sAddress , sPhone, sEmail,sID;
    private String currentUser;

    private Button AddNewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();

//        discountPriceEt.setVisibility(View.GONE);
//        discountNoteEt.setVisibility(View.GONE);



        AddNewProduct = (Button) findViewById(R.id.addpro);
        InputProductName = (EditText) findViewById(R.id.product_name);
        InputProductPrice = (EditText) findViewById(R.id.product_price);
        InputProductDescription = (EditText) findViewById(R.id.product_description);
        InputProductImage = (ImageView) findViewById(R.id.select_product_image);
        categoryTv = findViewById(R.id.product_category);
        discountSwitch = findViewById(R.id.discountSwitch);
        discountPriceEt = findViewById(R.id.discountProduct_price);
//        discountNoteEt = findViewById(R.id.discountedNoteEt);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_addProduct);
        categoryGender = findViewById(R.id.gender_category);


        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");

        sellerRef = FirebaseDatabase.getInstance().getReference().child("Store").child(currentUser);


        sellerRef.addValueEventListener(new ValueEventListener() {
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



        //if discountSwitch is checked
        discountSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    discountPriceEt.setVisibility(View.VISIBLE);
//                    discountNoteEt.setVisibility(View.VISIBLE);
                }
                else
                    {
                        discountPriceEt.setVisibility(View.GONE);
//                        discountNoteEt.setVisibility(View.GONE);
                }


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

        categoryGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryGenderDialog();
            }
        });

        AddNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProduct();
            }
        });
    }

    private void categoryGenderDialog()
    {
        // dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Gender Category")
                .setItems(Gender.genderOption, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //get picked category
                        String categorygender = Gender.genderOption[which];

                        //set picked category
                        categoryGender.setText(categorygender);

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
        GenderCategory = categoryGender.getText().toString();
        discountAvailable = discountSwitch.isChecked(); //true/false



        if (ImageUri== null)
        {
            Toast.makeText(this, "Please Upload Product Image", Toast.LENGTH_LONG).show();
            return;
        }

         if (TextUtils.isEmpty(Name))
        {
           InputProductName.setError("Enter Product Name");
            return;
        }
         if (TextUtils.isEmpty(Description))
        {
            InputProductDescription.setError("Enter Product Description");
            return;
        }
         if (TextUtils.isEmpty(Category))
        {
            categoryTv.setError("Enter Product Category");
            return;
        }
         if (TextUtils.isEmpty(GenderCategory))
        {
            categoryGender.setError("Enter Gender Category");
            return;
        }
         if (TextUtils.isEmpty(Price))
        {
            InputProductPrice.setError("Enter Product Price");
            return;
        }

         if (discountAvailable)
        {

            //product is with discount
            discountPrice = discountPriceEt.getText().toString().trim();



            if (TextUtils.isEmpty(discountPrice))
            {
                discountPriceEt.setError("Enter the Price");
                return;
            }

        }

        else
            {
                //product is without discount
                discountPrice = "0";

            }
        StoreProductInfo();

    }

    private void StoreProductInfo()

    {

        AddNewProduct.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);


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
                Toast.makeText(AddProductActivity.this, "Error:" + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
//                Toast.makeText(SellerAddNewProductActivity.this, " Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

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
//                            Toast.makeText(AddProductActivity.this, "got Product image Url Successfully", Toast.LENGTH_SHORT).show();
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
        productMap.put("pid",productRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",Description);
        productMap.put("image",downloadImageUrl);
        productMap.put("price",Price);
        productMap.put("productName",Name);
        productMap.put("gender",GenderCategory);
        productMap.put("discountPrice",discountPrice);
//        productMap.put("discountNote",discountNote);
        productMap.put("discountAvailable",discountAvailable);
        productMap.put("category",Category);
        productMap.put("sellerName",sName);
        productMap.put("sellerAddress",sAddress);
        productMap.put("sellerPhone",sPhone);
        productMap.put("sellerEmail",sEmail);
        productMap.put("sid",sID);
        productMap.put("trending","No");
        productMap.put("OFS",false);

        ProductRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {

//                           loadingBar.dismiss();
                            AddNewProduct.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            clearData();

//                            Toast.makeText(AddProductActivity.this, "Product is added successfully", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(AddProductActivity.this, SellerHomeActivity.class);
//                            startActivity(intent);
                            finish();
                        }

                        else
                        {
//                            loadingBar.dismiss();
                            AddNewProduct.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            String message = task.getException().toString();
                            Toast.makeText(AddProductActivity.this, "Error:" + message, Toast.LENGTH_SHORT).show();

                        }

                    }
                });


    }
    private void clearData()
    {

        InputProductName.setText("");
        InputProductPrice.setText("");
        InputProductDescription.setText("");
        InputProductImage.setImageResource(R.drawable.ic_baseline_add_a_photo_24);
        categoryTv.setText("");
        discountSwitch.setText("");
        discountPriceEt.setText("");
//        discountNoteEt.setText("");
        categoryGender.setText("");
        ImageUri = null;

    }


    private void OpenGallery()
    {
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
}