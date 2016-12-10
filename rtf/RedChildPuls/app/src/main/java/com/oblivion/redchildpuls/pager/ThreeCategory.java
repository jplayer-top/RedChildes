/*
package com.oblivion.redchildpuls.pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.bean.CatetoryResponse;

import java.util.List;

*/
/**
 * 三级分类页
 * Created by qinyin on 2016/12/4.
 *//*

public class ThreeCategory extends BaseCategoryPager {
    public ThreeCategory(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public void initData() {
        category_list_view.setAdapter(new FirstCategoryAdapter());
    }
*/
/**
 * 三级分类适配器
 *//*

    class FirstCategoryAdapter extends BaseAdapter{
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=View.inflate(parent.getContext(), R.layout.item_list_category2,null);
        TextView category_title = (TextView) view.findViewById(R.id.category_title);
        return view;
    }
}
}
*/
