package com.example.admin.mytourguide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class UploadImage extends AppCompatActivity{

    private static final int PICK_IMAGE_REQUEST = 234;
    ImageView imageView;
    //private Button buttonChoose,buttonUpload;
    Button buttonUpload;
    private Uri filePath;
    String url = "https://firebasestorage.googleapis.com/v0/b/mytourguide-47430.appspot.com/o/mandela_statue.jpg?alt=media&token=3508fdb3-e396-4614-b809-88ac1e8a0d7d";

    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        storageReference= FirebaseStorage.getInstance().getReference();

        imageView = (ImageView) findViewById(R.id.imageView);
        buttonUpload=(Button) findViewById(R.id.buttonUpload);

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(getApplicationContext()).load(url).into(imageView);
            }
        });}


        //storageReference= FirebaseStorage.getInstance().getReference();

        //imageView=(ImageView)findViewById(R.id.imageView);
        //buttonChoose=(Button)findViewById(buttonChoose);
        //buttonUpload=(Button)findViewById(buttonUpload);

        //buttonUpload.setOnClickListener(this);
        //buttonChoose.setOnClickListener(this);
    //}

    //private void showFileChooser(){
        //Intent intent = new Intent();
        //intent.setType("image/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        //startActivityForResult(Intent.createChooser(intent,"Select an image"),PICK_IMAGE_REQUEST);

    //}
    private void uploadFile() {
        if (filePath != null) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();


            StorageReference riversRef = storageReference.child("https://firebasestorage.googleapis.com/v0/b/mytourguide-47430.appspot.com/o/mandela_statue.jpg?alt=media&token=3508fdb3-e396-4614-b809-88ac1e8a0d7d");

            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"File Uploaded", Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                       @Override
                        public void onFailure(@NonNull Exception exception) {
                           progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),exception.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage(((int)progress)+ "% Uploaded...");
                }
            });

        }else {
            //display an error toast
            Toast.makeText(getApplicationContext(),"Please select image",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
           filePath=data.getData();
            try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
            imageView.setImageBitmap(bitmap);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //@Override
    //public void onClick(View view){
        //if (view==buttonChoose){
            //open file chooser
            //showFileChooser();
        //}else if (view==buttonUpload){
            //upload file to firebase storage
            //uploadFile();
        //}
    //}
}