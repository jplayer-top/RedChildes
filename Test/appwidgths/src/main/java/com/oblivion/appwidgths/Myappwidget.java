package com.oblivion.appwidgths;

import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;

/**
 * Created by oblivion on 2016/10/20.
 */
public class Myappwidget extends AppWidgetProvider {
    private static final String TAG = "Myappwidget";

    @Override
    public void onEnabled(Context context) {
        Log.i(TAG, "onEnabled: appwidget");
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.i(TAG, "onDisabled: appwidget");
    }
}
