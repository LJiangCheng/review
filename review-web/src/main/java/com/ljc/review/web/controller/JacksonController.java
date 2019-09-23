package com.ljc.review.web.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ljc.review.web.serialize.JacksonSerializeString;
import com.ljc.review.web.utils.BaseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("jackson")
public class JacksonController {

    @GetMapping("defaultValue")
    public BaseResult defaultValue() {
        User user = new User();
        user.setId(null);
        user.setUserName("");
        user.setAge(null);
        return BaseResult.success(user);
    }

    /**
     * 测试Jackson默认值的设置
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class User {
        @JsonSerialize(nullsUsing = JacksonSerializeString.class)  //使用自定义的字符串序列化处理类
        private String id;
        private String userName;
        private Integer age;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
    }
}
