package com.ljc.review.search.dict;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 读取多个细胞词库文件数据，汇集到一个文件
 */
public class DictAggregate {

    public static void main(String[] args) throws Exception {
        String path = "C:\\Users\\toolmall\\Desktop\\细胞词库";
        dictAggregate(path);
        System.out.println("=====处理成功！=====");
    }

    private static void dictAggregate(String path) throws Exception {
        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files != null && files.length > 0) {
            Set<String> keywords = new HashSet<>();
            for (File file : files) {
                if (file.isFile()) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                    while (true) {
                        String keyword = br.readLine();
                        if (StringUtils.isNotEmpty(keyword)) {
                            keywords.add(keyword.trim());
                        } else {
                            break;
                        }
                    }
                    br.close();
                }
            }
            if (keywords.size() > 0) {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\toolmall\\Desktop\\细胞词库\\汇总-" + keywords.size() + ".txt"), "UTF-8"));
                for (String word : keywords) {
                    word += " 1000";
                    bw.write(word);
                    bw.newLine();
                }
                bw.flush();
                bw.close();
            }
        }
    }

}







































