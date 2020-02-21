package com.ljc.review.rpc.dubbo.spi;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.ljc.review.rpc.dubbo.spi.robot.Robot;

/**
 * Dubbo Spi测试
 * 重新实现的一套功能更强的SPI机制
 */
public class DubboSpiTest {

    private String name = "";

    /**
     * spi入口
     */
    public void spi() {
        ExtensionLoader<Robot> loader = ExtensionLoader.getExtensionLoader(Robot.class);
        Robot optimusPrime = loader.getExtension("optimusPrime");
        optimusPrime.sayHello();
        Robot bumblebee = loader.getExtension("bumblebee");
        bumblebee.sayHello();
        System.out.println(new DubboSpiTest().name);
    }

    /**
     * 自适应扩展入口
     */
    public void extension() {

    }

}
