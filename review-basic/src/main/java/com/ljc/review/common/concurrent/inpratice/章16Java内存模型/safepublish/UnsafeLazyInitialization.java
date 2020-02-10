package com.ljc.review.common.concurrent.inpratice.章16Java内存模型.safepublish;


import javax.annotation.concurrent.NotThreadSafe;

/**
 * 不安全的延迟初始化
 * 两个隐患：
 * 1.可能重复初始化
 * 2.另一个线程可能看到对部分构造的Resource实例的引用
 * 因为一个线程写入resource的操作和另一个线程读取resource的操作不存在Happens-Before关系
 * 由于没有使用同步，因此线程B看到的线程A的操作顺序可能与线程A执行这些操作时的顺序并不相同。即使线程A初始化Resource实
 * 例之后再将resource指向它，线程B仍可能看到对resource的写入操作在对Resource各个域的写入操作之前发生
 */
@NotThreadSafe
public class UnsafeLazyInitialization {
    private static Resource resource;

    public static Resource getInstance() {  //加上synchronized就可以确保线程安全
        if (resource == null) {
            resource = new Resource(); // unsafe publication
        }
        return resource;
    }

    static class Resource {
    }
}
