package com.oblivion.rxjava.envity;


import com.oblivion.rxjava.bean.HomeBeam;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/11/22.
 */
public interface MyEnvity {
    @GET("weather?ak=qXYfeO4jG61DfcHeAiCbdIPKENySbvx1&output=json")
    Observable<HomeBeam> getCall(@Query("location") String path);
}
