package com.ljc.review.activiti.service;

import com.ljc.review.activiti.entity.BaseResult;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ApprovalService {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private HistoryService historyService;

    /**
     * 部署审批流程
     * 操作：向数据库中保存了该流程的所有信息
     */
    public BaseResult deploy() {
        DeploymentBuilder deploymentBuilder=repositoryService.createDeployment();//创建一个部署对象
        deploymentBuilder.name("helloWorld入门程序");//添加部署的名称
        deploymentBuilder.addClasspathResource("diagrams/approval/approval.bpmn");//从classpath的资源加载，一次只能加载一个文件
        deploymentBuilder.addClasspathResource("diagrams/approval/approval.png");//从classpath的资源加载，一次只能加载一个文件
        Deployment deployment=deploymentBuilder.deploy();//完成部署
        //打印流程信息
        System.out.println("流程Id:"+deployment.getId());
        System.out.println("流程Name:"+deployment.getName());
        return BaseResult.success();
    }

    /**
     * 启动流程实例
     * 启动一个流程就相当于提交了一次申请
     * PS:这个demo中申请人、各个节点的处理人、流转条件等都已经定义在流程中
     */
    public BaseResult start() {
        //使用流程定义的key，key对应bpmn文件对应的id，
        //(也是act_re_procdef表中对应的KEY_字段),默认是按照最新版本启动
        String processDefinitionkey="HelloWorld";//流程定义的key就是HelloWorld
        //获取流程实例对象
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionkey);
        System.out.println("流程实例ID："+processInstance.getId());//流程实例ID
        System.out.println("流程定义ID："+processInstance.getProcessDefinitionId());//流程定义ID
        return BaseResult.success();
    }

    /**
     * 查询用户当前的个人任务列表
     * 根据列表可以获取任务id然后处理任务
     */
    public BaseResult queryTask(String assignee) {
        //获取事务Service
        //TaskService taskService=processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery() //创建任务查询对象
                .taskAssignee(assignee) //指定办理人查询个人任务
                .list(); //获取该办理人下的事务列表

        if (!CollectionUtils.isEmpty(taskList)) {
            for (Task task : taskList) {
                System.out.println("任务ID：" + task.getId());
                System.out.println("任务名称：" + task.getName());
                System.out.println("任务的创建时间：" + task.getCreateTime());
                System.out.println("任务办理人：" + task.getAssignee());
                System.out.println("流程实例ID：" + task.getProcessInstanceId());
                System.out.println("执行对象ID：" + task.getExecutionId());
                System.out.println("流程定义ID：" + task.getProcessDefinitionId());
                System.out.println("========================================");
            }
        } else {
            System.out.println(assignee + "当前没有任务！");
            System.out.println("========================================");
        }
        return BaseResult.success();
    }

    /**
     * 根据taskId办理任务
     * 任务完成后即流转到下一节点，并从当前办理人的任务列表中移除
     * 问题：如何设置任务的流转条件？
     */
    public BaseResult processTask(String taskId) {
        taskService.complete(taskId);//完成taskId对应的任务
        System.out.println("完成ID为"+taskId+"的任务");
        return BaseResult.success();
    }
}
