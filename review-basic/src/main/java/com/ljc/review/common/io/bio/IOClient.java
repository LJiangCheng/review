package com.ljc.review.common.io.bio;

import java.io.IOException;
import java.net.Socket;

/**
 * 模拟io客户端
 */
public class IOClient {

    public static void main(String[] args) {
        //并发模拟
        for(int i=1;i<6;i++) {
            int j = i;
            new Thread(() -> {
                try {
                    Socket socket = new Socket("127.0.0.1", 3333);
                    while (true) {
                        try {
                            socket.getOutputStream().write(("Thread" + j + ": hello world").getBytes());
                            Thread.sleep(2000);
                        } catch (Exception e) {
                        }
                    }
                } catch (IOException e) {
                }
            }).start();
        }
    }

}
