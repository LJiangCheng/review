package com.ljc.alg.designmode.simplefactory;

public class RoundDrawing implements ShapeDrawing {

    @Override
    public void draw() {
        System.out.println("画圆！");
    }

    @Override
    public void erase() {
        System.out.println("擦除圆！");
    }
}
