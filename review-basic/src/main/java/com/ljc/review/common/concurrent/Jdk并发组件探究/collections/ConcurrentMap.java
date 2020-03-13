package com.ljc.review.common.concurrent.Jdk并发组件探究.collections;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class ConcurrentMap {

    @Test
    public void test() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("1", "one");
        map.put("2", "two");
        System.out.println(map);
    }

    public static void main(String[] args) throws InterruptedException {
        int n = 20;
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(n);
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
        System.out.println(map.size());
    }
}
