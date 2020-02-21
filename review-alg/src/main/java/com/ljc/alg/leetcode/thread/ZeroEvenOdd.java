package com.ljc.alg.leetcode.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * 假设有这么一个类：
 * class ZeroEvenOdd {
 *   public ZeroEvenOdd(int n) { ... }      // 构造函数
 * public void zero(printNumber) { ... }  // 仅打印出 0
 * public void even(printNumber) { ... }  // 仅打印出 偶数
 * public void odd(printNumber) { ... }   // 仅打印出 奇数
 * }
 * 相同的一个 ZeroEvenOdd 类实例将会传递给三个不同的线程：
 * 线程 A 将调用 zero()，它只输出 0 。
 * 线程 B 将调用 even()，它只输出偶数。
 * 线程 C 将调用 odd()，它只输出奇数。
 * 每个线程都有一个 printNumber 方法来输出一个整数。请修改给出的代码以输出整数序列 010203040506... ，其中序列的长度必须为 2n。
 * <p>
 * 1.如何切换任务
 * 2.如何终止任务
 */
public class ZeroEvenOdd {
    private final int n;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    private ReentrantLock lock = new ReentrantLock();
    private Condition zeroCondition = lock.newCondition();
    private Condition oddCondition = lock.newCondition();
    private Condition evenCondition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        ZeroEvenOdd zeo = new ZeroEvenOdd(9);
        new Thread(() -> {
            try {
                zeo.zero(System.out::print);
            } catch (InterruptedException ignored) {
            }
        }).start();
        new Thread(() -> {
            try {
                zeo.odd(System.out::print);
            } catch (InterruptedException ignored) {
            }
        }).start();
        new Thread(() -> {
            try {
                zeo.even(System.out::print);
            } catch (InterruptedException ignored) {
            }
        }).start();
    }

    void zero(Consumer<Integer> consumer) throws InterruptedException {
        try {
            lock.lock();
            for (int i = 1; i <= n; i++) {
                consumer.accept(0);
                if (i % 2 != 0) {
                    oddCondition.signal();
                } else {
                    evenCondition.signal();
                }
                zeroCondition.await();
            }
        } finally {
            lock.unlock();
        }
    }

    void odd(Consumer<Integer> consumer) throws InterruptedException {
        try {
            lock.lock();
            oddCondition.await();
            for (int i = 1; i <= n; i++) {
                if (i % 2 != 0) {
                    consumer.accept(i);
                    zeroCondition.signal();
                    oddCondition.await();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    void even(Consumer<Integer> consumer) throws InterruptedException {
        try {
            lock.lock();
            evenCondition.await();
            for (int i = 1; i <= n; i++) {
                if (i % 2 == 0) {
                    consumer.accept(i);
                    zeroCondition.signal();
                    evenCondition.await();
                }
            }
        } finally {
            lock.unlock();
        }
    }

}

























