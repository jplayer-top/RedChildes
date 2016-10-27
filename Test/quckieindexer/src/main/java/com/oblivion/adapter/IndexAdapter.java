package com.oblivion.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.oblivion.doman.Person;
import com.oblivion.quckieindexer.R;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by oblivion on 2016/10/27.
 */
public class IndexAdapter extends BaseAdapter {
    private List<Person> persons;

    public IndexAdapter(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public int getCount() {

        return persons.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = View.inflate(parent.getContext(), R.layout.item_person, null);
        } else {
            view = convertView;
        }
        TextView tv_pinyin = (TextView) view.findViewById(R.id.tv_pinyin);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        Person person = persons.get(position);
        String firstChar = person.getPinyin().substring(0, 1).toUpperCase();
        if (position != 0) {
            lastPosition = position - 1;
            String lastFistChar = persons.get(lastPosition).getPinyin().substring(0, 1).toUpperCase();
            if (TextUtils.equals(lastFistChar, firstChar)) {
                tv_pinyin.setVisibility(View.GONE);
            }else{
                tv_pinyin.setVisibility(View.VISIBLE);
            }
        }else {
            tv_pinyin.setVisibility(View.VISIBLE);
        }
        tv_pinyin.setText(firstChar + "");
        tv_name.setText(person.getName() + "");
        return view;
    }
}
