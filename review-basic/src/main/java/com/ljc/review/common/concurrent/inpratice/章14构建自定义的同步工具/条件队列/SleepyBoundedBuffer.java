package com.ljc.review.common.concurrent.inpratice.章14构建自定义的同步工具.条件队列;

/**
 * 通过轮询与休眠实现简单的阻塞
 * 问题：
 * 1.响应不够及时
 * 2.会有很多无谓的循环
 * 3.要提高响应性就要消耗更多的CPU资源
 */
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {
    private final long SLEEP_TIME = 1000;
    protected SleepyBoundedBuffer(int capacity) {
        super(capacity);
    }

    public void put(V v) throws InterruptedException {
        while (true) {
            //有界缓存为多线程协作工具，如生产者-消费者模式，需要保证先检查-后操作过程的完整性
            synchronized (this) {
                if (!isFull()) {
                    doPut(v);
                    return;
                }
            }
            Thread.sleep(SLEEP_TIME);
        }
    }

    public V get() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isEmpty()) {
                    return doTake();
                }
            }
            Thread.sleep(SLEEP_TIME);
        }
    }

}
