package com.oblivion.mvp_fragment.model.impl;

import android.os.SystemClock;

import com.oblivion.mvp_fragment.model.IModelGetData;

/**
 * Created by oblivion on 2016/11/18.
 */
public class IModelGetDataImpl implements IModelGetData {
    @Override
    public String getData() {
        SystemClock.sleep(2000);
        return "撩妹中,勿打扰...";
    }
}
