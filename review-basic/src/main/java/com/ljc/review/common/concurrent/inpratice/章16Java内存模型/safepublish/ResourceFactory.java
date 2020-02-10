package com.ljc.review.common.concurrent.inpratice.章16Java内存模型.safepublish;


import com.ljc.review.common.concurrent.inpratice.annotations.ThreadSafe;

/**
 * 延迟初始化占位类模式
 * 借助类加载机制实现线程安全的延迟加载
 */
@ThreadSafe
public class ResourceFactory {
    public static Resource getResource() {
        return ResourceFactory.ResourceHolder.resource;
    }

    private static class ResourceHolder {
        public static Resource resource = new Resource();
    }

    static class Resource {
    }
}
