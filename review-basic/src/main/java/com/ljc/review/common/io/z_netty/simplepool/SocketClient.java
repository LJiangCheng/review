package com.ljc.review.common.io.z_netty.simplepool;

import com.ljc.review.common.io.z_netty.simplepool.utils.CallbackService;
import com.ljc.review.common.io.z_netty.simplepool.utils.ChannelUtils;
import com.ljc.review.common.io.z_netty.simplepool.utils.IntegerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 简单的连接池实现示例，没有使用官方的FixedConnectionPool
 * 已实现的功能点：
 * 1.建立了一个到服务器的固定大小的连接池，可复用连接池中的连接
 * 2.解决了通信的半包问题：通过规定的消息格式，前四位byte附带消息长度信息
 * 3.解决了线程唤醒问题：通过绑定Callback对象在响应事件完成后唤醒调用线程(wait/notify) ToDo Netty的事件通知机制是怎么实现的
 * 4.解决了消息匹配问题：在每一次请求的消息中附带请求序列号，响应时同样带上这个序列号
 * 待实现的功能：
 * 1.连接释放和空闲连接回收机制
 * 2.心跳检测机制
 * 3.复用官方提供的连接池
 * 4.针对微服务优化，即如果服务端有多个实例，应该为每个实例维护一个连接池
 * 5.连接任务处理的池化
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
                    //加大竞争，使所有线程初始化完成后一同启动
                    beginLatch.await();
                    //生成请求序列号
                    int seq = IntegerFactory.getInstance().incrementAndGet();
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
        System.out.println("resultMap = " + resultMap);
    }

}
