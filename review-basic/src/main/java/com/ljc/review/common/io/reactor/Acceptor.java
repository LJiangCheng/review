package com.ljc.review.common.io.reactor;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

//连接事件就绪，处理连接事件。主要任务：构建handler在获取到和client相关的SocketChannel之后绑定到相应的handler上
public class Acceptor implements Runnable{

    private final Selector selector;
    private final ServerSocketChannel serverSocket;

    Acceptor(Selector selector, ServerSocketChannel serverSocket) {
        this.selector = selector;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            SocketChannel channel = serverSocket.accept();
            if (channel != null) {
                //注册读写
                new Handler(selector, channel);
            }
        } catch (IOException ex) { /* ... */ }
    }
}
