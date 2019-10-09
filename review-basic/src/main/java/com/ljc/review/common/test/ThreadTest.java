package com.ljc.review.common.test;

import org.junit.Test;

public class ThreadTest {

    @Test
    public void test() {
        ThreadDemo threadDemo = new ThreadDemo();
        threadDemo.run();
        threadDemo.run();
        Thread thread1 = new Thread(threadDemo);
        thread1.setName("thread1");
        thread1.start();
        Thread thread2 = new Thread(threadDemo);
        thread2.setName("thread2");
        thread2.start();
    }

    class ThreadDemo implements Runnable {
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }

}
