package com.ljc.review.common.concurrent.inpratice.章5;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.*;

public class ConcurrentCollections {

    public void list() {
        //写入时复制 仅当迭代操作远远多于修改操作时才使用这个容器
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
    }

    public void map() {
        //高并发的线程安全Map，但不提供独占访问
        ConcurrentHashMap map = new ConcurrentHashMap();
    }

    /**
     * 阻塞队列
     * 同步工具类：可以通过自身的状态来协调线程的工作流
     * 阻塞队列可以作为同步工具类，其他的还包括信号量(Semaphore)、栅栏(Barrier)、闭锁(Latch)等，也可以自定义同步工具类
     */
    public void queue() throws InterruptedException {
        //同步阻塞队列：生产者消费者模式组件 如Executor任务执行框架
        BlockingQueue queue = new ArrayBlockingQueue(16, true); //FIFO
        BlockingQueue queue1 = new LinkedBlockingQueue();  //FIFO
        BlockingQueue queue2 = new PriorityBlockingQueue();  //按优先级排序队列 Comparable/Comparator
    }

    //双端队列
    public void deque() {
        Deque deque = new ArrayDeque();   //非同步双端队列
        /*
        典型场景：Work Stealing(工作密取)模式
        生产者-消费者模式中，所有消费者有一个共有的工作队列。WorkStealing模式中每个消费者都有各自的双端队列，如果一个消费者完成了自己双端队列中的全部工作，那它可以从
        其它消费者双端队列末尾悄悄地获取任务。
        这种模式比传统的Customer-Consumer模式具有更高的可伸缩性，因为大多数时候工作线程不会在单个的共享队列上发生竞争，大多数时候，他们只是访问自己的队列，从而极大地减少
        了竞争，当它需要访问别的队列时会从尾部而非头部开始获取，这进一步减少了竞争
        WorkStealing模式非常适合既是生产者又是消费者的场景，如爬虫，在处理一个页面时，通常会发现更多待处理的页面，它会将他放在自己队列的末尾（或者共享工作，放入其他工作线
        程的末尾）
        */
        BlockingDeque deque2 = new LinkedBlockingDeque();   //同步双端队列
    }

}
