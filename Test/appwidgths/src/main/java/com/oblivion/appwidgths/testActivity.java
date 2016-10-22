package com.oblivion.appwidgths;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

/**
 * Created by oblivion on 2016/10/20.
 */
public class testActivity extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_second);
        Toast.makeText(testActivity.this, "tao", Toast.LENGTH_SHORT).show();
    }
}
