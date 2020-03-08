package com.ljc.review.common.io.z_netty.ademo;

import com.alibaba.fastjson.JSONObject;

public class User {

    private String userName;
    private Integer age;
    private String city;

    public User() {

    }

    public User(String userName, Integer age, String city) {
        this.userName = userName;
        this.age = age;
        this.city = city;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
