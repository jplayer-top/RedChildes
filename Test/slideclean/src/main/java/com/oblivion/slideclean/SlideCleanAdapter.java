package com.oblivion.slideclean;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.oblivion.ui.SlideClean;
import com.oblivion.utils.Cheeses;
import com.oblivion.utils.ToastUtils;

/**
 * Created by oblivion on 2016/10/29.
 */
public class SlideCleanAdapter extends BaseAdapter {
    /**
     * 条目状态为打开时，默认为false
     */
    private boolean isClean = false;
    /**
     * 根据回调得到的boolean类型值，得到是否打开,默认为false
     */
    private boolean currentState = false;
    private ListView v;

    /**
     * 构造函数携带自身View对象过来
     *
     * @param v
     */
    public SlideCleanAdapter(ListView v) {
        this.v = v;
    }

    @Override
    public int getCount() {
        return Cheeses.NAMES.length;
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        final SlideClean view;
        if (convertView == null) {
            view = (SlideClean) View.inflate(parent.getContext(), R.layout.activity_main, null);
        } else {
            view = (SlideClean) convertView;
        }
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_name.setText(Cheeses.NAMES[position]);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClean) {//如果为打开状态
                    ToastUtils.setToast(parent.getContext(), "清理");
                }
            }
        });
        //设定每个条目的监听
        view.setOnCleanStateListener(new SlideClean.onCleanState() {
            @Override
            public void onOpen(boolean flag) {//滑块为打开状态
                ToastUtils.setToast(parent.getContext(), "open");
                isClean = true;
                currentState = flag;
                view.setOpenState(currentState);
            }

            @Override
            public void onColse(boolean flag) {//滑块关闭状态
                ToastUtils.setToast(parent.getContext(), "close");
                isClean = false;
                currentState = flag;
            }

            @Override
            public void onDrag() {//滑动状态
                ToastUtils.setToast(parent.getContext(), "drag");
            }

        });
        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.setToast(v.getContext(),"wocao");
            }
        });

        return view;
    }
}
