package com.epicodus.gym.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.gym.R;
import com.epicodus.gym.UI.HomeScreen;
import com.epicodus.gym.contants.constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;

public class Authentication extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.email) EditText mEmail;
    @BindView(R.id.password) EditText mPassword;
    @BindView(R.id.login) Button mLogin;
    @BindView(R.id.signup) TextView mSignUp;
    @BindView(R.id.loginPart) CardView mFront;
    @BindView(R.id.SignPart) CardView mBack;
    @BindView(R.id.login2) TextView mLogin2;
    @BindView(R.id.loginUsername) EditText mloginUsername;
    //signup//
    @BindView(R.id.profile_image)
    CircleImageView mProfile;
    @BindView(R.id.username) EditText mUsername;
    @BindView(R.id.email1) EditText mEmail2;
    @BindView(R.id.password1) EditText mPassword1;
    @BindView(R.id.password2) EditText mPassword2;
    @BindView(R.id.phone) EditText mPhone;
    @BindView(R.id.signup1) Button mSignUpNow;
    @BindView(R.id.camera) TextView mCamera;
    public  static  String pc ="Dickson";
    private Bitmap mPic;
    private FirebaseAuth mAuth;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String preference;
    private ProgressDialog mAuthProgressDialog;
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
        mCamera.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        createAuthProgressDialog();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Toast.makeText(Authentication.this, "Register",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(Authentication.this, HomeScreen.class);
            startActivity(intent);
        }
    mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    mEditor = mSharedPreferences.edit();
    }
    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loggin in...");
        mAuthProgressDialog.setMessage("Checking Credentials...");
        mAuthProgressDialog.setCancelable(false);
    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser == null){
//
//        };
//    }
    @Override
    public void onClick(View v) {
    if(v == mLogin){
        String username = mloginUsername.getText().toString();
        if(username.isEmpty()){
            mloginUsername.setError("Invalid username");
        }         String emaill = mEmail.getText().toString();
         String passwordd = mPassword.getText().toString();
         if(emaill.isEmpty() || !(Patterns.EMAIL_ADDRESS.matcher(emaill).matches())){
             mEmail.setError("invalid email");
         }
         else if(passwordd.isEmpty() || passwordd.length() < 7){
             mPassword.setError("password is short / empty");
         }
         else if(!passwordd.isEmpty() && !emaill.isEmpty() && !username.isEmpty()){
             mAuthProgressDialog.show();
             mAuth.signInWithEmailAndPassword(emaill, passwordd)
                     .addOnCompleteListener(this, task -> {
                         if (task.isSuccessful()) {
                             String Lgusername = mloginUsername.getText().toString();
                             pc=Lgusername;
                             addToSharedPreferences(Lgusername);
                             Intent intent = new  Intent(Authentication.this,HomeScreen.class);
                             intent.putExtra("username",username);
                             intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                             startActivityForResult(intent, 0);
                             finish();
                         } else {
                             mAuthProgressDialog.show();
                             Toast.makeText(Authentication.this, "Login failed.",
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
        else if(v ==mProfile || v == mCamera){
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
                mAuthProgressDialog.show();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Authentication.this, task -> {
                    if (!task.isSuccessful()) {
                        FirebaseAuthException e = (FirebaseAuthException )task.getException();
                        String username1 = mUsername.getText().toString();
                        pc = username1;
                        addToSharedPreferences(username1);
                        String email1 = mEmail2.getText().toString();
                        String password1 = mPassword1.getText().toString();
                        String phone1 =mPhone.getText().toString();
                        String Array = "username :"+ " "+username1 +"  "+" Email :"+" "+email1 +"  "+" Password :"+" "+  password1 +" " +" Phone :"+" "+phone1;
                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");
                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (snapshot.hasChild(username1)) {
                                    mUsername.setError("Username already exists");
                                }
                                else{
                                    Bitmap pic = mPic;
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    pic.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                    String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
                                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users").child(username1);
                                    myRef.child("username").setValue(username1);
                                    myRef.child("Email").setValue(email1);
                                    myRef.child("phone").setValue(phone1);
                                    myRef.child("password").setValue(password1);
                                    myRef.child("image").setValue(imageEncoded);
                                    Intent intent = new Intent(Authentication.this, HomeScreen.class);
                                    intent.putExtra("username",username1);
                                    startActivityForResult(intent, 0);
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });



                    }
                });
            }
    }
}
    private void addToSharedPreferences(String user) {
        mEditor.putString(constants.username_save, user).apply();
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
            mPic =imageBitmap;
        }
    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Toast.makeText(Authentication.this, "welcome back ",
                Toast.LENGTH_SHORT).show();
    }

}

