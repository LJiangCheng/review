package com.ljc.review.common.io.z_netty.simplepool.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SocketServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //获取当前连接
        Channel channel = ctx.channel();
        ByteBuf buf = (ByteBuf) msg;
        //读取消息长度，将下标后移4位
        int length = buf.readInt();
        //读取消息序列号
        int seq = buf.readInt();
        //读取消息头部
        byte[] head = new byte[8];
        buf.readBytes(head);
        String headString = new String(head);
        System.out.println(headString);
        //读取消息体
        byte[] body = new byte[4];
        buf.readBytes(body);
        String bodyString = new String(body);
        //新建立一个缓存区,写入内容,返回给客户端
        UnpooledByteBufAllocator allocator = new UnpooledByteBufAllocator(false);
        ByteBuf responseBuf = allocator.buffer(20);
        responseBuf.writeInt(length);
        responseBuf.writeInt(seq);
        responseBuf.writeBytes(headString.getBytes());
        responseBuf.writeBytes(bodyString.getBytes());
        //将数据写回到客户端
        channel.writeAndFlush(responseBuf);
    }
}
