package com.ljc.review.common.concurrent.inpratice.章14构建自定义的同步工具.条件队列;

import com.ljc.review.common.concurrent.inpratice.annotations.GuardBy;
import com.ljc.review.common.concurrent.inpratice.annotations.ThreadSafe;

/**
 * 实现可重新关闭的阀门
 */
@ThreadSafe
public class ThreadGate {
    @GuardBy("this") private boolean isOpen;  //当前阀门状态
    @GuardBy("this") private int generation;  //当前阀门是第几代

    public synchronized void close() {
        isOpen = false;
    }

    public synchronized void open() {
        isOpen = true;
        ++generation;  //打开一次世代数增加1
        notifyAll();   //通知所有等待线程阀门已打开
    }

    public synchronized void await() throws InterruptedException {
        //记录线程等待之前的阀门世代数
        int currentGeneration = generation;
        //当阀门关闭且线程记录的世代数和当前阀门世代数相同时，等待阀门开启。否则放行当前线程
        while (!isOpen && currentGeneration == generation) {
            wait();
        }
    }

}
