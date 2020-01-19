package com.ljc.review.common.concurrent.inpratice.章5基础模块;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 栅栏：同步工具类的一种
 * 阻塞一组线程，直到某个事件发生。
 * 与闭锁的区别在于，所有线程必须同时到达栅栏位置，才能继续执行。闭锁用于等待事件，而栅栏用于等待其他线程。且闭锁是一次性的，栅栏可以复位
 */
public class Barrier {

    private final Board mainBoard;
    private final CyclicBarrier barrier;
    private final Worker[] workers;

    public Barrier(Board board) {
        this.mainBoard = board;
        int count = Runtime.getRuntime().availableProcessors();  //处理器核心数量
        this.barrier = new CyclicBarrier(count, mainBoard::commitNewValues); //指定栅栏同步的线程数量，及所有线程都到达后需要执行的任务，由最后到达的线程执行。可以在这里检视并通知任务是否完全完成
        this.workers = new Worker[count];
        for (int i = 0; i < count; i++) {
            workers[i] = new Worker(mainBoard.getSubBoard(count, i)); //根据核心数量创建工作线程，指定其任务范围
        }
    }

    public void start() {
        //将任务分解交付工作线程
        for (int i = 0; i < workers.length; i++) {
            new Thread(workers[i]).start();
        }
        //等待总任务（即所有子任务）完成
        mainBoard.waitForConvergence();
    }

    /**
     * 手动实现类似栅栏效果
     * TODO 思考，有哪些隐藏的问题？和Jdk的实现相比又怎样的不同？
     */
    @Test
    public void myBarrier() throws InterruptedException {
        final CountDownLatch endGate = new CountDownLatch(10);
        final AtomicInteger count = new AtomicInteger(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                count.getAndDecrement();
                for (; ; ) {
                    int cur = count.get();
                    if (cur == 0) {
                        break;
                    }
                }
                System.out.println(System.currentTimeMillis());
                endGate.countDown();
            }).start();
        }
        endGate.await();
    }

    //模拟计算任务接口
    interface Board {
        int getMaxX();

        int getMaxY();

        int getValue(int x, int y);

        int setNewValue(int x, int y, int value);

        void commitNewValues();

        boolean hasConverged();

        void waitForConvergence();

        Board getSubBoard(int numPartitions, int index);
    }

    private class Worker implements Runnable {
        private final Board board;

        public Worker(Board board) {
            this.board = board;
        }

        public void run() {
            while (!board.hasConverged()) {
                for (int x = 0; x < board.getMaxX(); x++) {
                    for (int y = 0; y < board.getMaxY(); y++) {
                        board.setNewValue(x, y, computeValue(x, y));  //为板块上每一个点执行计算任务
                    }
                }
                try {
                    //线程调用await()表示自己已经到达栅栏。
                    //使用栅栏等待其他线程同步，确保所有线程都已完成任务再进行下一轮循环（未必一轮循环就能完成所有计算，下一轮计算依赖上一轮计算的全部结果）
                    //屏障打开后会被重置以便下次使用
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException ex) {
                    return;
                }
            }
        }

        private int computeValue(int x, int y) {
            // Compute the new value that goes in (x,y)
            return 0;
        }
    }

}
