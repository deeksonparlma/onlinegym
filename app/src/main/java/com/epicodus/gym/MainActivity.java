package com.epicodus.gym;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
@BindView(R.id.start) Button mStart;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mStart.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Toast.makeText(MainActivity.this, "Register",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(MainActivity.this,HomeScreen.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == mStart ){
            Intent intent = new Intent(this,Home.class);
            startActivity(intent);
            finish();
        }
    }
}
