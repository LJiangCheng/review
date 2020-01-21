package com.ljc.alg.leetcode.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程问题，交替打印FooBar
 */
public class FooBar {

    private int n;
    private volatile int flag = 0;

    private FooBar(int n) {
        this.n = n;
    }

    private void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            // printFoo.run() outputs "foo". Do not change or remove this line.
            while (flag != 0) {
                Thread.yield();
            }
            printFoo.run();
            flag = 1;
        }
    }

    private void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            // printBar.run() outputs "bar". Do not change or remove this line.
            while (flag != 1) {
                Thread.yield();
            }
            printBar.run();
            flag = 0;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        final FooBar fooBar = new FooBar(3);
        executor.execute(() -> {
            try {
                fooBar.foo(() -> System.out.print("Foo"));
            } catch (InterruptedException ignore) {
            }
        });
        executor.execute(() -> {
            try {
                fooBar.bar(() -> System.out.print("Bar"));
            } catch (InterruptedException ignore) {
            }
        });
        executor.shutdown();
    }

}
