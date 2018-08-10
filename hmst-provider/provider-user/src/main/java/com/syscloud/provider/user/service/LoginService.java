package com.syscloud.provider.user.service;


import com.syscloud.exception.ParamException;
import com.syscloud.provider.user.model.dto.LoginUserDto;
import com.syscloud.provider.user.model.vo.LoginUser;
import com.syscloud.provider.user.model.vo.SysUser;

/**
 * Created by Administrator on 2018/3/21 0021.
 */
public interface LoginService {
     LoginUser LoginByUserNameAndPassword(String UserName, String password, Integer type) throws ParamException;
     LoginUserDto Login(SysUser user, LoginUser loginUser) throws  ParamException;
}
