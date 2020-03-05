package com.ljc.review.common.io.z_netty.timerdemo.server;

import com.alibaba.fastjson.JSONObject;
import com.ljc.review.common.io.z_netty.timerdemo.User;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class TimeEncoder extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws UnsupportedEncodingException {
        User user = (User) msg;
        ByteBuf encoded = ctx.alloc().buffer(1);
        encoded.writeBytes(JSONObject.toJSONString(user).getBytes(StandardCharsets.UTF_8));
        ctx.write(encoded, promise); // (1)
    }
}
