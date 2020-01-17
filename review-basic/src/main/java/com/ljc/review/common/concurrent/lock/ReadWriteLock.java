package com.ljc.review.common.concurrent.lock;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLock {

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Test
    public void test() throws InterruptedException {
        new Thread(this::read).start();
        new Thread(this::read).start();
        Thread.sleep(100000);
    }

    /**
     * 读锁允许多线程同时访问目标资源
     * 写锁不允许同时访问目标资源
     * 当读锁被占用时写锁需等待，反之亦然
     * synchronized和lock的对比：
     * lock是一个接口，比sync拥有更强的灵活性
     * sync发生异常时会主动释放锁，lock无论何时必须手动释放
     * lock可以让等待锁的线程响应中断，sync不行
     * lock可以有效提高多线程读的效率
     */
    private void read() {
        lock.writeLock().lock();
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(200);
                System.out.println(Thread.currentThread().getName());
            }
        } catch (InterruptedException ignore) {
        } finally {
            lock.writeLock().unlock();
        }
    }

}
