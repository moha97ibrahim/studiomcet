package com.example.mohammedibrahim.studiomcet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohammedibrahim.studiomcet.Activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class SignupActivity extends AppCompatActivity implements View.OnClickListener{





    private EditText eMail,Password,userName,mobileNumber;
    private Button regiter;
    private TextView SignIn;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private FirebaseDatabase mFirebaseDatabase;

    private static int RESULT_LOAD_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        eMail = findViewById(R.id.email);
        userName = findViewById(R.id.username);
        mobileNumber=findViewById(R.id.mobilenumber);
        Password = findViewById(R.id.password);
        regiter = (findViewById(R.id.register));
        SignIn = (findViewById(R.id.signin));
        regiter.setOnClickListener(this);
        SignIn.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = this.mFirebaseDatabase.getReference().child("users");
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }





        //TextView buttonLoadImage = (TextView) findViewById(R.id.editImage);
//        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//
//                Intent i = new Intent(
//                        Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//                startActivityForResult(i, RESULT_LOAD_IMAGE);
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            //ImageView imageView = (ImageView) findViewById(R.id.profileImage);
           // imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }


    public void registeruser(){
        String email = eMail.getText().toString().trim();
        String password = Password.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "enter the email",Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "enter the password", Toast.LENGTH_LONG).show();
        } else {
            progressDialog.setMessage("Registring Please Wait...");
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        progressDialog.dismiss();

                        sendEmailVerification();

                        return;

                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(SignupActivity.this, "could not register please try again", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

    }

    private void sendEmailVerification() {
        FirebaseUser user2 = FirebaseAuth.getInstance().getCurrentUser();
        if (user2 != null) {
            user2.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        saveUserInformation();
                        Toast.makeText(SignupActivity.this, "Check your email for verification", Toast.LENGTH_LONG).show();
                        FirebaseAuth.getInstance().signOut();
                    }

                }
            });
        }
    }


    public void saveUserInformation(){

        Log.d("Ib","hgds");
        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(new users(
                eMail.getText().toString().trim(),
                userName.getText().toString().trim(),
                mobileNumber.getText().toString().trim()));

        startActivity(new Intent(this, LoginActivity.class));
        Toast.makeText(this, "Sign In Now", Toast.LENGTH_LONG).show();



    }


    @Override
    public void onClick(View view) {
        String email = eMail.getText().toString().trim();
        String usrname = userName.getText().toString().trim();

        String pwd =  Password.getText().toString().trim();
        String mobilenum = mobileNumber.getText().toString().trim();

        if(view == regiter){
            if(TextUtils.isEmpty(usrname))
            {
                Toast.makeText(this,"Enter the name",Toast.LENGTH_LONG).show();
            }
            else  if(TextUtils.isEmpty(email))
            {
                Toast.makeText(this,"Enter the name",Toast.LENGTH_LONG).show();
            }
            else if(TextUtils.isEmpty(pwd))
            {
                Toast.makeText(this,"Enter the name",Toast.LENGTH_LONG).show();
            }
            else if(TextUtils.isEmpty(mobilenum))
            {
                Toast.makeText(this,"Enter the name",Toast.LENGTH_LONG).show();
            }else {
                registeruser();

            }
        }
        if(view == SignIn){
            startActivity(new Intent(this, LoginActivity.class));

        }

    }
}

