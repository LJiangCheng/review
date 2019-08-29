package com.ljc.review.common.test;

import cn.hutool.core.util.EscapeUtil;
import org.junit.Test;

public class EscapeTest {

    /**
     * JS escape() and encodeURI()
     *
     * escape()
     * 可对字符串进行编码，这样就可以在所有的计算机上读取该字符串。
     * 该方法不会对 ASCII 字母和数字进行编码，也不会对下面这些 ASCII 标点符号进行编码： * @ - _ + . / 。其他所有的字符都会被转义序列替换。
     * 因此escape()可以用于请求参数的编码
     *
     * encodeURI()
     * 该方法不会对 ASCII 字母和数字进行编码，也不会对这些 ASCII 标点符号进行编码： - _ . ! ~ * ' ( ) 。
     * 该方法的目的是对 URI 进行完整的编码，因此对以下在 URI 中具有特殊含义的 ASCII 标点符号，encodeURI() 函数是不会进行转义的：;/?:@&=+$,#
     * 因此encodeURI()适用于URL编码
     */
    @Test
    public void test() {
        String escape = EscapeUtil.unescape("%5B%u624B%u7535%u94BB%261561561%3D12121%5D");
        System.out.println(escape);
    }

}
