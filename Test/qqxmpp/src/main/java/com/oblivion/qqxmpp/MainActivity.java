package com.oblivion.qqxmpp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.oblivion.qqxmpp.domain.Friend;
import com.oblivion.qqxmpp.fragment.BaseFragment;
import com.oblivion.qqxmpp.fragment.FriendFragment;
import com.oblivion.qqxmpp.fragment.TakeFragment;
import com.oblivion.qqxmpp.global.Global;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by oblivion on 2016/11/11.
 */
public class MainActivity extends FragmentActivity {
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.tv_friend)
    TextView tvFriend;
    @Bind(R.id.tv_take)
    TextView tvTake;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    private List<BaseFragment> mList;
    private Roster roster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    /**
     * 两个条目的点击事件
     * 动态设定UI
     *
     * @param view
     */
    @OnClick({R.id.tv_friend, R.id.tv_take})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_friend:
                viewpager.setCurrentItem(0);
                break;
            case R.id.tv_take:
                viewpager.setCurrentItem(1);
                break;
        }
    }

    /**
     * 初始化View适配器，监听。。。
     */
    private void initView() {
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mList.get(position);
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(listener);
    }

    /**
     * 创建page滑动监听
     */
    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //更新UI
            changeUI(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * 更新UI
     *
     * @param position 所移动到的位置
     */
    private void changeUI(int position) {
        switch (position) {
            case 0:
                tvFriend.setEnabled(false);
                tvTake.setEnabled(true);
                tvTitle.setText("好友");
                break;
            case 1:
                tvFriend.setEnabled(true);
                tvTake.setEnabled(false);
                tvTitle.setText("会话");
                break;
        }
    }

    /**
     * 初始化数据,将fargment封装到List集合中
     */
    private void initData() {
        mList = new ArrayList<>();
        mList.add(new FriendFragment());
        mList.add(new TakeFragment());
    }
}
