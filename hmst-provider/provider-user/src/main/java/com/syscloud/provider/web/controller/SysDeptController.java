package com.syscloud.provider.web.controller;

import com.syscloud.pojo.JsonData;
import com.syscloud.provider.model.dto.SysDeptDto;
import com.syscloud.provider.model.param.DeptParam;
import com.syscloud.provider.service.SysDeptService;
import com.syscloud.provider.service.SysTreesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by hm on 2017/12/23.
 */
@Slf4j
@RequestMapping("/sys/dept")
@Controller
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysTreesService sysTreesService;

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam param) {
        sysDeptService.save(param);
        return JsonData.success();
    }


    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(DeptParam param) {
        sysDeptService.upDateDept(param);
        return JsonData.success();
    }

    @RequestMapping("/dept.page")
    public String page(){
        return  "dept";
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree() {
        List<SysDeptDto> dtoList = sysTreesService.deptTree();
        return JsonData.success(dtoList);
    }
}
