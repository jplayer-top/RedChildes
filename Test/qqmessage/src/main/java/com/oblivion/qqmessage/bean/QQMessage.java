package com.oblivion.qqmessage.bean;

import com.oblivion.qqmessage.utils.CurrentTimeUtils;

/**
 * Created by oblivion on 2016/11/9.
 */
public class QQMessage extends ProtocolObj {
    public String type = QQMessageType.MSG_TYPE_CHAT_P2P;// 类型的数据 chat login
    public int from = 0;// 发送者 account
    public String fromNick = "qq";// 昵称
    public int fromAvatar = 1;// 头像
    public int to = 0; // 接收者 account
    public String content = ""; // 消息的内容  约不?
    public String sendTime = CurrentTimeUtils.getTime(); // 发送时间
}
