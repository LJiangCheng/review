package com.ljc.review.common.concurrent.inpratice.章5基础模块.cache.compute;

/**
 * 并发缓存的实现示例
 * 计算接口定义
 *
 * @param <A> 输入
 * @param <V> 输出
 */
public interface Computable<A, V> {
    //假设这是一个非常耗时的操作
    V compute(A arg) throws InterruptedException;
}
