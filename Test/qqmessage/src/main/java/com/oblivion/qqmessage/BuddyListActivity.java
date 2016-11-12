package com.oblivion.qqmessage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.oblivion.qqmessage.bean.QQBuddyList;
import com.oblivion.qqmessage.bean.QQMessage;
import com.oblivion.qqmessage.bean.QQMessageType;
import com.oblivion.qqmessage.bean.QQScoke;
import com.oblivion.qqmessage.core.QQConnection;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.itcast.server.bean.QQBuddy;

/**
 * Created by oblivion on 2016/11/10.
 */
public class BuddyListActivity extends AppCompatActivity {
    @Bind(R.id.listview)
    ListView listview;
    private ArrayList<QQBuddy> arrayList;
    private ArrayAdapter<QQBuddy> adapter;
    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buddylist);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        QQBuddyList mList = (QQBuddyList) intent.getSerializableExtra("mList");
        arrayList = mList.buddyList;
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (arrayList.get(position).account.equals(QQScoke.account)) {
                    //点击自己的话，不做处理
                    return;
                } else {
                    Intent chatIntent = new Intent(getBaseContext(), ChatOtherActivity.class);
                    chatIntent.putExtra("nick", arrayList.get(position).nick);
                    chatIntent.putExtra("account", arrayList.get(position).account);
                    startActivity(chatIntent);
                }
            }
        });
        adapter = new ArrayAdapter<QQBuddy>(this, 0, arrayList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                ViewHolder holder;
                if (convertView == null) {
                    view = View.inflate(getContext(), R.layout.item_buddylist, null);
                    holder = new ViewHolder();
                    holder.account = (TextView) view.findViewById(R.id.account);
                    holder.nick = (TextView) view.findViewById(R.id.nick);
                    view.setTag(holder);
                } else {
                    view = convertView;
                    holder = (ViewHolder) view.getTag();
                }
                if (arrayList.get(position).account.equals(QQScoke.account)) {
                    holder.account.setText("自己");
                } else {
                    holder.account.setText(arrayList.get(position).account + "@oblivion.com");
                }
                holder.nick.setText(arrayList.get(position).nick);
                return view;
            }
        };
        listview.setAdapter(adapter);

        // System.out.println(arrayList.get(0).account+"-----");
        QQScoke.mConnection.addOnQQMessageReceiverListener(new QQConnection.OnQQMessageReceiverListener() {
            @Override
            public void OnQQMessageReceiver(QQMessage msg) {
                // System.out.println(msg.sendTime);
                if (msg.type.equals(QQMessageType.MSG_TYPE_BUDDY_LIST)) {
                    //如果是登陆成功群发后的消息，接收到之后传递给所有在线的设备
                    QQBuddyList newList = new QQBuddyList();
                    newList = (QQBuddyList) newList.fromXML(msg.content);
                    //更新之前清空listview,添加到集合，别忘记处理只有一个条目时bug
                    arrayList.clear();
                    arrayList.addAll(newList.buddyList);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    static class ViewHolder {
        TextView account;
        TextView nick;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        QQScoke.mConnection.disConnection();
    }
}
