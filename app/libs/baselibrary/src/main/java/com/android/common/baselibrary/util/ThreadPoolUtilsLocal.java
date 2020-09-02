package com.android.common.baselibrary.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 */
public class ThreadPoolUtilsLocal {
    private static ScheduledExecutorService scheduledThreadPool = null;

    private static ScheduledExecutorService scheduledExecutorService = null;

    private static ExecutorService mCacheThreadExecutor = null;

    private static ExecutorService mCachedThreadPool = null;

    private static ThreadPoolUtilsLocal threadPoolUtils;

    public static ThreadPoolUtilsLocal newInstance() {
        return threadPoolUtils = SingleTon.single;
    }

    private static class SingleTon {
        private final static ThreadPoolUtilsLocal single = new ThreadPoolUtilsLocal();
    }

    public static ExecutorService newCachedThreadPool() {
        if (null == mCachedThreadPool || mCachedThreadPool.isShutdown() || mCachedThreadPool.isTerminated()) {
            mCachedThreadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                    60L, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>());
        }
        return mCachedThreadPool;
    }

    public static ExecutorService newSingleThreadPool() {
        return Executors.newSingleThreadExecutor();
    }

    public static ScheduledExecutorService newScheduledThreadPool() {
        return  Executors.newSingleThreadScheduledExecutor();
    }

    public static ScheduledExecutorService newScheduledThreadPool(int coreSize) {
        if (null == scheduledExecutorService || scheduledExecutorService.isShutdown() || scheduledThreadPool.isTerminated()) {
            scheduledExecutorService = Executors.newScheduledThreadPool(coreSize);
        }
        return scheduledExecutorService;
    }

    public static ExecutorService newCacheThreadExecutor() {
        if (null == mCacheThreadExecutor || scheduledExecutorService.isShutdown() || scheduledThreadPool.isTerminated()) {
            mCacheThreadExecutor = Executors.newCachedThreadPool();
        }
        return mCacheThreadExecutor;
    }

    public static void newCachedThreadPoolShutDownNow() {
        if (null != mCachedThreadPool) {
            mCachedThreadPool.shutdownNow();
        }
    }

}
