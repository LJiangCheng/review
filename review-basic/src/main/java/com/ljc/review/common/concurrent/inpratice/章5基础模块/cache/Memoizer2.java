package com.ljc.review.common.concurrent.inpratice.章5基础模块.cache;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 使用FutureTask封装，初步解决重复计算问题
 */
public class Memoizer2<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public Memoizer2(Computable<A, V> c) {
        this.c = c;
    }

    /**
     * if代码块中的'先检查再执行'操作是非原子性的，这个实现依旧存在重复计算的问题，但是相比之前重复的概率大大降低
     */
    @Override
    public V compute(final A arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
        if (f == null) {
            Callable<V> eval = () -> c.compute(arg);
            FutureTask<V> ft = new FutureTask<>(eval);
            f = ft;
            cache.put(arg, ft);
            ft.run();
        }
        try {
            return f.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getCause());
        }
    }

}
