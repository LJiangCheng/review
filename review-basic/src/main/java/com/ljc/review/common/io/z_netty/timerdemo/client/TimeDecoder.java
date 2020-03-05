package com.ljc.review.common.io.z_netty.timerdemo.client;

import com.alibaba.fastjson.JSONObject;
import com.ljc.review.common.io.z_netty.timerdemo.User;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

public class TimeDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
        String value = in.readCharSequence(128, Charset.forName("UTF-8")).toString().trim();
        if (!value.endsWith("}")) {
            return;
        }
        out.add(JSONObject.parseObject(value, User.class));
    }

}
