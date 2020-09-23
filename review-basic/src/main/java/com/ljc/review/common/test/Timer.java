package com.ljc.review.common.test;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Timer {

    @Test
    public void parse() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();
        Date parse = df.parse("09:30:00");
        System.out.println(parse);
        System.out.println(df.format(now));
        long l = 24l / 7;
        System.out.println(l);
        System.out.println(l % 3);
        int i = Integer.parseInt("09") - Integer.parseInt("00");
        System.out.println(i);
        System.out.println(Math.abs(-12));
    }

    @Test
    public void str() {
        String s = "000070006";
        int i = Integer.parseInt(s);
        System.out.println(++i + "");
    }

}
