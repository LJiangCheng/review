package com.ljc.alg.pattern.creational.prototype;

import com.ljc.alg.pattern.creational.prototype.abs.DeepClone;
import com.ljc.alg.pattern.creational.prototype.abs.ShallowClone;
import org.junit.Test;

public class ZTest {

    @Test
    public void test() {
        //循环引用的对象 针对抽象类编程
        ShallowClone shallow = new ShallowCloneImpl("shallow", 1, 2);
        DeepClone deep = new DeepCloneImpl("deep", 3, 4);
        shallow.setDeepClone(deep);
        deep.setShallowClone(shallow);

        //测试浅克隆
        ShallowClone shallowClone = shallow.clone();
        System.out.println(shallow == shallowClone);  //false 克隆创建了一个新对象
        System.out.println(shallow.getContent() == shallowClone.getContent());  //true 引用的是同一个对象       String
        System.out.println(shallow.getNum1() == shallowClone.getNum1());  //true 引用的是同一个对象             Integer
        System.out.println(shallow.getNum2() == shallowClone.getNum2());  //true 基本类型，值相同               int
        System.out.println(shallow.getDeepClone() == shallowClone.getDeepClone());  //true 引用的是同一个对象   Object
        System.out.println("========================");
        //测试深克隆
        DeepClone deepClone = deep.deepClone();
        System.out.println(deep == deepClone);   //false 克隆创建了一个新对象
        System.out.println(deep.getContent() == deepClone.getContent());  //false 引用的是不同对象              String
        System.out.println(deep.getNum1() == deepClone.getNum1());  //false 引用的是不同对象                    Integer
        System.out.println(deep.getNum2() == deepClone.getNum2());  //true 基本类型，值相同                     int
        System.out.println(deep.getShallowClone() == deepClone.getShallowClone());  //false 引用的是不同对象    Object

    }

}
