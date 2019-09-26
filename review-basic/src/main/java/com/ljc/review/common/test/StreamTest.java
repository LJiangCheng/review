package com.ljc.review.common.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StreamTest {

    @Test
    public void test() {
        List<SimpleProduct> list = new ArrayList<>();
        SimpleProduct simpleProduct = new SimpleProduct();
        simpleProduct.setUnicode("");
        list.add(simpleProduct);
        List<String> unicodeList = list.stream()
                .filter(p -> (p != null && StringUtils.isNotEmpty(p.getUnicode()))).map(SimpleProduct::getUnicode).collect(Collectors.toList());
        System.out.println(unicodeList.size());
    }

}

class SimpleProduct{
    private String unicode;

    public String getUnicode() {
        return unicode;
    }

    public void setUnicode(String unicode) {
        this.unicode = unicode;
    }
}
