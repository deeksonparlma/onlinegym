package com.epicodus.gym;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Home extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.email) EditText mEmail;
    @BindView(R.id.password) EditText mPassword;
    @BindView(R.id.login) Button mLogin;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mLogin.setOnClickListener(this);
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
        }
    }
}
