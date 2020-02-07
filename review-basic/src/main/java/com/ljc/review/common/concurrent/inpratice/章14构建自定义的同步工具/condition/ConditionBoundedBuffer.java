package com.ljc.review.common.concurrent.inpratice.章14构建自定义的同步工具.condition;

import com.ljc.review.common.concurrent.inpratice.annotations.GuardBy;
import com.ljc.review.common.concurrent.inpratice.annotations.ThreadSafe;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用显式条件队列实现循环的有界缓存
 * 如果需要一些高级功能，例如使用公平的队列操作、或者在每个锁上等待多个线程集，那么应该优先使用Condition
 */
@ThreadSafe
public class ConditionBoundedBuffer<T> {
    private static final int BUFFER_SIZE = 16;
    private final Lock lock = new ReentrantLock();
    //条件谓词notFull：count < items.length
    private final Condition notFull = lock.newCondition();
    //notEmpty：count > 0
    private final Condition notEmpty = lock.newCondition();
    @GuardBy("lock")
    private final T[] items = (T[]) new Object[BUFFER_SIZE];  //cast to T
    @GuardBy("lock")
    private int head,tail,count;

    //阻塞，直到notFull
    public void put(T t) throws InterruptedException {
        lock.lock();
        try {
            while (isFull()) {
                notFull.await();
            }
            items[tail] = t;
            if (++tail == items.length) {
                tail = 0;
            }
            count++;
            //可以使用单一事件通知，不必担心信号丢失
            //signal比signalAll更高效，它能极大地减少在每次缓存操作中发生的上下文切换及锁请求次数
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    //阻塞，直到notEmpty
    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (isEmpty()) {
                notEmpty.await();
            }
            T t = items[head];
            items[head] = null;
            if (++head == items.length) {
                head = 0;
            }
            count--;
            //通知等待notFull条件的线程活动
            notFull.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    private boolean isFull() {
        return count == items.length;
    }

    private boolean isEmpty() {
        return count == 0;
    }

}












