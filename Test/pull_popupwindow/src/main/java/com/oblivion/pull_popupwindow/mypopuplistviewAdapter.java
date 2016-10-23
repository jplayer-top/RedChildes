package com.oblivion.pull_popupwindow;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oblivion on 2016/10/22.
 */
public class mypopuplistviewAdapter extends BaseAdapter {
    private List<String> list;

    public mypopuplistviewAdapter(List<String> list) {
        this.list = list;
        for (int i = 0; i < 20; i++) {
            list.add("10000" + i);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder holder = null;
        if (convertView == null) {
            view = View.inflate(parent.getContext(), R.layout.popupweidow, null);
            holder = new ViewHolder();
            holder.ib_button = (ImageButton) view.findViewById(R.id.ib_delete);
            holder.tv_phonenum = (TextView) view.findViewById(R.id.tv_select);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.ib_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.tv_phonenum.setText(list.get(position));
        return view;
    }

    class ViewHolder {
        TextView tv_phonenum;
        ImageButton ib_button;
    }
}
