package com.ljc.review.common.concurrent.inpratice.章5基础模块.cache;

import java.math.BigInteger;

/**
 * 计算接口实现示例
 * 假设compute方法需要大量计算，则最好使用一种缓存机制复用计算结果
 */
public class ExpensiveFunction implements Computable<String, BigInteger> {

    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        //经过长时间的计算后
        return new BigInteger(arg);
    }

}
