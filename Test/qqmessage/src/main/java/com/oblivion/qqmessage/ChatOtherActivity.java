package com.oblivion.qqmessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.oblivion.qqmessage.bean.QQMessage;
import com.oblivion.qqmessage.bean.QQMessageType;
import com.oblivion.qqmessage.bean.QQScoke;
import com.oblivion.qqmessage.core.QQConnection;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by oblivion on 2016/11/10.
 */
public class ChatOtherActivity extends AppCompatActivity {

    @Bind(R.id.title)
    TextView tvName;
    @Bind(R.id.chatlistview)
    ListView listview;
    @Bind(R.id.input)
    EditText input;
    @Bind(R.id.send)
    Button send;

    private String account;
    private ArrayList<QQMessage> mList;
    private ArrayAdapter<QQMessage> adapter;
    private QQConnection.OnQQMessageReceiverListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        initData();
        //创建监听，监听是否有消息发过来
        listener = new QQConnection.OnQQMessageReceiverListener() {
            @Override
            public void OnQQMessageReceiver(QQMessage msg) {
                System.out.println(msg.content + "content");
                System.out.println(msg.from + "from");
                System.out.println(msg.to + "to");
                if (msg.type.equals(QQMessageType.MSG_TYPE_CHAT_P2P)) {
                    mList.add(msg);
                    System.out.println("---");
                } else {
                    System.out.println("000");
                    System.out.println(msg.type);
                    System.out.println(QQMessageType.MSG_TYPE_CHAT_P2P);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        listview.setSelection(mList.size() - 1);
                    }
                });
            }
        };
        QQScoke.mConnection.addOnQQMessageReceiverListener(listener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        QQScoke.mConnection.removeQQMessageReceiverListener(listener);
    }

    @OnClick(R.id.send)
    public void onClick() {
        String message = input.getText().toString().trim();
        QQMessage msg = new QQMessage();
        msg.type = QQMessageType.MSG_TYPE_CHAT_P2P;
        msg.from = Integer.parseInt(QQScoke.account);
        msg.to = Integer.parseInt(account);
        msg.content = message;
        try {
            QQScoke.mConnection.sendMessage(msg);
            //发送之后显示数据
            mList.add(msg);
            adapter.notifyDataSetChanged();
            listview.setSelection(mList.size() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static class ViewHolder {
        TextView content;
    }

    private void initViem() {
        adapter = new ArrayAdapter<QQMessage>(this, 0, mList) {
            private final int SEND = 0;
            private final int RECEIVER = 1;

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                int type = getItemViewType(position);
                switch (type) {
                    case SEND:
                        ViewHolder sendHolder;
                        if (convertView == null) {
                            convertView = View.inflate(getContext(), R.layout.item_chat_send, null);
                            sendHolder = new ViewHolder();
                            sendHolder.content = (TextView) convertView.findViewById(R.id.content);
                            convertView.setTag(sendHolder);
                        } else {
                            sendHolder = (ViewHolder) convertView.getTag();
                        }
                        sendHolder.content.setText(mList.get(position).content);
                        return convertView;
                    default:
                        ViewHolder receiverHolder;
                        if (convertView == null) {
                            convertView = View.inflate(getContext(), R.layout.item_chat_receive, null);
                            receiverHolder = new ViewHolder();
                            receiverHolder.content = (TextView) convertView.findViewById(R.id.content);
                            convertView.setTag(receiverHolder);
                        } else {
                            receiverHolder = (ViewHolder) convertView.getTag();
                        }
                        receiverHolder.content.setText(mList.get(position).content);
                        return convertView;
                }

            }

            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public int getItemViewType(int position) {
                int from = mList.get(position).from;
                if (from == Integer.parseInt(QQScoke.account)) {
                    return SEND;
                } else {
                    return RECEIVER;
                }
            }
        };
        listview.setAdapter(adapter);
        listview.setSelection(mList.size() - 1);//始终最后的条目
    }

    public void initData() {
        Intent intent = getIntent();
        String nick = intent.getStringExtra("nick");
        tvName.setText(nick);
        account = intent.getStringExtra("account");
        mList = new ArrayList<>();
        //模拟数据，发送方
        //send
        for (int i = 0; i < 5; i++) {
            QQMessage sendMessage = new QQMessage();
            sendMessage.type = QQMessageType.MSG_TYPE_CHAT_P2P;
            sendMessage.from = Integer.parseInt(QQScoke.account);
            sendMessage.to = Integer.parseInt(account);
            sendMessage.content = "小" + i + "你好";
            try {
                //  QQScoke.mConnection.sendMessage(sendMessage);

            } catch (Exception e) {
                e.printStackTrace();
            }
            // mList.add(sendMessage);
        }//receiver
        for (int i = 0; i < 5; i++) {
            QQMessage receiverMessage = new QQMessage();
            receiverMessage.type = QQMessageType.MSG_TYPE_CHAT_P2P;
            receiverMessage.from = Integer.parseInt(account);
            receiverMessage.to = Integer.parseInt(QQScoke.account);
            receiverMessage.content = i + "不好";
            try {
                //      QQScoke.mConnection.sendMessage(receiverMessage);

            } catch (Exception e) {
                e.printStackTrace();
            }
            // mList.add(receiverMessage);
        }
        System.out.println(mList.size());
        initViem();
    }
}
