package com.oblivion.getmd5;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(){
            @Override
            public void run() {
                try {
                    //新建一个字符串，用来获取byte[]数组
                    String str = "测试文字";
                    byte[] buffer = str.getBytes();
                    //获取以md5格式的MessageDigest
                    MessageDigest digest = MessageDigest.getInstance("md5");
                    byte[] newBuffer = digest.digest(str.getBytes());
                    //创建一个StringBuffer 用来存放字符
                    StringBuffer sb = new StringBuffer();
                    //增强for循环遍历得到的新数组
                    for (byte byt : newBuffer
                            ) {
                        //将byte数组转化为int 数；通过  &0xff
                        int number = byt&0Xff;
                        //在转化为十六进制字符串，如果是长度为一，将在前边添加一个“0”
                        String newStr = Integer.toHexString(number);
                        if(newStr.length()<=1){
                            sb.append("0");
                        }
                        sb.append(newStr);
                    }
                    //子线程更新UI
                    Looper.prepare();
                    Toast.makeText(MainActivity.this,sb.toString(), Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
