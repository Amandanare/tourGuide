package com.example.admin.mytourguide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSignin;
    private EditText editTextMail;
    private EditText editTextPassword;
    private TextView textViewSignIn;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!=null){
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }

        progressDialog= new ProgressDialog(this);

        editTextMail= (EditText)findViewById(R.id.editTextMAil);
        editTextPassword= (EditText)findViewById(R.id.editTextPassword);
        buttonSignin=(Button) findViewById(R.id.buttonSignin);
        textViewSignIn=(TextView)findViewById(R.id.textViewSignin);

        buttonSignin.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);
    }
    private void userLogin(){
        String email = editTextMail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering user");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       progressDialog.dismiss();
                      if (task.isSuccessful()){
                          //Start the profile activity
                          finish();
                          startActivity(new Intent(getApplicationContext(),ProfileActivity.class));


                      }
                    }
                });
    }
    @Override
    public void onClick(View view){
        if (view == buttonSignin){
            userLogin();
        }
        if (view == textViewSignIn){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }

    }
}
