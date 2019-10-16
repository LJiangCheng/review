package com.ljc.review.activiti.unittest;

import com.ljc.review.activiti.ReviewActivitiApplication;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ReviewActivitiApplication.class)
public class FlowTest {

    @Autowired
    private ProcessEngine processEngine;

    @Test
    public void countFlow() {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        System.out.println("当前流程实例的数量：" + runtimeService.createProcessInstanceQuery().count());
    }

}
