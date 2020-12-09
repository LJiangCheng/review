package com.ljc.review.common.test;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {

    @Test
    public void getByRegex() {
        String s = "尺子1/2 3/8";
        Pattern p = Pattern.compile("\\d{1,2}/\\d{1,2}"); //将正则编译成模式
        Matcher m = p.matcher(s);    //匹配字符串中的此模式
        while (m.find()) {      //find()确定下一个
            System.out.println(m.group()); //group()输出下一个
        }
        s = m.replaceAll("");
        System.out.println(s);
    }
}
