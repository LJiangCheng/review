package com.ljc.review.common.concurrent.inpratice.章3对象的发布和共享;

public class NoVisibility {
    private static boolean ready;
    private static int number;

    /**
     * 程序可能持续循环下去，因为读线程可能永远看不到ready的值。读线程得到的是失效数据
     * 也可能输出0，因为指令重排序，这是一种更加奇怪的情况
     */
    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                yield();
                System.out.println(number);
            }
        }
    }

}
