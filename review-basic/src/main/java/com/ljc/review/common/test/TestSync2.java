package com.ljc.review.common.test;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSync2 implements Runnable {

    static final Logger LOGGER = LoggerFactory.getLogger(TestSync2.class);

    int b = 100;

    synchronized void m1() throws InterruptedException {
        b = 1000;
        Thread.sleep(500);
        System.out.println("b=" + b);
    }

    synchronized void m2() throws InterruptedException {
        Thread.sleep(250);
        b = 2000;
    }

    public static void main(String[] args) throws InterruptedException {
        TestSync2 tt = new TestSync2();
        Thread t = new Thread(tt);
        t.start();
        tt.m2();
        int a = tt.b;
        LOGGER.info("main thread b=" + a);
        System.out.println("main thread b=" + tt.b);
    }

    @Override
    public void run() {
        try {
            m1();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
