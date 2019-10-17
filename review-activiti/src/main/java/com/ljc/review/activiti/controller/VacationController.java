package com.ljc.review.activiti.controller;

import com.ljc.review.activiti.entity.BaseResult;
import com.ljc.review.activiti.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请假审批流程
 */
@RestController
@RequestMapping("/activiti/vacation")
public class VacationController {

    @Autowired
    private VacationService vacationService;

    /**
     * 部署请假流程
     * 如果多次部署同一个流程的话系统会保存新的部署信息，同时版本号加1，之后新建的流程实例会默认使用新版本的信息
     */
    @RequestMapping("deploy")
    public BaseResult deploy() {
        return vacationService.deploy();
    }

    /**
     * 创建流程实例，启动请假流程（提交请假申请）
     */
    @RequestMapping("start")
    public BaseResult start() {
        return vacationService.start();
    }

    /**
     * 查询用户为候选人的任务列表（流程中通过分组、角色、候选人等方式指定的处理人）
     */
    @RequestMapping("userCandidateTask")
    public BaseResult userCandidateTask(@RequestParam("username") String username) {
        return vacationService.userCandidateTask(username);
    }

    /**
     * 从可选任务中认领任务
     * 确认之后任务即进入用户的个人任务列表，通过候选人或者分组任务查询的方式无法再查询到这个任务
     */
    @RequestMapping("claim")
    public BaseResult claim(@RequestParam("taskId") String taskId) {
        return vacationService.claim(taskId);
    }

    /**
     * 查询用户的个人任务列表
     * 候选任务认领后才会出现在这里
     * 或者直接在流程图中指定了处理人的任务也会出现在这里
     */
    @RequestMapping("assigneeTask")
    public BaseResult assigneeTask(@RequestParam("username") String username) {
        return vacationService.assigneeTask(username);
    }

    /**
     * 查询分组任务列表
     */
    @RequestMapping("groupTask")
    public BaseResult groupTask(@RequestParam("groupName") String groupName) {
        return vacationService.groupTask(groupName);
    }

    /**
     * 处理任务
     */
    @RequestMapping("processTask")
    public BaseResult processTask(@RequestParam("taskId") String taskId, String type) {
        return vacationService.processTask(taskId, type);
    }

    /**
     * 查询历史流程
     * 并流程图形式响应
     */
    @RequestMapping("queryHistory")
    public void queryHistory(@RequestParam String processId, HttpServletResponse response) throws IOException {
        vacationService.queryHistory(processId, response);
    }

}


















