package com.ljc.alg.pattern.creational.prototype.abs;

import com.ljc.alg.pattern.SysException;

import java.io.Serializable;

public abstract class ShallowClone implements Cloneable,Serializable {
    private String content;
    private Integer num1;
    private int num2;
    private DeepClone deepClone;

    public ShallowClone(String content, Integer num1, int num2) {
        this.content = content;
        this.num1 = num1;
        this.num2 = num2;
    }

    @Override
    public ShallowClone clone() {
        try {
            Object obj = super.clone();
            return (ShallowClone) obj;
        } catch (CloneNotSupportedException e) {
            throw SysException.instance("不支持克隆！");
        }
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setNum1(Integer num1) {
        this.num1 = num1;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }

    public DeepClone getDeepClone() {
        return deepClone;
    }

    public void setDeepClone(DeepClone deepClone) {
        this.deepClone = deepClone;
    }

    public String getContent() {
        return content;
    }

    public Integer getNum1() {
        return num1;
    }

    public int getNum2() {
        return num2;
    }

}
