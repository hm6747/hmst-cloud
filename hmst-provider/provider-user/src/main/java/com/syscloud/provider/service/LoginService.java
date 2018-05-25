package com.syscloud.provider.service;


import com.syscloud.exception.ParamException;
import com.syscloud.provider.model.dto.LoginUserDto;
import com.syscloud.provider.model.vo.LoginUser;
import com.syscloud.provider.model.vo.SysUser;

/**
 * Created by Administrator on 2018/3/21 0021.
 */
public interface LoginService {
     LoginUser LoginByUserNameAndPassword(String UserName, String password, Integer type) throws ParamException;
     LoginUserDto Login(SysUser user, LoginUser loginUser) throws  ParamException;
}
