package com.oblivion.slideclean;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.oblivion.ui.SlideClean;
import com.oblivion.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {
private ListView lv_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        lv_test = (ListView) findViewById(R.id.lv_test);
        lv_test.setAdapter(new SlideCleanAdapter(lv_test));
    }
}
