package com.ljc.review.common.test;

import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ThreadTest {

    /**
     * run和start
     */
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

    /**
     * 测试当任务数量超出线程池等待队列数且线程数量达到上限时程序的反应
     */
    @Test
    public void testThreadPool() throws InterruptedException {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3);
        taskExecutor.setMaxPoolSize(5);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setRejectedExecutionHandler((r, executor) -> System.out.println("任务已拒绝！")); //当任务拒绝时进行处理而不是抛出异常
        taskExecutor.initialize();
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            taskExecutor.execute(() -> System.out.println(finalI));
            //Thread.sleep(1);  //如果主线程不设置睡眠且没有配置RejectedExecutionHandler的话，会导致任务的增长速度大于线程的归还速度，最终线程池耗尽，程序抛出异常
        }
    }

    class ThreadDemo implements Runnable {
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }

}
