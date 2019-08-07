package com.ljc.alg.pattern.creational.singleton;

/**
 * 饿汉式 类加载时实例化
 * 可能造成资源浪费
 */
public class Singleton1 {

    private static Singleton1 instance;

    private Singleton1() {}

    static {
        instance = new Singleton1();
    }

    public static Singleton1 getInstance() {
        return instance;
    }

}
