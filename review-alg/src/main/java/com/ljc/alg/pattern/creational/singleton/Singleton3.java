package com.ljc.alg.pattern.creational.singleton;

/**
 * 懒汉式更好的实现
 */
public class Singleton3 {

    private Singleton3() {}

    /**
     * 调用getInstance时才会加载InstanceHandler类，同时初始化instance
     * 由JVM加载机制保证实例的唯一性
     */
    public static Singleton3 getInstance() {
        return InstanceHandler.instance;
    }

    private static class InstanceHandler {
        private static final Singleton3 instance = new Singleton3();
    }

}
