package com.epicodus.gym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.email) EditText mEmail;
    @BindView(R.id.password) EditText mPassword;
    @BindView(R.id.login) Button mLogin;
    private FirebaseAuth mAuth;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mLogin.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
    if(v == mLogin){
         String email = mEmail.getText().toString();
         String password = mPassword.getText().toString();
         if(email.isEmpty() || !(Patterns.EMAIL_ADDRESS.matcher(email).matches())){
             mEmail.setError("invalid email");
         }
         else if(password.isEmpty() || password.length() < 7){
             mPassword.setError("password is short / empty");
         }
         else if(!password.isEmpty() && !email.isEmpty()){

             mAuth.signInWithEmailAndPassword(email, password)
                     .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if (task.isSuccessful()) {
                                 Intent intent = new  Intent(Home.this,MainActivity.class);
                                 intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                 startActivityForResult(intent, 0);
                                 finish();
                             } else {
                                 Toast.makeText(Home.this, "Login failed.",
                                         Toast.LENGTH_SHORT).show();
                             }
                         }
                     });
         }
        }
    }
}
