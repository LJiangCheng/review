package com.ljc.review.common.concurrent.inpratice.章5基础模块.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Computable接口的通用缓存封装：接受一个任意类型的Computable实例，为其提供缓存机制
 */
public class Memoizer<A, V> implements Computable<A, V> {
    private final Map<A, V> cache = new HashMap<>();
    private final Computable<A, V> c;

    public Memoizer(Computable<A, V> c) {
        this.c = c;
    }

    /**
     * 重量级锁，只能串行执行，性能低
     *
     * @param arg
     * @return
     * @throws InterruptedException
     */
    @Override
    public synchronized V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }

}
