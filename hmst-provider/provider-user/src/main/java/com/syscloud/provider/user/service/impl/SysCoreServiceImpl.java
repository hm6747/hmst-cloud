package com.syscloud.provider.user.service.impl;

import com.google.common.collect.Lists;
import com.syscloud.provider.user.mapper.*;
import com.syscloud.provider.user.model.vo.SysAcl;
import com.syscloud.provider.user.model.vo.SysRole;
import com.syscloud.provider.user.model.vo.SysUser;
import com.syscloud.provider.user.service.SysCoreService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysCoreServiceImpl implements SysCoreService {

    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysRoleAclMapper sysRoleAclMapper;
    @Resource
    private SysUserMapper sysUserMapper;
/*    @Resource
    private SysCacheService sysCacheService;*/

    @Override
    public List<SysAcl> getCurrentUserAclList(int userId) {
        return getUserAclList(userId);
    }
    @Override
    public List<SysRole> getCurrentUserRoleList(int userId) {
        return getUserRoleList(userId);
    }
    @Override
    public List<SysAcl> getRoleAclList(int roleId) {
        List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.<Integer>newArrayList(roleId));
        if (CollectionUtils.isEmpty(aclIdList)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(aclIdList);
    }
    @Override
    public List<SysAcl> getUserAclList(int userId) {
        if (isSuperAdmin(userId)) {
            return sysAclMapper.getAll();
        }
        List<Integer> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleIdList)) {
            return Lists.newArrayList();
        }
        List<Integer> userAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(userRoleIdList);
        if (CollectionUtils.isEmpty(userAclIdList)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(userAclIdList);
    }

    @Override
    public List<SysRole> getUserRoleList(int userId) {
        if (isSuperAdmin(userId)) {
            return sysRoleMapper.getAll(null,null);
        }
        List<Integer> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleIdList)) {
            return Lists.newArrayList();
        }
        return sysRoleMapper.getByIdList(userRoleIdList);
    }

    public boolean isSuperAdmin(int id) {
        // 这里是我自己定义了一个假的超级管理员规则，实际中要根据项目进行修改
        // 可以是配置文件获取，可以指定某个用户，也可以指定某个角色
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
        if (sysUser.getUsername().equals("黄铭")) {
            return true;
        }
        return false;
    }
    @Override
   public boolean hasPermission(String url,int userId) {
        if (isSuperAdmin(userId)) {
            return true;
        }
        List<SysAcl> aclList =this.getUserAclList(userId);
        if (CollectionUtils.isEmpty(aclList)) {
            return false;
        }
        boolean hasValidAcl = false;
        // 规则：只要有一个权限点有权限，那么我们就认为有访问权限
        for (SysAcl acl : aclList) {
            // 判断一个用户是否具有某个权限点的访问权限
            if (acl == null || acl.getStatus() != 1) { // 权限点无效
                continue;
            }
            if (url.equals(acl.getUrl())) {
                hasValidAcl = true;
                break;
            }
        }
        return hasValidAcl;
    }
}
