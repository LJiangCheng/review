package com.ljc.review.activiti.service;

import com.ljc.review.activiti.entity.BaseResult;
import com.ljc.review.activiti.util.ActivitiUtils;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.util.IoUtil;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VacationService {

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

    public BaseResult deploy() {
        DeploymentBuilder deploymentBuilder=repositoryService.createDeployment();//创建一个部署对象
        deploymentBuilder.name("请假流程");
        deploymentBuilder.addClasspathResource("diagrams/vacation/vacation.bpmn");
        deploymentBuilder.addClasspathResource("diagrams/vacation/vacation.png");
        Deployment deployment=deploymentBuilder.deploy();//完成部署
        //打印流程信息
        System.out.println("流程Id:"+deployment.getId());
        System.out.println("流程Name:"+deployment.getName());
        return BaseResult.success();
    }

    /**
     * 启动请假流程
     * 请假人、请假时间、请假理由等由程序参数指定
     * 其中参数名已经由占位符写在了流程中
     */
    public BaseResult start() {
        Map<String, Object> variables = createVacationMsg();
        variables.put("userId", "Kermit");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("vacationRequest", variables);

        System.out.println("流程实例ID：" + processInstance.getId());//流程实例ID
        System.out.println("流程定义ID：" + processInstance.getProcessDefinitionId());//流程定义ID
        // Verify that we started a new process instance
        System.out.println("当前流程实例的数量：" + runtimeService.createProcessInstanceQuery().count());
        return BaseResult.success();
    }

    /**
     * 查询用户候选的任务列表
     */
    public BaseResult userCandidateTask(String username) {
        List<Task> taskList = taskService.createTaskQuery() //创建任务查询对象
                .taskCandidateUser(username) //根据办理候选人列表来查询任务 PS：传入的用户必须是manager组的成员
                .list(); //获取该办理人下的事务列表
        //处理任务
        outputTaskMsg(taskList, username);
        return BaseResult.success(true);
    }

    /**
     * 认领任务
     */
    public BaseResult claim(String taskId) {
        taskService.claim(taskId, "nick");
        return BaseResult.success(true);
    }

    /**
     * 查询用户已领取的任务列表
     */
    public BaseResult assigneeTask(String username) {
        List<Task> taskList = taskService.createTaskQuery() //创建任务查询对象
                .taskAssignee(username) //指定用户已领取的任务
                .list(); //获取该办理人下的事务列表
        outputTaskMsg(taskList, username);
        return BaseResult.success(true);
    }

    /**
     * 查询某一分组的任务列表
     */
    public BaseResult groupTask(String groupName) {
        List<Task> taskList = taskService.createTaskQuery()
                .taskCandidateGroup(groupName)
                .list();
        outputTaskMsg(taskList, groupName);
        return BaseResult.success(true);
    }

    /**
     * 处理任务
     */
    public BaseResult processTask(String taskId, String type) {
        Map<String, Object> taskVariables = new HashMap<>();
        if (StringUtils.equals("manager", type)) {
            //设置本节点的选择，通过选择决定流程走向，以及将参数传递给下一个节点
            taskVariables.put("vacationApproved", "true");
            taskVariables.put("managerMotivation", "Have fun!");
            taskVariables.put("userId", taskService.getVariable(taskId, "userId"));
        } else if (StringUtils.equals("employee", type)) {
            taskVariables = createVacationMsg();
            taskVariables.put("resendRequest", "true");
            taskVariables.put("userId", taskService.getVariable(taskId, "userId"));
        }
        taskService.complete(taskId, taskVariables);
        return BaseResult.success(true);
    }

    /**
     * 查询流程历史
     * 并输出当前流程图
     */
    public void queryHistory(String processId, HttpServletResponse response) throws IOException {
        //查询当前流程实例
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processId).singleResult();
        if (processInstance == null) {
            return;
        }
        //根据流程对象获取流程对象模型
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        //获取图表生成器
        ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        //构造历史流程节点查询对象
        HistoricActivityInstanceQuery historyInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processId);
        //获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
        List<HistoricActivityInstance> historicActivityInstanceList = historyInstanceQuery.orderByHistoricActivityInstanceStartTime().asc().list();
        if (historicActivityInstanceList == null || historicActivityInstanceList.size() == 0) {
            outputImg(response, bpmnModel, null, null, diagramGenerator);
            return;
        }
        // 已执行的节点ID集合(将historicActivityInstanceList中元素的activityId字段取出封装到executedActivityIdList)
        List<String> executedActivityIdList = historicActivityInstanceList.stream().map(HistoricActivityInstance::getActivityId).collect(Collectors.toList());
        // 获取流程已走过的线
        List<String> flowIds = ActivitiUtils.getHighLightedFlows(bpmnModel, historicActivityInstanceList);
        // 输出图像，并设置高亮
        outputImg(response, bpmnModel, flowIds, executedActivityIdList, diagramGenerator);
    }

    /**
     * <p>输出图像</p>
     * @param response               响应实体
     * @param bpmnModel              图像对象
     * @param flowIds                已执行的线集合
     * @param executedActivityIdList void已执行的节点ID集合
     * @param diagramGenerator
     */
    private void outputImg(HttpServletResponse response, BpmnModel bpmnModel, List<String> flowIds, List<String> executedActivityIdList, ProcessDiagramGenerator diagramGenerator) {
        InputStream imageStream = null;
        try {
            imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", executedActivityIdList, flowIds, "宋体", "微软雅黑", "黑体", null, 1.0);
            // 输出资源内容到相应对象
            byte[] b = new byte[1024];
            int len;
            while ((len = imageStream.read(b, 0, 1024)) != -1) {
                response.getOutputStream().write(b, 0, len);
            }
            response.getOutputStream().flush();
        } catch (Exception e) {
            System.out.println("流程图输出异常！");
        } finally { // 流关闭
            IoUtil.closeSilently(imageStream);
        }
    }

    //打印任务信息
    private void outputTaskMsg(List<Task> taskList, String name) {
        if (!CollectionUtils.isEmpty(taskList)) {
            for (Task task : taskList) {
                System.out.println("任务ID：" + task.getId());
                System.out.println("任务名称：" + task.getName());
                System.out.println("任务办理人：" + task.getAssignee());
                System.out.println("任务描述：" + task.getDescription());
                System.out.println("流程实例ID：" + task.getProcessInstanceId());
                System.out.println("执行对象ID：" + task.getExecutionId());
                System.out.println("流程定义ID：" + task.getProcessDefinitionId());
                System.out.println("流程发起人：" + taskService.getVariable(task.getId(), "userId"));
                System.out.println("========================================");
            }
        } else {
            System.out.println(name + "当前没有任务！");
            System.out.println("========================================");
        }
    }

    //填充请假信息
    private Map<String, Object> createVacationMsg() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("employeeName", "Kermit");
        variables.put("numberOfDays", 4);
        variables.put("vacationMotivation", "I'm really tired!");
        return variables;
    }

}














