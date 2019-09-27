package com.example.mohammedibrahim.studiomcet;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity implements View.OnClickListener{

    private EditText email;
    private Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        email = findViewById(R.id.emailreset);
        reset = findViewById(R.id.btnreset);
        reset.setOnClickListener(this);
    }


    public void resetPassword(){

        String mail = email.getText().toString().trim();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = mail;

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("zfv", "Email sent.");
                            Toast.makeText(ResetPassword.this, "Email sent", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }



    @Override
    public void onClick(View view) {
        if(view == reset){

            resetPassword();
        }
    }
}
