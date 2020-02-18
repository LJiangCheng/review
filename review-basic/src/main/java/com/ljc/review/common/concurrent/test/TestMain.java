package com.ljc.review.common.concurrent.test;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试main方法执行完成之后JVM是否会关闭从而使其它线程也随之终止
 */
public class TestMain {

    /**
     * 直接通过main方法启动，主线程执行完毕之后不会关闭JVM，其它线程继续执行
     */
    public static void main(String[] args) {
        System.out.println("=====MainStart=====");
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignore) {}
            System.out.println("=====ChildStillAlive=====");
        }).start();
        System.out.println("=====MainStop=====");
        //System.exit(0);
    }

    /**
     * 通过Junit执行，主线程执行完毕之后JVM被关闭了，应该是主线程退出时显式调用了System.exit(0)
     */
    @Test
    public void test() {
        System.out.println("=====MainStart=====");
        //测试线程
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignore) {}
            System.out.println("=====ChildStillAlive=====");
        }).start();
        //打印堆栈信息，获取程序主入口
        Throwable th = new Throwable();
        th.printStackTrace();
        System.out.println("=====MainStop=====");
    }

    /**
     * 等待，直到通知线程关闭
     */
    @Test
    public void testCondition() throws InterruptedException {
        System.out.println("=====MainStart=====");
        ReentrantLock lock = new ReentrantLock();
        Condition stop = lock.newCondition();
        //测试线程
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignore) {}
            System.out.println("=====ChildStillAlive=====");
            try{
                //必须持有锁才能使用对应的condition
                lock.lock();
                stop.signal();
            } finally {
                lock.unlock();
            }
        }).start();
        System.out.println("=====MainWait=====");
        //主线程等待子线程通知之后再结束
        try{
            lock.lock();
            stop.await();
        } finally {
            lock.unlock();
        }
    }

}
