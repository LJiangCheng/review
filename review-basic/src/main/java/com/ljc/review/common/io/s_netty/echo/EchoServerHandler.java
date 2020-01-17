package com.ljc.review.common.io.s_netty.echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handles a server side channel
 * Print and echo any incoming data
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        // Discard the received data silently.
        //ByteBuf in = (ByteBuf) msg;
        try {
            //System.out.println(in.toString(CharsetUtil.UTF_8));
        } finally {
            //ReferenceCountUtil.release(in); // (3)  It is the handler's responsibility to release any reference-counted object passed to the handler
        }
        ctx.writeAndFlush(msg);  //did not release the received message,because Netty releases it for you when it is written out to the wire
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

}
