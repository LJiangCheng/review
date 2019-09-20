package com.ljc.review.common.test;

import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.nio.Buffer;
import java.util.*;

public class BaseTest {

    public static void main(String[] args) {
        try {
            int i = 10 / 0;
        } catch (Exception e) {
            //throw e;
        }
        System.out.println("aaa");
    }

    @Test
    public void math() {
        int max = 5;
        System.out.println(Math.log10(2)/max);
        System.out.println(Math.log10(31)/max);
        System.out.println(Math.log10(137)/max);
        System.out.println(Math.log10(540)/max);
        System.out.println(Math.log10(1800)/max);
        System.out.println(Math.log10(5600)/max);
        System.out.println(Math.log10(10089)/max);
        System.out.println(Math.log10(56995)/max);
        System.out.println(Math.log10(98888)/max);
        System.out.println(Math.log10(298888)/max);
        System.out.println(Math.log10(798888)/max);
        System.out.println(Math.log10(998888)/max);
    }

    @Test
    public void keywordProcess() throws Exception {
        File file = new File("C:\\Users\\toolmall\\Desktop\\B端本地词库.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        String word;
        List<String> noRepeteList = new ArrayList<>();
        List<String> repeteList = new ArrayList<>();
        while (!StringUtils.isEmpty(word = br.readLine())) {
            String norepet = removeRepet(word);
            if (!(norepet.length() == word.length())) {
                repeteList.add(word);
            } else {
                noRepeteList.add(word);
            }
        }
        //大纲
        //这是我第27次从十月一日的早晨醒来，时间依然是八点整。曾经无数次幻想的场景变成了现实，我重复的过上了十一假期。
        //
        //输出到文件
        File noRepeteFile = new File("C:\\Users\\toolmall\\Desktop\\无重复字符词.txt");
        noRepeteFile.createNewFile();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(noRepeteFile), "UTF-8"));
        for (String w : noRepeteList) {
            bw.write(w);
            bw.flush();
            bw.newLine();
        }
        File repeteFile = new File("C:\\Users\\toolmall\\Desktop\\重复字符词.txt");
        repeteFile.createNewFile();
        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(repeteFile), "UTF-8"));
        for (String w : repeteList) {
            bw.write(w);
            //bw.flush();
            //bw.newLine();
        }
        br.close();
        bw.close();
    }

    @Test
    public void replaceAllTest() {
        System.out.print("输入:");
        Scanner scan = new Scanner(System.in);
        String src = scan.nextLine();
        String reg = scan.nextLine();
        System.out.println(src.replaceAll(reg, "hello"));
    }

    @Test
    public void t1() {
        String s = "rememberMe=YFJDQLAHQh57qR1EClE7yWVguZlrtyhuhFdOnZcSSj173whOR0u7jVbsi4ldlwhepVlV3IluElYQTsl4rgAbrwb6uwjJxC9wgOu+3VT1Xk/Pd6LUEztvjo98PqxKWBZS4c+d+hLiYMqxSdZZfAAoQigzB7F/D5ipbofN/qM90r0mnjlLl1AsW8L6qIC6otAQKqk1sB9HrdDyLUs+Ke3TeUTULIy4e5jfI1lXvDdaj7vTtNty/vnd2WsSB3u1E3A+9znTSUr/Ti1Eei3FMSOI6mgAe2BPpazW8RtHv62nhWSZena+sCCleMCVl/s5Vxh+YOpAP6V3kH9lpdV3A4MwcG2d50T9n9DPSNhmTmYTHJtBwlz/sRsMLvxLZGVH4t9MmkYsYbJGvONqZgPDXheB8Z+15yqhM6m/02yMBBkJkgIaJv5RchvclSApDt2xzFHncQ24zDMshpMmKroARwNDTjWCkTilcS7YRJHUSiqZCQhTdg64QQiMEjHn7pl03QJ+AAedl9V3U3D3y0hIm0mvMQWHD544xITPmDMQlw77aZikHZyUmB2j7vH+DHQJbrK8XEbecyYDzje2J1zXhnIYvyN2SSUS07OPtkTtRa9UkthmrSPztjeabnOrKv3JZBUAzoeR/2zzekniajBe58WzgVD02Qntj0kNm7/aALzZ9PPIoUPo0t5HzWT55krhg1lZLH+no9C7LiTA4RvOzeO3sanbkmH1MEPY6Hbf3k020log6hu/YUaPH2XlooMTme04qMtJ36bdHFMIVy7ish/mMlbyyC+6wjkX22dNG/n5sTtGDVwXMv6FPQlJQR4F8rzk; Hm_lvt_82116c626a8d504a5c0675073362ef6f=1566350005,1566436486,1566536140,1566782243; search_history=fdec40b660584da0b2428cfff62e99ed; JSESSIONID=81DF18CE227D3426951F937DB45D7A0A";
        String[] split = s.split(";");
        for (String ss : split) {
            System.out.println(ss);
        }
    }

    @Test
    public void t2() {
        List<String> list = new ArrayList<>();
        list.addAll(null);
    }

    @Test
    public void t3() {
        String src = "12111\n21233";
        System.out.println(src.replaceAll("\\n", " "));
    }

    @Test
    public void t4() {
        String sp = "";

    }

    public String removeRepet(String key) {
        StringBuilder sb = new StringBuilder();
        int len = key.length();
        for (int i = 0; i < len; i++) {
            char c = key.charAt(i);
            if (key.indexOf(c) == key.lastIndexOf(c)) {
                sb.append(c);
            } else if (key.indexOf(c) == i){
                sb.append(c);
            }
        }
        return sb.toString();
    }

}










































