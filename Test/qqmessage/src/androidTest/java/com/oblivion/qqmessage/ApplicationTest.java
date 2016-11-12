package com.oblivion.qqmessage;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.oblivion.qqmessage.bean.QQMessage;
import com.oblivion.qqmessage.bean.QQMessageType;
import com.oblivion.qqmessage.utils.CurrentTimeUtils;
import com.thoughtworks.xstream.XStream;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void test1() {
        QQMessage qqMessage = new QQMessage();
        qqMessage.type = QQMessageType.MSG_TYPE_CHAT_P2P;
        qqMessage.from = 123;
        qqMessage.to = 10086;
        qqMessage.content = "我请求成功了没";
        qqMessage.sendTime = CurrentTimeUtils.getTime();
        String xml = qqMessage.toXML();
        QQMessage msg2 = (QQMessage) qqMessage.fromXML(xml);
        System.out.println(msg2.content);
    }

    public void test2() {
        QQMessage msg = new QQMessage();
        msg.type = QQMessageType.MSG_TYPE_CHAT_P2P;
        msg.content = "---0---";
        msg.sendTime = CurrentTimeUtils.getTime();
        msg.from = 007;
        msg.to = 10086;
        String xml = msg.toXML();
        QQMessage msg2 = (QQMessage) msg.fromXML(xml);
        System.out.println(msg2.content);
    }
}