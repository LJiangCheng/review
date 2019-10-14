package com.ljc.review.activiti.controller;

import com.ljc.review.activiti.entity.BaseResult;
import com.ljc.review.activiti.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 单一流向的简单审批流程demo
 */
@RestController
@RequestMapping("/activiti/approval")
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;

    /**
     * 部署简单审批流程
     */
    @RequestMapping("deploy")
    public BaseResult deploy() {
        return approvalService.deploy();
    }

    /**
     * 启动流程实例
     */
    @RequestMapping("start")
    public BaseResult start() {
        return approvalService.start();
    }

    /**
     * 查询办理人当前的个人任务
     */
    @RequestMapping("queryTask")
    public BaseResult queryTask(@RequestParam("assignee") String assignee) {
        return approvalService.queryTask(assignee);
    }

    /**
     * 根据taskId办理任务
     */
    @RequestMapping("processTask")
    public BaseResult processTask(@RequestParam("taskId") String taskId) {
        return approvalService.processTask(taskId);
    }

}
