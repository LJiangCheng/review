package com.ljc.alg.leetcode.thread;

import java.util.concurrent.atomic.AtomicInteger;
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
 * 0.如何保证任务顺序
 * 1.如何切换任务
 * 2.如何保证任务可以终止
 */
public class ZeroEvenOdd {
    private final int n;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    /*使用信号量：逻辑清晰，代码简单
    private Semaphore zeroSemaphore = new Semaphore(1);
    private Semaphore oddSemaphore = new Semaphore(0);
    private Semaphore evenSemaphore = new Semaphore(0);

    void zero(Consumer<Integer> consumer) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            zeroSemaphore.acquire();
            consumer.accept(0);
            if (i % 2 != 0) {
                oddSemaphore.release();
            } else {
                evenSemaphore.release();
            }
        }
    }

    void odd(Consumer<Integer> consumer) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            oddSemaphore.acquire();
            consumer.accept(i);
            zeroSemaphore.release();
        }
    }

    void even(Consumer<Integer> consumer) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            evenSemaphore.acquire();
            consumer.accept(i);
            zeroSemaphore.release();
        }
    }*/

    /*
     * 使用ReentrantLock
     * 1.必须保证先执行的是zero，所以需要一个标志位辅助判断
     * 2.必须保证zero执行之前odd和even已经在条件队列上等待，否则会造成唤醒信号丢失
     * 3.线程等待必须在每个循环的起始位置，否则会造成线程无法终止
     */
    private volatile boolean zeroPrinted = false;
    private AtomicInteger readyThread = new AtomicInteger(0);
    private ReentrantLock lock = new ReentrantLock();
    private Condition zeroCondition = lock.newCondition();
    private Condition oddCondition = lock.newCondition();
    private Condition evenCondition = lock.newCondition();

    void zero(Consumer<Integer> consumer) throws InterruptedException {
        try {
            lock.lock();
            for (int i = 1; i <= n; i++) {
                //等待另外两个线程准备就绪
                if (readyThread.get() < 2) {
                    zeroCondition.await();
                }
                //打印0
                consumer.accept(0);
                //修改起始状态
                if (!zeroPrinted) {
                    zeroPrinted = true;
                }
                //唤醒其它线程
                if (i % 2 != 0) {
                    oddCondition.signal();
                } else {
                    evenCondition.signal();
                }
                //等待同步。最后一轮会被唤醒，不存在终止问题
                zeroCondition.await();
            }
        } finally {
            lock.unlock();
        }
    }

    void odd(Consumer<Integer> consumer) throws InterruptedException {
        try {
            lock.lock();
            for (int i = 1; i <= n; i += 2) {
                if (!zeroPrinted) {
                    //判断任务起始状态
                    if (readyThread.incrementAndGet() == 2) {
                        zeroCondition.signal();
                    }
                    oddCondition.await();
                } else {
                    //odd和even的await必须放在任务之前，否则最后一次会无人唤醒。
                    //另外，第一次判断任务起始状态之后已经wait过了，如果放在最后等于多wait了一次
                    oddCondition.await();
                }
                consumer.accept(i);
                zeroCondition.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    void even(Consumer<Integer> consumer) throws InterruptedException {
        try {
            lock.lock();
            for (int i = 2; i <= n; i += 2) {
                if (!zeroPrinted) {
                    if (readyThread.incrementAndGet() == 2) {
                        zeroCondition.signal();
                    }
                    evenCondition.await();
                } else {
                    evenCondition.await();
                }
                consumer.accept(i);
                zeroCondition.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ZeroEvenOdd zeo = new ZeroEvenOdd(90);
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

}

























