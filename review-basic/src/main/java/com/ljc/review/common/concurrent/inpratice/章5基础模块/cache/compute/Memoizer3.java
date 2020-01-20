package com.ljc.review.common.concurrent.inpratice.章5基础模块.cache.compute;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 使用putIfAbsent做双重检查彻底解决重复计算问题
 * 尚未解决的问题：
 * 1.因Future存在的缓存污染，即需要检测执行失败的Future并将其从缓存移除以保证下一次可以执行
 * 2.缓存时效设置
 * 3.缓存清理问题
 */
public class Memoizer3<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public Memoizer3(Computable<A, V> c) {
        this.c = c;
    }

    /**
     * putIfAbsent：如果存在则返回原值，如果不存在则放入且返回null
     * 总结：
     * Map提供缓存机制
     * ConcurrentHashMap提供线程安全性及高并发性能，并通过putIfAbsent提供安全的二次检查
     * Future提供结果等待机制
     */
    @Override
    public V compute(final A arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
        if (f == null) {
            Callable<V> eval = () -> c.compute(arg);
            FutureTask<V> ft = new FutureTask<>(eval);
            f = cache.putIfAbsent(arg, ft);
            if (f == null) {
                //不存在，说明是第一次计算，需要在此处执行
                f = ft;
                ft.run();
            }
        }
        try {
            return f.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getCause());
        }
    }

}
