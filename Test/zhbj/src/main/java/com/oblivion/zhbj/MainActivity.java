package com.oblivion.zhbj;

import android.os.Bundle;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.oblivion.zhbj.fragment.LeftFragment;
import com.oblivion.zhbj.fragment.MainFragment;

/**
 * Created by oblivion on 2016/10/30.
 */
public class MainActivity extends SlidingFragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSlideMenu();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ll_main, new MainFragment());
        transaction.replace(R.id.rl_left, new LeftFragment());
        transaction.commit();

    }

    /**
     * 设定滑动界面
     */
    private void setSlideMenu() {
        SlidingMenu slidingMenu = getSlidingMenu();
        setBehindContentView(R.layout.left_menu);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffset(200);
    }
}
