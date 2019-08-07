package com.ljc.alg.pattern.creational.prototype.abs;

import com.ljc.alg.pattern.SysException;

import java.io.*;

public abstract class DeepClone implements Serializable {
    private String content;
    private Integer num1;
    private int num2;
    private ShallowClone shallowClone;

    public DeepClone(String content, Integer num1, int num2) {
        this.content = content;
        this.num1 = num1;
        this.num2 = num2;
    }

    /**
     * 深克隆实现方式之一：序列化和反序列化
     */
    public DeepClone deepClone() {
        try {
            //将对象写入流中
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            //从流中读取对象
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            DeepClone deepClone = (DeepClone) ois.readObject();
            ois.close();
            oos.close();
            return deepClone;
        } catch (IOException e) {
            throw SysException.instance("不支持序列化！");
        } catch (ClassNotFoundException e) {
            throw SysException.instance("反序列化失败，无对应类型！");
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

    public ShallowClone getShallowClone() {
        return shallowClone;
    }

    public void setShallowClone(ShallowClone shallowClone) {
        this.shallowClone = shallowClone;
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
