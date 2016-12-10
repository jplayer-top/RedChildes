/*
 * Copyright 2016 XuJiaji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oblivion.redchildpuls.zxq.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 项目中Fragment的基类
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    protected T presenter;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        presenter = getPresenter();

        rootView = inflater.inflate(getLayoutId(), container, false);

        onInit();
        onListener();

        if (presenter != null) {
            presenter.start();
        } else {
            throw new RuntimeException("Presenter不能为空");
        }
        return rootView;
    }

    protected abstract T getPresenter();


    /**
     * 添加监听
     */
    protected void onListener() {

    }

    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected void onInit() {
    }

    public View getRootView() {
        return this.rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.end();
    }
}
