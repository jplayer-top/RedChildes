package com.oblivion.qqxmpp.server;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.oblivion.qqxmpp.global.Global;
import com.oblivion.qqxmpp.provider.ChatProvider;
import com.oblivion.qqxmpp.utils.Change2PinYinUtils;
import com.oblivion.qqxmpp.utils.CurrentTimeUtils;
import com.oblivion.qqxmpp.utils.StringSub;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

/**
 * Created by oblivion on 2016/11/12.
 */
public class ChatServer extends Service {

    private ChatManager chatManager;
    private ChatManagerListener chatManagerListener;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //不必放在子线程中，，，但是如果彻底退出程序会造成一致连接不上服务器，
    //点击登陆之后可以考虑将聊天的服务都放在一直运行的服务中，这样就能一直保持连接
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("聊天服务被创建");
        chatManager = Global.conn.getChatManager();
        chatManagerListener = new ChatManagerListener() {
            @Override
            public void chatCreated(Chat chat, boolean b) {
                if (!b) {//不明白为嘛这句话就打印一次。。
                    //因为没有放在监听器里边吧
                    System.out.println("---别人与该账号聊天中");
                    chat.addMessageListener(new MessageListener() {
                        @Override
                        public void processMessage(Chat chat, Message message) {
                            //传送过来的数据需要裁剪-----到时候toXML看一下。
                            saveOrUpdate(message);
                        }
                    });
                }
            }
        };
        chatManager.addChatListener(chatManagerListener);
    }

    /**
     * 插入接收到的消息数据
     *
     * @param message
     */
    public void saveOrUpdate(Message message) {
        ContentValues values = new ContentValues();
        values.put(ChatProvider.SMS.FROM_ID, StringSub.getFromOrSession(message.getFrom()));
        values.put(ChatProvider.SMS.FROM_AVATAR, "0");
        values.put(ChatProvider.SMS.FROM_NICK, Change2PinYinUtils.getNick(message.getFrom()));
        values.put(ChatProvider.SMS.BODY, message.getBody());
        values.put(ChatProvider.SMS.TYPE, message.getType() + "");
        values.put(ChatProvider.SMS.TIME, CurrentTimeUtils.getTime());
        values.put(ChatProvider.SMS.SESSION_ID, StringSub.getFromOrSession(message.getFrom()));
        values.put(ChatProvider.SMS.SESSION_NAME, Change2PinYinUtils.getNick(message.getFrom()));
        values.put(ChatProvider.SMS.UNREAD, "1");
        // int update = getContentResolver().update(ChatProvider.SMS_URI, values, ChatProvider.SMS.SESSION_ID + "=?", new String[]{message.getTo()});
        getContentResolver().insert(ChatProvider.SMS_URI, values);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        chatManager.removeChatListener(chatManagerListener);
    }
}
