package com.syscloud.provider.user.web.controller;

import com.google.common.collect.Maps;
import com.syscloud.pojo.JsonData;
import com.syscloud.provider.user.model.param.AclParam;
import com.syscloud.provider.user.model.query.PageQuery;
import com.syscloud.provider.user.model.vo.SysRole;
import com.syscloud.provider.user.service.SysAclService;
import com.syscloud.provider.user.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/sys/acl")
@Controller
public class SysAclController {

    @Autowired
    private SysAclService sysAclService;
    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveAclModule(AclParam param) {
     sysAclService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateAclModule(AclParam param) {
        sysAclService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData list(@RequestParam("aclModuleId") Integer aclModuleId, PageQuery pageQuery, String keyword) {
        return JsonData.success(sysAclService.getPageByAclModuleId(aclModuleId, pageQuery,keyword));
    }

   @RequestMapping("acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam("aclId") int aclId) {
        Map<String, Object> map = Maps.newHashMap();
        List<SysRole> roleList = sysRoleService.getRoleListByAclId(aclId);
        map.put("roles", roleList);
        map.put("users", sysRoleService.getUserListByRoleList(roleList));
        return JsonData.success(map);
    }
}
