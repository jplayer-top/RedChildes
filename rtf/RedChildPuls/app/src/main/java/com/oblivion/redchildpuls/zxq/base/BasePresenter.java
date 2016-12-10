package com.oblivion.redchildpuls.zxq.base;

/**
 * Created by arkin on 2016/12/7.
 */
public class BasePresenter<T extends Contract.BaseContractView> {
    protected T view;
    public BasePresenter(T view) {
        this.view = view;
    }

    public void start() {}

    public void end() {
        view = null;
    }
}
