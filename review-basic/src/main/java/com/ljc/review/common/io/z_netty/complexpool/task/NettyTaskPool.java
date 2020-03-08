package com.ljc.review.common.io.z_netty.complexpool.task;

import java.util.concurrent.*;

/**
 * 任务工具类：使用线程池并发地执行任务
 */
public class NettyTaskPool {

    /**
     * 线程池线程数量,对应CachedThreadPoolExecutor
     */
    private static final int CORE_POOL_SIZE = 0;
    private static final int MAX_POOL_SIZE = Integer.MAX_VALUE;

    //手动创建线程池
    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            3,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(),
            new ThreadPoolExecutor.DiscardOldestPolicy());

    //执行任务
    public static String submitTask(String message) throws ExecutionException, InterruptedException {
        Future<String> future = threadPool.submit(new ChannelTask(message));
        return future.get();
    }

}
