package com.oblivion.qqxmpp.server;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;

import com.oblivion.qqxmpp.domain.Friend;
import com.oblivion.qqxmpp.global.Global;
import com.oblivion.qqxmpp.provider.ContactProvider;
import com.oblivion.qqxmpp.utils.Change2PinYinUtils;
import com.oblivion.qqxmpp.utils.StringSub;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import java.util.Collection;

/**
 * Created by oblivion on 2016/11/12.
 */
public class IMServer extends Service {
    private Roster roster;
    private RosterListener rosterListener;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        System.out.println("IMserver服务开启");
        //获取到花名册信息
        roster = Global.conn.getRoster();
        //获取到所有的好友组
        Collection<RosterGroup> groups = roster.getGroups();
        for (RosterGroup group : groups) {
            //获取到每个组的好友
            Collection<RosterEntry> entries = group.getEntries();
            for (RosterEntry entry : entries) {
                //已经能拿到数据了
                // System.out.println("name" + entry.getName() + "user" + entry.getUser() + "status" + entry.getStatus());
                System.out.println(entry.toString());
                Friend friend = new Friend();
                friend.account = entry.getUser();
                friend.nick = entry.getName();
                saveOrUpdate(friend);
            }
        }
        /**
         * 获取列表的更新的Listener
         */
        //能监听成功---
        rosterListener = new RosterListener() {
            @Override
            public void entriesAdded(Collection<String> collection) {
                insertorUpdate(collection);
                System.out.println("一个用户被添加");
            }

            @Override
            public void entriesUpdated(Collection<String> collection) {
                //能监听成功---
                insertorUpdate(collection);
                System.out.println("更新了用户名");
            }

            @Override
            public void entriesDeleted(Collection<String> collection) {
                //能监听成功---
                for (String account : collection) {
                    getApplicationContext().getContentResolver().delete(ContactProvider.URI, ContactProvider.CONTACT_FIELD.ACCOUNT + "=?", new String[]{account});
                }
                System.out.println("删除了一个用户");
            }

            @Override
            public void presenceChanged(Presence presence) {

            }
        };
        roster.addRosterListener(rosterListener);
    }

    private void insertorUpdate(Collection<String> collection) {
        //能监听成功---
        for (String s : collection) {//这是个集合哇---
            RosterEntry entry = roster.getEntry(s);
            // System.out.println(entry.getName() + "---user" + entry.getUser());
            Friend friend = new Friend();
            friend.account = entry.getUser();
            friend.nick = entry.getName();
            saveOrUpdate(friend);
        }
    }

    /**
     * 更新或保存数据-->如果存在就更新，如果不存在就添加到数据库
     *
     * @param friend
     */
    private void saveOrUpdate(Friend friend) {
        ContentValues values = new ContentValues();
        values.put(ContactProvider.CONTACT_FIELD.ACCOUNT, friend.account);
        values.put(ContactProvider.CONTACT_FIELD.AVATAR, "1");//头像暂时不处理
        values.put(ContactProvider.CONTACT_FIELD.NICK, friend.nick);
        values.put(ContactProvider.CONTACT_FIELD.SORT, Change2PinYinUtils.getPinYin(StringSub.getString(friend.account)));
        //先进行一部更新，如果数据存在，更新，，如果数据不存在插入，
        int update = getApplicationContext().getContentResolver().update(ContactProvider.URI, values, ContactProvider.CONTACT_FIELD.ACCOUNT + "=?", new String[]{friend.account});
        if ((update < 1)) {//如果不存在数据，将该条数据插入到数据库
            getApplicationContext().getContentResolver().insert(ContactProvider.URI, values);
        }
    }

    @Override
    public void onDestroy() {
        //取消监听
        roster.removeRosterListener(rosterListener);
    }
}
