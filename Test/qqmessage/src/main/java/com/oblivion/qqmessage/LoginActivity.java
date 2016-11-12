package com.oblivion.qqmessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.oblivion.qqmessage.bean.QQBuddyList;
import com.oblivion.qqmessage.bean.QQMessage;
import com.oblivion.qqmessage.bean.QQMessageType;
import com.oblivion.qqmessage.bean.QQScoke;
import com.oblivion.qqmessage.core.QQConnection;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.account)
    EditText account;
    @Bind(R.id.pwd)
    EditText pwd;
    @Bind(R.id.login)
    Button login;
    private String account1;
    private String pswd;
    private QQConnection conn;
    private QQConnection.OnQQMessageReceiverListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        conn.removeQQMessageReceiverListener(listener);
    }

    @OnClick(R.id.login)
    public void onClick() {
        account1 = account.getText().toString().trim();
        pswd = pwd.getText().toString().trim();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final QQMessage msg = new QQMessage();
                    msg.type = QQMessageType.MSG_TYPE_LOGIN;
                    msg.content = account1 + "#" + pswd;

                    conn = new QQConnection("192.168.16.21", 5225);
                    conn.connection();
                    conn.sendMessage(msg);
                    listener = new QQConnection.OnQQMessageReceiverListener() {
                        @Override
                        public void OnQQMessageReceiver(QQMessage msg) {
                            System.out.println("获取数据" + msg.type);//获取到了
                            if (!msg.type.equals(QQMessageType.MSG_TYPE_BUDDY_LIST)) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getBaseContext(), "登陆失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                String content = msg.content;
                                System.out.println(content);
                                QQBuddyList mList = new QQBuddyList();
                                QQBuddyList fromXML = (QQBuddyList) mList.fromXML(content);
                                //字段不要写错。。。。。。。。。多想想Json的解析就OK
                                //System.out.println(fromXML.buddyList.get(0).account);
                                //跳转
                                Intent intent = new Intent(getBaseContext(), BuddyListActivity.class);
                                intent.putExtra("mList", fromXML);//传递fromXML需要将类
                                startActivity(intent);
                                finish();
                                QQScoke.mConnection = conn;
                                QQScoke.account = account1;
                                QQScoke.pswd = pswd;
                            }

                        }
                    };
                    conn.addOnQQMessageReceiverListener(listener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
