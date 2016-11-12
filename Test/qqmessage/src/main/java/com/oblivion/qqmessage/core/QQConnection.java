package com.oblivion.qqmessage.core;

import com.oblivion.qqmessage.bean.QQMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oblivion on 2016/11/9.
 */
public class QQConnection extends Thread {
    //1.连接到服务端  2.发送消息 3.接收消息 4.断开与服务器的连接
    //继承Thread为了方便开启线程
    private Socket socket = null;
    private String host;
    private int port;
    private DataInputStream reader;
    private DataOutputStream writer;
    private boolean flag;

    public QQConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 监听接口
     */
    public interface OnQQMessageReceiverListener {
        void OnQQMessageReceiver(QQMessage msg);
    }

    public List<OnQQMessageReceiverListener> Listeners = new ArrayList();

    /**
     * 添加监听
     *
     * @param listener
     */
    public void addOnQQMessageReceiverListener(OnQQMessageReceiverListener listener) {
        Listeners.add(listener);
    }

    public void removeQQMessageReceiverListener(OnQQMessageReceiverListener listener) {
        Listeners.remove(listener);
    }

    public OnQQMessageReceiverListener listener;

    /**
     * 连接服务端
     */
    public void connection() throws Exception {

        if (socket == null) {
            socket = new Socket(host, port);
        }
        reader = new DataInputStream(socket.getInputStream());
        writer = new DataOutputStream(socket.getOutputStream());
        flag = true;//开启服务的时候开启接收消息，并且设定falg = true
        start();
    }

    /**
     * 接收消息
     */
    @Override
    public void run() {
        while (flag) {
            try {
                String xml = reader.readUTF();
                for (OnQQMessageReceiverListener onQQMessageReceiverListener : Listeners) {
                    if (onQQMessageReceiverListener != null) {
                        QQMessage msg = new QQMessage();
                        msg = (QQMessage) msg.fromXML(xml);//获取到msg
                        onQQMessageReceiverListener.OnQQMessageReceiver(msg);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                disConnection();
            }
        }
    }

    /**
     * 断开与服务器的链接
     */
    public void disConnection() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                socket = null;
            }
            try {
                if (writer!= null){
                    writer.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                writer = null;
            }
            try {
                if(reader != null){
                reader.close();

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                reader = null;
            }
        }
        flag = false;//取消掉链接设定为false
    }

    /**
     * 发送消息
     *
     * @param msg
     * @throws Exception
     */
    public void sendMessage(QQMessage msg) throws Exception {
        writer.writeUTF(msg.toXML());
        writer.flush();
    }


}
