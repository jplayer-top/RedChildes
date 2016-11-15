package com.oblivion.qqxmpp.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.oblivion.qqxmpp.R;
import com.oblivion.qqxmpp.provider.ContactProvider;

/**
 * Created by oblivion on 2016/11/11.
 */
public class TakeFragment extends BaseFragment {

    @Override
    public View createViewSaveData(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_take, null);

        return view;
    }


}
