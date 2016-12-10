package com.oblivion.redchildpuls.holder;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.bean.CategoryFilterBean;
import com.oblivion.redchildpuls.utils.PrefUtils;

import org.senydevpkg.base.BaseHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 周乐 on 2016/12/8.
 */
public class FilterHolder extends BaseHolder<CategoryFilterBean.ListFilterBean> {
    private static final String TAG = "FilterHolder";
    @Bind(R.id.tv_filter_key)
    TextView tvFilterKey;
    @Bind(R.id.tv_filter_value)
    TextView tvFilterValue;
    @Bind(R.id.iv_filter_goBuy)
    ImageView ivFilterGoBuy;

    public FilterHolder(Context mContext) {
        super(mContext);
    }

    @Override
    protected View initView() {
        View view = View.inflate(MyApplication.mContext, R.layout.item_filter, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void bindData(CategoryFilterBean.ListFilterBean data) {
       // if (!PrefUtils.getBoolean(MyApplication.mContext,"firstinterfilter",true)){
        //获取每个一级筛选的position
        int position = PrefUtils.getInt(MyApplication.mContext, data.key + "position", -1);
        if (position!=-1){
            String name = data.valueList.get(position).name;
            tvFilterValue.setTextColor(Color.RED);
            tvFilterValue.setText(name);
        }
       // }


        tvFilterKey.setText(data.key+":");

    }
}
