package com.oblivion.redchildpuls.factory;


import com.oblivion.redchildpuls.proxy.ThreadPoolProxy;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/4.
 */
public class ThreadPoolFactory {
    private ThreadPoolFactory() {
    }

    private static ThreadPoolProxy proxy;

    public static ThreadPoolProxy getProxy() {
        if (proxy == null) {
            synchronized (ThreadPoolFactory.class) {
                if (proxy==null) {
                    proxy = new ThreadPoolProxy();
                }

            }
        }
        return proxy;
    }
}
