package com.ljc.review.common.test;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import org.junit.Test;

public class V8Test {

    @Test
    public void test() {
        V8 v8 = V8.createV8Runtime();
        String script = "var mapping = {" +
                "    mapTo: function (jsonStr) {" +
                "        var param = JSON.parse(jsonStr);" +
                "        var result = {};" +
                "        result.id = param.productId;" +
                "        return JSON.stringify(result);" +
                "    }" +
                "};";
        v8.executeVoidScript(script);
        V8Object mapping = v8.getObject("mapping");
        //V8Object param = new V8Object(v8).add("productId", "900000002");
        V8Array paramArr = new V8Array(v8).push("{\"productId\":\"900000002\"}");
        String mapTo = mapping.executeStringFunction("mapTo", paramArr);
        System.out.println(mapTo);
        //释放资源
        //param.release();
        mapping.release();
        paramArr.release();
        v8.release();
    }

}
