package com.ljc.review.rpc.dubbo.spi;

import com.ljc.review.rpc.dubbo.spi.robot.Robot;

import java.util.ServiceLoader;

/**
 * Java Service Provider Interface
 * 一种服务发现机制。
 * SPI的本质是将接口实现类的全限定名配置在文件中，并由服务加载器读取配置文件，加载实现类。
 * 这样可以在运行时，动态为接口替换实现类。正因此特性，我们可以很容易的通过 SPI 机制为我们的程序提供拓展功能。
 *
 * 实现：在META-INF/services文件夹下创建以接口全限定名为文件名的文件，其内容是所有需要加载的接口实现类
 */
public class JavaSpiTest {

    public static void main(String[] args) {
        ServiceLoader<Robot> load = ServiceLoader.load(Robot.class);
        System.out.println("JavaSpi");
        load.forEach(Robot::sayHello);
    }

}
