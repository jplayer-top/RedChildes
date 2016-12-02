package com.oblivion.jni_text;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
    static {
        System.loadLibrary("jni_text");
    }
//javah -d jni -classpath <SDK_android.jar>;<APP_classes> lab.sodino.jnitest.MainActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    public native String getStringFromC();

    @Override
    public void onClick(View v) {
        Toast.makeText(MainActivity.this, getStringFromC(), Toast.LENGTH_SHORT).show();
    }

}
