package com.oblivion.redchildpuls.base;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;

import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.factory.ThreadPoolFactory;
import com.oblivion.redchildpuls.holder.LoadMoreHolder;

import org.senydevpkg.base.AbsBaseAdapter;
import org.senydevpkg.base.BaseHolder;

import java.util.List;

/**
 * Created by 周乐 on 2016/12/6.
 */
public abstract class MyAdapter<T> extends AbsBaseAdapter {

    public static final int VIEWTYPE_LOADMORE = 0;
    public static final int VIEWTYPE_NORMAL = 1;

    private BaseHolder holder;
    public LoadMoreHolder mLoadMoreHolder;
    private LoadMoreTask mLoadMoreTask;

    /**
     * 接收AbsListView要显示的数据
     *
     * @param data 要显示的数据
     */
    public MyAdapter(List data) {
        super(data);
    }

    @Override
    public int getCount() {
        return super.getCount()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return VIEWTYPE_LOADMORE;
        } else {
            return VIEWTYPE_NORMAL;
        }
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount()+1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int curViewType = getItemViewType(position);
        if (convertView == null) {
            if (curViewType == VIEWTYPE_LOADMORE) {//当前的条目是加载更多类型
                holder = getLoadMoreHolder();
            } else {//当前的条目是普通的类型
                //创建holer对象
                holder = onCreateViewHolder(parent,getItemViewType(position));
            }
        } else {
            holder = (BaseHolder) convertView.getTag();
        }
            /*--------------- 得到数据,然后绑定数据 ---------------*/
        if (curViewType == VIEWTYPE_LOADMORE) {//当前的条目是加载更多类型
            if (hasLoadMore()) {
                //显示正在加载更多的视图
                mLoadMoreHolder.bindData(LoadMoreHolder.LOADMORE_LOADING);

                //触发加载更多的数据
                triggerLoadMoreData();

            } else {
                //隐藏加载更多的视图,以及重试视图
                mLoadMoreHolder.bindData(LoadMoreHolder.LOADMORE_NONE);
            }
        } else {
            Object data = (T) mData.get(position);
            holder.bindData(data);
        }

        return holder.rootView;//其实这个convertView是经过了数据绑定的convertView
    }

    /**
     * 触发加载更多的数据
     */
    private void triggerLoadMoreData() {
        if (mLoadMoreTask == null) {

            //加载之前显示正在加载更多
            int state = LoadMoreHolder.LOADMORE_LOADING;
            mLoadMoreHolder.bindData(state);


            //异步加载
            mLoadMoreTask = new LoadMoreTask();
            ThreadPoolFactory.getProxy().submit(mLoadMoreTask);
        }
    }

    class LoadMoreTask implements Runnable {
        private static final int PAGESIZE = 15;//每页请求的总数

        @Override
        public void run() {
            SystemClock.sleep(2000);
            loadMoreAndRefeshUi();
            mLoadMoreTask = null;
        }


    }


    /**
     * 具体加载更多数据及刷新ui
     */
    public  void loadMoreAndRefeshUi() {}

    /**
     * @return
     * @des 属于BaseHolder的子类对象
     * @des 加载更多的Holder的对象
     */
    private LoadMoreHolder getLoadMoreHolder() {
        if (mLoadMoreHolder == null) {
            mLoadMoreHolder = new LoadMoreHolder(MyApplication.mContext);
        }
        return mLoadMoreHolder;
    }

    /**
     * @return
     * @des 是否有加载更多, 默认没有加载更多
     * @des 子类可以覆写该方法, 可以决定有加载跟多
     */
    public boolean hasLoadMore() {
        return false;//默认没有加载更多
    }

}
