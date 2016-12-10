package com.oblivion.redchildpuls.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.bean.BrandsBean;

import org.senydevpkg.base.AbsBaseAdapter;
import org.senydevpkg.base.BaseHolder;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 周乐 on 2016/12/6.
 */
public class BrandHolder extends BaseHolder<BrandsBean.BrandBean> {

    @Bind(R.id.tv_item_brand)
    TextView tvItemBrand;
    @Bind(R.id.gv_item_brand)
    GridView gvItemBrand;

    public BrandHolder(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        View view = View.inflate(getContext(), R.layout.item_brand, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void bindData(BrandsBean.BrandBean data) {

        tvItemBrand.setText(data.key);
        List<BrandsBean.BrandBean.ValueBean> beanList = data.value;
        //在这里可以修改数据
       // if ()
        gvItemBrand.setAdapter(new BrandGriadViewAdapter(beanList));
    }

    class BrandGriadViewAdapter extends AbsBaseAdapter{

        /**
         * 接收AbsListView要显示的数据
         *
         * @param data 要显示的数据
         */
        public BrandGriadViewAdapter(List data) {
            super(data);
        }

        @Override
        protected BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BrandGriadViewHolder(MyApplication.mContext);
        }
    }
}
