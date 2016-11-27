package com.oblivion.changetheme;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.oblivion.changetheme.utils.PrefUtils;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean theme = PrefUtils.getBoolean(getApplicationContext(), "theme", true);
        //进入之前设置主题
        if (theme) {
            setTheme(R.style.basicTheme);
        } else {
            setTheme(R.style.darkTheme);
        }
        PrefUtils.putBoolean(getApplicationContext(), "theme", theme);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        initActionBar();
    }

    /**
     * 初始化ActionBar
     */
    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.emo_im_tongue_sticking_out);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);//显示图标
        actionBar.setTitle("修改主题");
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerToggle.syncState();//同步
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerToggle.onOptionsItemSelected(item);
                break;
            case R.id.ss:
                boolean theme = PrefUtils.getBoolean(getApplicationContext(), "theme", true);
                if (!theme) {
                    setTheme(R.style.basicTheme);
                } else {
                    setTheme(R.style.darkTheme);
                }
                recreate();//重建Activity
                PrefUtils.putBoolean(getApplicationContext(), "theme", !theme);
                Toast.makeText(MainActivity.this, "" + theme, Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
