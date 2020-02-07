package com.ljc.review.common.concurrent.inpratice.章14构建自定义的同步工具.条件队列;

import com.ljc.review.common.concurrent.inpratice.annotations.ThreadSafe;

/**
 * 使用条件队列实现的有界缓存
 * 条件队列：使得一组线程(等待线程集合)能够通过某种方式等待特定的条件变成真。条件队列中的元素是一个个正在等待相关条件的线程
 * PS：每个Java对象都可以作为一个锁，每个Java对象同样可以作为一个条件队列
 */
@ThreadSafe
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {
    protected BoundedBuffer(int capacity) {
        super(capacity);
    }

    public synchronized void put(V v) throws InterruptedException {
        //条件等待中的三元关系：加锁、wait方法、条件谓词
        while (isFull()) {
            wait();   //wait会自动释放持有的锁，被唤醒后会再次尝试获取锁
        }
        doPut(v);
        //可能存在的弊端：1.过早唤醒 2.信号丢失
        //可优化：判断队列是否由空到非空，如果是才发送唤醒消息
        //notifyAll会唤醒所有线程，导致新一轮的锁竞争
        notifyAll();
    }

    public synchronized V take() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        V v = doTake();
        notifyAll();
        return v;
    }

    /*public void put(V v) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isFull()) {
                    doPut(v);
                    notifyAll();
                    return;
                }
                wait();
            }
        }
    }

    public V take() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isEmpty()) {
                    V v = doTake();
                    notifyAll();
                    return v;
                }
                wait();
            }
        }
    }*/

}
