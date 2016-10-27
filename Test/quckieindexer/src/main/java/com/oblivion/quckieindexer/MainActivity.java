package com.oblivion.quckieindexer;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.oblivion.adapter.IndexAdapter;
import com.oblivion.doman.Person;
import com.oblivion.ui.QuckieIndex;
import com.oblivion.utils.Cheeses;
import com.oblivion.utils.Chinese2Spell;
import com.oblivion.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lv_names;
    private List<Person> personList;
    private com.oblivion.ui.QuckieIndex qiv_items;
    private TextView tv_show;
    private Handler handler = new Handler();
    private int Index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qiv_items = (QuckieIndex) findViewById(R.id.qiv_items);
        lv_names = (ListView) findViewById(R.id.lv_names);
        tv_show = (TextView) findViewById(R.id.tv_show);
        personList = new ArrayList<>();
        for (String name : Cheeses.NAMES) {
            Person person = new Person();
            person.setName(name);
            person.setPinyin(Chinese2Spell.teaC2E(name));
            personList.add(person);
        }
        //对personList排序
        Collections.sort(personList);
        System.out.println(personList.toString());
        lv_names.setAdapter(new IndexAdapter(personList));
        qiv_items.setOnIndexChangeListener(new QuckieIndex.OnIndexChangeListener() {
            @Override
            public void OnIndexChange(int cellIndex, final String showIndex) {
                tv_show.setVisibility(View.VISIBLE);
                tv_show.setText(showIndex);
                //动态隐藏
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handler.removeCallbacksAndMessages(null);
                        tv_show.setVisibility(View.GONE);
                    }
                }, 1000);
                Index = -1;
                for (int i = 0; i < personList.size(); i++) {
                    //找到移动到的地方,并将listView移动到该位置
                    if (personList.get(i).getPinyin().substring(0, 1).toUpperCase().equals(showIndex)) {
                        Index = i;
                        //设定到选定位置
                        lv_names.setSelection(Index);
                        return;
                    }
                }
            }

        });
    }
}
