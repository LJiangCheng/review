package com.ljc.review.common.concurrent.inpratice.章14构建自定义的同步工具.AqsSynchronizer;

import com.ljc.review.common.concurrent.inpratice.annotations.GuardBy;
import com.ljc.review.common.concurrent.inpratice.annotations.ThreadSafe;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock和Semaphore之间存在许多共同点
 * 下面通过Lock来实现计数信号量（反之同样可以通过信号量来实现锁）
 * 事实上ReentrantLock和Semaphore实现时都使用了共同的基类，即AbstractQueuedSynchronizer(AQS)
 * 不仅如此，CountDownLatch/ReentrantReadWriteLock/SynchronousQueue/FutureTask等并发工具都是基于AQS实现的
 * AQS解决了实现同步器时涉及的大量问题（Page255）
 */
@ThreadSafe
public class SemaphoreOnLock {
    //锁和条件队列
    private final Lock lock = new ReentrantLock();
    private final Condition permitsAvailable = lock.newCondition();
    //许可数量
    @GuardBy("lock")
    private int permits;

    SemaphoreOnLock(int initPermits) {
        lock.lock();  //ToDo 这里可否不加锁？实例化的时候不是默认只有一个线程能够进来吗？
        try {
            this.permits = initPermits;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取许可。阻塞，直到permitsAvailable
     */
    public void acquire() throws InterruptedException {
        lock.lock();
        try {
            while (permits <= 0) {
                permitsAvailable.await();
            }
            --permits;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 释放许可
     */
    public void release() {
        lock.lock();
        try {
            ++permits;
            permitsAvailable.signal();
        } finally {
            lock.unlock();
        }
    }

}














