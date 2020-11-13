package com.ljc.review.common.test;

import java.util.concurrent.locks.LockSupport;

public class ParkTest {

    public static void main(String[] args) throws InterruptedException {
        testLockSupport();
    }

    public static void testLockSupport() throws InterruptedException {
        Thread thread = new Thread(new ThreadPark(), "park");
        thread.start(); //park
        Thread.sleep(1000); //主线程等待
        thread.interrupt(); //设置thread中断状态，中断thread  这将导致thread从park中恢复
        System.out.println("是否中断1:" + thread.isInterrupted() + " " + thread.getState()); //打印中断状态 可能是true or false
        Thread thread2 = new Thread(new ThreadUnPark(thread), "unPark");
        thread2.start(); //sleep 5000 and unpark thread
        System.out.println("是否中断2:" + thread.isInterrupted()); //打印中断状态 只会是false
        Thread.sleep(6000);
    }

    static class ThreadPark implements Runnable {
        @Override
        public void run() {
            System.out.println("ThreadPark开始");
            System.out.println("当前阻塞的线程：" + Thread.currentThread().getName());
            LockSupport.park(Thread.currentThread());//阻塞当前线程
            /*for (int i = 0; i < 1000000000; i++) {
                int y = i + 1;
            }*/
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
            System.out.println("当前线程：" + Thread.currentThread().getName());
            System.out.println("将被唤醒的线程：" + thread.getName());
            LockSupport.unpark(thread);//唤醒被阻塞的线程
            System.out.println("ThreadUnPark结束");
        }
    }


}
