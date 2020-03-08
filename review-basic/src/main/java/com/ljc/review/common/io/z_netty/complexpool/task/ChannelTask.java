package com.ljc.review.common.io.z_netty.complexpool.task;

import com.ljc.review.common.io.z_netty.complexpool.NettyClientHandler;
import com.ljc.review.common.io.z_netty.complexpool.NettyClientPool;
import com.ljc.review.common.io.z_netty.complexpool.util.Constant;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * 客户端任务的抽象
 */
public class ChannelTask implements Callable<String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelTask.class);

    private final NettyClientPool pool = NettyClientPool.getInstance();
    private String message;

    public ChannelTask(String message) {
        this.message = message;
    }

    @Override
    public String call() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //同一个线程使用同一个全局唯一的随机数，保证从同一个池中获取和释放资源，同时使用改随机数作为Key获取返回值，时间戳+6位随机数
        long random = Long.valueOf(sdf.format(new Date())) * 1000000 + Math.round(Math.random() * 1000000);
        //从连接池中获取一个连接
        Channel channel = pool.getChannel(random);
        LOGGER.debug("在链接池池中取到的Channel： " + channel.id());
        UnpooledByteBufAllocator allocator = new UnpooledByteBufAllocator(false);
        ByteBuf buffer = allocator.buffer(20);
        //使用固定分隔符的半包解码器，将数据写入buffer
        String msg = message + Constant.DELIMITER;
        buffer.writeBytes(msg.getBytes());
        LOGGER.info("SEND NO[{}] MESSAGE AND CHANNEL id [{}]", random, channel.id());
        //获取handler对象
        NettyClientHandler clientHandler = channel.pipeline().get(NettyClientHandler.class);
        //与服务器交互
        String serverMsg = clientHandler.sendMessage(buffer, channel);
        //通信完成，释放连接
        NettyClientPool.release(channel);
        return "请求NO[" + random + "] " + serverMsg;
    }
}














