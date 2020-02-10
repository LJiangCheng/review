package com.ljc.review.common.concurrent.inpratice.章7取消中断与关闭;

import java.util.concurrent.*;

/**
 * 通过Future取消任务
 */
public class TimeRun {
    private static ExecutorService taskExec = Executors.newFixedThreadPool(5);

    /**
     * 通过线程池执行任务，超时则取消
     * 如果当前线程被中断，直接抛出异常
     */
    public static void timeRun(Runnable task, long timeOut, TimeUnit unit) throws InterruptedException {
        Future<?> future = taskExec.submit(task);
        try {
            future.get(timeOut, unit);
        } catch (TimeoutException e) {
            //超时，接下来取消任务
        } catch (ExecutionException e) {
            //如果任务中抛出了异常，那么重新抛出该异常
            throw new RuntimeException(e.getCause());
        } finally {
            //如果任务已经结束，那么执行取消操作也不会带来任何影响
            future.cancel(true); //如果任务正在运行，那么将被取消
        }
    }

}
