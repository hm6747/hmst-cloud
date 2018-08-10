package com.syscloud.provider.user.service;


import com.syscloud.exception.ParamException;
import com.syscloud.provider.user.model.param.UserParam;
import com.syscloud.provider.user.model.query.PageQuery;
import com.syscloud.provider.user.model.query.PageResult;
import com.syscloud.provider.user.model.vo.SysUser;

/**
 * Created by hm on 2017/12/25.
 */
public interface SysUserService {
    public void save(UserParam param) throws ParamException;
    public void update(UserParam param) throws  ParamException;
    public SysUser findByKeyword(String username);
    SysUser findById(Integer id);
    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery page, String keyword) throws  ParamException;
    }
