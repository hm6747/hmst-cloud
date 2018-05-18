package com.syscloud.provider.service;


import com.syscloud.provider.model.vo.SysAcl;
import com.syscloud.provider.model.vo.SysRole;

import java.util.List;

/**
 * Created by hm on 2017/12/28.
 */
public interface SysCoreService {
    public List<SysAcl> getCurrentUserAclList(int userId);
    public List<SysRole> getCurrentUserRoleList(int userId);
    public List<SysAcl> getRoleAclList(int roleId);
    public List<SysAcl> getUserAclList(int userId);
    public List<SysRole> getUserRoleList(int userId);
}
