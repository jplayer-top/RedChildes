package com.oblivion.demo.view;

import android.view.View;

/**
 * .View是指显示数据并且和用户交互的层。在安卓中，它们可以是一个Activity，一个Fragment，一个android.view.View或者是一个Dialog。
 * Created by oblivion on 2016/11/16.
 */
public interface IShowView {
    /**
     * 登陆成功界面
     */
    View showLoginSuccessView();

    /**
     * 登陆进程中界面
     */
    void showProgressView();

    /**
     * 登陆失败界面
     */
    void showLoginError();
}
