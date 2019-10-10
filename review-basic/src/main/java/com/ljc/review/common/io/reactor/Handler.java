package com.ljc.review.common.io.reactor;

import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

/**
 * 数据的读写处理
 * 一个Channel对应一个Handler
 */
public class Handler implements Runnable {
    private final SocketChannel channel;
    private final SelectionKey sk;
    //缓冲区的大小决定了单次可读的最大数据量，如果过小则数据会被拆分
    private ByteBuffer input = ByteBuffer.allocate(1024);
    private ByteBuffer output = ByteBuffer.allocate(1024);
    //初始化线程池
    private static TaskExecutor taskExecutor = null;
    static {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setQueueCapacity(1000);
        threadPoolTaskExecutor.setKeepAliveSeconds(60);
        //配置任务拒绝时的处理机制 注：属于主线程，会阻塞主线程
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

    //构造器
    Handler(Selector selector, SocketChannel c) throws IOException {
        channel = c;
        channel.configureBlocking(false);
        // Optionally try first read now
        sk = channel.register(selector, SelectionKey.OP_READ);
        //绑定回调对象：处理事件
        sk.attach(this);
        //构造时关注Read事件
        sk.interestOps(SelectionKey.OP_READ);
        //唤醒选择器：立即执行并返回一次
        selector.wakeup();
    }

    //事件就绪后调用
    public void run() {
        if (sk.isReadable()) {
            read();
        } else if (sk.isWritable()) {
            write();
        }
    }

    //处理读
    private void read() {
        try {
            //TODO read操作必须放在reactor同一线程中，否则会出现很多空数据，为什么？
            channel.read(input);
            //使用多线程处理业务逻辑，提高服务性能
            taskExecutor.execute(this::processAndHandOff);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //处理写
    private void write() {
        try {
            channel.write(output);
            if (outputIsComplete()) {
                sk.cancel();  //写出完成后事件结束
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读是否完成
    private boolean inputIsComplete() {
        return false;
    }

    //写是否完成
    private boolean outputIsComplete() {
        return false;
    }

    /**
     * 数据读取到缓冲区后，业务逻辑及后续处理的同步方法
     */
    private synchronized void processAndHandOff() {
        process();
        //业务逻辑处理完成之后判断输入是否已经完成，以及下一步的状态变换
        if (inputIsComplete()) {
            sk.interestOps(SelectionKey.OP_WRITE);  //下一步处理写事件
            sk.selector().wakeup();
        }
    }

    /**
     * 具体的业务逻辑处理
     */
    private void process() {
        try {
            input.flip();
            String inputStr = Charset.defaultCharset().newDecoder().decode(input).toString();
            input.clear();
            System.out.println(inputStr + "==>" + Thread.currentThread().getName().replaceAll("ThreadPoolTaskExecutor-","线程"));
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }
    }

    private void sleep(int time) {
        if (time > 0) {
            try {
                //模拟业务处理占用的时间。处理时间过长会导致线程池任务排满，如果没有重入机制的话，则此次任务会被丢弃，但是通道中的数据不会丢失而是会积压，等待下次读取
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
