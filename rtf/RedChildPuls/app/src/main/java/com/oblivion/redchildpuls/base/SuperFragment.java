package com.oblivion.redchildpuls.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.oblivion.redchildpuls.activity.MainActivity;

import butterknife.ButterKnife;

/**
 * 1,设置布局文件,重写 onCreateView 方法,返回一个 View
 * 2,通过 findViewById 找到感兴趣的控件
 * 3,对控件进行数据初始化的设置,listView 设置 adapter ,textView 设置 Text,ImageView 设置 Image
 * 4,对控件进行用户交互的处理,button 设置 onclickListener, listview 设置 onItemClickListener
 * 5,获取网络数据,解析网络数据,展示网络数据
 */
public abstract class SuperFragment extends BaseFragment {

    protected MainActivity mMainActivity;
    private View mViewRoot;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMainActivity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //判断为空，为空就去加载布局，onCreateView在界面切换的时候会被多次调用,防止界面跳转回来的时候显示空白
        if (mViewRoot == null) {
            mViewRoot = createView(inflater, container, savedInstanceState);
            ButterKnife.bind(this, mViewRoot);
            initData();
            handleUserInput();
            requestNetData();
        }
        loadData();
        return mViewRoot;
    }

    protected abstract void loadData();


    /**
     * 创建 fragment 对应的 View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    abstract protected View createView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState);

       /**
     * 初始化控件的数据
     */
    protected abstract void initData();

    /**
     * 处理用户输入
     */
    protected abstract void handleUserInput();

    /**
     * 请求网络数据
     */
    protected abstract void requestNetData();


    //当前的界面被切换出去的时候被调用,解决ViewGroup只有一个子View的bug
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mViewRoot != null) {
            ViewParent parent = mViewRoot.getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) parent;
                viewGroup.removeView(mViewRoot);
            }
        }else {
            
        }
    }


}
