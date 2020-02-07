package com.ljc.review.common.concurrent.inpratice.章15原子变量与非同步阻塞机制.random;

import com.ljc.review.common.concurrent.inpratice.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用AtomicInteger实现的随机数生成器
 * 每生成下一个随机数时需要用到上一个数字，所以类中必须记录前一个数值作为其状态的一部分。
 * 对两个随机数生成器的性能测试：
 * 在竞争程度较高的情况下(一般很少出现)随着线程数量增加锁的吞吐率略高于AtomicInteger
 * 但在竞争程度适中的情况下，AtomicInteger的吞吐率显著高于锁
 * 这说明：
 * 在中低程度的竞争下，原子变量能提供更高的可伸缩性，而在高强度竞争下，锁能有效避免竞争
 * 在实际情况中，原子变量在可伸缩性上要高于锁，因为在应对常见的竞争时，原子变量的效率会更高。
 */
@ThreadSafe
public class AtomicPseudoRandom extends PseudoRandom {
    private AtomicInteger seed;

    public AtomicPseudoRandom(int seed) {
        this.seed = new AtomicInteger(seed);
    }

    public int nextInt(int n) {
        while (true) {
            int s = seed.get();
            int nextSeed = calculateNext(s);
            //比较并交换。如果在此期间seed已被更新过，则操作失败进入重试
            if (seed.compareAndSet(s, nextSeed)) {
                int remainder = s % n;
                return remainder > 0 ? remainder : remainder + n;
            }
        }
    }

}





