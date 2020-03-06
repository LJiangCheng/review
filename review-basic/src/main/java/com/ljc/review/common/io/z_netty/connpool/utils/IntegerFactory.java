package com.ljc.review.common.io.z_netty.connpool.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 工具类：为每一次请求产生唯一的序列号
 */
public class IntegerFactory {

    private IntegerFactory() {

    }

    private static class SingletonHolder {
        private static final AtomicInteger INSTANCE = new AtomicInteger();
    }

    public static AtomicInteger getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
