package com.ljc.review.common.io.bio;

import java.io.IOException;
import java.net.Socket;

/**
 * 模拟io客户端
 */
public class IOClient {

    public static void main(String[] args) {
        //并发模拟:10个客户端同时建立连接
        for(int i=0;i<10;i++) {
            int j = i;
            new Thread(() -> {
                try {
                    Socket socket = new Socket("127.0.0.1", 3333);
                    while (true) {
                        try {
                            socket.getOutputStream().write(("发送者" + j + ": " + System.currentTimeMillis() / (1000)).getBytes());
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }
                    }
                } catch (IOException e) {/* ... */}
            }).start();
        }
    }

}
