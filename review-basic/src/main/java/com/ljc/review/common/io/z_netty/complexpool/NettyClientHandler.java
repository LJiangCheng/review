package com.ljc.review.common.io.z_netty.complexpool;

import com.ljc.review.common.io.z_netty.complexpool.util.Constant;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Channel事件处理器
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClientHandler.class);

    /**
     * 使用阻塞式LinkedBlockingQueue，对响应结果保存，用于记录通道响应的结果集
     */
    private static final Map<Long, LinkedBlockingQueue<String>> RESULT_MAP = new ConcurrentHashMap<>();

    //发送请求的函数，之所以放在这里是因为要用到RESULT_MAP
    public String sendMessage(ByteBuf message, Channel ch) {
        //请求线程会在发出请求后，需要同步等待服务端的返回
        //使用LinkedBlockingQueue进行等待，在Netty接受到响应之后，通知请求线程结果
        LinkedBlockingQueue<String> linked = new LinkedBlockingQueue<>(1);
        //获取channel中存储的全局唯一随机值并关联到结果集
        Long randomId = ch.attr(AttributeKey.<Long>valueOf(Constant.RANDOM_KEY)).get();
        RESULT_MAP.put(randomId, linked);
        //正式写出数据
        ch.writeAndFlush(message);
        //等待响应
        String res = null;
        try {
            //设置3分钟的获取超时时间或者使用take()--获取不到返回结果一直阻塞
            res = RESULT_MAP.get(randomId).poll(3, TimeUnit.MINUTES);
            RESULT_MAP.remove(randomId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回调用结果
        return res;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String message = null;
        if (msg instanceof String) {
            message = msg.toString();
        } else if (msg instanceof ByteBuf) {
            message = ((ByteBuf) msg).toString(Charset.defaultCharset());
        }
        //获取channel中存储的全局唯一随机值（和simplePool实现不同，这里每个channel都只对应一次请求）
        Long randomId = ctx.channel().attr(AttributeKey.<Long>valueOf(Constant.RANDOM_KEY)).get();
        LOGGER.info(" READ INFO 服务端返回结果:" + message);
        LinkedBlockingQueue<String> linked = RESULT_MAP.get(randomId);
        //往阻塞队列中添加数据，poll/take线程即可被唤醒
        linked.add(message);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        boolean active = ctx.channel().isActive();
        LOGGER.debug("[此时通道状态] {}", active);
    }

    private volatile static Map<Integer, Set<Channel>> coreChannel = new HashMap<>();

    /**
     * 用户事件被触发时调用。结合心跳检测进行通道的动态回收
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        Channel channel = ctx.channel();
        //判断事件类型
        if (evt instanceof IdleStateEvent) {
            LOGGER.info("[客户端心跳监测发送] 通道编号：{}", ctx.channel().id());
            //当客户端开始发送心跳检测时，说明没有业务请求过来，释放通道数为设定的CORE_CONNECTIONS
            if (channel.isActive()) {
                //使用pool的hash值作为Key，维护CORE_CONNECTIONS个数个通道，多余的关闭
                int poolHash = NettyClientPool.getPoolHash(channel);
                Set<Channel> channels = coreChannel.get(poolHash);
                channels = channels == null ? new HashSet<>(Constant.CORE_CONNECTIONS) : channels;
                channels.add(channel);
                if (channels.stream().filter(Channel::isActive).count() > Constant.CORE_CONNECTIONS) {
                    LOGGER.info("关闭 CORE_CONNECTIONS 范围之外的通道：{}", channel.id());
                    channels.remove(channel);
                    //关闭channel
                    channel.close();
                }
                //更新coreChannel
                coreChannel.put(poolHash, channels);
            }
            //ToDo 这一段的含义是否覆盖了心跳检测？
            String heartBeat = Constant.HEART_BEAT + Constant.DELIMITER;
            ByteBuf byteBuf = Unpooled.copiedBuffer(heartBeat.getBytes());
            channel.writeAndFlush(byteBuf);
        } else {
            //非心跳检测事件，不作处理
            super.userEventTriggered(ctx, evt);
        }
    }

}
