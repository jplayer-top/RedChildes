package com.oblivion.redchildpuls.proxy;


import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/4.
 */
public class ThreadPoolProxy {
    private ThreadPoolExecutor executor;

//    private ThreadPoolProxy() {
//    }

    private void initExecutor() {
        int corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
        int maximumPoolSize = corePoolSize;
        long keepAliveTime = 1;
        TimeUnit unit = TimeUnit.HOURS;
        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
        if (executor == null || executor.isTerminated() || executor.isShutdown()) {
            synchronized (ThreadPoolProxy.class) {
                if (executor == null || executor.isTerminated() || executor.isShutdown()) {
                    executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime
                            , unit, workQueue, threadFactory, handler);
                }
            }

        }
    }

    public void submit(Runnable task) {
        initExecutor();
        executor.submit(task);
    }


    public void excute(Runnable task) {
        initExecutor();
        executor.execute(task);
    }

    public void remove(Runnable task) {
        initExecutor();
        executor.remove(task);
    }
}
