package com.ljc.review.common.test;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SimpleDateFormat是线程不安全的，在多线程环境下必须进行外部同步处理
 */
public class DateFormatTest {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> System.out.println(111));
        t.start();
        //wait/notify对应synchronized，是Java关键字配套的特性。它是内置条件队列（每一个Object有且仅有一个），底层通过native方法实现，native中是通过C实现的条件队列功能
        t.join();
        t.notify();
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        //await/signal和AQS配套，是通过Java编程实现的条件队列功能，用于Lock及其实现类。一个Lock可以拥有多个条件队列，因此比wait更加灵活
        condition.await();
        condition.signal();
    }

    @Test
    public void test() {
        DateFormat dateTimeInstance = DateFormat.getDateTimeInstance();
        if (dateTimeInstance instanceof SimpleDateFormat) {
            System.out.println(true);
        }
    }


}
