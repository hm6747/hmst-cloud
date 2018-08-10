package com.syscloud.provider.user.service;


import com.syscloud.exception.ParamException;
import com.syscloud.provider.user.model.param.DeptParam;

/**
 * Created by hm on 2017/12/23.
 */
public interface SysDeptService {
    public  void save(DeptParam param) throws ParamException;
    public  void upDateDept(DeptParam param) throws ParamException;
}
