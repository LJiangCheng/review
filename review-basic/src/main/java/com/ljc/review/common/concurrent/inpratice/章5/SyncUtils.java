package com.ljc.review.common.concurrent.inpratice.章5;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 同步工具类
 */
public class SyncUtils {

    /**
     * 闭锁
     * Latch:在闭锁到达结束状态之前，没有任何线程能通过；当达到结束状态后，允许所有线程通过。  关注点是状态
     */
    public void latch(int nThreads,final Runnable task) throws InterruptedException {
        //示例：创建若干线程，等待某件事情发生或某个状态达成之后执行任务
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);
        for(int i=0;i<nThreads;i++) {
            new Thread(() -> {
                try {
                    startGate.await();   //所有线程等在起始点 注：这并不能保证所以线程同时执行，因为闭锁打开时可能有的线程尚未初始化。这是Barrier的功能
                    try {
                        task.run();
                    } finally {
                        endGate.countDown();  //完成后递减任务计数器
                    }
                } catch (InterruptedException ignored) {}
            }).start();
        }
        //DoSomething....
        startGate.countDown();   //触发任务执行
        endGate.await();  //等待所有任务执行完毕
    }

    /**
     * 栅栏
     */
    @Test
    public void barrier() throws InterruptedException {

    }

    /**
     * 手动实现类似栅栏效果
     * TODO 思考，有哪些隐藏的问题？和Jdk的实现相比又怎样的不同？
     */
    @Test
    public void myBarrier() throws InterruptedException {
        final CountDownLatch endGate = new CountDownLatch(10);
        final AtomicInteger count = new AtomicInteger(10);
        for(int i=0;i<10;i++) {
            new Thread(() -> {
                count.getAndDecrement();
                for(;;) {
                    int cur = count.get();
                    if (cur == 0) {
                        break;
                    }
                }
                System.out.println(System.currentTimeMillis());
                endGate.countDown();
            }).start();
        }
        endGate.await();
    }

}






















