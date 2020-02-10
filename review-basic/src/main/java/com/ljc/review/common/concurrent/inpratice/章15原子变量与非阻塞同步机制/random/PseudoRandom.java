package com.ljc.review.common.concurrent.inpratice.章15原子变量与非阻塞同步机制.random;

/**
 * 伪随机数生成器基类
 */
public class PseudoRandom {
    int calculateNext(int prev) {
        prev ^= prev << 6;
        prev ^= prev >>> 21;
        prev ^= (prev << 7);
        return prev;
    }
}