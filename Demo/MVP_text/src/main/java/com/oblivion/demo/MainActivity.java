package com.oblivion.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.oblivion.demo.preshenter.DataPresenter;
import com.oblivion.demo.view.IShowView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements IShowView {

    private DataPresenter mDataPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDataPresenter = new DataPresenter(this, getApplicationContext());
        init();
    }

    private void init() {
        mDataPresenter.doUiLogic();
    }

    /**
     * 当界面可见时或者onStart()好像都可以跑成功 创建
     */
    @Override
    protected void onStart() {
        super.onStart();
//        mDataPresenter.doUiLogic();
    }

    @Override
    public View showLoginSuccessView() {
        System.out.println("1");
        return (TextView) findViewById(R.id.tv_lll);
    }

    @Override
    public void showProgressView() {

    }

    @Override
    public void showLoginError() {

    }
}
