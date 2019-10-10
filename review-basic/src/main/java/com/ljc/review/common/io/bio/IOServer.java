package com.ljc.review.common.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * IO服务端：使用多线程模拟异步IO
 *
 * 1.单线程同时只能处理一个连接请求
 * 2.基于多线程(使用线程池)可以获得类似异步IO的效果，但如果并发数过多则无法处理，要么请求阻塞要么由于线程数过多导致系统崩溃
 * 3.在活动连接数不是特别高(小于单机1000)的情况下，这种模型是比较不错的，可以让每一个连接专注于自己的I/O并且编程模型简单，
 * 也不用过多考虑系统的过载、限流等问题。线程池本身就是一个天然的漏斗，可以缓冲一些系统处理不了的连接或请求。但是，当面对十万
 * 甚至百万级连接的时候，传统的BIO模型是无能为力的
 */
public class IOServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3333);
        // 接收到客户端连接请求之后为每个客户端创建一个新的线程进行链路处理
        new Thread(() -> {
            while (true) {
                try {
                    // 阻塞方法获取新的连接
                    Socket socket = serverSocket.accept();
                    // 每一个新的连接都创建一个线程，负责读取数据
                    new Thread(() -> {
                        try {
                            int len;
                            byte[] data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();
                            // 按字节流方式读取数据
                            while ((len = inputStream.read(data)) != -1) {  //只要IOClient的传输没有停止，while方法就会继续。服务端线程只能和客户端一一对应
                                System.out.println(new String(data, 0, len));
                            }
                        } catch (IOException e) {
                        }
                    }).start();

                } catch (IOException e) {
                }
            }
        }).start();
    }
}
