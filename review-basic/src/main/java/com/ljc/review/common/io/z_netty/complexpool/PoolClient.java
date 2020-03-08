package com.ljc.review.common.io.z_netty.complexpool;

import com.ljc.review.common.io.z_netty.complexpool.task.NettyTaskPool;

/**
 * 实现simplepool未实现的功能
 */
public class PoolClient {

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            //模拟多线程客户端，提交任务
            final int n = i;
            new Thread(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        String longMsgBody = n + "" + j + "中华人民共和国,中华人民共和国,中华人民共和国,中华人民共和国,中华人民共和国";
                        String response = NettyTaskPool.submitTask(longMsgBody);
                        System.out.println(n + "" + j + "==" + response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
