package com.ljc.review.common.io.z_netty.simplepool.utils;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.Map;

/**
 * 工具类
 * 负责处理channel、dataMap和CallbackService的关联
 */
public class ChannelUtils {
    public static final int MESSAGE_LENGTH = 16;
    public static final AttributeKey<Map<Integer, Object>> DATA_MAP_ATTRIBUTEKEY = AttributeKey.valueOf("dataMap");

    public static <T> void putCallback2DataMap(Channel channel, int seq, T callback) {
        channel.attr(DATA_MAP_ATTRIBUTEKEY)   //通过给定的AttributeKey获取Channel中的Attribute
                .get()                //获取Attribute的当前值(已知为Map)。可能为null，所以在channel初始化时就要绑定一个ConcurrentHashMap
                .put(seq, callback);  //放入seq-callback，建立关联关系。回调时通过seq获取callback进行回调处理(通过notify机制通知到正确的线程)
    }

    @SuppressWarnings("unchecked")
    public static <T> T removeCallback(Channel channel, int seq) {
        return (T) channel
                .attr(DATA_MAP_ATTRIBUTEKEY)
                .get()
                .remove(seq);
    }

}
