package com.ljc.review.reviewsearch.elasticsearch.test;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 本地分词算法测试
 */
public class LocalSegTest {

    @Test
    public void seg() {
        //创建数据
        String keyWord = "手动钢盾十字大柄螺丝批螺帽";
        List<String> lexicon = getLexicon();
        //切分
        String[] wordArr = keyWord.trim().replaceAll("[\\s\\u00A0]+", "").split("");
        //去重并记录位置
        Map<String, List<Integer>> keywordMap = new HashMap<>();
        for (int i = 0; i < wordArr.length; i++) {
            String key = wordArr[i];
            if (keywordMap.containsKey(key)) {
                keywordMap.get(key).add(i);
            } else {
                List<Integer> indexList = new ArrayList<>();
                indexList.add(i);
                keywordMap.put(key, indexList);
            }
        }
        //匹配
        Map<String, List<String>> matchMap = new HashMap<>();
        for (String key : keywordMap.keySet()) {
            List<String> collect = lexicon.stream().map(str -> {
                if (str.contains(key)) {
                    return str;
                } else {
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());
            matchMap.put(key, collect);
        }
        //计分器
        Map<String, Double> scoreMap = new HashMap<>();
        //去重并计算出现次数
        for (Map.Entry<String, List<String>> entry : matchMap.entrySet()) {
            List<String> strList = entry.getValue();
            if (!CollectionUtils.isEmpty(strList)) {
                for (String str : strList) {
                    if (scoreMap.containsKey(str)) {
                        Double score = scoreMap.get(str);
                        scoreMap.put(str, score + 1D);
                    } else {
                        scoreMap.put(str, 1D);
                    }
                }
            }
        }
        //去除非完全匹配：如果出现次数和key的长度相等，则意味着该词完全匹配(PS：包括叠词)
        Iterator<Map.Entry<String, Double>> it1 = scoreMap.entrySet().iterator();
        Set<String> distinct = new HashSet<>();
        while (it1.hasNext()) {
            Map.Entry<String, Double> entry = it1.next();
            String key = entry.getKey();
            distinct.addAll(Arrays.asList(key.split("")));
            if (distinct.size() > entry.getValue()) {
                it1.remove();
            }
            distinct.clear();
        }
        //计算相关度
        Iterator<Map.Entry<String, Double>> it2 = scoreMap.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry<String, Double> entry = it2.next();
            String key = entry.getKey();
            //获取key的每个字符在原词中的位置，可以有多个下标，根据下标计算相关度
            String[] keySplit = key.split("");
            List<List<Integer>> indexList = new ArrayList<>();
            for (String ks : keySplit) {
                indexList.add(keywordMap.get(ks));
            }
            //根据不同字符的多个下标找出最近的相对距离
            List<Integer> distance = new ArrayList<>();
            for (int i = 0; i < indexList.size() - 1; i++) {
                List<Integer> before = indexList.get(i);
                List<Integer> after = indexList.get(i + 1);
                int dis = after.get(0) - before.get(0);
                for (int bef : before) {
                    for (int aft : after) {
                        int rel = aft - bef;
                        if (rel >= 0) {
                            if (dis < 0) {
                                dis = rel;
                            } else if (dis > rel) {
                                dis = rel;
                            }
                        } else {
                            if (dis < 0) {
                                if (dis < rel) {
                                    dis = rel;
                                }
                            }
                        }
                    }
                }
                distance.add(dis);
            }
            //根据相对距离算分，权重可调  要素：匹配词长度(正比)，词距(反比)
            Double score = key.length()/10D;
            for (int dis : distance) {
                if (dis > 0) {
                    score += (double) 1 / dis;
                } else if (dis < 0) {
                    score += (double) dis / 5;
                }
            }
            entry.setValue(score);
        }
        // 输出处理后结果
        List<Map.Entry<String, Double>> collect = scoreMap.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue)).collect(Collectors.toList());
        Collections.reverse(collect);
        collect.forEach(entry -> System.out.println(entry.getKey() + ":" + entry.getValue()));
        //所有匹配结果
        System.out.println("==============================================================");
        matchMap.entrySet().forEach(entry -> {
            List<String> value = entry.getValue();
            StringBuilder sb = new StringBuilder();
            for (String val : value) {
                sb.append(val).append("、");
            }
            if(sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
            System.out.println(entry.getKey() + ":" + sb.toString());
        });
    }

    private List<String> getLexicon() {
        List<String> lexicon = new ArrayList<>();
        lexicon.add("螺丝批");
        lexicon.add("螺丝");
        lexicon.add("手动");
        lexicon.add("钢盾");
        lexicon.add("十字螺丝");
        lexicon.add("十字");
        lexicon.add("十字螺丝批");
        lexicon.add("扭矩螺丝批");
        lexicon.add("螺丝十字");
        lexicon.add("大熊抱枕");
        lexicon.add("十字抱枕");
        lexicon.add("螺丝批螺帽");
        lexicon.add("螺帽");
        lexicon.add("手大帽");
        return lexicon;
    }

    @Test
    public void test() {
        String str = "";
        str = str.trim().replaceAll("[\\s\\u00A0]+", "");
        String[] split = str.split("");
        Map<String, Integer> map = new HashMap<>();
        for(int i=0;i<split.length;i++) {
            if (!map.containsKey(split[i])) {
                map.put(split[i], i);
            }
        }
        List<String> collect = map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue)).map(Map.Entry::getKey).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        collect.forEach(sb::append);
        System.out.println(sb.toString());
    }

    @Test
    public void test1() {
        String str = "手";
        System.out.println(str.toLowerCase());
        System.out.println(str.toUpperCase());
    }

    @Test
    public void testAppendNull() {
        String s = null;
        StringBuilder sb = new StringBuilder();
        System.out.println(sb.append(s).toString());
    }

}
