package com.ljc.review.common.concurrent.inpratice.章14构建自定义的同步工具.AqsSynchronizer;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 基于AQS实现的一个简单的闭锁
 * AQS管理着同步器类中的状态，它管理了一个整数状态信息，可以通过getState/setState/compareAndSetState来操作
 * 这个整数可以用于表示任意状态，在不同的实现中可以为它赋予不同的意义。
 * 如Semaphore用来表示剩余的许可数量，FutureTask用来表示任务状态（未开始、正在运行、已完成、已取消）等
 * 在这里则用来表示闭锁的开关状态。
 */
public class SimpleLatch {
    private final Sync sync = new Sync();

    public void signal() {
        sync.releaseShared(0);
    }

    public void await() throws InterruptedException {
        sync.acquireInterruptibly(0);

    }

    /**
     * 通常使用私有内部类扩展AQS来管理状态而非由主类直接扩展，那会导致不必要的、危险的接口暴露。
     */
    private class Sync extends AbstractQueuedSynchronizer {
        protected int tryAcquireShared(int ignored) {
            //如果闭锁是打开的（state == 1），那么这个操作将成功，否则就失败
            return (getState() == 1) ? 1 : -1;
        }

        protected boolean tryReleaseShared(int ignored) {
            setState(1); //打开闭锁
            return true; //现在其他线程可以获取该闭锁
        }

    }
}
