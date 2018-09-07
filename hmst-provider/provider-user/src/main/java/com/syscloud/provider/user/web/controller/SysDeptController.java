package com.syscloud.provider.user.web.controller;

import com.syscloud.pojo.JsonData;
import com.syscloud.provider.auth.annotation.IgnoreUserToken;
import com.syscloud.provider.user.model.dto.SysDeptDto;
import com.syscloud.provider.user.model.param.DeptParam;
import com.syscloud.provider.user.service.SysDeptService;
import com.syscloud.provider.user.service.SysTreesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@Api(value = "部门管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,description = "部门管理")
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysTreesService sysTreesService;

    @RequestMapping("/save.json")
    @ResponseBody
    @ApiOperation(httpMethod = "PUT",value = "新增部门")
    public JsonData saveDept(DeptParam param) {
        sysDeptService.save(param);
        return JsonData.success();
    }


    @RequestMapping("/update.json")
    @ResponseBody
    @ApiOperation(httpMethod = "DELETE",value = "更新部门")
    public JsonData updateDept(DeptParam param) {
        sysDeptService.upDateDept(param);
        return JsonData.success();
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    @IgnoreUserToken
    @ApiOperation(httpMethod = "GET",value = "获取部门信息")
    public JsonData tree() {
        List<SysDeptDto> dtoList = sysTreesService.deptTree();
        return JsonData.success(dtoList);
    }
}
