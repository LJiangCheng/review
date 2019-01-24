package com.ljc.review.reviewsearch.opensearch.test;


import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.huaban.analysis.jieba.WordDictionary;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 分词器测试
 */
public class TokenizerTest {

    @Test
    public void testJieBa() throws IOException {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        WordDictionary wd = WordDictionary.getInstance();
        String str = "星工（XINGGONG）口罩 防尘防雾霾PM2.5活性炭呼吸阀折叠耳戴式透气 XG8831CV5只装";
        //str = str.replaceAll(" ", "");
        List<SegToken> process = segmenter.process(str, JiebaSegmenter.SegMode.SEARCH);
        for (SegToken st : process) {
            String word = st.word;
            System.out.println(word);
        }

        //加载自定义词典 PS：jeiba自定义词典规则：一行一个词，分别为：词条/词频/词性(可省略)，之间用空格分开
        /*wd.loadUserDict(Paths.get(ResourceUtils.getFile("classpath:user-dict.txt").getAbsolutePath()));
        process = segmenter.process(str, JiebaSegmenter.SegMode.SEARCH);
        for (SegToken st : process) {
            String word = st.word;
            System.out.println(word);
        }*/
    }

    @Test
    public void testAnsj() {
        Result result = ToAnalysis.parse("充电钻");
        List<Term> terms = result.getTerms();
        for (Term term : terms) {
            String name = term.getName();
            System.out.println(name);
        }
    }

    @Test
    public void testZhengZ() {
        String productName = "按钮盒button boxes 施耐德 XALB03C";
        //处理别名
        productName = productName.trim().replaceAll("、", "/").replaceAll("[\\s\\u00A0]+", "/")
                .replaceAll(",", "/").replaceAll("，", "/");
        System.out.println(productName);
    }

    @Test
    public void testStringBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append("hello");
        sb.insert(0, "world");
        System.out.println(sb.toString());
    }

}












