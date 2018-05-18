package com.syscloud.provider.service;

import java.util.List;

/**
 * Created by Administrator on 2018/3/15 0015.
 */
public interface SysRoleAclService {
    public void changeRoleAcls(Integer roleId, List<Integer> aclIds);
}

