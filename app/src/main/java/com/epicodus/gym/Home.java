package com.epicodus.gym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Home extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.email) EditText mEmail;
    @BindView(R.id.password) EditText mPassword;
    @BindView(R.id.login) Button mLogin;
    @BindView(R.id.signup) TextView mSignUp;
    @BindView(R.id.loginPart) CardView mFront;
    @BindView(R.id.SignPart) CardView mBack;
    @BindView(R.id.login2) TextView mLogin2;
    //signup//
    @BindView(R.id.profile) ImageView mProfile;
    @BindView(R.id.username) EditText mUsername;
    @BindView(R.id.email1) EditText mEmail2;
    @BindView(R.id.password1) EditText mPassword1;
    @BindView(R.id.password2) EditText mPassword2;
    @BindView(R.id.phone) EditText mPhone;
    @BindView(R.id.signup1) Button mSignUpNow;

    private FirebaseAuth mAuth;
    private static final int REQUEST_IMAGE_CAPTURE = 111;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mLogin.setOnClickListener(this);
        mSignUp.setOnClickListener(this);
        mLogin2.setOnClickListener(this);
        mProfile.setOnClickListener(this);
        mSignUpNow.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
    if(v == mLogin){
         String emaill = mEmail.getText().toString();
         String passwordd = mPassword.getText().toString();
         if(emaill.isEmpty() || !(Patterns.EMAIL_ADDRESS.matcher(emaill).matches())){
             mEmail.setError("invalid email");
         }
         else if(passwordd.isEmpty() || passwordd.length() < 7){
             mPassword.setError("password is short / empty");
         }
         else if(!passwordd.isEmpty() && !emaill.isEmpty()){
             mAuth.signInWithEmailAndPassword(emaill, passwordd)
                     .addOnCompleteListener(this, task -> {
                         if (task.isSuccessful()) {
                             Intent intent = new  Intent(Home.this,HomeScreen.class);
                             intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                             startActivityForResult(intent, 0);
                             finish();
                         } else {
                             Toast.makeText(Home.this, "Login failed.",
                                     Toast.LENGTH_SHORT).show();
                         }
                     });
         }
        }
        else if(v == mSignUp){
            mFront.setVisibility(View.INVISIBLE);
            mBack.setVisibility(View.VISIBLE);
        }
        else if(v == mLogin2){
        mBack.setVisibility(View.INVISIBLE);
        mFront.setVisibility(View.VISIBLE);
         }
        else if(v ==mProfile){
        onLaunchCamera();
             }
        else if(v == mSignUpNow){
            String username = mUsername.getText().toString();
            String email = mEmail2.getText().toString();
            String password= mPassword2.getText().toString();
            String confirmPassword=mPassword2.getText().toString();
            String phone =mPhone.getText().toString();

            if (username.isEmpty()) {
                mUsername.setError("invalid Username");
            }
            else if(!(Patterns.EMAIL_ADDRESS.matcher(email).matches())){
                mEmail2.setError("Invalid email");
            }
            else if (password.isEmpty()) {
                mUsername.setError("invalid password");
            }
            else if (!confirmPassword.matches(password)) {
                mUsername.setError("password mismatch");
            }
            else if(phone.length() < 9){
                mPhone.setError("invalid phone number");
            }
            else if(!email.isEmpty() &&  confirmPassword.matches(password) && !username.isEmpty() && phone.length() > 9){
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Home.this, task -> {
                    if (!task.isSuccessful()) {
                        FirebaseAuthException e = (FirebaseAuthException )task.getException();
                        String username1 = mUsername.getText().toString();
                        String email1 = mEmail2.getText().toString();
                        String password1 = mPassword1.getText().toString();
                        String phone1 =mPhone.getText().toString();
                        String Array = "username :"+ " "+username1 +"  "+" Email :"+" "+email1 +"  "+" Password :"+" "+  password1 +" " +" Phone :"+" "+phone1;
                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users").child(username1);
                        myRef.setValue(Array);
                        Intent intent = new Intent(Home.this, HomeScreen.class);
                        startActivityForResult(intent, 0);
                        finish();
                    }
                });
            }
    }
}

        private void onLaunchCamera(){
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mProfile.setImageBitmap(imageBitmap);
//            encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Toast.makeText(Home.this, "me",
                Toast.LENGTH_SHORT).show();
    }

}

