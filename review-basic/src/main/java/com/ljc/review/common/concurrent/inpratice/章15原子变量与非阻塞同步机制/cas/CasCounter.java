package com.ljc.review.common.concurrent.inpratice.章15原子变量与非阻塞同步机制.cas;

import com.ljc.review.common.concurrent.inpratice.annotations.ThreadSafe;

/**
 * 基于CAS实现的非阻塞计数器
 * CAS是一种乐观技术：假设不会发生冲突，如果确实有其他线程在最近一次检查后更新了该变量，那么CAS能检测到这个错误
 * 锁是一种悲观技术：假设一定会发生冲突，只有独占的状态下才可以运行
 */
@ThreadSafe
public class CasCounter {
    private SimulateCAS value;

    public int get() {
        return value.get();
    }

    /**
     * 递增并返回递增之后的值
     * 先获取旧值，然后做原子更新。如果更新失败则重新获取最新值再重试
     */
    public int increment() {
        int v;
        do {
            v = value.get();
        } while (!value.compareAndSet(v, v + 1));
        return v + 1;
    }

}
