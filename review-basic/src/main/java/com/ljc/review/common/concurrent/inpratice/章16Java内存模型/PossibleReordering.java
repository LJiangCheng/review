package com.ljc.review.common.concurrent.inpratice.章16Java内存模型;

/**
 * 可能输出(1,1),(1,0),(0,1)甚至是(0,0)
 * 重排序：为了提高性能，编译器和处理器常常会对指令做重排序，重排序不会影响单线程程序语义，但会导致多线程执行的时候有数据不一致问题，
 * 导致多线程程序结果不是理想结果。
 * 在缺乏足够同步的情况下，由于重排序的存在程序的行为会变得不可预测
 */
public class PossibleReordering {
    static int x = 0, y = 0;
    static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread(new Runnable() {
            public void run() {
                a = 1;
                x = b;
            }
        });
        Thread other = new Thread(new Runnable() {
            public void run() {
                b = 1;
                y = a;
            }
        });
        one.start();
        other.start();
        one.join();
        other.join();
        System.out.println("( " + x + "," + y + ")");
    }
}
