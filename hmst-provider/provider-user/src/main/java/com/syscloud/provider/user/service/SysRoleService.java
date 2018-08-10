package com.syscloud.provider.user.service;


import com.syscloud.exception.ParamException;
import com.syscloud.provider.user.model.param.RoleParam;
import com.syscloud.provider.user.model.query.PageQuery;
import com.syscloud.provider.user.model.query.PageResult;
import com.syscloud.provider.user.model.vo.SysRole;
import com.syscloud.provider.user.model.vo.SysUser;

import java.util.List;

/**
 * Created by hm on 2017/12/27.
 */
public interface SysRoleService {
    public void save(RoleParam param) throws ParamException;
    public void update(RoleParam param) throws ParamException;
    public PageResult<SysRole> getAll(String keyword, PageQuery pageQuery)throws  ParamException;
    public List<SysRole> getRoleListByUserId(int userId);
    public List<SysRole> getRoleListByAclId(int aclId);
    public List<SysUser> getUserListByRoleList(List<SysRole> roleList);
}
