package com.ljc.review.common.io.z_netty.connpool;

import io.netty.channel.Channel;

import java.util.concurrent.CountDownLatch;

/**
 * 客户端程序入口
 * 1.多个线程可能共用一个Channel，如何保证通信的准确性？  为每一次通信设置一个唯一的序列号，通过序列号匹配线程，并进行线程同步
 * 1.1如何进行通信同步？  最简单的方式：wait/notify
 * 2.如何维护Channel的生命周期？  release返回连接池
 * 3.如何监测Channel状态？  心跳检测
 */
public class SocketClient {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch beginLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(10);
        NettyConnectionPool channelPool = new NettyConnectionPool();
        //10个线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    //为了加大竞争，使所有线程初始化完成后一同启动
                    beginLatch.await();
                    //获取连接
                    Channel channel = channelPool.getChannelSync();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    endLatch.countDown();
                }
            }).start();
        }
        beginLatch.countDown();
        endLatch.await();
        //打印获取到的数据，验证程序正确性

    }

}
