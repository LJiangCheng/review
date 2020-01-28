package com.ljc.review.common.concurrent.inpratice.章7取消中断与关闭;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by lijiangcheng on 2020/1/27.
 */
public class BrokenPrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;
    private volatile boolean cancelled = false;

    public BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            //生产素数
            //while (!cancelled) {
            while (!Thread.currentThread().isInterrupted()) {
                BigInteger p = BigInteger.ONE;
                queue.put(p = p.nextProbablePrime());  //阻塞方法，可以响应中断
            }
        } catch (InterruptedException e) {
            //自定义中断策略处理中断 Runnable定义的任务无法传递InterruptedException
        }
    }

    public void cancel() {
        cancelled = true;
    }

    /**
     * 模拟消费
     */
    private class Consumer {
        void consumePrimes() throws InterruptedException {
            //创建生产者进行生产
            BlockingQueue<BigInteger> queue = new ArrayBlockingQueue<BigInteger>(32);
            BrokenPrimeProducer producer = new BrokenPrimeProducer(queue);
            producer.start();
            //消费者消费
            try{
                while (needMorePrime()) {   //消费者判断是否需要继续消费
                    consume(queue.take());
                }
            } finally {
                //最终通知生产者取消生产
                //问题：当生产者的生产速度大于消费者的消费速度时，这个通知就会失效
                //解释：这种情况下队列会被填满，消费者停止消费后生产者依然阻塞在put方法上无法检查任务状态
                //方案：使用中断而非boolean标志来请求取消
                //producer.cancel();
                producer.interrupt();
            }
        }

        private void consume(BigInteger take) {

        }

        private boolean needMorePrime() {
            return false;
        }
    }

}
