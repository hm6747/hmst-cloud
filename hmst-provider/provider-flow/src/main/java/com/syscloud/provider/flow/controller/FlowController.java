package com.syscloud.provider.flow.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/8/30 0030.
 */
@RequestMapping("/activityService")
@RestController
public interface FlowController {

    /**
     * 流程demo
     * @return
     */
    @RequestMapping(value="/startActivityDemo")
    public boolean startActivityDemo();

    @RequestMapping(value="/create")
    public void testDynamicDeploy() throws Exception;
}
