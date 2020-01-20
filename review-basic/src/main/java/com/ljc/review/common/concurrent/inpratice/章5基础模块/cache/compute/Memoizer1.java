package com.ljc.review.common.concurrent.inpratice.章5基础模块.cache.compute;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用ConcurrentHashMap解决并发性能问题
 */
public class Memoizer1<A, V> implements Computable<A, V> {
    private final Map<A, V> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public Memoizer1(Computable<A, V> c) {
        this.c = c;
    }

    /**
     * 提高并发性，但存在重复计算的问题
     * 对于更通用化的缓存机制来说，这个漏洞存在安全风险，如：只提供单次初始化的对象缓存
     */
    @Override
    public V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }

}
