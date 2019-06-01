package com.epicodus.gym;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
@BindView(R.id.start) Button mStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mStart ){
            Intent intent = new Intent(this,Home.class);
            startActivity(intent);
        }
    }
}
