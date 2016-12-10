package com.oblivion.redchildpuls.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import com.oblivion.redchildpuls.R;

public class SplashActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        delay();

    }
    public void delay(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2500);
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        }).start();

    }
}
