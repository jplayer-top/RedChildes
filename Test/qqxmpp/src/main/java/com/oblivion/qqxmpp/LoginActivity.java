package com.oblivion.qqxmpp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.oblivion.qqxmpp.global.Global;
import com.oblivion.qqxmpp.utils.ThreadUtils;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by oblivion on 2016/11/11.
 */
public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.account)
    EditText account;
    @Bind(R.id.pwd)
    EditText pwd;
    @Bind(R.id.login)
    Button login;
    private String account1;
    private String pswd;
    private boolean isLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    private Handler handler = new Handler();

    @OnClick(R.id.login)
    public void onClick() {
        account1 = this.account.getText().toString().trim();
        pswd = pwd.getText().toString().trim();
        ThreadUtils.runInThread(new Runnable() {
            @Override
            public void run() {
                isLogin = false;
                ConnectionConfiguration configuration = new ConnectionConfiguration(Global.HOST, Global.PORT);
                configuration.setDebuggerEnabled(true);
                //密码安全传输，，，萌萌哒的设定先设定为disable;
                configuration.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
                XMPPConnection connection = new XMPPConnection(configuration);
                try {
                    connection.connect();
                    connection.login(account1, pswd);
                    isLogin = true;
                    Global.conn = connection;
                    Global.account = account1;
                    Global.pswd = pswd;
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                } catch (XMPPException e) {
                    e.printStackTrace();
                    isLogin = false;
                } finally {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), isLogin ? "登陆成功" : "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        });
    }
}
