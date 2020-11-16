package com.ljc.review.common.io.reactor;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 主从分离的Reactor
 * 主Reactor：负责接收连接事件并转发到Acceptor
 * 从Reactor：负责接收读写事件并转发到Handler
 */
public class Reactor extends AbstractReactor {

    private static final int SUB_REACTORS_SIZE = 2;
    private static final Reactor[] SUB_REACTORS = new Reactor[SUB_REACTORS_SIZE];
    private static final AtomicInteger NEXT_INDEX = new AtomicInteger(0);

    //初始化subReactors
    static {
        IntStream.range(0, SUB_REACTORS_SIZE).forEach(i -> {
            try {
                SUB_REACTORS[i] = new Reactor(3333, 10, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    //构造器:区分主从Reactor
    Reactor(int port, int timeOut, boolean isMainReactor) throws IOException {
        super(port, timeOut, isMainReactor);
    }

    //循环获取subReactor
    public static Reactor nextSubReactor() {
        int curIdx = NEXT_INDEX.getAndIncrement();
        if (curIdx >= SUB_REACTORS_SIZE) {
            NEXT_INDEX.set(0);
            curIdx = 0;
        }
        return SUB_REACTORS[(curIdx % SUB_REACTORS_SIZE)];
    }

    //程序执行流程
    //mainReactor初始化：创建选择器，ServerSocketChannel初始化并监听3333端口，serverSocket将OP_ACCEPT事件注册到mainReactor的选择器上，同时绑定回调对象(Acceptor)
    //subReactors启动，创建选择器(此时没有事件注册)
    //mainReactor获取到新连接，将事件分发给绑定的acceptor
    //acceptor创建handler：从subReactors中获取一个reactor，并将读取事件绑定到对应的selector上，同时绑定回调对象(Handler)
    //subReactor线程获取到读取就绪事件，将事件分发给绑定的handler处理
    //handler读取数据，然后使用线程池进行业务逻辑处理
    //总结：mainReactor负责接收连接并交给acceptor，acceptor负责创建读写事件和对应的handler并绑定到subReactor，subReactor负责分发准备就绪的读写事件，handler负责具体执行
    public static void main(String[] args) throws IOException {
        //启动主Reactor线程，负责客户端连接就绪事件
        Reactor mainReactor = new Reactor(3333, 10, true);
        new Thread(mainReactor).start();
        //启动从Reactor线程，负责读写事件
        IntStream.range(0, SUB_REACTORS_SIZE).forEach(i -> new Thread(SUB_REACTORS[i]).start());
    }

}










