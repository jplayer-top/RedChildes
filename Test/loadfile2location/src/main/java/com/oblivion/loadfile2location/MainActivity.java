package com.oblivion.loadfile2location;

import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText et_path;
    private ProgressBar pb1;
    private ProgressBar pb2;
    private ProgressBar pb3;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_path = (EditText) findViewById(R.id.et_path);
        pb1 = (ProgressBar) findViewById(R.id.pb1_progress);
        pb2 = (ProgressBar) findViewById(R.id.pb2_progress);
        pb3 = (ProgressBar) findViewById(R.id.pb3_progress);
    }

    public void load(View view) {
        path = et_path.getText().toString().trim();
        new Thread() {
            @Override
            public void run() {
                try {
                    //获取URL
                    URL url = new URL(path);
                    //与服务器创建链接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //设定返回时长，设定请求方法，以及获取返回的状态码
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        //获取要下载的文件的长度
                        int length = conn.getContentLength();
                        //在本地创建随机输入流，为了colon一个有相同长度的文件，此时文件中有长度但是全为空
                        RandomAccessFile raf = new RandomAccessFile(Environment.getExternalStorageDirectory() + "/xxx.apk", "rw");
                        raf.setLength(length);
                        //关闭流，防止OOM异常
                        raf.close();
                        int ThreadNum = 3;
                        //实现多线程下载，需要设定每一个线程要下载的起点与终点
                        int blackSize = (length / ThreadNum);
                        for (int i = 0; i < ThreadNum; i++) {
                            int startThread = i * (blackSize);
                            int endThread = (i + 1) * blackSize - 1;
                            if (i == ThreadNum - 1) {
                                endThread = (int) (length - 1);
                            }
                            //创建线程开始以设定的起点终点
                            new downThread(startThread, endThread, i).start();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }.start();
    }

    /**
     * 类继承Thread 创建构造函数，创建该线程时传入起点终点以及线程ID
     */
    public class downThread extends Thread {
        private int startThread;
        private int endThread;
        private int ThreadID;

        /**
         * 线程的构造函数
         * @param startThread 开始的位置
         * @param endThread  结束的位置
         * @param i 哪个线程
         */
        public downThread(int startThread, int endThread, int i) {
            this.startThread = startThread;
            this.endThread = endThread;
            this.ThreadID = i;
            System.out.println(startThread + "---" + endThread + "---" + i);
        }

        ;

        @Override
        public void run() {
            try {

                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //查看文件是否存在
                File oldfile = new File(Environment.getExternalStorageDirectory(), "/" + ThreadID + ".position");
                int currentIndex = 0;
                if (oldfile.exists()) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(oldfile)));
                    String Index = br.readLine();
                    br.close();
                    System.out.println(Index);
                    //设定当前进度是读取到的进度
                    currentIndex = Integer.valueOf(Index);
                }
                //告诉服务器 只想下载资源的一部分
                conn.setRequestProperty("Range", "bytes=" + startThread + "-" + endThread);
                InputStream is = conn.getInputStream();
                byte[] buffer = new byte[1024];
                int len = -1;
                RandomAccessFile raf = new RandomAccessFile(Environment.getExternalStorageDirectory() + "/xxx.apk", "rw");
                //设定好流的开始结束位置之后必须设定raf的seek位置
                raf.seek(currentIndex);
                //断点下载的指针位置
                File file = new File(Environment.getExternalStorageDirectory(), "/" + ThreadID + ".position");
                //如果文件不存在就创建新的文件
                if (!file.exists()) {
                    file.createNewFile();
                }
                while ((len = is.read(buffer)) != -1) {
                    //把每个线程下载的数据放在自己的空间里面.
                    raf.write(buffer, 0, len);
                    currentIndex += len;
                    //设定rwd格式是每次写入数据后就能存放到所指定的文件中
                    RandomAccessFile positionraf = new RandomAccessFile(file.getAbsolutePath(), "rwd");
                    //将指针位置存放到文件中，关闭流，防止OOM
                    positionraf.write((startThread + currentIndex - 1 + "").getBytes());
                    // System.out.println(startThread+currentIndex);
                    positionraf.close();
                    SystemClock.sleep(500);
                    //设定进度条
                    switch (ThreadID) {
                        case 0:
                            pb1.setMax(endThread - startThread);
                            pb1.setProgress(currentIndex);
                            break;
                        case 1:
                            pb2.setMax(endThread - startThread);
                            pb2.setProgress(currentIndex);

                            break;
                        case 2:
                            pb3.setMax(endThread - startThread);
                            pb3.setProgress(currentIndex);
                            break;
                    }
                }
                raf.close();
                is.close();
                System.out.println("线程:" + ThreadID + "下载完毕了...");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
