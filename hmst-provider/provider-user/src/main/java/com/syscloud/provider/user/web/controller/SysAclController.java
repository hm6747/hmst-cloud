package com.syscloud.provider.user.web.controller;

import com.google.common.collect.Maps;
import com.syscloud.pojo.JsonData;
import com.syscloud.provider.auth.annotation.IgnoreUserToken;
import com.syscloud.provider.user.model.param.AclParam;
import com.syscloud.provider.user.model.query.PageQuery;
import com.syscloud.provider.user.model.vo.SysRole;
import com.syscloud.provider.user.service.SysAclService;
import com.syscloud.provider.user.service.SysCoreService;
import com.syscloud.provider.user.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/sys/acl")
@RestController
@Api(value = "权限管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,description = "权限管理")
public class SysAclController {

    @Autowired
    private SysAclService sysAclService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysCoreService sysCoreService;
    @RequestMapping("/save.json")
    @ApiOperation(httpMethod = "PUT",value = "新增权限点")
    public JsonData saveAclModule(AclParam param) {
     sysAclService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ApiOperation(httpMethod = "PUT",value = "更新权限点")
    public JsonData updateAclModule(AclParam param) {
        sysAclService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/page.json")
    @ApiOperation(httpMethod = "GET",value = "获取模块权限")
    public JsonData list(@RequestParam("aclModuleId") Integer aclModuleId, PageQuery pageQuery, String keyword) {
        return JsonData.success(sysAclService.getPageByAclModuleId(aclModuleId, pageQuery,keyword));
    }

   @RequestMapping("acls.json")
   @ApiOperation(httpMethod = "GET",value = "获取所有权限")
    public JsonData acls(@RequestParam("aclId") int aclId) {
        Map<String, Object> map = Maps.newHashMap();
        List<SysRole> roleList = sysRoleService.getRoleListByAclId(aclId);
        map.put("roles", roleList);
        map.put("users", sysRoleService.getUserListByRoleList(roleList));
        return JsonData.success(map);
    }

    @RequestMapping("/hasPermission.json")
    @IgnoreUserToken
    @ApiOperation(httpMethod = "GET",value = "判断权限")
    public JsonData list(@RequestParam("url") String url,@RequestParam("userId") int userId) {
        return JsonData.success(sysCoreService.hasPermission(url,userId));
    }
}
