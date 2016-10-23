package com.oblivion.toggleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.oblivion.viewUI.MyToggleButton;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MyToggleButton myToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToggleButton = (MyToggleButton) findViewById(R.id.mb_test);
        //直接定义布局属性
       /* int slide_backgroundResId = R.drawable.slide_background;
        int slide_iconResId = R.drawable.slide_icon;
        myToggleButton.setSlideBackground(slide_backgroundResId);
        myToggleButton.setSlideIcon(slide_iconResId);
        myToggleButton.setSwitchState(true);*/
        //监听状态改变事件
        myToggleButton.setOnStateChangeListener(new MyToggleButton.OnStateChangeListener() {

            @Override
            public void OnStateChange(boolean state) {
                Toast.makeText(MainActivity.this, state ? "开启" : "关闭", Toast.LENGTH_SHORT).show();
                Log.i(TAG, state ? "开启" : "关闭");
            }
        });
    }
}
