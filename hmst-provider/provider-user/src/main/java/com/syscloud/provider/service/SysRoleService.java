package com.syscloud.provider.service;


import com.syscloud.exception.ParamException;
import com.syscloud.provider.model.param.RoleParam;
import com.syscloud.provider.model.query.PageQuery;
import com.syscloud.provider.model.query.PageResult;
import com.syscloud.provider.model.vo.SysRole;
import com.syscloud.provider.model.vo.SysUser;

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
