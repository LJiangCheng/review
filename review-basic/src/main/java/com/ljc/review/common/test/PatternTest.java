package com.ljc.review.common.test;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {

    @Test
    public void getByRegex() {
        String s = "尺子1231/2 23/822";
        Pattern p = Pattern.compile("\\d+/\\d+"); //将正则编译成模式
        Matcher m = p.matcher(s);    //匹配字符串中的此模式
        while (m.find()) {      //find()确定下一个
            System.out.println(m.group()); //group()输出下一个
        }
        s = m.replaceAll("");
        System.out.println(s);
    }

    @Test
    public void test() throws ParseException {
        Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-01-01 00:00:00");
        if (new Date().compareTo(endDate) < 0) {
            System.out.println(111);
        }
    }
}
