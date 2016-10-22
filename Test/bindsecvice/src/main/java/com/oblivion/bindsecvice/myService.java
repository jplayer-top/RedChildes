package com.oblivion.bindsecvice;

import android.app.Service;
import android.content.Intent;
import android.os.BaseBundle;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by oblivion on 2016/10/20.
 */
public class myService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        myBinder binder = new myBinder();
        return binder;
    }
   private   class myBinder extends Binder implements Iservice{


       @Override
       public void testService() {
           myMethod();
       }
   }
    public void myMethod(){
        System.out.println("123");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("sdadadada", "onCreate: ");
    }
}
