package com.example.admin.mytourguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private TextView textviewUserEmail;
    private Button buttonLogout;

    private DatabaseReference databaseReference;

    private EditText editTextName,editTextAddress;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth= FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        databaseReference= FirebaseDatabase.getInstance().getReference();

        editTextAddress=(EditText)findViewById(R.id.editTextAddress);
        editTextName=(EditText)findViewById(R.id.editTextName);
        buttonSave=(Button)findViewById(R.id.buttonSave);


        FirebaseUser user =firebaseAuth.getCurrentUser();

        //Initializing views
        textviewUserEmail=(TextView)findViewById(R.id.textviewUserEmail);

        //Displaying logged in user name
        textviewUserEmail.setText("Welcome"+ "" + user.getEmail());

        buttonLogout=(Button)findViewById(R.id.buttonLogout);

        //Adding listener to button
        buttonLogout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);


    }

    private void saveUserInformation(){
        String name = editTextName.getText().toString().trim();
        String add =editTextAddress.getText().toString().trim();

        userInformation userinformation = new userInformation(name,add);

        FirebaseUser user= firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userinformation);

        Toast.makeText(this, "Information saved...",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onClick (View view){
      if (view == buttonLogout){
         firebaseAuth.signOut();
          finish();
          startActivity(new Intent(this,LoginActivity.class));
      }

     if (view== buttonSave){
         saveUserInformation();
     }

    }
}
