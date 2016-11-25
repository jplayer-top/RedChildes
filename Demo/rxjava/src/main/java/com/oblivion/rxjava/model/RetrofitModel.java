package com.oblivion.rxjava.model;


import com.oblivion.rxjava.bean.HomeBeam;
import com.oblivion.rxjava.envity.MyEnvity;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/11/25.
 */
public class RetrofitModel {

    public RetrofitModel() {

    }

    public HomeBeam getResString(String etCity) {
        Retrofit retrofit = new Retrofit.Builder()
                //http://api.map.baidu.com/telematics/v3/weather?location=%E5%98%89%E5%85%B4&output=json&ak=qXYfeO4jG61DfcHeAiCbdIPKENySbvx1
                .baseUrl("http://api.map.baidu.com/telematics/v3/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyEnvity myEnvity = retrofit.create(MyEnvity.class);
        myEnvity.getCall("etCity")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//在主线程更新
                .subscribe(new Subscriber<HomeBeam>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        getHomeBean(e.toString(),null);
                    }

                    @Override
                    public void onNext(HomeBeam homeBeam) {
                       getHomeBean(null,homeBeam);
                    }
                });

        return null;
    }

    /**
     *  @param homeBeam
     * @param
     */
    public  HomeBeam getHomeBean(String error, HomeBeam homeBeam) {
        return homeBeam;
    }
}
