package com.ljc.review.common.io.z_netty.simplepool.utils;

import io.netty.buffer.ByteBuf;

/**
 * 同步回调工具类
 */
public class CallbackService {
    public volatile ByteBuf result;

    public void receiveMessage(ByteBuf receiveBuf) {
        synchronized (this) {
            result = receiveBuf;
            //回调，唤醒等待响应的线程
            this.notify();
        }
    }
}
