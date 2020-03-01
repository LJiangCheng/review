package com.ljc.alg.leetcode.thread;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 现在有两种线程，氢hydrogen和氧oxygen，你的目标是组织这两种线程来产生水分子。
 * 存在一个屏障（barrier）使得每个线程必须等候直到一个完整水分子能够被产生出来。
 * 氢和氧线程会被分别给予 releaseHydrogen 和 releaseOxygen 方法来允许它们突破屏障。
 * 这些线程应该三三成组突破屏障并能立即组合产生一个水分子。
 * 你必须保证产生一个水分子所需线程的结合必须发生在下一个水分子产生之前。
 * <p>
 * 换句话说:
 * 如果一个氧线程到达屏障时没有氢线程到达，它必须等候直到两个氢线程到达。
 * 如果一个氢线程到达屏障时没有其它线程到达，它必须等候直到一个氧线程和另一个氢线程到达。
 * 书写满足这些限制条件的氢、氧线程同步代码。
 * <p>
 * 示例 1:
 * 输入: "HOH"
 * 输出: "HHO"
 * 解释: "HOH" 和 "OHH" 依然都是有效解。
 * <p>
 * 示例 2:
 * 输入: "OOHHHH"
 * 输出: "HHOHHO"
 * 解释: "HOHHHO", "OHHHHO", "HHOHOH", "HOHHOH", "OHHHOH", "HHOOHH", "HOHOHH" 和 "OHHOHH" 依然都是有效解。
 * <p>
 * 限制条件:
 * <p>
 * 输入字符串的总长将会是 3n, 1 ≤ n ≤ 50；
 * 输入字符串中的 “H” 总数将会是 2n；
 * 输入字符串中的 “O” 总数将会是 n。
 * <p>
 * 主要问题：如何让线程三三成组？
 */
public class H2O {
    //线程池
    private ExecutorService executor = Executors.newFixedThreadPool(8);
    //栅栏
    private CyclicBarrier barrier = new CyclicBarrier(3);
    //信号量
    private Semaphore h2oSemaphore = new Semaphore(0);
    private Semaphore groupSemaphore = new Semaphore(0);

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        H2O h2O = new H2O();
        h2O.h2oSync("HOHHHOHHHHHHHHHHOOOOOHOHHOHHHOHHHHHHHHHHOOOOOHOH");
    }

    private void h2oSync(String input) throws BrokenBarrierException, InterruptedException {
        //输入校验
        if (input == null || input.equals("")) {
            System.out.println("字符串为空！");
            return;
        }
        List<String> chars = Arrays.asList(input.split(""));
        List<String> hList = chars.stream().filter((s) -> s.equals("H")).collect(Collectors.toList());
        List<String> oList = chars.stream().filter((s) -> s.equals("O")).collect(Collectors.toList());
        if (hList.size() == 0 || oList.size() == 0 || (hList.size() != oList.size() * 2)) {
            System.out.println("输入有误，H应是O的两倍");
            return;
        }
        //输出H2O
        for (int i = 0; i < oList.size(); i++) {
            executor.submit(() -> {
                try {
                    hydrogen(() -> System.out.println("H"));
                } catch (BrokenBarrierException | InterruptedException ignored) {
                }
            });
            executor.submit(() -> {
                try {
                    hydrogen(() -> System.out.println("H"));
                } catch (BrokenBarrierException | InterruptedException ignored) {
                }
            });
            executor.submit(() -> {
                try {
                    oxygen(() -> System.out.println("O"));
                } catch (BrokenBarrierException | InterruptedException ignored) {
                }
            });
            groupSemaphore.acquire(3);
        }
        //关闭线程池
        executor.shutdown();
    }

    private void hydrogen(Runnable releaseHydrogen) throws BrokenBarrierException, InterruptedException {
        // releaseHydrogen() outputs "H". Do not change or remove this line.
        barrier.await();
        releaseHydrogen.run();
        h2oSemaphore.release();
        groupSemaphore.release();
    }

    private void oxygen(Runnable releaseOxygen) throws BrokenBarrierException, InterruptedException {
        // releaseOxygen() outputs "O". Do not change or remove this line.
        barrier.await();
        h2oSemaphore.acquire(2);
        releaseOxygen.run();
        groupSemaphore.release();
    }

}














































