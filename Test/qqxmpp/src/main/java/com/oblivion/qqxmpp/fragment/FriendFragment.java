package com.oblivion.qqxmpp.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.oblivion.qqxmpp.ChatActivity;
import com.oblivion.qqxmpp.R;
import com.oblivion.qqxmpp.provider.ContactProvider;

/**
 * Created by oblivion on 2016/11/11.
 */
public class FriendFragment extends BaseFragment {
    private ListView listView;
    private Cursor cursor;

    @Override
    public View createViewSaveData(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_friend, null);
        listView = (ListView) view.findViewById(R.id.lv_friends);
        //创建内容监听
        getContext().getContentResolver().registerContentObserver(ContactProvider.URI, false, observer);
        //查询数据库----并且根据昵称的字母升序DE
        cursor = getContext().getContentResolver().query(ContactProvider.URI, null, null, null, "sort DESC");
        setAdapterOrNotification();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                cursor.moveToPosition(position);
                intent.putExtra("account", cursor.getString(cursor.getColumnIndex("account")));
                intent.putExtra("nick", cursor.getString(cursor.getColumnIndex("nick")));
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消掉内容监听
        getContext().getContentResolver().unregisterContentObserver(observer);
    }

    private MyObserver observer = new MyObserver(new Handler());

    /**
     * 内容观察者
     */
    public class MyObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public MyObserver(Handler handler) {
            super(handler);
        }

        /**
         * 一个参数的是低版本，两个参数是高版本
         *
         * @param selfChange
         */
        @Override
        public void onChange(boolean selfChange) {
            //检测到数据库更新，请求ListView更新
            setAdapterOrNotification();
        }
    }

    /**
     * 指针适配器
     */
    private CursorAdapter adapter;

    static class ViewHolder {
        TextView account;
        TextView nick;
    }

    /**
     * 更新或设置适配器
     */
    private void setAdapterOrNotification() {
        System.out.println("你经历了什么");
        if (adapter == null) {
            adapter = new CursorAdapter(getContext(), cursor, true) {
                @Override
                public View newView(Context context, Cursor cursor, ViewGroup parent) {
                    return null;
                }

                @Override
                public void bindView(View view, Context context, Cursor cursor) {
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view;
                    ViewHolder holder;
                    if (convertView == null) {
                        view = View.inflate(getContext(), R.layout.item_friends, null);
                        holder = new ViewHolder();
                        holder.account = (TextView) view.findViewById(R.id.tv_frinend_account);
                        holder.nick = (TextView) view.findViewById(R.id.tv_friend_nick);
                        view.setTag(holder);
                    } else {
                        view = convertView;
                        holder = (ViewHolder) view.getTag();
                    }
                    cursor.moveToPosition(position);
                    holder.account.setText
                            (cursor.getString(cursor.getColumnIndex(ContactProvider.CONTACT_FIELD.ACCOUNT)));
                    holder.nick.setText
                            (cursor.getString(cursor.getColumnIndex(ContactProvider.CONTACT_FIELD.NICK)));
                    return view;//你麻的，总是忘记添加，，下次先给你添加上
                }
            };
            listView.setAdapter(adapter);
            System.out.println("你刚被创建");
        } else {
            adapter.getCursor().requery();//通过这个方式更新数据；
            System.out.println("你已经创建了");
        }
    }
}
