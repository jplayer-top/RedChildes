package com.oblivion.appwidgths;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    Timer timer;
    TimerTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                AppWidgetManager awm =AppWidgetManager.getInstance(MainActivity.this);
                ComponentName provider = new ComponentName(MainActivity.this,Myappwidget.class);
                Intent intent = new Intent(MainActivity.this,testActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent pendingintent = PendingIntent.getActivity(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                RemoteViews views = new RemoteViews(getApplicationInfo().packageName,R.layout.example_appwidget);
                views.setOnClickPendingIntent(R.id.button,pendingintent);
                awm.updateAppWidget(provider,views);
                RadioButton rb = (RadioButton) findViewById(R.id.rb);
            }
        };
        timer.schedule(task, 0, 2000);
    }
}
