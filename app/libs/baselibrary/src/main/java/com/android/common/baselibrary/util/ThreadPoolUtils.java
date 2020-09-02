package com.android.common.baselibrary.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池工具类
 */
public class ThreadPoolUtils {
    private static int count = 0;
    private static RejectedExecutionHandler handler = new RejectedExecutionHandler() {
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if (ThreadPoolUtils.count == 0) {
                //SdLogUtil.writeSystemLog("队列任务太长，丢掉多余任务");
            }
            ThreadPoolUtils.addCount();
            if (ThreadPoolUtils.count == 10) {
                ThreadPoolUtils.count = 0;
            }
        }
    };
    private static ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger integer = new AtomicInteger();

        public Thread newThread(Runnable r) {
            return new Thread(r, "myThreadPool thread:" + this.integer.getAndIncrement());
        }
    };
    private static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue(1000);
    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 30, (long) 120, TimeUnit.SECONDS, workQueue, threadFactory, handler);


    static int addCount() {
        int i = count;
        count = i + 1;
        return i;
    }

    private ThreadPoolUtils() {
    }

    public static void execute(Runnable runnable) {
        threadPool.execute(runnable);
    }

    public static <T> Future<T> submit(Callable<T> call) {
        if (threadPool != null) {
            return threadPool.submit(call);
        }
        return null;
    }

}
