package com.oblivion.mvp_fragment.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.oblivion.mvp_fragment.R;
import com.oblivion.mvp_fragment.adapter.FirstFragmentAdapter;
import com.oblivion.mvp_fragment.presenter.Presenter;
import com.oblivion.mvp_fragment.view.IShowView;

import java.util.List;

/**
 * Created by oblivion on 2016/11/18.
 */
public class FirstFragment extends Fragment implements IShowView {
    private static List<Integer> mLists;
    private Presenter presenter;
    private ListView listView;

    public static Fragment newInstance(List<Integer> mList) {
        mLists = mList;
        return new FirstFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, null);
        listView = (ListView) view.findViewById(R.id.lv_first);
        presenter = new Presenter(this);
        presenter.doLogic();
        return view;
    }

    @Override
    public void showNext() {

    }

    @Override
    public void showSuccess(int data) {
        //数据加载成功的话
        mLists.add(data);
        listView.setAdapter(new FirstFragmentAdapter(mLists));
    }

    @Override
    public void showError() {

    }
}
