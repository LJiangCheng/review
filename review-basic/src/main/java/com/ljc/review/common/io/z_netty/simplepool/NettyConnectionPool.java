package com.ljc.review.common.io.z_netty.simplepool;

import com.ljc.review.common.io.z_netty.simplepool.utils.ChannelUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.Attribute;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public class NettyConnectionPool {

    private final Channel[] channels;
    private final Object[] locks;     //每一个channel对应一个锁
    private static final int MAX_CHANNEL_COUNT = 4;

    public NettyConnectionPool() {
        channels = new Channel[MAX_CHANNEL_COUNT];
        locks = new Object[MAX_CHANNEL_COUNT];
        for (int i = 0; i < MAX_CHANNEL_COUNT; i++) {
            locks[i] = new Object();
        }
    }

    public Channel getChannelSync() throws InterruptedException {
        //随机获取Channel
        int index = new Random().nextInt(MAX_CHANNEL_COUNT);
        Channel channel = channels[index];
        //如果Channel可用，直接返回
        if (channel != null && channel.isActive()) {
            return channel;
        }
        synchronized (locks[index]) {
            //再判断一次，防止重复创建
            channel = channels[index];
            if (channel != null && channel.isActive()) {
                return channel;
            }
            //跟服务器交互，获取Channel
            channel = connectToServer();
            channels[index] = channel;
        }
        return channel;
    }

    private Channel connectToServer() throws InterruptedException {
        //配置连接属性
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new SelfDefineEncodeHandler());
                        pipeline.addLast(new SocketClientHandler());
                    }
                });

        //发起连接
        ChannelFuture channelFuture = bootstrap.connect("localhost", 8180);
        //等待连接完成后，返回Channel
        Channel channel = channelFuture.sync().channel();

        //为刚刚创建的channel初始化一个和DATA_MAP_ATTRIBUTEKEY关联的属性
        Attribute<Map<Integer, Object>> attribute = channel.attr(ChannelUtils.DATA_MAP_ATTRIBUTEKEY);
        //记录seq-callback关联关系的dataMap
        ConcurrentHashMap<Integer, Object> dataMap = new ConcurrentHashMap<>();
        attribute.set(dataMap);
        return channel;
    }

}





























