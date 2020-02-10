package com.ljc.review.common.concurrent.inpratice.章5基础模块.cache.compute;

import java.math.BigInteger;

/**
 * 因式分解计算缓存使用示例
 */
public class UseCacheDemo {

    private final Computable<BigInteger, BigInteger[]> c = this::factor;
    private final Computable<BigInteger, BigInteger[]> cacheDelegate = new Memoizer<>(c);

    public void service(BigInteger i) throws InterruptedException {
        BigInteger[] result = cacheDelegate.compute(i);
    }

    private BigInteger[] factor(BigInteger i) {
        //巨量计算之后....
        return new BigInteger[]{i};
    }

}
