package com.ljc.review.common.concurrent.inpratice.章16Java内存模型;


import com.ljc.review.common.concurrent.inpratice.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

/**
 * 不可变对象的初始化安全性
 * 如果states不是final类型，或者存在除构造方法以外的方法能够修改states，那么初始化安全性将无法确保在缺少同步的情况下安全地
 * 访问SafeStates
 */
@ThreadSafe
public class SafeStates {
    private final Map<String, String> states;

    public SafeStates() {
        states = new HashMap<String, String>();
        states.put("alaska", "AK");
        states.put("alabama", "AL");
        /*...*/
        states.put("wyoming", "WY");
    }

    public String getAbbreviation(String s) {
        return states.get(s);
    }
}
