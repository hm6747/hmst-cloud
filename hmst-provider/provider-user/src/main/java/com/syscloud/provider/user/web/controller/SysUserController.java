package com.syscloud.provider.user.web.controller;

import com.syscloud.pojo.JsonData;
import com.syscloud.provider.user.model.param.UserParam;
import com.syscloud.provider.user.model.query.PageQuery;
import com.syscloud.provider.user.model.query.PageResult;
import com.syscloud.provider.user.model.vo.SysUser;
import com.syscloud.provider.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hm on 2017/12/25.
 */
@Slf4j
@RequestMapping("/sys/user")
@Controller
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(UserParam param) {
        sysUserService.save(param);
        return JsonData.success();
    }


    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateUser(UserParam param) {
            sysUserService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public JsonData page(@RequestParam("deptId") int deptId, PageQuery pageQuery, String keyword) {
        PageResult<SysUser> result = sysUserService.getPageByDeptId(deptId, pageQuery, keyword);
        return JsonData.success(result);
    }
}
