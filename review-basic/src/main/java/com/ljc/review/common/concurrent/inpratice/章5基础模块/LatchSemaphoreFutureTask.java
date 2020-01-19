package com.ljc.review.common.concurrent.inpratice.章5基础模块;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

/**
 * 同步工具类
 * 另见Barrier/ConcurrentCollections
 */
public class LatchSemaphoreFutureTask {

    /**
     * 闭锁
     * Latch:在闭锁到达结束状态之前，没有任何线程能通过；当达到结束状态后，允许所有线程通过。  关注点是状态
     * 闭锁是一次性对象，一旦进入终止状态就不能被重置
     */
    public void latch(int nThreads, final Runnable task) throws InterruptedException {
        //示例：创建若干线程，等待某件事情发生或某个状态达成之后执行任务
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);
        for (int i = 0; i < nThreads; i++) {
            new Thread(() -> {
                try {
                    startGate.await();   //所有线程等在起始点 注：这并不能保证所以线程同时执行，因为闭锁打开时可能有的线程尚未初始化。这是Barrier的功能
                    try {
                        task.run();
                    } finally {
                        endGate.countDown();  //完成后递减任务计数器
                    }
                } catch (InterruptedException ignored) {
                }
            }).start();
        }
        //DoSomething....
        startGate.countDown();   //触发任务执行
        endGate.await();  //等待所有任务执行完毕
    }

    /**
     * 信号量 Semaphore
     * 示例：使用信号量为容器设置边界
     */
    private static class MySemaphore<T> {
        private final Set<T> set;
        private final Semaphore semaphore;

        public MySemaphore(int bound) {
            set = Collections.synchronizedSet(new HashSet<>());
            semaphore = new Semaphore(bound);
        }

        public boolean add(T o) throws InterruptedException {
            semaphore.acquire();  //为当前线程获取一个许可，如果没有了，阻塞直到下一个许可可用或者线程被中断
            boolean wasAdded = false;
            try {
                wasAdded = set.add(o);
                return wasAdded;
            } finally {
                if (!wasAdded) {
                    semaphore.release();  //如果没有添加成功，释放这个许可信号
                }
            }
        }

        public boolean remove(T o) {
            boolean wasRemoved = set.remove(o);
            if (wasRemoved) {
                semaphore.release();
            }
            return wasRemoved;
        }
    }

    /**
     * FutureTask也可用于闭锁
     * 示例：通过FutureTask提前加载数据
     */
    private class MyFuture {

        private final FutureTask<ProductInfo> future = new FutureTask<>(this::loadProductInfo);
        private final Thread thread = new Thread(future);

        ProductInfo loadProductInfo() throws DataLoadException {
            return null;
        }

        public void start() {
            thread.start();
        }

        public ProductInfo get() throws DataLoadException, InterruptedException {
            try {
                return future.get();
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                if (cause instanceof DataLoadException) {
                    throw (DataLoadException) cause;
                } else {
                    throw new RuntimeException("");
                }
            }
        }

        private class ProductInfo {
        }

        private class DataLoadException extends Exception {
        }
    }

}























