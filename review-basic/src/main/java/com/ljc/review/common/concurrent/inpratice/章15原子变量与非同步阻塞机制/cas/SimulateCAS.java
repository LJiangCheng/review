package com.ljc.review.common.concurrent.inpratice.章15原子变量与非同步阻塞机制.cas;

import com.ljc.review.common.concurrent.inpratice.annotations.ThreadSafe;

/**
 * 模拟CAS语义（不是实现或性能，真实的CAS由硬件支持）
 */
@ThreadSafe
public class SimulateCAS {
    private int val;

    public synchronized int get() {
        return val;
    }

    public synchronized int compareAndSwap(int expectVal, int newVal) {
        int oldVal = this.get();
        //如果旧值等于期望值，说明期间没有其他线程更新过，本线程可以继续更新，否则更新失败
        if (oldVal == expectVal) {
            val = newVal;
        }
        //无论更新是否成功，返回更新之前的值
        return oldVal;
    }

    public synchronized boolean compareAndSet(int expectVal, int newVal) {
        return (expectVal == compareAndSwap(expectVal, newVal));
    }

}
