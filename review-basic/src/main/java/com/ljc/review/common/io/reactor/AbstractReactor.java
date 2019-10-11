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
public abstract class AbstractReactor implements Runnable {

    protected final Selector selector;
    protected final int timeOut;

    //Reactor构造初始化
    AbstractReactor(int port, int timeOut, boolean isMainReactor) throws IOException {
        this.timeOut = timeOut;
        //每个Reactor都有自己的选择器
        selector = Selector.open();
        if (isMainReactor) {
            //由主Reactor监听端口
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            serverSocket.socket().bind(new InetSocketAddress(port));
            //非阻塞
            serverSocket.configureBlocking(false);
            //初始化，接收accept事件，注册到MainReactor的selector上
            SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            //为accept绑定回调处理对象
            sk.attach(new Acceptor(serverSocket));
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                if (selector.select(timeOut) > 0) {
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

    protected void dispatch(SelectionKey k) {
        // 若是连接事件获取是acceptor
        // 若是IO读写事件获取是handler
        Runnable r = (Runnable) (k.attachment());
        if (r != null) {
            r.run();  //方法调用，单线程
        }
    }

}

