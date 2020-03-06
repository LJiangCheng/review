package com.ljc.review.common.io.z_netty.connpool.utils;

import io.netty.buffer.ByteBuf;

/**
 * 同步回调工具类
 */
public class CallbackService {
    public volatile ByteBuf result;

    public void receiveMessage(ByteBuf receiveBuf) {
        synchronized (this) {
            result = receiveBuf;
            this.notify();
        }
    }
}
