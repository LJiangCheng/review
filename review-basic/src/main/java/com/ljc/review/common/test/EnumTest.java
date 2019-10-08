package com.ljc.review.common.test;

import cn.hutool.core.util.EscapeUtil;
import com.ljc.review.common.enums.WarmTipsTypeEnum;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class EnumTest {

    @Test
    public void test14() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);

        // 可改变对象
        list.stream().map((i) -> i * 3).forEach(System.out::println);

        // 不可改变list中的原有对象
        list.forEach(i -> i = i * 3);
        list.forEach(System.out::println);
    }

    @Test
    public void tt() {
        String s = "rememberMe=YFJDQLAHQh57qR1EClE7yWVguZlrtyhuhFdOnZcSSj173whOR0u7jVbsi4ldlwhepVlV3IluElYQTsl4rgAbrwb6uwjJxC9wgOu+3VT1Xk/Pd6LUEztvjo98PqxKWBZS4c+d+hLiYMqxSdZZfAAoQigzB7F/D5ipbofN/qM90r0mnjlLl1AsW8L6qIC6otAQKqk1sB9HrdDyLUs+Ke3TeUTULIy4e5jfI1lXvDdaj7vTtNty/vnd2WsSB3u1E3A+9znTSUr/Ti1Eei3FMSOI6mgAe2BPpazW8RtHv62nhWSZena+sCCleMCVl/s5Vxh+YOpAP6V3kH9lpdV3A4MwcG2d50T9n9DPSNhmTmYTHJtBwlz/sRsMLvxLZGVH4t9MmkYsYbJGvONqZgPDXheB8Z+15yqhM6m/02yMBBkJkgIaJv5RchvclSApDt2xzFHncQ24zDMshpMmKroARwNDTjWCkTilcS7YRJHUSiqZCQhTdg64QQiMEjHn7pl03QJ+AAedl9V3U3D3y0hIm0mvMQWHD544xITPmDMQlw77aZikHZyUmB2j7vH+DHQJbrK8XEbecyYDzje2J1zXhnIYvyN2SSUS07OPtkTtRa9UkthmrSPztjeabnOrKv3JZBUAzoeR/2zzekniajBe58WzgVD02Qntj0kNm7/aALzZ9PPIoUPo0t5HzWT55krhg1lZLH+no9C7LiTA4RvOzeO3sanbkmH1MEPY6Hbf3k020log6hu/YUaPH2XlooMTme04qMtJ36bdHFMIVy7ish/mMlbyyC+6wjkX22dNG/n5sTtGDVwXMv6FPQlJQR4F8rzk; Hm_lvt_82116c626a8d504a5c0675073362ef6f=1566350005,1566436486,1566536140,1566782243; search_history=fdec40b660584da0b2428cfff62e99ed; JSESSIONID=81DF18CE227D3426951F937DB45D7A0A";
        String[] split = s.split(";");
        for (String ss : split) {
            System.out.println(ss);
        }
    }

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

    @Test
    public void test1() {
        String code = "bot1tom";
        try {
            WarmTipsTypeEnum warmTypeEnum = WarmTipsTypeEnum.valueOf(code);
            System.out.println(warmTypeEnum.getMessage());
            System.out.println("三皇五帝，夏商周，春秋、战国，秦，汉，三国，两晋，南北朝，隋唐，五代十国，两宋，元明清");
        } catch (Exception e) {
            System.out.println("大鹏一日同风起，扶摇直上九万里。假令风歇时下来，犹能簸却沧溟水");
            System.out.println("月亮透过窗照下来，给桌子铺上了一层轻纱。月光下，他合上书，只盯着封面怔怔出神。");
            System.out.println("那上面画着一个");
            System.out.println("咚咚咚，有人敲门。他回过神来，");

        }
    }

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
    public void test2() {
        String escape = EscapeUtil.unescape("%5B%u624B%u7535%u94BB%261561561%3D12121%5D");
        System.out.println(escape);
    }

    @Test
    public void replaceAllTest() {
        Scanner scan = new Scanner(System.in);
        System.out.print("输入:");
        String src = scan.nextLine();
        String reg = scan.nextLine();
        System.out.println(src);
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
        Integer[] ints = new Integer[]{1, 3, 2, 5, 8, 1, 5, 2, 7, 2, 3};
        List<Integer> list = Arrays.asList(ints);
        System.out.println(list.stream().map(String::valueOf).collect(Collectors.joining(",")));
        list.sort((o1, o2) -> {
            if (o1 > o2) {
                return 1;
            } else if (o1 < o2) {
                return -1;
            } else {
                return 0;
            }
        });
        System.out.println(list.stream().map(String::valueOf).collect(Collectors.joining(",")));
        //list = Lists.reverse(list);
        System.out.println(list.stream().map(String::valueOf).collect(Collectors.joining(",")));
    }

}
