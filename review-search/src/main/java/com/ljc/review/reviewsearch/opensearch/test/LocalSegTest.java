package com.ljc.review.reviewsearch.opensearch.test;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 本地分词算法测试
 */
public class LocalSegTest {

    @Test
    public void seg() {
        //创建数据
        String keyWord = "手动螺丝钢盾十字大柄螺丝批";
        List<String> lexicon = getLexicon();
        //匹配
        String[] split = keyWord.split("");
        Map<String, Integer> keyMap = new HashMap<>();   //map保存原始位置
        for (String key : split) {

        }
        Set<String> keySet = new HashSet<>();      //Set去重
        keySet.addAll(Arrays.asList(split));
        Map<String, List<String>> matchMap = new HashMap<>();
        for (String key : keySet) {
            List<String> collect = lexicon.stream().map(str -> {
                if (str.contains(key)) {
                    return str;
                } else {
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());
            matchMap.put(key, collect);
        }

        //计算相关度

        // 输出
    }

    private List<String> getLexicon() {
        List<String> lexicon = new ArrayList<>();
        lexicon.add("螺丝批");
        lexicon.add("大柄螺丝批");
        lexicon.add("螺丝");
        lexicon.add("手动螺丝批");
        lexicon.add("手动");
        lexicon.add("钢盾");
        lexicon.add("手大");
        lexicon.add("十字螺丝");
        lexicon.add("十字");
        return lexicon;
    }

}
