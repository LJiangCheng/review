package com.ljc.review.common.io.z_netty.connpool;

/**
 * 客户端程序入口
 * 1.多个线程可能共用一个Channel，如何保证通信的准确性？  为每一次通信设置一个唯一的序列号，通过序列号匹配线程，并进行线程同步
 * 1.1如何进行通信同步？  最简单的方式：wait/notify
 * 2.如何维护Channel的生命周期？  release返回连接池
 * 3.如何监测Channel状态？  心跳检测
 */
public class SocketClient {

    public static void main(String[] args) {

    }

    /**
     * 回调同步工具类
     */
    public static class CallBackService {

    }

}
