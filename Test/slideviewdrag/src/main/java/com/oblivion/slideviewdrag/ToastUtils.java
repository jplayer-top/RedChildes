package com.oblivion.slideviewdrag;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by oblivion on 2016/10/26.
 */
public class ToastUtils {

    private static Toast toast;

    public static void setToast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.show();
    }
}
