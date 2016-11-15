package com.oblivion.qqxmpp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.oblivion.qqxmpp.global.Global;
import com.oblivion.qqxmpp.provider.ChatProvider;
import com.oblivion.qqxmpp.provider.ContactProvider;
import com.oblivion.qqxmpp.utils.Change2PinYinUtils;
import com.oblivion.qqxmpp.utils.CurrentTimeUtils;
import com.oblivion.qqxmpp.utils.StringSub;
import com.oblivion.qqxmpp.utils.ThreadUtils;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by oblivion on 2016/11/12.
 */
public class ChatActivity extends AppCompatActivity {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.lv_chat)
    ListView lvChat;
    @Bind(R.id.et_SMS)
    EditText etSMS;
    @Bind(R.id.bt_sendSMS)
    Button btSendSMS;
    private String account;
    private ChatManager chatManager;
    private Chat chat;
    private Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        initData();
    }

    /**
     * 初始化消息通道
     */
    private void initData() {
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        String nick = intent.getStringExtra("nick");
        tvTitle.setText("与" + nick + "聊天中...");
        ThreadUtils.runInThread(new Runnable() {
            @Override
            public void run() {
                if (chat == null) {
                    chatManager = Global.conn.getChatManager();
                    chat = chatManager.createChat(account, messageListener);
                    System.out.println("聊天通道被创建");
                }
            }
        });
        cursor = getContentResolver().query(ChatProvider.SMS_URI, null, ChatProvider.SMS.SESSION_ID + "=?", new String[]{account}, null);
        // cursor = getContentResolver().query(ChatProvider.SMS_URI, null, null, null, null);
//        System.out.println(cursor.getCount() + "一共多少条目");
//        System.out.println(account);
//        cursor.moveToPosition(0);
//        System.out.println(cursor.getString(cursor.getColumnIndex(ChatProvider.SMS.SESSION_ID)));
        setAdapterOrUpdata();
    }

    private CursorAdapter adapter;
    private static final int SEND = 0;
    private static final int RECEIVER = 1;

    static class ViewHolder {
        TextView content;
    }


    /**
     * 设置adapter或者更新数据
     */
    private void setAdapterOrUpdata() {

        if (adapter == null) {
            adapter = new CursorAdapter(getApplicationContext(), cursor, true) {
                @Override
                public int getItemViewType(int position) {
                    cursor.moveToPosition(position);
                    //获取到存储的数据ID
                    String fromId = cursor.getString(cursor.getColumnIndex(ChatProvider.SMS.FROM_ID));
                    if (fromId.equals(Global.account)) {
                        return SEND;
                    }
                    return RECEIVER;
                }

                //返回条目的类型种类
                @Override
                public int getViewTypeCount() {
                    return 2;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    int type = getItemViewType(position);
                    switch (type) {
                        case SEND:
                            ViewHolder sendHolder;
                            if (convertView == null) {
                                convertView = View.inflate(getApplicationContext(), R.layout.item_chat_send, null);
                                sendHolder = new ViewHolder();
                                sendHolder.content = (TextView) convertView.findViewById(R.id.content);
                                convertView.setTag(sendHolder);
                            } else {
                                sendHolder = (ViewHolder) convertView.getTag();
                            }
                            cursor.moveToPosition(position);
                            sendHolder.content.setText(cursor.getString(cursor.getColumnIndex(ChatProvider.SMS.BODY)));
                            return convertView;
                        default:
                            ViewHolder receiverHolder;
                            if (convertView == null) {
                                convertView = View.inflate(getApplicationContext(), R.layout.item_chat_receive, null);
                                receiverHolder = new ViewHolder();
                                receiverHolder.content = (TextView) convertView.findViewById(R.id.content);
                                convertView.setTag(receiverHolder);
                            } else {
                                receiverHolder = (ViewHolder) convertView.getTag();
                            }
                            receiverHolder.content.setText(cursor.getString(cursor.getColumnIndex(ChatProvider.SMS.BODY)));
                            return convertView;
                    }


                }

                @Override
                public View newView(Context context, Cursor cursor, ViewGroup parent) {
                    return null;
                }

                @Override
                public void bindView(View view, Context context, Cursor cursor) {

                }

            };
            lvChat.setAdapter(adapter);
        } else {
            adapter.getCursor().requery();
        }

    }

    /**
     * 消息监听，，不要被误导，，这是接收到消息
     */
    private MessageListener messageListener = new MessageListener() {
        //这里是处理消息，里边的参数是你设定过的----（接收到别人发给你的消息）
        @Override
        public void processMessage(Chat chat, final Message message) {

            if (message.getType().equals(Message.Type.chat)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChatActivity.this, message.getBody(), Toast.LENGTH_SHORT).show();
                        System.out.println("接收到界面消息");//不打算在这里添加消息到数据库中，因为这样会重复插入
                    }
                });
            }
        }
    };

    /**
     * 插入数据
     *
     * @param message
     */

    public void saveOrUpdate(Message message) {
        ContentValues values = new ContentValues();
        values.put(ChatProvider.SMS.FROM_ID, message.getFrom());
        values.put(ChatProvider.SMS.FROM_AVATAR, "0");
        values.put(ChatProvider.SMS.FROM_NICK, Change2PinYinUtils.getNick(message.getFrom()));
        values.put(ChatProvider.SMS.BODY, message.getBody());
        values.put(ChatProvider.SMS.TYPE, message.getType() + "");
        values.put(ChatProvider.SMS.TIME, CurrentTimeUtils.getTime());
        values.put(ChatProvider.SMS.SESSION_ID, message.getTo());
        values.put(ChatProvider.SMS.SESSION_NAME, Change2PinYinUtils.getNick(message.getTo()));
        values.put(ChatProvider.SMS.UNREAD, "1");
        // int update = getContentResolver().update(ChatProvider.SMS_URI, values, ChatProvider.SMS.SESSION_ID + "=?", new String[]{message.getTo()});
        getContentResolver().insert(ChatProvider.SMS_URI, values);
    }


    /**
     * 发送消息
     */
    @OnClick(R.id.bt_sendSMS)
    public void onClick() {
        String body = etSMS.getText().toString().trim();
        Message msg = new Message();
        msg.setType(Message.Type.chat);
        msg.setBody(body);
        msg.setFrom(Global.account);// @itheima.com
        msg.setTo(account);
        try {
            chat.sendMessage(msg);
            saveOrUpdate(msg);
        } catch (XMPPException e) {
            e.printStackTrace();
        } finally {
            etSMS.setText("");
        }
    }
}
