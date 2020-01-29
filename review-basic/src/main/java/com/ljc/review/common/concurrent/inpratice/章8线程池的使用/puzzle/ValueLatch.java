package com.ljc.review.common.concurrent.inpratice.章8线程池的使用.puzzle;

import java.util.concurrent.CountDownLatch;

/**
 * Created by lijiangcheng on 2020/1/29.
 */
public class ValueLatch<T> {
    private T value;
    private CountDownLatch done = new CountDownLatch(1);

    public boolean isSet() {
        return done.getCount() == 0;
    }

    public synchronized void setValue(T newValue) {
        if (!isSet()) {
            this.value = newValue;
            done.countDown();
        }
    }

    /**
     * Get方法阻塞直到结果出现，且和Set方法依赖同一个对象锁
     */
    public T getValue() throws InterruptedException {
        done.await();
        synchronized (this) {
            return value;
        }
    }

}
