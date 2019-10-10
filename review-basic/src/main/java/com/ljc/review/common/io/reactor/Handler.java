package com.ljc.review.common.io.reactor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * 处理读写业务逻辑
 */
public class Handler implements Runnable {
    private final SocketChannel channel;
    private final SelectionKey sk;
    private ByteBuffer output = ByteBuffer.allocate(1024);
    //多线程处理业务逻辑
    private static TaskExecutor taskExecutor = null;
    static {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setQueueCapacity(1000);
        threadPoolTaskExecutor.setKeepAliveSeconds(60);
        //任务拒绝时的处理机制：属于主线程，会阻塞主线程
        threadPoolTaskExecutor.setRejectedExecutionHandler((r, executor) -> {
            System.out.println("任务拒绝，尝试重新添加到队列！");
            try {
                Thread.sleep(100);
                executor.execute(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadPoolTaskExecutor.initialize();
        taskExecutor = threadPoolTaskExecutor;
    }

    Handler(Selector selector, SocketChannel c) throws IOException {
        channel = c;
        channel.configureBlocking(false);
        // Optionally try first read now
        sk = channel.register(selector, SelectionKey.OP_READ);

        //绑定回调对象：处理读事件
        sk.attach(this);

        //注册Read就绪事件
        sk.interestOps(SelectionKey.OP_READ);
        //唤醒选择器：立即执行并返回一次
        selector.wakeup();
    }

    //读写事件就绪，处理事件。使用线程池
    public void run() {
        if (sk.isReadable()) {
            //read();
            taskExecutor.execute(this::read);
        } else if (sk.isWritable()) {
            //write();
            taskExecutor.execute(this::write);
        }
    }

    private void read() {
        /*try {
            //模拟业务处理占用的时间。处理时间过长会导致线程池任务排满，如果没有重入机制的话，则此次任务会被丢弃，但是通道中的数据不会丢失而是会积压，等待下次读取
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        try {
            ByteBuffer input = ByteBuffer.allocate(1024);    //缓冲区的大小决定了单次可读的最大数据量，如果过小则数据会被拆分
            channel.read(input);
            input.flip();
            String inputStr = Charset.defaultCharset().newDecoder().decode(input).toString();
            //就绪事件的疑问：为什么准备就绪的通道中会读取到如此之多的空信息？没有数据传输时通道的状态为什么会是读就绪？这个问题不解决会导致非常多的无用任务加入线程池
            //if (StringUtils.isNotEmpty(inputStr)) {
                System.out.println(inputStr + "==>" + Thread.currentThread().getName().replaceAll("ThreadPoolTaskExecutor-","线程"));
            //}
            if (inputIsComplete()) {
                process();  //业务处理
                sk.interestOps(SelectionKey.OP_WRITE);  //下一步处理写事件
                sk.selector().wakeup();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write() {
        try {
            channel.write(output);
            //output.clear();
            if (outputIsComplete()) {
                process();  //业务处理
                sk.interestOps(SelectionKey.OP_READ);  //下一步处理读事件
                sk.selector().wakeup();
                //sk.cancel();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean inputIsComplete() {
        return false;
    }

    private boolean outputIsComplete() {
        return false;
    }

    private void process() {

    }

}
