package com.ljc.review.common.test;

import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BaseTest {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\Users\\ljc\\Desktop\\1.txt")), StandardCharsets.UTF_8));
        BufferedWriter bufferedWriter =
                new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("C:\\Users\\ljc\\Desktop\\诡秘之主.txt"), true), StandardCharsets.UTF_8));
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            if (!StringUtils.isEmpty(s)) {
                s = s.replace("\n", "").trim();
                if (!s.equals("?") && !s.equals("？")) {
                    bufferedWriter.write(s);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            }
        }
        String ss = "";
    }

    @Test
    public void bigDecimal() {
        BigDecimal total = new BigDecimal("10.000");
        BigDecimal p1 = new BigDecimal("3.333333");
        BigDecimal p2 = new BigDecimal("3.333333");
        BigDecimal p3 = new BigDecimal("3.333333");
        System.out.println(p1.add(p2).add(p3));
        BigDecimal divide1 = p1.divide(total, 16, BigDecimal.ROUND_HALF_EVEN);
        BigDecimal divide2 = p2.divide(total, 16, BigDecimal.ROUND_HALF_EVEN);
        BigDecimal divide3 = p3.divide(total, 16, BigDecimal.ROUND_HALF_EVEN);
        System.out.println(divide1.add(divide2).add(divide3));
        BigDecimal m1 = total.multiply(divide1, new MathContext(16, RoundingMode.HALF_EVEN));
        System.out.println(p1 + "=" + m1);
        System.out.println(p1.subtract(m1));
        BigDecimal m2 = total.multiply(divide2, new MathContext(16, RoundingMode.HALF_EVEN));
        System.out.println(p2 + "=" + m2);
        System.out.println(p2.subtract(m2));
        BigDecimal m3 = total.multiply(divide3, new MathContext(16, RoundingMode.HALF_EVEN));
        System.out.println(p3 + "=" + m3);
        System.out.println(p3.subtract(m3));
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










































