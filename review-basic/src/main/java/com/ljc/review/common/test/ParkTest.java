package com.ljc.review.common.test;

import java.util.concurrent.locks.LockSupport;

public class ParkTest {

    public static void main(String[] args) throws InterruptedException {
        testLockSupport();
    }

    public static void testLockSupport() throws InterruptedException {
        Thread thread = new Thread(new ThreadPark(), "park");
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
        System.out.println("是否中断1:" + thread.isInterrupted());
        Thread thread2 = new Thread(new ThreadUnPark(thread), "unPark");
        thread2.start();
        System.out.println("是否中断2:" + thread.isInterrupted());
        Thread.sleep(6000);
    }

    static class ThreadPark implements Runnable {
        @Override
        public void run() {
            System.out.println("ThreadPark开始");
            LockSupport.park(this);//阻塞当前线程
            System.out.println("ThreadPark结束");
        }
    }

    static class ThreadUnPark implements Runnable {
        private Thread thread;

        public ThreadUnPark(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            System.out.println("ThreadUnPark开始");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LockSupport.park(thread);//阻塞线程
            System.out.println("ThreadUnPark结束");
        }
    }


}
