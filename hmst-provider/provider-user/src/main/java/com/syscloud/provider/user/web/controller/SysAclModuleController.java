package com.syscloud.provider.user.web.controller;

import com.syscloud.pojo.JsonData;
import com.syscloud.provider.user.model.param.AclModuleParam;
import com.syscloud.provider.user.service.SysAclModuleService;
import com.syscloud.provider.user.service.SysTreesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/aclModule")
@Slf4j
public class SysAclModuleController {

    @Autowired
    private SysAclModuleService sysAclModuleService;
    @Autowired
    private SysTreesService sysTreeService;


    @RequestMapping("/save.json")
    public JsonData saveAclModule(AclModuleParam param) {
        sysAclModuleService.save(param);
            return JsonData.success();
    }

    @RequestMapping("/update.json")
    public JsonData updateAclModule(AclModuleParam param) {
        sysAclModuleService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/tree.json")
    public JsonData tree() {
        return JsonData.success(sysTreeService.aclModuleTree());
    }

    @RequestMapping("/delete.json")
    public JsonData delete(@RequestParam("id") int id) {
        sysAclModuleService.delete(id);
        return JsonData.success();
    }

    @RequestMapping("menu.json")
    public JsonData menu() {
        sysAclModuleService.menu();
        return JsonData.success();
    }
}
