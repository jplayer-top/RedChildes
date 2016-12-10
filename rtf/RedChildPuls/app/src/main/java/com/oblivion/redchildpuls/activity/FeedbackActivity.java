package com.oblivion.redchildpuls.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.oblivion.redchildpuls.R;

/**
 * Created by CAILIN on 2016/12/9.
 */
public class FeedbackActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

    }
    public void submit(View view){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("提醒");
        pd.setMessage("你的反馈已收到");
        pd.show();
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pd.dismiss();
            };
        }.start();
    }
}

