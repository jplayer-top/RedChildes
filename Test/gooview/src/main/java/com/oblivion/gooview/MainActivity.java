package com.oblivion.gooview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.oblivion.ui.GooView;
import com.oblivion.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {

    private GooView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new GooView(this);
        setContentView(view);
        view.setOnMessageStateListener(new GooView.onMessageStateListener() {

            @Override
            public void onTouchMessage() {
                ToastUtils.setToast(getApplicationContext(),"触摸");
            }

            @Override
            public void onRetainMessage() {
                ToastUtils.setToast(getApplicationContext(),"保留");
            }

            @Override
            public void onRemoveMessage() {
                ToastUtils.setToast(getApplicationContext(),"清除");
            }
        });
    }
}
