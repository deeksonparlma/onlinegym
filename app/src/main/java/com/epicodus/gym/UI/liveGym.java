package com.epicodus.gym.UI;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.epicodus.gym.R;

public class liveGym extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.home) TextView mHome;
    @BindView(R.id.stats) TextView mStats;
    @BindView(R.id.videoView) VideoView videoView;
    @BindView(R.id.layt)
    RelativeLayout mLayout;
    @BindView(R.id.seekBar)
    SeekBar mSeekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_gym);
        ButterKnife.bind(this);
        mHome.setOnClickListener(this);
        mStats.setOnClickListener(this);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.workout);
        videoView.setVideoURI(uri);
        videoView.start();

    }

    Runnable mProgressRunner = new Runnable() {
        @Override
        public void run() {
            if (mSeekBar != null) {
                mSeekBar.setProgress(mSeekBar.getProgress()+50); // update seekbar
                    mSeekBar.postDelayed(mProgressRunner, 20); //repeat the update process
            }
        }
    };

    @Override
    public void onClick(View v) {
        if(v == mHome){
            final Animation myAnim = AnimationUtils.loadAnimation(this, R.animator.bounce);
            mHome.startAnimation(myAnim);
            Intent intent= new Intent(liveGym.this,HomeScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(intent, 0);
        }
        else if(v == mStats){
            final Animation myAnim = AnimationUtils.loadAnimation(this, R.animator.bounce);
            mStats.startAnimation(myAnim);
            Intent intent= new Intent(liveGym.this,userStats.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(intent, 0);
        } else if (v == videoView) {
            videoView.start();
        }
    }
}
