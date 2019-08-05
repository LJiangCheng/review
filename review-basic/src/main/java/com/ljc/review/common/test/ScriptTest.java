package com.ljc.review.common.test;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptTest {

    @Test
    public void ttt() throws NoSuchMethodException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try {
            engine.eval("function example(a,b,message) {" +
                    "    var result = {};" +
                    "    result.a = a;" +
                    "    result.b = b;" +
                    "    result.c = a+b;" +
                    "    result.message = message;" +
                    "    return result;" +
                    "}");
            if (engine instanceof Invocable) {
                Invocable in = (Invocable) engine;
                Object result = in.invokeFunction("example", 1, 1,"JS执行");
                System.out.println(JSONObject.toJSONString(result));
            }
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

}
