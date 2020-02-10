package com.ljc.review.common.concurrent.inpratice.章14构建自定义的同步工具.条件队列;

import com.ljc.review.common.concurrent.inpratice.annotations.GuardBy;
import com.ljc.review.common.concurrent.inpratice.annotations.ThreadSafe;

/**
 * 简单有界缓存实现的基类
 * 基于数组的循环缓存，其中各个缓存状态的变量均由缓存的内置锁保护
 * 提供了同步的doPut/doTake方法。通过这些方法，底层状态将对子类隐藏。
 */
@ThreadSafe
public abstract class BaseBoundedBuffer<V> {
    @GuardBy("this")
    private final V[] buf;
    @GuardBy("this")
    private int tail;
    @GuardBy("this")
    private int head;
    @GuardBy("this")
    private int count;

    protected BaseBoundedBuffer(int capacity) {
        this.buf = (V[]) new Object[capacity];
    }

    protected synchronized final void doPut(V v) {
        buf[tail] = v;
        if (++tail == buf.length) {
            tail = 0;
        }
        ++count;
    }

    protected synchronized final V doTake() {
        V v = buf[head];
        buf[head] = null;
        if (++head == buf.length) {
            head = 0;
        }
        --count;
        return v;
    }

    public synchronized final boolean isFull() {
        return count == buf.length;
    }

    public synchronized final boolean isEmpty() {
        return count == 0;
    }
}
