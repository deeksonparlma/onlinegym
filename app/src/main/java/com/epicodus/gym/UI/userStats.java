package com.epicodus.gym.UI;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.gym.Authentication.Authentication;
import com.epicodus.gym.R;
import com.epicodus.gym.contants.constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class userStats extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.home) TextView mHome;
    @BindView(R.id.liveGym) TextView mLiveGym;
    @BindView(R.id.usernameStat) TextView mUsername;
    @BindView(R.id.emailstats) TextView mEmail;
    @BindView(R.id.profilestat) ImageView mProfile;
    @BindView(R.id.phoneNumber) TextView mPhoneNumber;
    @BindView(R.id.profile_image)
    CircleImageView mProfileImage;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private FirebaseAuth mAuth;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stats);
        ButterKnife.bind(this);
        mHome.setOnClickListener(this);
        mLiveGym.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        Intent intent =getIntent();
        String bingo =intent.getStringExtra("username");
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = mSharedPreferences.getString(constants.username_save, null);

        if (username != null) {
            loadStats(username);
        }
//        username = Authentication.pc;
        loadStats(username);
    }

    private void loadStats(String usernamee) {
        FirebaseUser current = mAuth.getCurrentUser();
        current.getUid();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");
        String value = myRef.child("John").child("username").getKey();
//        mUsername.setText(value);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference username = ref.child(usernamee).child("username");
        DatabaseReference email = ref.child(usernamee).child("Email");
        DatabaseReference profile = ref.child(usernamee).child("image");
        DatabaseReference phone = ref.child(usernamee).child("phone");

        //username//
        username.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = dataSnapshot.getValue(String.class);
                mUsername.setText(email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //phone//
        phone.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String phone = dataSnapshot.getValue(String.class);
                mPhoneNumber.setText(phone);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //profile//
        profile.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String profile = dataSnapshot.getValue(String.class);
                try {
                    Bitmap imageBitmap = decodeFromFirebaseBase64(profile);
                    mProfileImage.setImageBitmap(imageBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //email//
        email.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = dataSnapshot.getValue(String.class);
                mEmail.setText(email);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
    @Override
    public void onClick(View v) {
        if(v == mLiveGym){
            final Animation myAnim = AnimationUtils.loadAnimation(this, R.animator.bounce);
            mLiveGym.startAnimation(myAnim);
            Intent intent= new Intent(userStats.this,liveGym.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(intent, 0);
        }
        else if(v == mHome){
            final Animation myAnim = AnimationUtils.loadAnimation(this, R.animator.bounce);
            mHome.startAnimation(myAnim);
            Intent intent= new Intent(userStats.this,HomeScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(intent, 0);
        }
    }

}
