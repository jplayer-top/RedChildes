package com.oblivion.demo.preshenter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.oblivion.demo.R;
import com.oblivion.demo.model.IModel;
import com.oblivion.demo.model.impl.ImodelImpl;
import com.oblivion.demo.view.IShowView;

import org.w3c.dom.Text;

/**
 * Created by oblivion on 2016/11/16.
 */
public class DataPresenter {
    IModel mIModel;
    IShowView mIShowView;
    private Context context;
    private Handler handler = new Handler();
    private TextView view;

    public DataPresenter(IShowView mIShowView, Context applicationContext) {
        mIModel = new ImodelImpl();
        this.mIShowView = mIShowView;
        this.context = applicationContext;
    }

    public void doUiLogic() {
        if (mIModel.isConnectionNet()) {
            new Thread(new MyRunnableTask()).start();
        }
    }

    class MyRunnableTask implements Runnable {
        @Override
        public void run() {
            //传递回来的需要是同一个实例-----
            view = (TextView) mIShowView.showLoginSuccessView();
            //view.setVisibility(View.GONE);
            final String s = mIModel.getJsonObject();
            System.out.println("2");
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                    view.setText(s);
                }
            });
        }
    }
}
