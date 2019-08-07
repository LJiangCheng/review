package com.ljc.alg.pattern.creational.singleton;

/**
 * 懒汉式
 */
public class Singleton2 {

    private static Singleton2 instance;
    private Singleton2() {

    }

    //会有一些判断和同步锁，可能影响效率
    public Singleton2 getInstance() {
        if (instance == null) {
            synchronized (Singleton2.class) {
                if (instance == null) {
                    instance = new Singleton2();
                }
            }
        }
        return instance;
    }

}
