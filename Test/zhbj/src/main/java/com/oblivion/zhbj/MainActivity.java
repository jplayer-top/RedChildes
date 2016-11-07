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

    private SlidingMenu slidingMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSlideMenu();
        //用两个Fragment代替数据填充到MainActivity中
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ll_main, new MainFragment(),"MAIN");
        transaction.replace(R.id.rl_left, new LeftFragment(),"LEFT");
        transaction.commit();
    }

    /**
     * 设定滑动界面
     */
    private void setSlideMenu() {
        slidingMenu = getSlidingMenu();
        setBehindContentView(R.layout.left_menu);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        slidingMenu.setBehindOffset(200);
    }

    /**
     * 返回一个slidemenu
     * @return
     */
    public SlidingMenu sendSlidingMenu(){
        return slidingMenu;
    }

}
