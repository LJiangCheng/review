package com.ljc.alg.pattern.creational.simplefactory;

import com.ljc.alg.pattern.creational.simplefactory.inter.ShapeDrawing;
import org.junit.Test;

public class MyTest implements Cloneable{

    private int a = 0;
    private Object obj = new Object();

    @Test
    public void test() throws CloneNotSupportedException {
        ShapeDrawing roundDrawing = ShapeDrawing.create("round");
        roundDrawing.draw();
        roundDrawing.erase();
        ShapeDrawing squareDrawing = ShapeDrawing.create("square");
        squareDrawing.draw();
        squareDrawing.erase();
        //ShapeDrawing.create("sanjiaoxing");
        MyTest clone = (MyTest) super.clone();
        System.out.println(this == clone);
        System.out.println(this.a == clone.a);
        System.out.println(this.obj == clone.obj);
    }

}
