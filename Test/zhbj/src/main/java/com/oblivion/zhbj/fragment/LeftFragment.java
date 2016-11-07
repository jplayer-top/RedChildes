package com.oblivion.zhbj.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.oblivion.zhbj.MainActivity;
import com.oblivion.zhbj.R;
import com.oblivion.zhbj.base.impl.MainNewsCenterPager;
import com.oblivion.zhbj.domain.NewsMenuMessage;

import java.util.List;

/**
 * Created by oblivion on 2016/10/30.
 */
public class LeftFragment extends BaseFragment {

    private ListView lv_items;
    private int mCurrentItem;
    private List<NewsMenuMessage.DataBean> dataBeen;
    private NewsAdapter mAdapter;

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_left, null);
        lv_items = (ListView) view.findViewById(R.id.lv_news_items);
        return view;
    }

    @Override
    public void initDate() {

    }

    /**
     * 从NewsMenuMessage获取到数据
     *
     * @param newsMenuMessage
     */
    public void getNewsMenus(NewsMenuMessage newsMenuMessage) {
//        System.out.println(newsMenuMessage.getData().get(1).getTitle());
        dataBeen = newsMenuMessage.getData();
        mCurrentItem = 0;
        mAdapter = new NewsAdapter();
        lv_items.setAdapter(mAdapter);
        //条目点击事件
        lv_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //记录当前点击的位置
                mCurrentItem = position;
                System.out.println(mCurrentItem + "----");
                //点击后更新一下,更新完成后发送对象
                mAdapter.notifyDataSetChanged();
                //得到主滑动界面的对象
                MainActivity mainActivity = (MainActivity) mActivity;
                MainFragment mainFragment = (MainFragment) mainActivity.getSupportFragmentManager().findFragmentByTag("MAIN");
                MainNewsCenterPager mainNewsCenterPager = mainFragment.getnewsCenterPager();
                mainNewsCenterPager.captureCurrentItem(mCurrentItem);
                //修改点击完成后的状态
                toggle();
            }
        });
    }

    //控制侧边栏的开关
    protected void toggle() {
        MainActivity mainUI = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        slidingMenu.toggle();//如果当前为开, 则关;反之亦然
    }

    /**
     * 适配器
     */
    class NewsAdapter extends BaseAdapter {

        public int getCount() {
            return dataBeen.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mActivity, R.layout.item_leftfragment_listview, null);
            TextView tv = (TextView) view.findViewById(R.id.tv_item_news);
            tv.setText(dataBeen.get(position).getTitle());
            if (mCurrentItem == position) {
                tv.setEnabled(true);
            } else {
                tv.setEnabled(false);
            }
            return view;
        }
    }
}
