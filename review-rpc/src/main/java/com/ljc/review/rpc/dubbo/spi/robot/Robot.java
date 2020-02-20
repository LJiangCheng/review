package com.ljc.review.rpc.dubbo.spi.robot;

import com.alibaba.dubbo.common.extension.SPI;

@SPI  //使用DUBBO SPI需要这个注解
public interface Robot {
    void sayHello();
}
