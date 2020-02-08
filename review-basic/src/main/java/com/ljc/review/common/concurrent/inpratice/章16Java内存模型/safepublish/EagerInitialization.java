package com.ljc.review.common.concurrent.inpratice.章16Java内存模型.safepublish;

import com.ljc.review.common.concurrent.inpratice.annotations.ThreadSafe;

/**
 * 提前初始化
 * 使用静态变量，借助类加载机制获得线程安全
 */
@ThreadSafe
public class EagerInitialization {
    private static Resource resource = new Resource();

    public static Resource getResource() {
        return resource;
    }

    static class Resource {}
}
