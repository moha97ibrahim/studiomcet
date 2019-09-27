package com.example.mohammedibrahim.studiomcet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohammedibrahim.studiomcet.Activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText eMail,Password;
    private Button signin;
    private TextView signup,reset;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eMail = findViewById(R.id.emailText);
        Password = findViewById(R.id.passwordText);
        signin = (findViewById(R.id.signin));
        signup = (findViewById(R.id.signup1));
        reset = (findViewById(R.id.reset));
        signin.setOnClickListener(this);

        reset.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });
    }


    public void userlogin(){

        String email = eMail.getText().toString().trim();
        String password = Password.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "enter the email", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "enter the password",  Toast.LENGTH_LONG).show();
        } else {
            progressDialog.setMessage("signing in...");
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        checkEmailVerified();
                        return;
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Incorrect UserName / Password", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }

    private void checkEmailVerified() {
        if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }
        else {
            Toast.makeText(this, "Verify the email address", Toast.LENGTH_LONG).show();
            sendEmailVerification();
            firebaseAuth.signOut();
        }
    }

    private void sendEmailVerification() {
        FirebaseUser user2 = FirebaseAuth.getInstance().getCurrentUser();
        if (user2 != null) {
            user2.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Check your email for verification", Toast.LENGTH_LONG).show();
                        FirebaseAuth.getInstance().signOut();
                    }
                }
            });
        }
    }


    @Override
    public void onClick(View view) {
        if (view == signin){
            userlogin();


        }


        if(view == reset)
        {

            startActivity(new Intent(this,ResetPassword.class));
        }


    }
}
