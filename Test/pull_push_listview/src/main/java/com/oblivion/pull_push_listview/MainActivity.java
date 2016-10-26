package com.oblivion.pull_push_listview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

import com.oblivion.ui.PullPushListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    /**
     * 在布局中找到控件
     */
    private PullPushListView pplv_test;
    private ArrayAdapter<String> adapter;
    private Handler handler = new Handler();
    private List<String> objects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pplv_test = (PullPushListView) findViewById(R.id.pplv_test);
        objects = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            objects.add("我今天吃了" + i + "碗饭");
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, objects);
        pplv_test.setAdapter(adapter);
        pplv_test.setOnRefreshStateListener(new PullPushListView.OnRefreshStateListener() {
            @Override
            public void OnRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        objects.add(0, "卧槽，刷新出来了");
                        adapter.notifyDataSetChanged();
                        pplv_test.refreshComplement();//通知控件刷新操作已经完成
                    }
                }, 3000);
            }
        });
        pplv_test.setOnLoadStateListener(new PullPushListView.onLoadStateListener() {
            @Override
            public void onLoadState() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        objects.add("卧槽，加载出来了");
                        adapter.notifyDataSetChanged();
                        pplv_test.loadComplement();//通知控件刷新操作已经完成
                    }
                }, 3000);
            }
        });
    }
}
