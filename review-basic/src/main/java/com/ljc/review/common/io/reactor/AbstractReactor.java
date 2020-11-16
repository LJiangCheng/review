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

    /**
     * Reactor构造初始化
     * 主从reactor的区别仅在于注册的channel不同：
     * 主reactor注册ServerSocketChannel用于处理连接事件（接收连接和分发到从reactor）
     * 从reactor注册SocketChannel用于处理客户端读写事件（读写数据和分发到handler）
     * 概念：
     * Selector代表选择器，选择器只关心事件而不关心事件的来源
     * 多个ServerSocketChannel和SocketChannel可以同时注册到一个selector上，本例只注册了一个
     * ServerSocketChannel代表一个服务端程序，监听着一个端口
     * SocketChannel代表一个客户端连接，由ServerSocketChannel接收就绪连接后产生
     */
    AbstractReactor(int port, int timeOut, boolean isMainReactor) throws IOException {
        this.timeOut = timeOut;
        //每个Reactor都有自己的选择器
        selector = Selector.open();
        if (isMainReactor) {
            //创建ServerSocketChannel监听端口，并注册到主reactor上
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            serverSocket.socket().bind(new InetSocketAddress(port));
            //非阻塞
            serverSocket.configureBlocking(false);
            //初始化，接收accept事件，注册到MainReactor的selector上
            SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            //为连接就绪绑定回调处理对象
            sk.attach(new Acceptor(serverSocket));
        }
    }

    /**
     * run方法，主从reactor共用，因为其执行逻辑是一样的
     * 轮询 -- 获取事件 -- 分发处理事件
     */
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                if (selector.select(timeOut) > 0) {
                    Set<SelectionKey> selected = selector.selectedKeys();
                    Iterator<SelectionKey> it = selected.iterator();
                    while (it.hasNext()) {
                        //主从同样的处理逻辑，不同之处在于各自SelectionKey绑定的回调处理器不同
                        dispatch(it.next());
                        it.remove();
                    }
                }
            }
        } catch (IOException ex) { /* ... */ }
    }

    protected void dispatch(SelectionKey k) {
        //主reactor处理连接就绪：k.attachment()获取到Acceptor
        //从reactor处理读写就绪：k.attachment()获取到Handler
        Runnable r = (Runnable) (k.attachment());
        if (r != null) {
            r.run();  //方法调用，同一线程
        }
    }

}

