package com.ljc.review.common.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * JavaApi的原生NIO服务端
 */
public class NIOServer {

    public static void main(String[] args) throws IOException {
        //1.serverSelector负责轮询是否有新的连接，服务端监测到新的连接之后注册到selector上，下次轮询时处理数据读取
        Selector serverSelector = Selector.open();
        try {
            // 对应IO编程中服务端启动
            ServerSocketChannel listenerChannel = ServerSocketChannel.open();
            listenerChannel.socket().bind(new InetSocketAddress(3333));
            listenerChannel.configureBlocking(false);
            //将通道注册到选择器上，并注册操作为："接收"
            listenerChannel.register(serverSelector, SelectionKey.OP_ACCEPT);
            while (true) {
                //监测是否有新的事件，如：新的通道连接、通道状态变化等； timeout:监听的阻塞等待时间ms； 返回值：事件数
                //即查询获取“准备就绪”的注册过的操作
                if (serverSelector.select(10000) > 0) {
                    //获取准备就绪的事件集合
                    //The selected-key set is the set of keys such that each key's channel was detected to be ready for
                    //at least one of the operations identified in the key's interest set during a prior selection operation
                    //The selected-key set is always a subset of the key set.
                    Set<SelectionKey> set = serverSelector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = set.iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        //根据事件类型处理事件
                        if (key.isAcceptable()) {
                            accept(key, serverSelector, keyIterator);
                        } else if (key.isReadable()) {
                            read(key, keyIterator);
                        } else if (key.isWritable()) {
                            write(key, keyIterator);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void accept(SelectionKey key, Selector serverSelector, Iterator<SelectionKey> keyIterator) throws IOException {
        try {
            //新通道：注册到Selector上，操作为：读取
            SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
            clientChannel.configureBlocking(false);
            clientChannel.register(serverSelector, SelectionKey.OP_READ);
        } finally {
            //事件处理完成之后从selected-key set中移除，否则会重复处理导致异常
            keyIterator.remove();
        }
    }

    private static void read(SelectionKey key, Iterator<SelectionKey> keyIterator) throws IOException {
        //"读就绪"状态的通道
        try {
            SocketChannel clientChannel = (SocketChannel) key.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
            //面向Buffer
            clientChannel.read(byteBuffer);
            byteBuffer.flip();
            System.out.println(Charset.defaultCharset().newDecoder().decode(byteBuffer).toString());
        } finally {
            //处理完从待处理集合中移除事件（通道所对应的的SelectionKey依然存在于key set中，触发选择后又会加入selected-key set）
            keyIterator.remove();
        }
    }

    private static void write(SelectionKey key, Iterator<SelectionKey> keyIterator) {

    }

}
