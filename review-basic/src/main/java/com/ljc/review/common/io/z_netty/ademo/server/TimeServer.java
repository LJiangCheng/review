package com.ljc.review.common.io.z_netty.ademo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 几个问题：
 * 1.Netty的事件通知机制是如何实现的？如何保证调用过程中请求和响应的一一对应？
 * 2.Netty使用了哪些设计模式？分别有什么好处？
 * 3.Netty的线程模型是怎样的的？如何保证线程安全？
 * 4.Dubbo是如何集成Netty的？
 * 其他问题：
 * BIO、NIO 和 AIO 的区别？
 * NIO 的组成？
 * Netty 的特点？
 * Netty 的线程模型？
 * TCP 粘包/拆包的原因及解决方法？
 * 了解哪几种序列化协议？
 * 如何选择序列化协议？
 * Netty 的零拷贝实现？
 * Netty 的高性能表现在哪些方面？
 * NIOEventLoopGroup 源码？
 */
public class TimeServer {

    private int port;

    public TimeServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new TimeServer(8180).run();
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new TimeEncoder(), new TimeServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
