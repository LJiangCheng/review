package com.ljc.review.common.io.z_netty.connpool;

import com.ljc.review.common.io.z_netty.connpool.utils.CallbackService;
import com.ljc.review.common.io.z_netty.connpool.utils.ChannelUtils;
import com.ljc.review.common.io.z_netty.connpool.utils.IntegerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
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
        final CountDownLatch beginLatch = new CountDownLatch(1);
        final CountDownLatch endLatch = new CountDownLatch(10);
        final NettyConnectionPool channelPool = new NettyConnectionPool();
        final Map<String, Object> resultMap = new HashMap<>();
        //10个线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    int seq = IntegerFactory.getInstance().incrementAndGet();  //请求序列号
                    //加大竞争，使所有线程初始化完成后一同启动
                    beginLatch.await();
                    //获取Channel及创建关联的Callback
                    Channel channel = channelPool.getChannelSync();
                    CallbackService callbackService = new CallbackService();
                    //记录请求和回调的关联关系
                    ChannelUtils.putCallback2DataMap(channel, seq, callbackService);
                    synchronized (callbackService) {
                        //准备数据
                        UnpooledByteBufAllocator allocator = new UnpooledByteBufAllocator(false);
                        ByteBuf buffer = allocator.buffer(20);
                        int length = ChannelUtils.MESSAGE_LENGTH;
                        buffer.writeInt(length);
                        buffer.writeInt(seq);
                        String threadName = Thread.currentThread().getName();
                        buffer.writeBytes(threadName.getBytes());
                        buffer.writeBytes("body".getBytes());
                        //写出数据
                        channel.writeAndFlush(buffer);
                        //等待响应
                        callbackService.wait();
                        //解析数据 数据在传递给callbackService时已经解析完成
                        ByteBuf result = callbackService.result;
                        int len = result.readInt();
                        int seqFromServer = result.readInt();
                        byte[] head = new byte[8];
                        byte[] body = new byte[4];
                        result.readBytes(head);
                        result.readBytes(body);
                        String headString = new String(head);
                        String bodyString = new String(body);
                        //记录每条线程读取到的结果
                        resultMap.put(threadName, seqFromServer + headString + bodyString + len);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    endLatch.countDown();
                }
            }).start();
        }
        //启动任务
        beginLatch.countDown();
        //等待10个线程完成，打印最终结果，验证程序正确性
        endLatch.await();
        System.out.println(resultMap);
    }

}
