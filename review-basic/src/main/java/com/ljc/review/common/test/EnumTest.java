package com.ljc.review.common.test;

import com.ljc.review.common.enums.WarmTipsTypeEnum;
import org.junit.Test;

public class EnumTest {

    @Test
    public void test() {
        String code = "bot1tom";
        try {
            WarmTipsTypeEnum warmTypeEnum = WarmTipsTypeEnum.valueOf(code);
            System.out.println(warmTypeEnum.getMessage());
        } catch (Exception e) {
            System.out.println("大鹏一日同风起，扶摇直上九万里。假令风歇时下来，犹能簸却沧溟水");
        }
    }

}
