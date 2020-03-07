package com.ljc.review.common.io.z_netty.connpool;

import com.ljc.review.common.io.z_netty.connpool.utils.CallbackService;
import com.ljc.review.common.io.z_netty.connpool.utils.ChannelUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 通道数据读取处理器
 */
public class SocketClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Channel channel = ctx.channel();
        ByteBuf responseBuf = (ByteBuf) msg;
        responseBuf.markReaderIndex();
        //获取消息序列号
        responseBuf.readInt();
        int seq = responseBuf.readInt();
        responseBuf.resetReaderIndex();
        //根据序列号获取对应的callback
        CallbackService callbackService = ChannelUtils.removeCallback(channel, seq);
        //唤醒调用线程
        callbackService.receiveMessage(responseBuf);
    }
}
