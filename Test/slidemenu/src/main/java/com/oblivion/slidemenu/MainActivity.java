package com.oblivion.slidemenu;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oblivion.ui.SlideMenu;
import com.oblivion.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {
    private TextView tv_news;

    private com.oblivion.ui.SlideMenu sliding_menu;

    private ImageView iv_showmenu;
    private boolean dragState = true;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_news = (TextView) findViewById(R.id.tv_news);
        sliding_menu = (SlideMenu) findViewById(R.id.sliding_menu);
        iv_showmenu = (ImageView) findViewById(R.id.iv_showmenu);
        iv_showmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //图片点击后触发改变状态
                sliding_menu.setStateChange(dragState);
            }
        });
        sliding_menu.setOnDragStateListener(new SlideMenu.onDragStateListener() {
            @Override
            public void onDragOpen() {
                ToastUtils.setToast(getApplicationContext(), "open");
                dragState = false;
                tv_news.setSelected(true);
                tv_news.setTextColor(Color.BLUE);
            }

            @Override
            public void onDragClose() {
                ToastUtils.setToast(getApplicationContext(), "close");
                dragState = true;
                tv_news.setSelected(false);
                tv_news.setTextColor(Color.WHITE);
            }
        });
        tv_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.setToast(getApplicationContext(), tv_news.getText().toString());
            }
        });
    }
}
