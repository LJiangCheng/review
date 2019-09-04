package com.ljc.review.common.test;

import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            bw.flush();
            bw.newLine();
        }
        br.close();
        bw.close();
    }

    @Test
    public void t1() {
        Map<String, Object> map = new HashMap<>();
        map.remove("dddd");
    }

    @Test
    public void t2() {
        BigDecimal i = new BigDecimal("1.4");
        BigDecimal j = new BigDecimal("1.2");
        System.out.println(i.multiply(j));
    }

    @Test
    public void t3() {
        int n = 0;
        String[] ss = new String[n];
        for(int i=0;i<n;i++) {
            System.out.println("111");
        }
        System.out.println("222");
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










































