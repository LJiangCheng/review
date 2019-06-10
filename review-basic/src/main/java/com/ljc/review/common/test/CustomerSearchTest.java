package com.ljc.review.common.test;

import com.alibaba.fastjson.JSONObject;
import com.ljc.util.MD5;
import com.ljc.util.OkHttpUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerSearchTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerSearchTest.class);

    @Test
    public void push() {
        for (int i = 1; i <= 5000; i++) {
            long l1 = System.currentTimeMillis();
            String json = OkHttpUtils.doGet("http://localhost:9888/pc/customer/search?keyword=手电钻" + i);
            JSONObject object = JSONObject.parseObject(json);
            String code = object.getString("code");
            long l2 = System.currentTimeMillis();
            LOGGER.info("请求" + i + ("0000".equals(code) ? "成功" : "失败") + ",耗时：" + (l2 - l1));
        }
    }

    @Test
    public void md5() {
        for(int i=1;i<=1000;i++) {
            long l = System.currentTimeMillis();
            MD5.md5(i + "");
            long l1 = System.currentTimeMillis();
            LOGGER.info(l1 - l + "ms");
        }
    }

}


























