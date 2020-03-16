package com.ljc.review.common.concurrent.Jdk并发组件探究.lock;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 基于读写锁实现缓存
 */
public class ReadWriteCache {

    private final Map<String, Object> cache = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final AtomicInteger hitCount = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        int taskCount = 10;
        ReadWriteCache cacheInstance = new ReadWriteCache();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(taskCount);
        for(int i = 0;i<taskCount;i++) {
            //使用CompletableFuture
            CompletableFuture.runAsync(() -> cacheInstance.get(""), executor).thenAcceptAsync(aVoid -> latch.countDown(), executor);
        }
        //主线程等待任务完成
        latch.await();
        //关闭线程池，否则主线程执行完毕之后JVM不会关闭
        executor.shutdown();
        System.out.println("缓存命中次数：" + cacheInstance.hitCount.get());
    }

    public Object get(String key) {
        Lock readLock = this.lock.readLock();
        Lock writeLock = this.lock.writeLock();
        //获取读锁
        readLock.lock();
        Object value;
        try {
            value = cache.get(key);
            if (value == null) {
                //释放读锁 如果缓存中不存在，需要写入
                readLock.unlock();
                //获取写锁。注：写锁中可以获取读锁，读锁中不可获取写锁
                writeLock.lock();
                //再检查一遍缓存中是否已存在以避免重复计算 这里和双重检查锁不同的是value一定是完成初始化之后才会放到缓存中的，不存在因为重排序导致的不安全问题
                if ((value = cache.get(key)) == null) {
                    try {
                        value = UUID.randomUUID().toString().replaceAll("-", "");
                        cache.put(key, value);
                    } finally {
                        //释放写锁
                        writeLock.unlock();
                    }
                }
                //恢复读锁
                readLock.lock();
            } else {
                hitCount.getAndIncrement();
            }
        } finally {
            //释放读锁
            readLock.unlock();
        }
        return value;
    }

    @Test
    public void test() {
        int i = 16;
        System.out.println(2 << i);
    }

}






















