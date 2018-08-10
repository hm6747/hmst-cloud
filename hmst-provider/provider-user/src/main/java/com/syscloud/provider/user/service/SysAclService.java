package com.syscloud.provider.user.service;


import com.syscloud.exception.ParamException;
import com.syscloud.provider.user.model.param.AclParam;
import com.syscloud.provider.user.model.query.PageQuery;
import com.syscloud.provider.user.model.query.PageResult;
import com.syscloud.provider.user.model.vo.SysAcl;

/**
 * Created by hm on 2017/12/27.
 */
public interface SysAclService {
    public void save(AclParam param) throws ParamException;
    public void update(AclParam para) throws  ParamException;
    public PageResult<SysAcl> getPageByAclModuleId(int aclModuleId, PageQuery page, String keyword) throws  ParamException;
}
