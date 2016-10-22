package com.oblivion.bindsecvice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_bind;
    private Button bt_unbind;
    private Button bt_start;
    private Button bt_stop;
    private Iservice mybinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bind = (Button) findViewById(R.id.bt_bind);
        Button unbind = (Button) findViewById(R.id.bt_unbind);
        Button start = (Button) findViewById(R.id.bt_start);
        Button stop = (Button) findViewById(R.id.bt_stop);
        bind.setOnClickListener(this);
        unbind.setOnClickListener(this);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    private class myServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mybinder = (Iservice) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    private myServiceConnection conn;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_bind:
                Intent bindintent = new Intent(this, myService.class);
                conn = new myServiceConnection();
                bindService(bindintent, conn, BIND_AUTO_CREATE);
                break;
            case R.id.bt_unbind:
                unbindService(conn);
                conn = null;
                break;
            case R.id.bt_start:
                Intent startintent = new Intent(this, myService.class);
                startService(startintent);
                break;
            case R.id.bt_stop:
                Intent stopintent = new Intent(this, myService.class);
                startService(stopintent);
                break;
        }
    }
    public void call(View view){
        mybinder.testService();
    }
}
