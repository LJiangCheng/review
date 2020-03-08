package com.ljc.review.common.io.z_netty.complexpool.server;

import com.ljc.review.common.io.z_netty.complexpool.util.Constant;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

public class PoolServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoolServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String message = null;
        if (msg instanceof String) {
            message = msg.toString();
        } else if (msg instanceof ByteBuf) {
            message = ((ByteBuf) msg).toString(Charset.defaultCharset());
        }
        //获取channel中存储的全局唯一随机值（和simplePool实现不同，这里每个channel都只对应一次请求）
        Channel channel = ctx.channel();
        //新建立一个缓存区,写入内容,返回给客户端
        UnpooledByteBufAllocator allocator = new UnpooledByteBufAllocator(false);
        ByteBuf responseBuf = allocator.buffer(20);
        message = message + Constant.DELIMITER;
        responseBuf.writeBytes(message.getBytes());
        //将数据写回到客户端
        channel.writeAndFlush(responseBuf);
        LOGGER.info("=====>Get client info :" + message);
    }
}
