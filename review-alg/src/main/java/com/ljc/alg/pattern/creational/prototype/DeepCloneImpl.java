package com.ljc.alg.pattern.creational.prototype;

import com.ljc.alg.pattern.creational.prototype.abs.DeepClone;

/**
 * 浅克隆：当对象被复制时只复制它本身和其中包含的值类型(包括字符串)的成员变量，而引用类型的成员对象并没有复制，复制的只是引用地址
 * 实现：使用Object的native方法clone()
 * <p>
 * 深克隆：当对象被复制时不仅复制它本身和其中包含的值类型的成员变量，引用类型的成员对象也会被复制，并且如果该对象中如果还有引用类型也会一起被复制
 * 实现：使用序列化等
 */
public class DeepCloneImpl extends DeepClone {
    public DeepCloneImpl(String content, Integer num1, int num2) {
        super(content, num1, num2);
    }
}
