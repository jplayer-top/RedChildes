package com.oblivion.redchildpuls.holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;

import org.senydevpkg.base.BaseHolder;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoadMoreHolder extends BaseHolder <Integer>{

    public static final int LOADMORE_LOADING = 0;//正在加载更多
    public static final int LOADMORE_ERROR = 1;//加载更多失败,点击重试
    public static final int LOADMORE_NONE = 2;//没有加载更多

    @Bind(R.id.item_loadmore_container_loading)
    LinearLayout mItemLoadmoreContainerLoading;
    @Bind(R.id.item_loadmore_tv_retry)
    TextView mItemLoadmoreTvRetry;
    @Bind(R.id.item_loadmore_container_retry)
    LinearLayout mItemLoadmoreContainerRetry;

    public LoadMoreHolder(Context context) {
        super(context);
    }


    @Override
    protected View initView() {
        View holderView = View.inflate(MyApplication.mContext, R.layout.item_loadmore, null);
        //找孩子
        ButterKnife.bind(this, holderView);
        return holderView;
    }

    @Override
    public void bindData(Integer curState) {
        //首先隐藏所有的视图
        mItemLoadmoreContainerLoading.setVisibility(View.GONE);
        mItemLoadmoreContainerRetry.setVisibility(View.GONE);
        switch (curState) {
            case LOADMORE_LOADING:
                mItemLoadmoreContainerLoading.setVisibility(View.VISIBLE);
                break;
            case LOADMORE_ERROR:
                mItemLoadmoreContainerRetry.setVisibility(View.VISIBLE);
                break;
            case LOADMORE_NONE:

                break;

            default:
                break;
        }
    }
}
