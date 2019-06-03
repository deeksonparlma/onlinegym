package com.epicodus.gym;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.VideoView;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {
@BindView(R.id.videoView) VideoView videoview;
@BindView(R.id.textView2) TextView mClass;
@BindView(R.id.home) TextView mHome;
@BindView(R.id.stats) TextView mStats;
@BindView(R.id.liveGym) TextView mLiveGym;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        ButterKnife.bind(this);
        videoview.setOnClickListener(this);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video);
        videoview.setVideoURI(uri);
        mHome.setOnClickListener(this);
        mStats.setOnClickListener(this);
        mLiveGym.setOnClickListener(this);
        videoview.start();
    }

    @Override
    public void onClick(View v) {
        if(v ==videoview){
            videoview.start();
        }
        else if(v ==mHome){
            final Animation myAnim = AnimationUtils.loadAnimation(this, R.animator.bounce);
            mHome.startAnimation(myAnim);
            mLiveGym.setTextColor(Color.parseColor("#FFFFFF"));
            mStats.setTextColor(Color.parseColor("#FFFFFF"));
            mHome.setTextColor(Color.parseColor("#A460F1"));
        }
        else if(v ==mStats){
            final Animation myAnim = AnimationUtils.loadAnimation(this, R.animator.bounce);
            mStats.startAnimation(myAnim);
            mHome.setTextColor(Color.parseColor("#FFFFFF"));
            mLiveGym.setTextColor(Color.parseColor("#FFFFFF"));
            mStats.setTextColor(Color.parseColor("#A460F1"));
        }
        else if(v ==mLiveGym){
            final Animation myAnim = AnimationUtils.loadAnimation(this, R.animator.bounce);
            mLiveGym.startAnimation(myAnim);
            mHome.setTextColor(Color.parseColor("#FFFFFF"));
            mStats.setTextColor(Color.parseColor("#FFFFFF"));
            mLiveGym.setTextColor(Color.parseColor("#A460F1"));
        }
    }
}
