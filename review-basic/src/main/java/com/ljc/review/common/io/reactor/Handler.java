package com.ljc.review.common.io.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Handler implements Runnable {
    private final SocketChannel channel;
    private final SelectionKey sk;
    private ByteBuffer input = ByteBuffer.allocate(1024);
    private ByteBuffer output = ByteBuffer.allocate(1024);

    Handler(Selector selector, SocketChannel c) throws IOException {
        channel = c;
        c.configureBlocking(false);
        // Optionally try first read now
        sk = channel.register(selector, SelectionKey.OP_READ);

        //绑定回调处理对象
        sk.attach(this);

        //第二步,注册Read就绪事件
        sk.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    public void run() {
        try {
            channel.read(input);
            input.flip();
            System.out.println(Charset.defaultCharset().newDecoder().decode(input).toString() + "==>" + Thread.currentThread().getName());
            input.clear();
            if (inputIsComplete()) {
                process();
                sk.attach(new Sender());  //状态迁移, Read后变成write, 用Sender作为新的callback对象
                sk.interestOps(SelectionKey.OP_WRITE);
                sk.selector().wakeup();
            }
        } catch (IOException ex) { /* ... */ }
    }

    private boolean inputIsComplete() {
        return false;
    }

    private boolean outputIsComplete() {
        return false;
    }

    private void process() {

    }

    class Sender implements Runnable {
        public void run(){
            try {
                channel.write(output);
                output.clear();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //write完就结束了, 关闭select key
                if (outputIsComplete()) {
                    sk.cancel();
                }
            }
        }
    }
}
