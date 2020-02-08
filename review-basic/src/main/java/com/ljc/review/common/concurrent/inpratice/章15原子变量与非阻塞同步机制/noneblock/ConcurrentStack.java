package com.ljc.review.common.concurrent.inpratice.章15原子变量与非阻塞同步机制.noneblock;

import com.ljc.review.common.concurrent.inpratice.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 实现线程安全的非阻塞栈，单链表结构
 * 每次操作只需要考虑如何更新栈顶引用
 */
@ThreadSafe
public class ConcurrentStack<E> {
    //保持对TOP节点的引用
    private AtomicReference<Node<E>> top = new AtomicReference<>();

    public void push(E item) {
        Node<E> newHead = new Node<>(item);
        Node<E> oldHead;
        do {
            oldHead = top.get();      //当前的栈顶
            newHead.next = oldHead;   //链表结构，指定下一个节点
        } while (!top.compareAndSet(oldHead, newHead));  //失败重试
    }

    public E pop() {
        Node<E> oldHead;
        do {
            oldHead = top.get();
            if (oldHead == null) {
                return null;
            }
        } while (!top.compareAndSet(oldHead, oldHead.next));
        return oldHead.item;
    }

    private static class Node<E>{
        public final E item;
        public Node<E> next;
        public Node(E item) {
            this.item = item;
        }
    }

}
