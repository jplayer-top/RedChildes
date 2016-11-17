package com.oblivion.demo.model.impl;

import android.os.SystemClock;

import com.oblivion.demo.model.IModel;

/**
 * Created by oblivion on 2016/11/16.
 */
public class ImodelImpl implements IModel {
    @Override
    public boolean isConnectionNet() {
        //网络是否链接成功，，这里测试返回true
        return true;
    }

    @Override
    public String getJsonObject() {
        //_____测试返回假数据
        SystemClock.sleep(2000);
        return "MVP";
    }
}
