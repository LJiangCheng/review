package com.ljc.alg.designmode.simplefactory;

public class SquareDrawing implements ShapeDrawing {

    @Override
    public void draw() {
        System.out.println("画方！");
    }

    @Override
    public void erase() {
        System.out.println("擦除方！");
    }
}
