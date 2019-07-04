package com.epicodus.gym.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import com.epicodus.gym.Authentication.Authentication;
import com.epicodus.gym.R;
import com.epicodus.gym.contants.constants;
import com.epicodus.gym.model.PostModel;
import com.epicodus.gym.viewholder.viewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {
@BindView(R.id.videoView) VideoView videoview;
@BindView(R.id.textView2) TextView mClass;
@BindView(R.id.home) TextView mHome;
@BindView(R.id.stats) TextView mStats;
@BindView(R.id.liveGym) TextView mLiveGym;
@BindView(R.id.logout) Button mLogout;
@BindView(R.id.cc) TextView mUsername;
@BindView(R.id.recyclerview) RecyclerView mRecyclerView;
@BindView(R.id.list)
    ListView mList;
    private LinearLayoutManager linearLayoutManager;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private FirebaseAuth mAuth;
    private String username;
    private String mUsernamee;
    NotificationManagerCompat notification;
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    private String[] restaurants = new String[] {"Mi Mero Mole", "Mother's Bistro",
            "Life of Pie", "Screen Door", "Luc Lac", "Sweet Basil",
            "Slappy Cakes", "Equinox", "Miss Delta's", "Andina",
            "Lardo", "Portland City Grill", "Fat Head's Brewery",
            "Chipotle", "Subway"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        ButterKnife.bind(this);
         notification= NotificationManagerCompat.from(this);
        videoview.setOnClickListener(this);
        mHome.setOnClickListener(this);
        mStats.setOnClickListener(this);
        mLiveGym.setOnClickListener(this);
        mLogout.setOnClickListener(this);
        videoview.start();
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUsernamee = mSharedPreferences.getString(constants.username_save, null);
        mUsername.setText(username);
        mAuth = FirebaseAuth.getInstance();
////        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
////        mBuilder.setSmallIcon(R.drawable.assistant);
////        mBuilder.setContentTitle("Online Gym is now live");
////        mBuilder.setContentText("Hi"+mUsernamee+"Online gym is now live,Join the class right away,Don't be left out");
////        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//// notificationID allows you to update the notification later on.
//        mNotificationManager.notify(notificationID, mBuilder.build());
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
//        fetch();
//        RecyclerView adapter =c ;
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, restaurants);
        mList.setAdapter(adapter);
    }

    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.assistant)
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a test notification");

        Intent notificationIntent = new Intent(this, liveGym.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(3, builder.build());
    }

    private void fetch() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("posts")
                .limitToLast(50);
        FirebaseRecyclerOptions<PostModel> options =
                new FirebaseRecyclerOptions.Builder<PostModel>()
                        .setQuery(query, PostModel.class)
                        .build();
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<PostModel, viewHolder>(options) {

            @NonNull
            @Override
            public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.postmodel, parent, false);
                return new viewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull viewHolder viewHolder, int i, @NonNull PostModel model) {
                viewHolder.bindpost(model);
            }
        };
        mRecyclerView.setAdapter(adapter);
    }

    private void addToSharedPreferences(String user) {
        mEditor.putString(constants.username_save, user).apply();
    }

    @Override
    public void onClick(View v) {
        if(v == videoview){
            videoview.start();
        }
        else if(v ==mHome){
            final Animation myAnim = AnimationUtils.loadAnimation(this, R.animator.bounce);
            mHome.startAnimation(myAnim);
        }
        else if(v ==mStats){
            final Animation myAnim = AnimationUtils.loadAnimation(this, R.animator.bounce);
            mStats.startAnimation(myAnim);
            Intent intent= new Intent(HomeScreen.this,userStats.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.putExtra("username",username);
            startActivityForResult(intent, 0);

        }
        else if(v ==mLiveGym){
            addNotification();
            final Animation myAnim = AnimationUtils.loadAnimation(this, R.animator.bounce);
            mLiveGym.startAnimation(myAnim);
            Intent intent= new Intent(HomeScreen.this,liveGym.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(intent, 0);


        }
        else if(v ==mLogout){
            mAuth.signOut();
            Intent intent = new Intent(HomeScreen.this, Authentication.class);
            startActivity(intent);

        }
    }
}
