package com.ljc.review.common.concurrent.inpratice.章16Java内存模型.safepublish;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * 双重检查锁的单例模式
 * 这是不安全的：线程可能看到一个仅被部分构造的Resource
 * 将resource声明为volatile类型可以使这种方式变成安全的（禁止指令重排序），且对性能影响很小。但没有这个必要，DCL的这种使用方式已被废弃，
 * 因为促使该模式出现的驱动力（无竞争同步的执行速度很慢，以及JVM启动时很慢）已经不复存在。延迟初始化占位类模式能带来同样的优势，并且更加易于理解
 */
@NotThreadSafe
public class DoubleCheckedLocking {
    private static Resource resource;

    public static Resource getInstance() {
        if (resource == null) {
            synchronized (DoubleCheckedLocking.class) {
                if (resource == null)
                    resource = new Resource();
            }
        }
        return resource;
    }

    static class Resource {

    }
}
