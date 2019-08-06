package com.ljc.alg.design.creational.simplefactory;

import com.ljc.alg.design.creational.simplefactory.inter.ShapeDrawing;
import org.junit.Test;

public class MyTest {

    @Test
    public void test() {
        ShapeDrawing roundDrawing = ShapeDrawing.create("round");
        roundDrawing.draw();
        roundDrawing.erase();
        ShapeDrawing squareDrawing = ShapeDrawing.create("square");
        squareDrawing.draw();
        squareDrawing.erase();
        ShapeDrawing.create("sanjiaoxing");
    }

}
