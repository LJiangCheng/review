package com.ljc.review.common.concurrent.inpratice.章15原子变量与非阻塞同步机制.noneblock;

import com.ljc.review.common.concurrent.inpratice.annotations.ThreadSafe;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 线程安全的链表队列
 * 需要能快速访问头、尾节点，因此新增节点时需要同时更新上一个节点的next引用以及队列的尾节点引用
 * 要基于原子引用完成这样的更新并同时保证线程安全需要一些额外的设计
 */
@ThreadSafe
public class LinkedQueue<E> {
    //初始化的哑结点
    private Node<E> dummy = new Node<>(null, new AtomicReference<>(null));
    //保持对头结点的引用
    private AtomicReference<Node<E>> head = new AtomicReference<>(dummy);
    //保持对尾节点的引用
    private AtomicReference<Node<E>> tail = new AtomicReference<>(dummy);

    /**
     * push操作需要完成两个操作
     * 1.将新节点链接到链表尾部：改变当前链表尾节点的next引用
     * 2.将对尾节点的引用后移一个节点
     * 如何保证两步操作的安全性？
     * 关键：判断当前尾节点的next引用是否为空，如果不是，说明有其他线程在执行push操作，帮它将尾节点后移之后重试
     */
    public boolean put(E item) {
        Node<E> newNode = new Node<>(item, new AtomicReference<>(null));
        Node<E> curTail;
        Node<E> tailNext;
        //PS:通过do-while做不了，因为条件判断之后还要执行其它操作所以不能作为while条件使用
        while (true) {
            curTail = tail.get();  //当前尾节点
            tailNext = curTail.next.get();   //尾节点的下一个节点，原子引用
            if (tailNext != null) {
                //队列处于中间状态，推进尾节点
                tail.compareAndSet(curTail, tailNext);
            } else {
                //处于稳定状态，尝试插入新节点
                if (curTail.next.compareAndSet(null, newNode)) {
                    //插入操作成功，尝试推进尾节点
                    tail.compareAndSet(curTail, newNode);
                    return true;
                }
            }
        }
    }

    @Test
    public void test() throws InterruptedException {
        final LinkedQueue<Integer> queue = new LinkedQueue<>();
        final CountDownLatch latch = new CountDownLatch(5);
        ExecutorService exec = Executors.newFixedThreadPool(4);
        for(int i=0;i<5;i++) {
            exec.execute(() -> {
                for (int j = 0; j < 20; j++) {
                    queue.put(j);
                    try {
                        Thread.sleep(1);  //通过睡眠观察并发性
                    } catch (InterruptedException ignored) {}
                }
                latch.countDown();
            });
        }
        //等待结果 PS:超出时间会直接放行
        latch.await(1, TimeUnit.MILLISECONDS);
        //遍历查看结果
        int count = 0;
        AtomicReference<Node<Integer>> node = queue.head;
        while (node != null && node.get() != null) {
            Integer item = node.get().item;
            if (item != null) {
                System.out.println(item);
                count++;
            }
            node = node.get().next;
        }
        System.out.println("COUNT:" + count);
    }

    private static class Node<E>{
        private final E item;
        private AtomicReference<Node<E>> next;

        public Node(E item, AtomicReference<Node<E>> next) {
            this.item = item;
            this.next = next;
        }
    }
}















