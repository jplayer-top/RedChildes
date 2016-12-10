package com.oblivion.redchildpuls.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by nick on 2016/12/7.
 */
public class NoScorllListView extends ListView {
    public NoScorllListView(Context context) {
        this(context,null,0);
    }

    public NoScorllListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NoScorllListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //设置禁止listview 滑动
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
