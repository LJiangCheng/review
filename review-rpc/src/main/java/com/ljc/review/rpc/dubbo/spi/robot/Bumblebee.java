package com.ljc.review.rpc.dubbo.spi.robot;

public class Bumblebee implements Robot {

    @Override
    public void sayHello() {
        System.out.println("I'm Bumblebee!");
    }
}
