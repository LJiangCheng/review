package com.ljc.review.common.concurrent.future;

import org.junit.Test;

import java.util.concurrent.*;

public class ExecutorTest {

    @Test
    public void testRunnableToCall() {
        //Executors将Runnable转为Callable result可指定
        Callable<String> callable = Executors.callable(() -> {
            //System.out.println("Task is running!");
            try {
                Thread.sleep(2000);
                //int i = 10 / 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "success");
        //固定数量的线程池
        ExecutorService executor = Executors.newFixedThreadPool(3);
        //将任务提交到线程池执行 使用submit方式获得Future
        Future<String> future = executor.submit(callable);
        //等待任务完成
        System.out.println("Waiting for result:");
        try {
            //future.get()接口会获取到Callable中的异常
            System.out.println(future.get());
        } catch (Exception e) {
            System.out.println("Task execute error: " + e.getMessage());
        }
    }

    @Test
    public void testCallableCall() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 5, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        Future<String> future = executor.submit(() -> {
            Thread.sleep(2000);
            //int i = 10 / 0;
            return "success";
        });
        try {
            System.out.println(future.get());
        } catch (Exception e) {
            System.out.println("Task execute error: " + e.getMessage());
        }
    }

    /**
     * 测试线程池定时执行任务
     */
    @Test
    public void testScheduledExecute() {
        //线程池
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
        //延迟执行任务，只执行一次
        ScheduledFuture<String> onceFuture = executorService.schedule(() -> {
            System.out.println("Once task executing...");
            Thread.sleep(2000);
            return "success";
        }, 2, TimeUnit.SECONDS);
        //延迟执行任务，并在之后循环执行  有两个不同的循环执行方法，分别以开始任务和结束任务的时间为计时起点，看文档描述
        ScheduledFuture<?> recycleFuture = executorService.scheduleAtFixedRate(() ->
                System.out.println("Recycle task is running..."), 1, 2, TimeUnit.SECONDS);
        try {
            System.out.println("Waiting for result:");
            System.out.println(onceFuture.get());
            Thread.sleep(10000);
            if (recycleFuture.cancel(false)) {
                System.out.println("Cancel recycle task!");
            }
        } catch (Exception e) {
            System.out.println("Task executor error:" + e.getMessage());
        }
    }

}














