package com.example.myshop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myshop.uinew.profile.ProfileNewFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateProfileActivity extends AppCompatActivity
{
    Toolbar toolbar;
     TextInputLayout userName,userPhoneNo,userEmail,userAddress;
     ProgressBar progressBar;
     FirebaseAuth firebaseAuth;
    private CircleImageView profileImageView;
    private TextView profileChangeTextBtn;
    private Button Save;
    private Uri imageUri;
    private String myUrl = "";
    private String currentUser;
    private StorageReference storageProfilePrictureRef;
    private String checker = "";
    private StorageTask uploadTask;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firebaseAuth = FirebaseAuth.getInstance();
        currentUser =  firebaseAuth.getCurrentUser().getUid();

        userName = (TextInputLayout) findViewById(R.id.UserName);
        userAddress = (TextInputLayout) findViewById(R.id.UserAddress);
        userPhoneNo = (TextInputLayout) findViewById(R.id.UserPhone);
        userEmail = (TextInputLayout) findViewById(R.id.UserEmail);
        profileImageView = (CircleImageView) findViewById(R.id.settings_profile_image);
        profileChangeTextBtn = (TextView) findViewById(R.id.profile_image_change_btn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Save = (Button) findViewById(R.id.Save_btn);



        userInFoDisplay(profileImageView, userName,userPhoneNo,userEmail,userAddress);


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (checker.equals("clicked"))
                {
                    userInfoSaved();
                }
                else
                {
                    updateOnlyUserInfo();
                }


            }
        });


        profileChangeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                checker = "clicked";
                CropImage.activity(imageUri)
                        .setAspectRatio(1,1)
                        .start(CreateProfileActivity.this);

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data!=null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            profileImageView.setImageURI(imageUri);
        }
        else
        {
            Toast.makeText(this, "Error, Try Again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CreateProfileActivity.this,CreateProfileActivity.class));
            finish();
        }
    }

    private void updateOnlyUserInfo()
    {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("Profile");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("name",userName.getEditText().getText().toString());
        userMap.put("address",userAddress.getEditText().getText().toString());
        userMap.put("phone",userPhoneNo.getEditText().getText().toString());
        userMap.put("email",userEmail.getEditText().getText().toString());
        ref.updateChildren(userMap);
    }

    private void userInFoDisplay(CircleImageView profileImageView, TextInputLayout userName, TextInputLayout userPhoneNo, TextInputLayout userEmail, TextInputLayout userAddress)
    {
        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("Profile");

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.child("profileImg").exists())
                    {
                        String image = dataSnapshot.child("profileImg").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String phone = dataSnapshot.child("phone").getValue().toString();
                        String email = dataSnapshot.child("email").getValue().toString();
                        String address = dataSnapshot.child("address").getValue().toString();

                        Picasso.get().load(image).into(profileImageView);
                        userName.getEditText().setText(name);
                        userPhoneNo.getEditText().setText(phone);
                        userEmail.getEditText().setText(email);
                        userAddress.getEditText().setText(address);


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }

    private void userInfoSaved()
    {
          if (TextUtils.isEmpty(userName.getEditText().getText().toString()))
          {
                  userName.setError("Enter the Name");
          }
        else if (TextUtils.isEmpty(userPhoneNo.getEditText().getText().toString()))
        {
            userName.setError("Enter the PhoneNo");
        }
        else if (TextUtils.isEmpty(userEmail.getEditText().getText().toString()))
        {
            userName.setError("Enter the EmailId");
        }
        else if (TextUtils.isEmpty(userAddress.getEditText().getText().toString()))
        {
            userName.setError("Enter the Address");
        }
        else if(checker.equals("clicked"))
        {
            uploadImg();
        }
    }

    private void uploadImg()
    {

        if(imageUri != null)
        {
            final StorageReference fileRef =  storageProfilePrictureRef.child(currentUser +".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception
                {
                    if(!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful())
                            {

                                Save.setVisibility(View.GONE);
                                progressBar.setVisibility(View.VISIBLE);
                                Uri downloadUri  = task.getResult();
                                myUrl = downloadUri.toString();

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("Profile");

                                HashMap<String, Object> userMap = new HashMap<>();
                                userMap.put("name",userName.getEditText().getText().toString());
                                userMap.put("address",userAddress.getEditText().getText().toString());
                                userMap.put("phone",userPhoneNo.getEditText().getText().toString());
                                userMap.put("email",userEmail.getEditText().getText().toString());
                                userMap.put("profileImg",myUrl);
                                userMap.put("uId",currentUser);
                                ref.updateChildren(userMap);


                                Save.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);

                                startActivity( new Intent(CreateProfileActivity.this, ProfileNewFragment.class));
                                finish();
                            }
                            else
                            {

                                Save.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(CreateProfileActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        else
        {
            Toast.makeText(this, "image is not selected", Toast.LENGTH_SHORT).show();
        }

    }

}