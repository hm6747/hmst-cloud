package com.syscloud.provider.user.service;


import com.syscloud.provider.user.model.dto.AclModuleLevelDto;
import com.syscloud.provider.user.model.dto.SysDeptDto;
import com.syscloud.provider.user.model.dto.SysRoleDto;

import java.util.List;

/**
 * Created by hm on 2017/12/23.
 */
public interface SysTreesService {
    public List<SysDeptDto> deptTree();
    public List<AclModuleLevelDto> aclModuleTree();
    public List<AclModuleLevelDto> roleTree(int roleId);
    public List<SysRoleDto> roleUserTree(int selectUserId, int userId);
}
