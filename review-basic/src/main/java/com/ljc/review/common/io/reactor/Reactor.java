package com.ljc.review.common.io.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 等待事件到来，分发事件处理
 * Reactor线程负责多路分离Socket，有新连接到来触发connect事件之后，交由Acceptor进行处理，有IO读写事件之后交给handler处理
 */
public class Reactor implements Runnable {

    private final Selector selector;

    //Reactor构造初始化
    Reactor(int port) throws IOException {
        selector = Selector.open();
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        //非阻塞
        serverSocket.configureBlocking(false);
        //初始化，接收accept事件
        SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        //为accept绑定回调处理对象
        sk.attach(new Acceptor(selector, serverSocket));
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                if (selector.select() > 0) {
                    Set selected = selector.selectedKeys();
                    Iterator it = selected.iterator();
                    while (it.hasNext()) {
                        //分发事件
                        dispatch((SelectionKey) (it.next()));
                        it.remove();
                    }
                }
            }
        } catch (IOException ex) { /* ... */ }
    }

    private void dispatch(SelectionKey k) {
        // 若是连接事件获取是acceptor
        // 若是IO读写事件获取是handler
        Runnable r = (Runnable) (k.attachment());
        if (r != null) {
            r.run();  //方法调用，单线程
        }
    }

}

