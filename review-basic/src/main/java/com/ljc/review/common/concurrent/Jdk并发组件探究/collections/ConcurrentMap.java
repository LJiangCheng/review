package com.ljc.review.common.concurrent.Jdk并发组件探究.collections;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**江碧鸟逾白，山青花欲燃。今春看又过，何日是归年
 * 1.AQS并发源码 2.HashMap/ConcurrentHashMap源码 3.Dubbo/Netty源码总结
 * 1.数据库如何分库分表 2.数据库优化
 * 1.项目架构润色  2.秒杀系统设计学习
 * 1.搜索引擎索引结构部分原理
 * 分布式：分布式锁、分布式事务、分布式缓存、redis集群、mysql集群
 */
public class ConcurrentMap {

    @Test
    public void test() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("1", "one");
        map.put("2", "two");
        map.get("");
        System.out.println(map);
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            verifyHashMapProblem();
        }
    }

    private static void verifyHashMapProblem() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        int n = 20;
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(n);
        //Map<String, String> map = new ConcurrentHashMap<>(2);
        //两个问题：1.resize过程中的死循环 2.put方法的覆盖问题 在高并发环境下必然发生！
        Map<String, String> map = new HashMap<>(2);
        for (int j = 0; j < n; j++) {
            new Thread(() -> {
                try {
                    begin.await();
                    for (int i = 0; i < 10000; i++) {
                        map.put(i + "" + Thread.currentThread().getName(), i + "");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    end.countDown();
                }
            }).start();
        }
        begin.countDown();
        end.await();
        long endTime = System.currentTimeMillis();
        System.out.println(map.size() + "========>" + (endTime - startTime) + "ms");
    }
}
