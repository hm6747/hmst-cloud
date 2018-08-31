package com.syscloud.provider.flow.service;

import com.syscloud.provider.flow.controller.FlowController;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

@Service("activityService")
public class FlowService implements FlowController {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;


    @Override
    public boolean startActivityDemo() {
        System.out.println("method startActivityDemo begin....");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("apply", "zhangsan");
        map.put("approve", "lisi");
            //流程启动
        ExecutionEntity pi1 = (ExecutionEntity) runtimeService.startProcessInstanceByKey("vacationProcess", map);
        String processId = pi1.getId();
        String taskId = pi1.getTasks().get(0).getId();
        taskService.complete(taskId, map);//完成第一步申请

        Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        String taskId2 = task.getId();
        map.put("pass", false);
        taskService.complete(taskId2, map);//驳回申请
        System.out.println("method startActivityDemo end....");
        return false;
    }

    @Override
    public void testDynamicDeploy() throws Exception {
        // 1. Build up the model from scratch
        BpmnModel model = new BpmnModel();
        Process process = new Process();
        model.addProcess(process);
        process.setId("my-process");

        process.addFlowElement(createStartEvent());
        process.addFlowElement(createUserTask("task1", "First task", "fred"));
        process.addFlowElement(createUserTask("task2", "Second task", "john"));
        process.addFlowElement(createEndEvent());

        process.addFlowElement(createSequenceFlow("start", "task1"));
        process.addFlowElement(createSequenceFlow("task1", "task2"));
        process.addFlowElement(createSequenceFlow("task2", "end"));

//        // 2. Generate graphical information
//        new BpmnAutoLayout(model).execute();
//        ActivitiRule activitiRule = new ActivitiRule();
//        // 3. Deploy the process to the engine
//        Deployment deployment = activitiRule.getRepositoryService().createDeployment()
//                .addBpmnModel("dynamic-model.bpmn", model).name("Dynamic process deployment")
//                .deploy();
//
//        // 4. Start a process instance
//        ProcessInstance processInstance = activitiRule.getRuntimeService()
//                .startProcessInstanceByKey("my-process");
//
//        // 5. Check if task is available
//        List tasks = activitiRule.getTaskService().createTaskQuery()
//                .processInstanceId(processInstance.getId()).list();
//
//        Assert.assertEquals(1, tasks.size());
//        Assert.assertEquals("First task", tasks.get(0).getName());
//        Assert.assertEquals("fred", tasks.get(0).getAssignee());
//
//        // 6. Save process diagram to a file
//        InputStream processDiagram = activitiRule.getRepositoryService()
//                .getProcessDiagram(processInstance.getProcessDefinitionId());
//        FileUtils.copyInputStreamToFile(processDiagram, new File("target/diagram.png"));
//
//        // 7. Save resulting BPMN xml to a file
//        InputStream processBpmn = activitiRule.getRepositoryService()
//                .getResourceAsStream(deployment.getId(), "dynamic-model.bpmn");
//        FileUtils.copyInputStreamToFile(processBpmn,
//                new File("target/process.bpmn20.xml"));
    }

    protected UserTask createUserTask(String id, String name, String assignee) {
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
        userTask.setAssignee(assignee);
        return userTask;
    }

    protected SequenceFlow createSequenceFlow(String from, String to) {
        SequenceFlow flow = new SequenceFlow();
        flow.setSourceRef(from);
        flow.setTargetRef(to);
        return flow;
    }

    protected StartEvent createStartEvent() {
        StartEvent startEvent = new StartEvent();
        startEvent.setId("start");
        return startEvent;
    }

    protected EndEvent createEndEvent() {
        EndEvent endEvent = new EndEvent();
        endEvent.setId("end");
        return endEvent;
    }

}
