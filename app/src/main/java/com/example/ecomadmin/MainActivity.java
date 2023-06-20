package com.example.ecomadmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ecomadmin.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private String id,title,decription,price;
    private Uri uri;

    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mAuth = FirebaseAuth.getInstance();
        onStart();


        binding.btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                title = binding.title.getText().toString();
                decription = binding.decription.getText().toString();
                price = binding.price.getText().toString();
              addProduct();
            }
        });
        //1  koi image field par click kare to ky hoga
        binding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,100);
            }
        });

        //3 when click on upload button controll will go in uploadImage() method call
        binding.uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImage();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        user = mAuth.getCurrentUser();
        if (user != null) {
            // do your stuff
        } else {
            signInAnonymously();
        }
    }





    private void signInAnonymously() {

       mAuth.signInAnonymously().addOnSuccessListener(this, new  OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // do your stuff
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure( Exception exception) {
                        Log.e("TAG", "signInAnonymously:FAILURE", exception);
                    }
                });

    }


    //4
    private void uploadImage()
    {
        //storage me kidhar jakar image store hoga
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("products/"+id+".png");// products/ here lash is to create new id
       storageReference.putFile(uri)
               .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       storageReference.getDownloadUrl()
                               .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                   @Override
                                   public void onSuccess(Uri uri) {
                                       FirebaseFirestore.getInstance()
                                               .collection("products")
                                               .document(id)
                                               .update("image",uri.toString());
                                       Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                   }
                               });
                   }
               });
    }

    public  void addProduct()
    {
        id = UUID.randomUUID().toString();//to create random id
        ProductModel productModel = new ProductModel(id,title,decription,null,true);
        FirebaseFirestore.getInstance()
                .collection("products")
                .document(id)
                .set(productModel);
        Toast.makeText(this, "Product added", Toast.LENGTH_SHORT).show();
    }


    //2  for get the  image  on imageView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100)
        {
            uri= data.getData();
            binding.image.setImageURI(uri);
        }
    }
}